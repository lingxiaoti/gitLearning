$(function(){
    //对页面中的元素进行抽取.
    //方法太凌乱,希望统一管理
    //按钮在JS统一进行监听
    var supplierDatagrid,supplierEditBtnAndQuitBtn,supplierDialog,supplierForm,supplierSearchBtn;
    supplierDatagrid = $("#supplier_datagrid");
    supplierEditBtnAndQuitBtn = $("#supplier_editBtn,#supplier_quitBtn");
    supplierDialog = $("#supplier_dialog");
    supplierForm = $("#supplier_form");
    supplierSearchBtn = $("#searchBtn");
    //数据表格
    supplierDatagrid.datagrid({
        fit:true,
        rownumbers:true,
        singleSelect:true,
        pagination:true,
        url:'/supplier/list',
        fitColumns:true,
        toolbar:'#supplier_datagrid_tb',
        columns:[
            [
                {field:'name',align:'center',width:10,title:'供应商'},
                {field:'debt',align:'center',width:10,title:'应付欠款'},
                {field:'refund',align:'center',width:10,title:'应收退款'},
                {field:'linkman',align:'center',width:10,title:'联系人'},
                {field:'phone',align:'center',width:10,title:'联系电话'},
                {field:'inputTime',align:'center',width:10,title:'录入时间'},
                {field:'operator',align:'center',width:10,formatter:operatorFormatter,title:'操作人员'}
            ]
        ],
        onClickRow:function(rowIndex,rowData){
            //判断当前记录中的状态的值.
            if(rowData.state==1){
                //员工已经离职了,编辑和离职按钮应该变灰.
                supplierEditBtnAndQuitBtn.linkbutton("disable");
            }else{
                //启用按钮
                supplierEditBtnAndQuitBtn.linkbutton("enable");
            }
        }
    });
    //对话框
    supplierDialog.dialog({
        width:250,
        height:380,
        buttons:'#supplier_dialog_bt',
        closed:true
    });
    supplierSearchBtn.textbox({
        width:230,
        label:"关键字:",
        labelWidth:50,
        prompt:"请输入搜索关键字",
        buttonText:'搜索',
        buttonIcon:'icon-search',
        onClickButton:function(){
            var keyword = $(this).val();
            supplierDatagrid.datagrid("load",{
                keyword:keyword
            });
        }
    });

    //对按钮进行统一事件监听
    $("a[data-cmd]").on("click",function(){
        var cmd = $(this).data("cmd");
        if(cmd){
            cmdObj[cmd]();
        }
    });

    //方法统一管理起来]
    var cmdObj = {
        add:function(){
            //1.清空表单数据
            supplierForm.form("clear");
            //2.设置对话框的标题
            supplierDialog.dialog("setTitle","新增");
            //3.打开对话框
            supplierDialog.dialog("open");
        },
        edit:function(){
            var rowData = supplierDatagrid.datagrid("getSelected");
            if(rowData){
                //1.清空表单数据
                supplierForm.form("clear");
                //2.设置对话框的标题
                supplierDialog.dialog("setTitle","新增");
                //3.打开对话框
                supplierDialog.dialog("open");
                //特殊数据的处理
                if(rowData.dept)
                    rowData["dept.id"] = rowData.dept.id;
                //4.回显数据
                supplierForm.form("load",rowData);//基于同名匹配规则
                //回显角色信息.
                //[1,3]----->List<Long>
                $.post("/role/queryRoleIdListForEmployeeForm?supplierId="+rowData.id,function(data){
                    $("#roleId").combobox("setValues",data);
                },"json");

            }else{
                $.messager.alert("温馨提示","请选择一条需要修改的数据.","warning");
            }

        },
        quit:function(){
            var rowData = supplierDatagrid.datagrid("getSelected");
            if(rowData){
                $.messager.confirm("温馨提示","您确定需要离职该员工吗?",function(yes){
                    if(yes){
                        $.get("/supplier/quit?id="+rowData.id,function(data){
                            if(data.success){
                                supplierDatagrid.datagrid("reload");
                                $.messager.alert("温馨提示",data.msg,"info");
                            }else{
                                $.messager.alert("温馨提示",data.msg,"error");
                            }
                        },"json")
                    }
                });
            }else{
                $.messager.alert("温馨提示","请选择需要离职的员工记录.","warning");
            }
        },
        reload:function(){
            //刷新数据表格
            supplierDatagrid.datagrid("reload");
        },
        save:function(){
            var url;
            var idVal = $("[name='id']").val();
            if(idVal){
                url = "/supplier/update";
            }else{
                url = "/supplier/save";
            }
            supplierForm.form("submit",{
                url:url,
                onSubmit:function(param){
                    //获取所有的角色信息
                    var roleIds = $("#roleId").combobox("getValues");
                    //把角色信息放入到表单中
                    for(var i=0;i<roleIds.length;i++){
                        param["roles["+i+"].id"] = roleIds[i];
                    }
                    return true;
                },
                success:function(data){
                    data = $.parseJSON(data);
                    if(data.success){
                        //提示消息,当点确定的时候,关闭对话框,刷新数据表格
                        $.messager.alert("温馨提示",data.msg,"info",function(){
                            supplierDialog.dialog("close");
                            supplierDatagrid.datagrid("reload");
                        });
                    }else{
                        $.messager.alert("温馨提示",data.msg,"error");
                    }
                }
            });
        },
        cancel:function(){
            supplierDialog.dialog("close");
        }
    }
});

function operatorFormatter(value,record,index){
    if(value){
        return value.name;
    }
    return value;
}
