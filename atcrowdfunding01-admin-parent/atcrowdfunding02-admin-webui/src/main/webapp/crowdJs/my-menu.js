function generateMenu() {
    //树节点json数据
    $.ajax({
        url: "menu/get/whole/tree.json",
        type: "post",
        dataType: "json",
        success: function (response) {
            var result = response.operationResult;
            if (result == "SUCCESS") {
                console.log(response)
                //存放配置的地方
                var setting = {
                    view: {
                        "addDiyDom": myAddDiyDom,
                        "addHoverDom": myAddHoverDom,
                        "removeHoverDom": myRemoveHoverDom
                    },
                    data:{
                        key:{
                            url:""
                        }
                    }
                };
                var zNodes = response.queryData;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes)
            }
            if (result == "FIALED") {
                layer.msg(response.message);
            }
        }
    })
}

function myRemoveHoverDom(treeId, treeNode) {
    var btnGroupId = treeNode.tId + "_btnGrp";
    $("#" + btnGroupId).remove();
}

function myAddHoverDom(treeId, treeNode) {
    var btnGroupId = treeNode.tId + "_btnGrp";
    if ($("#" + btnGroupId).length > 0) {
        return;
    }
    // 准备各个按钮的 HTML 标签
    var addBtn = "<a id='" + treeNode.id + "' class='btn btn-info dropdown-toggle btn-xs addBtn' style='margin-left:10px;padding-top:0px;' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='" + treeNode.id + "' class='btn btn-info dropdown-toggle btn-xs removeBtn' style='margin-left:10px;padding-top:0px;' title=' 删 除 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='" + treeNode.id + "' class='btn btn-info dropdown-toggle btn-xs editBtn' style='margin-left:10px;padding-top:0px;' title=' 修 改 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    var level = treeNode.level;
    if (level == 0) {
        // 级别为 0 时是根节点，只能添加子节点
        btnHTML = addBtn;
    }
    if (level == 1) {
        // 级别为 1 时是分支节点，可以添加子节点、修改
        btnHTML = addBtn + " " + editBtn;
        // 获取当前节点的子节点数量
        var length = treeNode.children.length;
        // 如果没有子节点，可以删除
        if (length == 0) {
            btnHTML = btnHTML + " " + removeBtn;
        }
    }
    if (level == 2) {
        // 级别为 2 时是叶子节点，可以修改、删除
        btnHTML = editBtn + " " + removeBtn;
    }
    // 找到附着按钮组的超链接
    var anchorId = treeNode.tId + "_a";
    $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHTML + "</span>");

}

function myAddDiyDom(treeId, treeNode) {
    console.log(treeId)
    console.log(treeNode)
    //找到span的id
    var spanId = treeNode.tId + "_ico";
    $("#" + spanId).removeClass().addClass(treeNode.icon)
}