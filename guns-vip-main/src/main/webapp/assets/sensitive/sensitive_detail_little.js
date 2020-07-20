/**
 * 详情对话框
 */
var SensitiveInfoDlg = {
    data: {
        newsContent: "",
        sensitiveWords: ""
    }
};

layui.use(['form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/news/dialog/detail?newsId=" + Feng.getUrlParam("newsId"));
    var result = ajax.start();
    //新闻内容
    var newsContent = result.data.newsContent;
    $("#newsContent").html(newsContent);

    //敏感词
    var sensitiveWords = result.data.sensitiveWords;
    $("#sensitiveWords").html(sensitiveWords);

    //关键词
    var keyWords = result.data.keyWords;
    $("#keyWords").html(keyWords);

    //译文内容
    var newsTranslate = result.data.newsTranslate;
    $("#newsTranslate").html(newsTranslate);

});

