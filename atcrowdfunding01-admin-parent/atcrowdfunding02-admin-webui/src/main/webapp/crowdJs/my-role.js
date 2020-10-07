function fillAuthTree() {
    var ajaxReturn = $.ajax({
        url: "assign/get/all/auth.json",
        type: "post",
        dataType: "json",
        async: false
    })
    if (ajaxReturn.status != 200) {
        layer.msg(" 请 求 处 理 出 错 ！ 响 应 状 态 码 是 ： " + ajaxReturn.status + " 说 明 是 ： " + ajaxReturn.statusText);
        return;
    }

    var authList = ajaxReturn.responseJSON.queryData;
    var setting = {
        data: {
            simpleData: {
                enable: "true",
                pIdKey: "categoryId"
            },
            key: {
                // 使用 title 属性显示节点名称，不用默认的 name 作为属性名了
                name: "title"
            }
        },
        check: {
            enable: true
        }
    }
    //生成树型结构
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    zTreeObj.expandAll(true);

    ajaxReturn = $.ajax({
        url: "assign/get/assigned/auth/id/by/role/id.json",
        type: "post",
        data: {
            roleId: window.roleId
        },
        dataType: "json",
        async: false
    })
    if (ajaxReturn.status != 200) {
        layer.msg(" 请 求 处 理 出 错 ！ 响 应 状 态 码 是 ： " + ajaxReturn.status + " 说 明 是 ： " + ajaxReturn.statusText);
        return;
    }
    //获取auth_id数组
    var authIdArray = ajaxReturn.responseJSON.queryData;

    //遍历
    for (var i = 0; i < authIdArray.length; i++) {
        var authId = authIdArray[i];
        var zTreeNode = zTreeObj.getNodeByParam("id", authId);
        var checked = true;
        var checkTypeFlag = false;
        zTreeObj.checkNode(zTreeNode, checked, checkTypeFlag);
    }


}

function showConfirmModal(roleArray) {
    console.log(roleArray)
    //打开
    $("#confirmModal").modal("show");
    //清除roleNameDiv中的数据
    $("#roleNameDiv").empty();
    //声明全局变量数组存储roleId
    window.roleIdArray = [];
    //遍历数组
    for (var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName + "<br/>");
        var roleId = role.roleId;
        window.roleIdArray.push(roleId);
    }
}

function generatePage() {
    //1.获取后端数据
    var pageInfo = getPageInfoRemote();
    //2.将数据填充到表格
    fillTableBody(pageInfo);
}

function getPageInfoRemote() {
    var ajaxResult = $.ajax({
            url: "role/get/page/info.json",
            type: "post",
            data: {
                "pageNum": window.pageNum,
                "pageSize": window.pageSize,
                "keyWord": window.keyWord
            },
            dataType: "json",
            async: false
        }
    )
    console.log(ajaxResult);
    var statusCode = ajaxResult.status;
    if (statusCode != 200) {
        layer.msg("失败！响应状态码=" + statusCode + " 说明信息=" + ajaxResult.statusText);
        return null;
    }
    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.operationResult;
    if (result == "FAILED") {
        layer.msg(resultEntity.operationMessage);
        return null;
    }
    var pageInfo = resultEntity.queryData;
    return pageInfo;
}

function fillTableBody(pageInfo) {
    $("#rolePageBody").empty();
    $("#Pagination").empty();
    //判断pageInfo中是否有数据
    if (pageInfo == null || pageInfo.list.length == 0 || pageInfo == undefined || pageInfo.list == null) {
        var info = "<tr><td colspan='4' align='center'>抱歉！没有查询到您搜 索的数据！</td></tr>";
        $("#rolePageBody").append(info);
        return;
    }
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='" + roleId + "' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        var checkBtn = "<button type='button' id='" + roleId + "' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button type='button' id='" + roleId + "' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button type='button' id='" + roleId + "' class='btin btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";
        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);
}

function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;
    // 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    }
    // 调用 pagination()函数
    $("#Pagination").pagination(totalRecord, properties);
}

function paginationCallBack(pageIndex, jQuery) {
    // 修改 window 对象的 pageNum 属性
    window.pageNum = pageIndex + 1;
    // 调用分页函数
    generatePage();
    //取消全选选中状态
    $("#summaryBox").prop("checked", false);
    // 取消页码超链接的默认行为
    return false;

}