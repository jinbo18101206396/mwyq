layui.use(['table', 'ax', 'func', 'layer', 'element','form','carousel'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;
    var layer = layui.layer;
    var element = layui.element;
    var form = layui.form;
    var laydate = layui.laydate;

    var weiboId = $("#weiboId").val();

    //获取新闻内容、译文内容、敏感词、关键词
    var ajax = new $ax(Feng.ctxPath + '/retrieval/weibo/translate/es?id='+weiboId);
    var result = ajax.start();

    console.log(result.data)


    $("#weiboContent").html(result.data.content);
    $("#transContent").html(result.data.translateContent);
});
