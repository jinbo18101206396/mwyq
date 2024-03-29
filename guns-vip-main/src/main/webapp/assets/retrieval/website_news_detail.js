layui.use(['table', 'ax', 'func', 'layer', 'element','form','carousel'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;
    var layer = layui.layer;
    var element = layui.element;
    var form = layui.form;
    var laydate = layui.laydate;

    var newsId = $("#newsId").val();

    //获取新闻内容、译文内容、敏感词、关键词
    var ajax = new $ax(Feng.ctxPath + '/retrieval/news/translate/es?newsId='+newsId);
    var result = ajax.start();

    console.log(result.data)


    $("#newsContent").html(result.data.newsContent);
    $("#translateContent").html(result.data.translateContent);
});
