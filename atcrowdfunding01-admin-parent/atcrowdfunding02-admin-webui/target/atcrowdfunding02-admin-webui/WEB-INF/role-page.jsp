<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowdJs/my-role.js"></script>
<script type="text/javascript">
    $(function () {
        //1.初始化数据
        window.pageSize = 5;
        window.pageNum = 0;
        window.keyWord = "";
        //2.执行分页函数
        generatePage();

        $("#searchBtn").click(function () {
            window.keyWord = $("#keyWordInput").val();
            generatePage();
        });

        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        })
        //添加新角色
        $("#saveRoleBtn").click(function () {
            var roleName = $.trim($("#addModal [name=roleName]").val());
            $.ajax({
                url: "role/save.json",
                data: {
                    name: roleName
                },
                dataType: "json",
                success: function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        window.pageNum = 99999;
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败!" + response.operationMessage);
                    }

                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            })
            $("#addModal").modal("hide");
            $("#addModal [name=roleName]").val("");
        })
        //点击修改按钮的回显操作
        $("#rolePageBody").on("click", ".pencilBtn", function () {
            $("#editModal").modal("show");
            var roleName = $(this).parent().prev().text();
            window.roleId = this.id;
            $("#editModal [name=roleName]").val(roleName);
        })
        //修改角色信息的ajax操作
        $("#updateRoleBtn").click(function () {
            var roleName = $("#editModal [name=roleName]").val();
            console.log(roleName)
            console.log(window.roleId)
            $.ajax({
                url: "role/update.json",
                type: "post",
                data: {
                    id: window.roleId,
                    name: roleName
                },
                dataType: "json",
                success: function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        // 重新加载分页数据
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.operationMessage);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            })
            $("#editModal").modal("hide");

        })
        //删除角色操作
        $("#removeRoleBtn").click(function () {
            var requestBody = JSON.stringify(window.roleIdArray);
            $.ajax({
                url: "role/remove/by/role/id/array.json",
                type: "post",
                contentType: "application/json;charset=utf-8",
                data: requestBody,
                dataType: "json",
                success: function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                        generatePage();
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.operationMessage);
                    }
                },
                error: function (response) {
                    layer.msg(response.status + "" + response.statusText);
                }
            })
            $("#confirmModal").modal("hide");
            $("#summaryBox").prop("checked", false);
        })
        //单选删除操作
        $("#rolePageBody").on("click", ".removeBtn", function () {
            var roleName = $(this).parent().prev().text();
            var roleId = this.id;
            var roleArray = [{
                roleId: roleId,
                roleName: roleName
            }];
            showConfirmModal(roleArray);
        });
        //设置全选
        $("#summaryBox").click(function () {
            var checkStatus = this.checked;
            $(".itemBox").prop("checked", checkStatus);
        })
        $("#rolePageBody").on("click", ".itemBox", function () {
            var checkedBoxCount = $(".itemBox:checked").length;
            var totalBoxCount = $(".itemBox").length;
            $("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount);
        })
        //多条删除
        $("#batchRemoveBtn").click(function () {
            var roleArray = [];
            $(".itemBox:checked").each(function () {
                var roleId = this.id;
                var roleName = $(this).parent().next().text();
                roleArray.push({
                    roleId: roleId,
                    roleName: roleName
                });
                console.log(roleArray)

            });
            console.log(roleArray)
            showConfirmModal(roleArray);
        })
        $("#rolePageBody").on("click", ".checkBtn", function () {
            window.roleId = this.id;
            $("#assignModal").modal("show");
            fillAuthTree();
        })
        $("#assignBtn").click(function () {
            //设置存放authId的数组
            var authIdList = [];
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
            var zTreeNodes = zTreeObj.getCheckedNodes();
            //遍历zTreeNodes获取authId
            for (var i = 0; i < zTreeNodes.length; i++) {
                var authId = zTreeNodes[i].id;
                authIdList.push(authId);
            }

            var requsetBody = {
                authIdList: authIdList,
                roleId: [window.roleId]
            }
            //进行权限分配
            $.ajax({
                url: "assign/do/role/assign/auth.json",
                type: "post",
                data: JSON.stringify(requsetBody),
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                success: function (response) {
                    var result = response.operationResult;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");
                    }
                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.operationMessage);
                    }
                },
                error: function (response) {
                    layer.msg(response.status+" "+response.statusText);
                }
            })
            $("#assignModal").modal("hide");
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
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keyWordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <div id="Pagination" class="pagination">
                                        </div>
                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-role-add.jsp" %>
<%@include file="modal-role-edit.jsp" %>
<%@include file="modal-role-confirm.jsp" %>
<%@include file="modal-role-assign-auth.jsp" %>
</body>
</html>