layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var Toutiao = {
        tableId: "toutiaoTable"
    };

    /**
     * 初始化表格的列
     */
    Toutiao.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: '文章id'},
            {field: 'content', align: "center", sort: true, title: '头条内容', minWidth:450,templet:function (d) {
                    return '<div style="text-align: left"><a href="'+d.articleUrl+'" class="layui-table-link" target="_blank">'+d.content+'</a></div>';
                }
            },
            {field: 'authorName', sort: true, align: "center",title: '作者'},
            {field: 'lang', align: "center", sort: true, title: '语言类型', templet: function (d) {
                    if(d.lang == 'cn'){
                        return "<p>中文</p>>";
                    }else if (d.lang == 'zang') {
                        return "<p>藏文</p>";
                    } else if(d.lang == 'wei'){
                        return "<p>维吾尔文</p>";
                    }else if(d.lang == 'meng'){
                        return "<p>蒙古文</p>";
                    }else{
                        return "<p></p>";
                    }
                }},
            {field: 'sentiment', align: "center", sort: true, title: '情感类型', templet: function (d) {
                    if (d.sentiment === '3') {
                        return "<p style='color:green;font-weight: bold'>正向</p>";
                    } else if (d.sentiment === '1') {
                        return "<p style='color:blue;font-weight: bold'>中性</p>";
                    } else if (d.sentiment === '2') {
                        return "<p style='color:red;font-weight: bold'>敏感</p>";
                    } else {
                        return "<p style='font-weight: bold'></p>";
                    }
                }
            },
            {field: 'keyword', sort: true, align: "center",title: '关键词'},
            {field: 'description', sort: true, align: "center",title: '摘要'},
            {field: 'createTime', sort: true, align: "center",title: '发布时间'},
            {field: 'likeCount', sort: true, align: "center",title: '点赞量'},
            {field: 'commentCount', sort: true, align: "center",title: '评论量'},
        ]];
    };

    /**
     * 点击查询按钮
     */
    Toutiao.search = function () {
        var queryData = {};
        queryData['authorName'] = $("#authorName").val();
        queryData['lang'] = $("#lang").val();
        queryData['sentiment'] = $("#sentiment").val();
        queryData['keyword'] = $("#keyword").val();
        table.reload(Toutiao.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    Toutiao.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/toutiao/add',
            tableId: Toutiao.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    Toutiao.openEditDlg = function (data) {
        func.open({
            title: '修改',
            content: Feng.ctxPath + '/toutiao/edit?id=' + data.id,
            tableId: Toutiao.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    Toutiao.exportExcel = function () {
        var checkRows = table.checkStatus(Toutiao.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Toutiao.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/toutiao/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Toutiao.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Toutiao.tableId,
        url: Feng.ctxPath + '/toutiao/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Toutiao.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Toutiao.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Toutiao.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Toutiao.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Toutiao.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Toutiao.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Toutiao.onDeleteItem(data);
        }
    });
});
