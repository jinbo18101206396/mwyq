layui.use(['table', 'ax', 'treetable','laydate', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var func = layui.func;

    /**
     * 基础字典管理
     */
    var customWordField = {
        tableId: "customWordFieldTable"
    };

    /**
     * 初始化表格的列
     */
    customWordField.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', align: "center", hide: true, title: '新闻Id'},
            {
                field: 'title',align: "center", sort: true, title: '新闻标题',minWidth: 450,templet:function (d) {
                    return '<div style="text-align: left"><a href="'+d.newsUrl+'" class="layui-table-link" target="_blank">'+d.newsTitle+'</a></div>';
                }},
            {
                field: 'sensitive', align: "center", sort: true, title: '情感类型',minWidth: 120, templet: function (d) {
                    if(d.isSensitive === 3){
                        return "<p style='color:green;font-weight: bold'>正向</p>";
                    }else if (d.isSensitive === 1) {
                        return "<p style='color:blue;font-weight: bold'>中性</p>";
                    } else if(d.isSensitive === 2){
                        return "<p style='color:red;font-weight: bold'>敏感</p>";
                    }else{
                        return "<p style='font-weight: bold'>敏感</p>";
                    }
                }
            },
            {
                field: 'sensitiveCategory', align: "center", sort: true, title: '敏感类别',minWidth: 150, templet: function (d) {
                    if(d.sensitiveCategory === 1){
                        return "<p>国家安全</p>";
                    }else if (d.sensitiveCategory === 2) {
                        return "<p>暴恐</p>";
                    } else if(d.sensitiveCategory === 3){
                        return "<p>民生</p>";
                    }else if (d.sensitiveCategory === 4) {
                        return "<p>色情</p>";
                    } else if(d.sensitiveCategory === 5){
                        return "<p>贪腐</p>";
                    }else{
                        return "<p>其他</p>";
                    }
                }
            },
            {
                field: 'sensitiveWords',align: "center", sort: true, title: '敏感词',minWidth: 200,templet:function (d) {
                    return '<div style="text-align: left">'+d.sensitiveWords+'</div>';
                }},
            {
                field: 'keyWords',align: "center", sort: true, title: '关键词',minWidth: 170,templet:function (d) {
                    return '<div style="text-align: left">'+d.keyWords+'</div>';
                }},
            {field: 'newsCategory', align: "center", sort: true, title: '新闻类别',minWidth: 120},
            {field: 'websitename', align: "center", sort: true, title: '新闻来源',minWidth: 120},
            {field: 'newsTime', align: "center", sort: true, title: '发布时间'},
            {fixed: 'right',align: "center",toolbar: '#customWordFieldTableBar', title: '操作', width:150}
        ]];
    };

    var type = $("#type").val();

    //渲染表格
    var tableResult = table.render({
        elem: '#' + customWordField.tableId,
        url: Feng.ctxPath + '/customWord/field/list?type='+type,
        page: true,
        limit:90,
        height: "full-158",
        cellMinWidth: 100,
        cols: customWordField.initColumn(),
        initSort:{
            field:'newsTime',
            type:'desc'
        }
    });

    //初始加载情感类型数据
    loadSensitiveTypeData(type,'','','','','','','');
    //初始加载新闻来源数据
    loadNewsSourceData(type,'','','','','','','');
    //初始加载敏感类别数据
    loadSenCategoryData(type,'','','','','','','');

    //加载领域相关新闻
    function loadCustomWordFieldNewsData(type,langType,isSensitive,sensitiveCategory,newsSource,sensitiveWords,keyWords,timeLimit){
        var queryData = {};
        queryData['lang'] = langType;
        queryData['isSensitive'] = isSensitive;
        queryData['sensitiveCategory'] = sensitiveCategory;
        queryData['newsSource'] = newsSource;
        queryData['sensitiveWords'] = sensitiveWords;
        queryData['keyWords'] = keyWords;
        queryData['timeLimit'] = timeLimit;
        table.reload(customWordField.tableId, {
            where: queryData, page: {curr: 1}
        });
    }

    //情感类型
    var SensitiveTypeCharts = echarts.init(document.getElementById('senType'));
    function loadSensitiveTypeData(type,langType,isSensitive,sensitiveCategory,newsSource,sensitiveWords,keyWords,timeLimit){
        $.get(Feng.ctxPath + '/customWord/field/sensitive/type?type='+type+'&lang='+langType+'&newsSource='+newsSource+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory+'&sensitiveWords='+sensitiveWords+'&keyWords='+keyWords+'&timeLimit='+timeLimit, function (data) {
            SensitiveTypeCharts.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                color:["green","blue","red"],
                series : [
                    {
                        name: '情感类型',
                        type: 'pie',
                        radius: '75%',
                        center: ['50%', '50%'],
                        data:data.sensitiveTypeData,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            },true)
        }, 'json');
    }

    //新闻来源
    var newsSourceCharts = echarts.init(document.getElementById('source'),myEchartsTheme);
    function loadNewsSourceData(type,langType,isSensitive,sensitiveCategory,newsSource,sensitiveWords,keyWords,timeLimit) {
        $.get(Feng.ctxPath + '/customWord/field/news/source?type='+type+'&lang='+langType+'&newsSource='+newsSource+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory+'&sensitiveWords='+sensitiveWords+'&keyWords='+keyWords+'&timeLimit='+timeLimit, function (data) {
            newsSourceCharts.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series: [
                    {
                        name: '新闻来源',
                        type: 'pie',
                        radius: '75%',
                        center: ['50%', '50%'],
                        data: data.newsSourceData,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            }, true)
        }, 'json');
    }

    //敏感类别
    var senCategoryCharts = echarts.init(document.getElementById('senCategory'),myEchartsTheme);
    function loadSenCategoryData(type,langType,isSensitive,sensitiveCategory,newsSource,sensitiveWords,keyWords,timeLimit){
        $.get(Feng.ctxPath + '/customWord/field/news/sensitive/category?type='+type+'&lang='+langType+'&newsSource='+newsSource+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory+'&sensitiveWords='+sensitiveWords+'&keyWords='+keyWords+'&timeLimit='+timeLimit, function (data) {
            senCategoryCharts.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        name: '敏感类别',
                        type: 'pie',
                        radius: '75%',
                        center: ['50%', '50%'],
                        data:data.sensitiveCategoryData,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            },true)
        }, 'json');
    }

    /**
     * 点击查询按钮
     */
    customWordField.search = function () {
        var langType = $("#langType").val();
        var isSensitive = $("#sensitiveType").val();
        var sensitiveCategory = $("#sensitiveCategory").val();
        var sensitiveWords = $("#sensitiveWords").val();
        var keyWords = $("#keyWords").val();
        var newsSource = $("#newsSource").val();
        var timeLimit = $("#timeLimit").val();

        loadCustomWordFieldNewsData(type,langType,isSensitive,sensitiveCategory,newsSource,sensitiveWords,keyWords,timeLimit);
        loadSensitiveTypeData(type,langType,isSensitive,sensitiveCategory,newsSource,sensitiveWords,keyWords,timeLimit);
        loadNewsSourceData(type,langType,isSensitive,sensitiveCategory,newsSource,sensitiveWords,keyWords,timeLimit);
        loadSenCategoryData(type,langType,isSensitive,sensitiveCategory,newsSource,sensitiveWords,keyWords,timeLimit);
    };

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        customWordField.search();
    });

    // 关闭页面
    $('#btnBack').click(function () {
        window.location.href = Feng.ctxPath + "/customWord";
    });

    //查看新闻详情
    customWordField.openDetailDlg = function (data) {
        func.open({
            title: '详细信息',
            content:  Feng.ctxPath + '/home/news/detail?newsId=' + data.newsId+'&lang='+data.langType,
            tableId: customWordField.tableId
        });
    };

    // 热门新闻工具条点击事件
    table.on('tool(' + customWordField.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'showNewsDetail') {
            customWordField.openDetailDlg(data);
        }
    });
});
