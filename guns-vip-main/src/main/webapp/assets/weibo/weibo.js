layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var Weibo = {
        tableId: "weiboTable"
    };

    /**
     * 初始化表格的列
     */
    Weibo.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: ''},
            {field: 'authorId', sort: true, title: '博主'},
            {field: 'content', sort: true, title: '微博内容'},
            {field: 'readCount', sort: true, title: '阅读量'},
            {field: 'commentCount', sort: true, title: '评论量'},
            {field: 'likeCount', sort: true, title: '点赞量'},
            {field: 'transmitCount', sort: true, title: '转发量'},
            {field: 'contentType', sort: true, title: '内容类型'},
            {field: 'deviceType', sort: true, title: '设备类型'},
            {field: 'sentiment', sort: true, title: '情感类型'},
            {field: 'createTime', sort: true, title: '创建时间'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Weibo.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(Weibo.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Weibo.tableId,
        url: Feng.ctxPath + '/weibo/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Weibo.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Weibo.search();
    });

    // 工具条点击事件
    table.on('tool(' + Weibo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
    });
});
