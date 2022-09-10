layui.use(['table', 'ax', 'treetable','laydate', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var func = layui.func;

    var TopicNews = {
        tableId:"topicNewsTable"
    };

    //话题新闻表格的列
    TopicNews.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', align: "center", hide: true, title: '新闻Id'},
            {
                field: 'title', align: "center", sort: true, title: '新闻标题',minWidth: 350, templet: function (d) {
                    return '<div style="text-align: left"><a href="'+d.newsUrl+'" class="layui-table-link" target="_blank">'+d.newsTitle+'</a></div>';
                }
            },
            {
                field: 'sensitive', align: "center", sort: true, title: '情感类型', templet: function (d) {
                    if(d.isSensitive === 3){
                        return "<p style='color:green;font-weight: bold'>正向</p>";
                    }else if (d.isSensitive === 1) {
                        return "<p style='color:blue;font-weight: bold'>中性</p>";
                    } else if(d.isSensitive === 2){
                        return "<p style='color:red;font-weight: bold'>敏感</p>";
                    }else{
                        return "<p style='font-weight: bold'></p>";
                    }
                }
            },
            {
                field: 'sensitiveCategory', align: "center", sort: true, title: '敏感类别', templet: function (d) {
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
                    }else if(d.sensitiveCategory === 7){
                        return "<p>政府</p>";
                    }
                }
            },
            {field: 'websitename', align: "center", sort: true, title: '新闻来源'},
            {field: 'newsTime', align: "center", sort: true, title: '发布时间'},
            {fixed: 'right',align: "center",toolbar: '#topicNewsTableBar', minWidth: 120,title: '操作'}
        ]];
    };

    /**
     * 话题相关新闻
     */
    var topicId = $("#topicId").val();
    var langType = $("#langType").val();
    var tableResult = table.render({
        elem: '#' + TopicNews.tableId,
        url: Feng.ctxPath + '/topic/relate/news?topicId='+topicId+'&langType='+langType,
        page: true,
        height: 500,
        cellMinWidth: 100,
        cols: TopicNews.initColumn(),
        initSort:{
            field:'newsTime',
            type:'desc'
        }
    });

    //话题相关新闻来源
    var topicNewsSourceCharts = echarts.init(document.getElementById('topicNewsSource'),myEchartsTheme);
    function loadNewsSource(topicId,langType,isSensitive,sensitiveCategory,websitename,timeLimit){
        $.get(Feng.ctxPath + '/topic/relate/news/source?topicId='+topicId+'&langType='+langType+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory+'&websitename='+websitename+'&timeLimit='+timeLimit, function (data) {
            topicNewsSourceCharts.setOption({
                title : {
                    text: '',
                    x: 'center'
                },
                legend : {
                    data : data.websiteName,
                    orient : 'vertical',
                    x : 'left'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        name: '新闻来源',
                        type: 'pie',
                        radius: '65%',
                        center: ['55%', '55%'],
                        data:data.topicNewsSourceData,
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


    //初始加载敏感分布
    loadNewSensitive(topicId,langType,'','','','');
    //初始加载新闻来源
    loadNewsSource(topicId,langType,'','','','');

    //敏感分布
    var newSensitiveCharts = echarts.init(document.getElementById('sensitive'),myEchartsTheme);
    function loadNewSensitive(topicId,langType,isSensitive,sensitiveCategory,websitename,timeLimit){
        $.get(Feng.ctxPath + '/topic/relate/news/sensitive?topicId='+topicId+'&langType='+langType+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory+'&websitename='+websitename+'&timeLimit='+timeLimit, function (data) {
            newSensitiveCharts.setOption({
                title : {
                    text: '',
                    x: 'center'
                },
                legend : {
                    data : ['正向新闻', '中性新闻', '敏感新闻'],
                    orient : 'vertical',
                    x : 'left'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                color : ['#00a6ac', '#33a3dc','#f3704b'],
                series : [
                    {
                        name: '情感类型',
                        type: 'pie',
                        radius: '65%',
                        center: ['55%', '55%'],
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

    //实体统计
    var personCharts = echarts.init(document.getElementById('person'),myEchartsTheme);
    var locationCharts = echarts.init(document.getElementById('location'),myEchartsTheme);
    var organizeCharts = echarts.init(document.getElementById('organize'),myEchartsTheme);
    var wordcloudCharts = echarts.init(document.getElementById('entityWordcloud'), myEchartsTheme);

    personCharts.showLoading();
    locationCharts.showLoading();
    organizeCharts.showLoading();
    wordcloudCharts.showLoading();
    $.get(Feng.ctxPath + '/topic/entity/static?topicId='+$("#topicId").val()+'&langType='+$("#langType").val(), function (data) {

        //话题概览，人物、地点、组织机构实体个数统计
        $("#personEntityCount").html(data.personEntityCount);
        $("#locationEntityCount").html(data.locationEntityCount);
        $("#organizeEntityCount").html(data.organizeEntityCount);

        //人名实体统计
        personCharts.hideLoading();
        personCharts.setOption({
            color: ['#33a3dc'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                height:230,
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: data.personName,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '人物统计',
                    type: 'bar',
                    barWidth: 35,
                    data: data.personNum
                }
            ]
        });

        //地名实体统计
        locationCharts.hideLoading();
        locationCharts.setOption({
            color: ['#33a3dc'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                height:230,
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: data.locationName,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '地点统计',
                    type: 'bar',
                    barWidth: 35,
                    data: data.locationNum
                }
            ]
        });

        //组织机构实体统计
        organizeCharts.hideLoading();
        organizeCharts.setOption({
            color: ['#33a3dc'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                height:230,
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: data.organizeName,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '组织机构统计',
                    type: 'bar',
                    barWidth: 35,
                    data: data.organizeNum
                }
            ]
        });

        //实体词云
        wordcloudCharts.hideLoading();
        wordcloudCharts.setOption({
            title:{
                text:'实体词云',
                textStyle:{
                    color:'#ffffff'
                }
            },
            series:[{
                type:'wordCloud',
                grideSize:2,//网格尺寸,尺寸越大，字体之间的间隔越大
                sizeRange:[12,35],//字体的最大与最小字号
                rotationRange:[45,90,135,-90],//字体旋转的范围
                shape:'smooth',
                textStyle:{
                    normal:{//字体随机颜色
                        color:function(){
                            return 'rgb('+[
                                Math.round(Math.random()*255),
                                Math.round(Math.random()*255),
                                Math.round(Math.random()*255)
                            ].join(',')+')';
                        }
                    },
                    emphasis:{
                        shadowBlur:1,//阴影距离
                        shadowColor:'#111' //阴影颜色
                    }
                },
                width:'90%',
                height:'90%',
                data:data.wordcloud
            }]
        });
    }, 'json');

    //实体关系
    var entityRelationChart = echarts.init(document.getElementById('entityRelation'),'light');
    entityRelationChart.showLoading();
    $.get(Feng.ctxPath + '/topic/entity/relation?topicId='+$("#topicId").val()+'&langType='+$("#langType").val(), function (data) {
        entityRelationChart.hideLoading();

        var result = data.entityRelation;
        var links = [];
        var nodes = [];
        var entityKey = [];
        var newsTitle = [];
        var categories = [
            {name:'人物'}, {name:'地点'},{name:'组织结构'}
        ];

        $.each(result,function(i,obj){
            links.push({
                source:obj.newsTitle,
                target:obj.entityKey,
                weight:1,
            });
            if(newsTitle.indexOf(obj.newsTitle)==-1){
                newsTitle.push(obj.newsTitle);
                nodes.push({
                    category:0,
                    name:obj.newsTitle,
                    value:20
                });
            }
            if(entityKey.indexOf(obj.entityKey)==-1){
                entityKey.push(obj.entityKey);
                if(obj.entityType==1){
                    nodes.push({
                        category:1,
                        name:obj.entityKey,
                        value:15
                    });
                }else if (obj.entityType==2){
                    nodes.push({
                        category:2,
                        name:obj.entityKey,
                        value:15
                    });
                } else{
                    nodes.push({
                        category:3,
                        name:obj.entityKey,
                        value:15
                    });
                }
            }
        });
        entityRelationChart.setOption({
            tooltip : {
                trigger: 'item',
                formatter: '{b}'
            },
            toolbox: {
                show : true,
                feature : {
                    restore : {show: true},
                    magicType: {
                        show: true,
                        type: ['force', 'chord']
                    }
                }
            },
            legend: [{
                orient : 'vertical',
                x : 'left',
                data: categories.map(function (a) {
                    return a.name;
                })
            }],
            animationDurationUpdate: 1500,
            animationEasingUpdate: 'quinticInOut',
            series : [
                {
                    name : "关系",
                    type: 'graph',
                    layout: 'circular',
                    circular: {
                        rotateLabel: true
                    },
                    data:nodes,
                    links:links,
                    categories: categories,
                    roam: true,
                    label: {
                        show:false,
                        position: 'right',
                        formatter: '{b}'
                    },
                    lineStyle: {
                        color: 'source',
                        curveness: 0.3
                    },
                    itemStyle: {
                        borderColor: '#fff',
                        borderWidth: 1,
                        shadowBlur: 10,
                        shadowColor: 'rgba(0, 0, 0, 0.3)'
                    },
                }
            ]
        })
    }, 'json');

    //查看话题新闻详情
    TopicNews.openDetailDlg = function (data) {
        func.open({
            title: '详细信息',
            content: Feng.ctxPath + '/home/news/detail?newsId=' + data.newsId+'&lang='+data.langType,
            tableId: TopicNews.tableId
        });
    };

    // 话题新闻工具条点击事件
    table.on('tool(' + TopicNews.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'showTopicNewsDetail') {
            TopicNews.openDetailDlg(data);
        }
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });


     //点击查询按钮
    TopicNews.search = function () {
        var topicId = $("#topicId").val();
        var langType = $("#langType").val();
        var isSensitive = $("#sensitiveType").val();
        var sensitiveCategory = $("#sensitiveCategory").val();
        var websitename = $("#newsSource").val();
        var timeLimit = $("#timeLimit").val();
        loadNewsData(isSensitive,sensitiveCategory,websitename,timeLimit);
        loadNewSensitive(topicId,langType,isSensitive,sensitiveCategory,websitename,timeLimit);
        loadNewsSource(topicId,langType,isSensitive,sensitiveCategory,websitename,timeLimit);
    };

    function loadNewsData(isSensitive,sensitiveCategory,websitename,timeLimit){
        var queryData = {};
        queryData['isSensitive'] = isSensitive;
        queryData['sensitiveCategory'] = sensitiveCategory;
        queryData['websitename'] = websitename;
        queryData['timeLimit'] = timeLimit;
        table.reload(TopicNews.tableId, {
            where: queryData, page: {curr: 1}
        });
    }

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
       TopicNews.search();
    });
});