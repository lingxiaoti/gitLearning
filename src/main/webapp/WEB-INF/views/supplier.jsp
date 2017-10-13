<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>供应商管理</title>
    <%@include file="common.jsp"%>
    <link rel="stylesheet" href="/static/js/bootstrap/css/bootstrap.min.css">
    <script src="/static/js/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/js/views/supplier.js"></script>
</head>
<body>
<table id="supplier_datagrid" class="table table-hover"></table>
<!-- 定义对话框 -->
<div id="supplier_dialog">
    <form id="supplier_form" method="post">
        <input type="hidden" name="id">
        <div align="center" style="margin-top: 10px;" >

            <div>
                <input type="text" name="name" class="easyui-textbox" data-options="label:'用户名:',labelPosition:'top', width:150">
            </div>
            <div>
                <input type="text" name="联系人" class="easyui-textbox" data-options="label:'真实姓名:',labelPosition:'top', width:150">
            </div>
            <div>
                <input type="text" name="联系电话" class="easyui-textbox" data-options="label:'联系方式:',labelPosition:'top', width:150">
            </div>
            <div>
                <input type="text" name="QQNumber" class="easyui-textbox" data-options="label:'邮箱:',labelPosition:'top', width:150">
            </div>
            <div>
                <select  name="dept.id" class="easyui-combobox"
                         data-options="
						 url:'/department/selectListForEmployeeForm',
						 width:150,
						 label:'所属部门:',
						 labelPosition:'top',
						 valueField:'id',
						 textField:'name',
						  value:'-1'
						">
                </select>
            </div>
            <div>
                <select  id="roleId" class="easyui-combobox"
                         data-options="
						 width:150,
						 label:'角色:',
						 labelPosition:'top',
						 multiple:true,
						 valueField:'id',
						 textField:'name',
						 url:'/role/selectListForEmployeeForm'
						">
                </select>
            </div>
        </div>
    </form>
</div>
<!-- 定义顶部按钮 -->
<div id="supplier_datagrid_tb">
    <div>
        <shiro:hasPermission name="supplier:save">
            <a class="easyui-linkbutton" iconCls="icon-add" plain="true" data-cmd="add">新增</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="supplier:update">
            <a id="supplier_editBtn" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  data-cmd="edit">编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="supplier:quit">
            <a id="supplier_quitBtn" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-cmd="quit">离职</a>
        </shiro:hasPermission>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" data-cmd="reload">刷新</a>
    </div>
    <div>
        <input id="searchBtn" type="text">
    </div>
</div>
<!-- 对话框底部按钮 -->
<div id="supplier_dialog_bt">
    <a class="easyui-linkbutton" iconCls="icon-save" plain="true" data-cmd="save">保存</a>
    <a class="easyui-linkbutton" iconCls="icon-cancel" plain="true" data-cmd="cancel">取消</a>
</div>
</body>
</html>
