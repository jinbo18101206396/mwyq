layui.use(['table', 'admin','laydate', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 管理
     */
    var Twitter = {
        tableId: "twitterTable"
    };

    /**
     * 初始化表格的列
     */
    Twitter.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', hide: true, title: ''},
            {field: 'content',  align: "center",sort: true, title: '推特内容',minWidth: 450,templet:function (d) {
                    return '<div style="text-align: left"><a style="color: #01AAED;">'+d.content+'</a></div>';
            }},
            {field: 'name',align: "center",sort: true, title: '推特作者'},
            {field: 'lang', align: "center", sort: true, title: '语言类型', templet: function (d) {
                    if(d.lang == 'cn'){
                        return "<p>中文</p>>";
                    }else if (d.lang == 'zang') {
                        return "<p>藏文</p>";
                    } else if(d.lang == 'wei'){
                        return "<p>维吾尔文</p>";
                    }else if(d.lang == 'meng'){
                        return "<p>蒙古文</p>";
                    }else if(d.lang == 'en'){
                        return "<p>英文</p>";
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
                        return "<p style='color:red;font-weight: bold'>负向</p>";
                    } else {
                        return "<p style='font-weight: bold'></p>";
                    }
                }
            },
            {field: 'retweetCount',  align: "center",sort: true, title: '评论量'},
            {field: 'likeCount',  align: "center",sort: true, title: '点赞量'},
            {field: 'replyCount',  align: "center",sort: true, title: '转发量'},
            {field: 'time',  align: "center",sort: true, title: '发布时间'},
            {field: 'location',  align: "center",sort: true, title: '发布位置'}
        ]];
    };

    var authorCharts = echarts.init(document.getElementById('author'),myEchartsTheme);
    function loadBloggerRankData(lang,sentiment,timeLimit,name,keyword,location){
        authorCharts.showLoading();
        $.get(Feng.ctxPath + '/twitter/author/rank?top=10&lang='+lang+'&sentiment='+sentiment+'&timeLimit='+timeLimit+'&name='+name+'&keyword='+keyword+'&location='+location, function (data) {
            authorCharts.hideLoading();
            authorCharts.setOption({
                color: ['#33a3dc'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                xAxis: [
                    {
                        name:'作者',
                        type: 'category',
                        data: data.authors,
                        axisLabel: { //X轴字体倾斜设置
                            interval: 0,
                            rotate: -10 //倾斜的程度
                        }
                    }
                ],
                yAxis: [
                    {
                        name: '数量',
                        type: 'value'
                    }
                ],
                series: [
                    {
                        type: 'bar',
                        barWidth: 50,
                        data: data.twitterCount
                    }
                ]
            });
        });
    }


    function loadTwitterData(lang,sentiment,timeLimit,name,keyword,location){
        var queryData = {};
        queryData['lang'] = lang;
        queryData['sentiment'] = sentiment;
        queryData['timeLimit'] = timeLimit;
        queryData['name'] = name;
        queryData['keyword'] = keyword;
        queryData['location'] = location;
        table.reload(Twitter.tableId, {
            where: queryData, page: {curr: 1}
        });
    }

    // 初始加载数据
    loadTwitterData("","","","","","")
    loadBloggerRankData("","","","","","")

        /**
     * 点击查询按钮
     */
    Twitter.search = function () {

        var lang = $("#lang").val();
        var sentiment = $("#sentiment").val();
        var timeLimit = $("#timeLimit").val();
        var name = $("#name").val();
        var keyword = $("#keyword").val();
        var location = $("#location").val();

        loadTwitterData(lang,sentiment,timeLimit,name,keyword,location)
        loadBloggerRankData(lang,sentiment,timeLimit,name,keyword,location);
    };

    /**
     * 导出excel按钮
     */
    Twitter.exportExcel = function () {
        var checkRows = table.checkStatus(Twitter.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Twitter.tableId,
        url: Feng.ctxPath + '/twitter/list',
        page: true,
        limit:90,
        height: "full-158",
        cellMinWidth: 100,
        cols: Twitter.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Twitter.search();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Twitter.exportExcel();
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: "至",
        max: Feng.currentDate()
    });

    // 工具条点击事件
    table.on('tool(' + Twitter.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

    });
});
