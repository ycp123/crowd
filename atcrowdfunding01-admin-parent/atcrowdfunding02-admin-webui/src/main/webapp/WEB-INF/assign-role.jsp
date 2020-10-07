<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@include file="include-head.jsp" %>
<script type="text/javascript">
    $(function () {
        $("#toRightBtn").click(function () {
            $("select:eq(0)>option:selected").appendTo($("select:eq(1)"));
        })
        $("#toLeftBtn").click(function () {
            $("select:eq(1)>option:selected").appendTo($("select:eq(0)"));
        })
        $("#submitBtn").click(function () {
            $("select:eq(1)>option").prop("selected","selected");
        })
    })
</script>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="admin/get/page.html">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="assign/do/role/assign.html" method="post" role="form" class="form-inline">
                        <input type="hidden" name="pageNum" value="${param.pageNum}">
                        <input type="hidden" name="adminId" value="${param.adminId}">
                        <input type="hidden" name="keyWord" value="${param.keyWord}">
                        <div class="form-group">
                            <label>未分配角色列表</label><br>
                            <select class="form-control" multiple="" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach var="role" items="${requestScope.unAssignedRoleList}">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="toRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="toLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label>已分配角色列表</label><br>
                            <select name="roleIdList" class="form-control" multiple="" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach var="role" items="${requestScope.assignedRoleList}">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button id="submitBtn" type="submit" class="btn btn-lg btn-success btn-block" style="width: 150px;margin: 0 auto 0 auto"> 保存</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>