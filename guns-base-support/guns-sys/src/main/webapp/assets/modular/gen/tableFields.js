layui.use(['layer', 'ax', 'form', 'laydate', 'element', 'table'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var form = layui.form;
    var laydate = layui.laydate;
    var element = layui.element;
    var table = layui.table;

    table.render({
        elem: '#fieldTable',
        url: Feng.ctxPath + '/getTableFields?tableName=' + $("#tableName").val() + "&dbId=" + $("#dbId").val(),
        page: false,
        cellMinWidth: 100,
        cols: [[
            {type: 'checkbox'},
            {field: 'columnName', title: '字段名'},
            {field: 'columnComment', title: '字段注释'}
        ]]
    });

    //点击提交时
    $('#submit').click(function () {
        var checkStatus = table.checkStatus('fieldTable');
        var columnNames = "";
        for (var tableItem in checkStatus.data) {
            columnNames += "CAT" + checkStatus.data[tableItem].columnName;
        }

        var ajax = new $ax(Feng.ctxPath + "/setConditionFields", function (data) {
            Feng.success("设置成功！");
            parent.layer.close(parent.layer.getFrameIndex(window.name))
        }, function (data) {
            Feng.error("设置失败！");
        });
        ajax.set("tableName", $("#tableName").val());
        ajax.set("fields", columnNames);
        ajax.start();

    });

})
;
