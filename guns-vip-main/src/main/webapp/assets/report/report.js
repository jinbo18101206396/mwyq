layui.use(['table', 'ax', 'treetable','laydate', 'func', 'layer', 'element'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var func = layui.func;
    var layer = layui.layer;
    var element = layui.element;

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    $('#download').click(function () {
        // $(location).attr('href', '/report/download');
    });
});
