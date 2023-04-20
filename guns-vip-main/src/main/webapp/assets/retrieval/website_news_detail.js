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
    var keyWords = $("#keyWords").val();
    var langType = $("#langType").val();
    var newsTime = $("#newsTime").val();
    var newsUrl = $("#newsUrl").val();
    var newsTitle = $("#newsTitle").val();
    var content = $("#content").val();

    //获取新闻内容、译文内容、敏感词、关键词
    var ajax = new $ax(Feng.ctxPath + '/retrieval/news/translate/es?newsId='+newsId+'&keyWords='+keyWords+'&langType='+langType+'&newsTime='+newsTime+'&newsUrl='+newsUrl+'&newsTitle='+newsTitle+'&newsContent='+content);
    var result = ajax.start();

    $("#newsContent").html(result.data.newsContent);
    $("#translateContent").html(result.data.translateContent);
});
