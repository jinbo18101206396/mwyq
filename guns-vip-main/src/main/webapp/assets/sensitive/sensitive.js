layui.use(['table', 'ax', 'treetable','laydate', 'func', 'layer', 'element'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var func = layui.func;
    var layer = layui.layer;
    var element = layui.element;

    /**
     * 基础字典管理
     */
    var sensitive = {
        tableId: "sensitiveTable"
    };

    /**
     * 初始化表格的列
     */
    sensitive.initColumn = function () {
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
                    } else if(d.isSensitive === 34){
                        return "<p style='color:yellow;font-weight: bold'>正向-禁用词</p>";
                    } else if(d.isSensitive === 14){
                        return "<p style='color:yellow;font-weight: bold'>中性-禁用词</p>";
                    } else if(d.isSensitive === 24){
                        return "<p style='color:yellow;font-weight: bold'>敏感-禁用词</p>";
                    } else {
                        return "<p style='font-weight: bold'></p>";
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
                    }else if(d.sensitiveCategory === 6){
                        return "<p>其他</p>";
                    }else if (d.sensitiveCategory === 7) {
                        return "<p>政治</p>";
                    }else{
                        return "<p></p>";
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
            {field: 'websitename', align: "center", sort: true, title: '新闻来源',minWidth: 120},
            {field: 'newsCategory', align: "center", sort: true, title: '新闻类别'},
            {field: 'newsTime', align: "center", sort: true, title: '发布时间'},
            {fixed: 'right',align: "center",toolbar: '#tableBar', title: '操作', width:150}
        ]];
    };

    /**
     * 渲染表格
     */
    var tableResult = table.render({
        elem: '#' + sensitive.tableId,
        url: Feng.ctxPath + '/sensitive/list',
        page: true,
        limit:90,
        height: "full-158",
        cellMinWidth: 100,
        cols: sensitive.initColumn(),
        initSort:{
            field:'newsTime',
            type:'desc'
        }
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    //加载新闻数据
    function loadNewsData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit){
        var queryData = {};
        queryData['langType'] = langType;
        queryData['websitename'] = websitename;
        queryData['isSensitive'] = isSensitive;
        queryData['sensitiveWords'] = sensitiveWords;
        queryData['keyWords'] = keyWords;
        queryData['sensitiveCategory'] = sensitiveCategory;
        queryData['timeLimit'] = timeLimit;
        table.reload(sensitive.tableId, {
            where: queryData, page: {curr: 1}
        });
    }

    //初始化加载情感走势数据
    loadSensitiveTrendData('','','','','','','');
    //初始化加载情感分布数据
    loadSensitiveTypeData('','','','','','','');
    //初始化加载敏感类别数据
    loadSensitiveCategoryData('','','','','','','');
    //初始化加载新闻来源数据
    loadSensitiveSourceData('','','','','','','');

    //加载情感走势数据
    var sensitiveTrendChart = echarts.init(document.getElementById('sensitiveTrend'));
    function loadSensitiveTrendData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit){
        $.get(Feng.ctxPath + '/news/sensitive/trend?langType='+langType+'&websitename='+websitename+'&isSensitive='+isSensitive+'&sensitiveWords='+sensitiveWords+'&keyWords='+keyWords+'&sensitiveCategory='+sensitiveCategory+'&timeLimit='+timeLimit, function (data) {
            sensitiveTrendChart.setOption({
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data:['敏感新闻', '中性新闻','正向新闻' ]
                },
                toolbox: {
                    show : true,
                    feature : {
                        restore : {show: true},
                        magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']}
                    }
                },
                calculable : true,
                dataZoom : {
                    show : true,
                    realtime : true,
                    start : 0,
                    end : 100
                },
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : true,
                        data: data.dataTime
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name: '敏感新闻',
                        type: 'line',
                        data: data.senNum
                    },
                    {
                        name: '中性新闻',
                        type: 'line',
                        data: data.neuNum
                    },
                    {
                        name: '正向新闻',
                        type: 'line',
                        data: data.forNum
                    }
                ]
            })
        }, 'json');
    }

    //加载情感分布数据
    var senTypeCharts = echarts.init(document.getElementById('senType'), myEchartsTheme);
    function loadSensitiveTypeData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit){
        $.get(Feng.ctxPath + '/news/sensitive/type?langType='+langType+'&websitename='+websitename+'&isSensitive='+isSensitive+'&sensitiveWords='+sensitiveWords+'&keyWords='+keyWords+'&sensitiveCategory='+sensitiveCategory+'&timeLimit='+timeLimit, function (data) {
            senTypeCharts.setOption({
                title : {
                    text: '',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                color:["green","blue","red"],
                series : [
                    {
                        name: '敏感分布',
                        type: 'pie',
                        radius: '80%',
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
            })
        }, 'json');
    }

    //加载敏感类别数据
    var senCategoryCharts = echarts.init(document.getElementById('senCategory'), myEchartsTheme);
    function loadSensitiveCategoryData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit){
        $.get(Feng.ctxPath + '/news/sensitive/category?langType='+langType+'&websitename='+websitename+'&isSensitive='+isSensitive+'&sensitiveWords='+sensitiveWords+'&keyWords='+keyWords+'&sensitiveCategory='+sensitiveCategory+'&timeLimit='+timeLimit, function (data) {
            senCategoryCharts.setOption({
                title: {
                    text: ''
                },
                tooltip: {
                    trigger : 'item'
                },
                xAxis: [{
                    type: 'category',
                    data: data.categoryName,
                    axisLabel : {
                        interval : 0,
                        rotate : 0,
                        margin : 5,
                    }
                }],
                yAxis : [ {
                    name : '数量',
                    type : 'value',
                    boundaryGap : [ 0, 0.01 ]
                } ],
                series: [{
                    type: 'bar',
                    barWidth: "40%",
                    data: data.categoryNum,
                    itemStyle: {
                        normal: {
                            //每根柱子颜色设置
                            color: function(params) {
                                var colorList = [
                                    "red",
                                    "orange",
                                    "#da70d6",
                                    "#FFD700",
                                    "#2E8B57",
                                    "#1E90FF",
                                    "#00FFFF"
                                ];
                                return colorList[params.dataIndex];
                            },
                            label : {
                                show : true,
                                position:'top',
                                textStyle : {
                                    fontWeight : 'boler',
                                    fontSize : '12',
                                    fontFamily : '微软雅黑',
                                    color : "#000000"
                                }
                            }
                        }
                    }
                }]
            })
        }, 'json');
    }

    //新闻来源
    var newsSourceCharts = echarts.init(document.getElementById('source'));
    function loadSensitiveSourceData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit){
        $.get(Feng.ctxPath + '/news/sensitive/source?langType='+langType+'&websitename='+websitename+'&isSensitive='+isSensitive+'&sensitiveWords='+sensitiveWords+'&keyWords='+keyWords+'&sensitiveCategory='+sensitiveCategory+'&timeLimit='+timeLimit, function (data) {
            newsSourceCharts.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        name: '新闻网站',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '50%'],
                        data:data.senSourceData,
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

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        var langType = $("#lang").val();
        var websitename = $("#newsSource").val();
        var isSensitive = $("#sensitiveType").val();
        var sensitiveWords = $("#sensitiveWords").val();
        var keyWords = $("#keyWords").val();
        var sensitiveCategory = $("#sensitiveCategory").val();
        var timeLimit = $("#timeLimit").val();
        loadNewsData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit);
        loadSensitiveTrendData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit);
        loadSensitiveTypeData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit);
        loadSensitiveCategoryData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit);
        loadSensitiveSourceData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit)
    });

    /**
     * 点击详情
     * @param data 点击按钮时候的行数据
     */
    sensitive.openDetailDlg = function (data) {
        func.open({
            title: '详细信息',
            content: Feng.ctxPath + '/home/news/detail?newsId=' + data.newsId+'&lang='+data.langType,
            tableId: sensitive.tableId
        });
    };

    /**
     * 点击纠偏
     *
     * @param data 点击按钮时候的行数据
     */
    sensitive.openEditDlg = function (data) {
        func.open({
            title: '新闻纠偏',
            content: Feng.ctxPath + '/sensitive/edit?newsId=' + data.newsId,
            tableId: sensitive.tableId
        });
    };

    // 工具条点击事件
    table.on('tool(' + sensitive.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'showDetail') {
            sensitive.openDetailDlg(data);
        }else if (layEvent === 'edit') {
            sensitive.openEditDlg(data);
        }
    });

    // 窗口大小改变事件
    window.onresize = function () {
        sensitiveTrendChart.resize();
        senCategoryCharts.resize();
        senTypeCharts.resize();
    };
});
