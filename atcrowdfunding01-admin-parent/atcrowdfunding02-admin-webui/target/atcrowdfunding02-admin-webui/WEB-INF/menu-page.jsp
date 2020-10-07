<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowdJs/my-menu.js"></script>
<script type="text/javascript">
    $(function () {
        generateMenu();
        //点击按钮打开模态框
        $("#treeDemo").on("click", ".addBtn", function () {
            window.pid = this.id;
            $("#menuAddModal").modal("show");
        })
        $("#treeDemo").on("click", ".removeBtn", function () {
            window.id = this.id;
            $("#menuConfirmModal").modal("show");
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var key = "id";
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key, value);
            $("#removeNodeSpan").html(" 【 <i class='" + currentNode.icon + "'></i>" + currentNode.name + "】");

        })
        $("#treeDemo").on("click", ".editBtn", function () {
            window.id = this.id;
            $("#menuEditModal").modal("show");
            //回显操作
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var key = "id";
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key, value);
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);
        })

        //添加节点保存操作
        $("#menuSaveBtn").click(function () {
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $("#menuAddModal [name=icon]:checked").val();
            $.ajax({
                url: "menu/save.json",
                type: "post",
                data: {
                    pid: window.pid,
                    name: name,
                    url: url,
                    icon: icon
                },
                dataType: "json",
                success: function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        generateMenu();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.operationMessage);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            })
            $("#menuAddModal").modal("hide");
            $("#menuResetBtn").click()
        })
        //修改节点保存操作
        $("#menuEditBtn").click(function () {
            var name = $.trim($("#menuEditModal [name=name]").val());
            var url = $.trim($("#menuEditModal [name=url]").val());
            var icon = $("#menuEditModal [name=icon]:checked").val();
            $.ajax({
                url: "menu/update.json",
                type: "post",
                data: {
                    id: window.id,
                    name: name,
                    url: url,
                    icon: icon
                },
                dataType: "json",
                success: function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        generateMenu();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.operationMessage);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            })
            $("#menuEditModal").modal("hide");
            $("#menuResetBtn").click();
        })
        //删除节点保存操作
        $("#confirmBtn").click(function () {
            $.ajax({
                url: "menu/remove.json",
                type: "post",
                data: {
                    id: window.id
                },
                dataType: "json",
                success: function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        generateMenu();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.operationMessage);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            })
            $("#menuConfirmModal").modal("hide");
        })
    })
</script>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree">

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-menu-add.jsp" %>
<%@include file="modal-menu-confirm.jsp" %>
<%@include file="modal-menu-edit.jsp" %>
</body>
</html>