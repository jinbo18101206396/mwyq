/**
 * 详情对话框
 */
var ToutiaoInfoDlg = {
    data: {
        authorId: "",
        authorName: "",
        keyword: "",
        description: "",
        content: "",
        articleUrl: "",
        createTime: "",
        likeCount: "",
        commentCount: "",
        sentiment: "",
        lang: "",
        Queryword: ""
    }
};

layui.use(['form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/toutiao/detail?id=" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('toutiaoForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/toutiao/editItem", function (data) {
            Feng.success("更新成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();

        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

});