layui.use(['table', 'admin', 'ax', 'func', 'layer', 'laydate', 'element'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var laydate = layui.laydate;
    var element = layui.element;
    var layer = layui.layer;

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
            {field: 'id', align: "center", hide: true, title: '博主id'},
            {field: 'content', align: "left", sort: true, title: '微博内容', minWidth:450,templet:function (d) {
                    return '<div style="text-align: left"><a href="'+d.articleUrl+'" class="layui-table-link" target="_blank">'+d.content+'</a></div>';
             }
            },
            {field: '',align: "left",toolbar: '#transBar', title: '译', width:50},
            {field: 'authorName', align: "center", sort: true, title: '博主姓名'},
            {field: 'lang', align: "center", sort: true, title: '语言类型', templet: function (d) {
                    if(d.lang == 'cn'){
                        return "<p>中文</p>>";
                    }else if (d.lang == 'zang') {
                        return "<p'>藏文</p>";
                    } else if(d.lang == 'wei'){
                        return "<p>维吾尔文</p>";
                    }else if(d.lang == 'meng'){
                        return "<p>蒙古文</p>";
                    }else{
                        return "<p></p>";
                    }
                }},

            {field: 'sentiment', align: "center", sort: true, title: '情感类型', templet: function (d) {
                if(d.sentiment == 3){
                    return "<p style='color:green;font-weight: bold'>正向</p>>";
                }else if (d.sentiment === 1) {
                    return "<p style='color:blue;font-weight: bold'>中性</p>";
                } else if(d.sentiment === 2){
                    return "<p style='color:red;font-weight: bold'>负向</p>";
                }else{
                    return "<p style='font-weight: bold'></p>";
                }
            }},
            {field: 'commentCount', align: "center", sort: true, title: '评论量'},
            {field: 'likeCount', align: "center", sort: true, title: '点赞量'},
            {field: 'transmitCount', align: "center", sort: true, title: '转发量'},
            {field: 'createTime', align: "center", sort: true, title: '发布时间'},
            {field: 'location', align: "center", sort: true, title: '属地'}
        ]];
    };

    table.on('tool(' + Weibo.tableId + ')', function (obj){
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent == 'translate') {
            Weibo.weiboTranslate(data);
        }
    })

    Weibo.weiboTranslate = function (data) {
        layer.open({
            type: 2,
            title: '翻译',
            closeBtn: 1,
            shade: 0,
            content: Feng.ctxPath + '/weibo/translate?content=' + data.content + '&lang=' + data.lang,
            area: ['30%','30%'],
            offset: 'auto',
            fixed: false
        })
    }

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Weibo.tableId,
        url: Feng.ctxPath + '/weibo/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Weibo.initColumn(),
        initSort:{
            field:'createTime',
            type:'desc'
        }
    });

    // 渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: "至",
        max: Feng.currentDate()
    });

    //加载微博数据
    function loadWeiboData(lang,sentiment,timeLimit,authorName,location) {
        var queryData = {};
        queryData['lang'] = lang;
        queryData['sentiment'] = sentiment;
        queryData['timeLimit'] = timeLimit;
        queryData['authorName'] = authorName;
        queryData['location'] = location;
        table.reload(Weibo.tableId, {
            where: queryData, page: {curr: 1}
        })
    }
    //加载微博情感趋势数据
    var weiboTrendChart = echarts.init(document.getElementById('weiboTrend'));
    function loadWeiboTrendData(lang,sentiment,timeLimit,authorName,location){
        $.get(Feng.ctxPath + '/weibo/sentiment/trend?lang=' +lang+ '&sentiment=' +sentiment+ '&timeLimit=' +timeLimit+'&authorName='+authorName+'&location='+location, function (data) {
            weiboTrendChart.setOption({
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {
                    data:['正向微博', '中性微博', '负向微博']
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
                    end : 20
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
                        name: '负向微博',
                        type: 'line',
                        data: data.senNum,
                        itemStyle: {
                            normal: {
                                color: 'red'
                            }
                        }
                    },
                    {
                        name: '中性微博',
                        type: 'line',
                        data: data.neuNum,
                        itemStyle: {
                            normal: {
                                color: 'blue'
                            }
                        }
                    },
                    {
                        name: '正向微博',
                        type: 'line',
                        data: data.forNum,
                        itemStyle: {
                            normal: {
                                color: 'green'
                            }
                        }
                    }
                ]
            })
        }, 'json');
    }

    //加载微博情感分布数据
    var sentimentCharts = echarts.init(document.getElementById('weiboSentiment'), myEchartsTheme);
    function loadWeiboSentimentData(lang,sentiment,timeLimit,authorName,location){
        $.get(Feng.ctxPath + '/weibo/sentiment/type?lang='+lang+'&sentiment='+sentiment+'&timeLimit='+timeLimit+'&authorName='+authorName+'&location='+location, function (data) {
            sentimentCharts.setOption({
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
                        name: '情感分布',
                        type: 'pie',
                        radius: '80%',
                        center: ['50%', '50%'],
                        data:data.sentimentTypeData,
                        label:{
                          normal: {
                              formatter:'{b}({d}%)',
                              textStyle: {
                                  fontWeight:'normal',
                                  fontSize:15
                              }
                          }
                        },
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

    //博主发文量排行
    var bloggerCharts = echarts.init(document.getElementById('blogger'), myEchartsTheme);
    function loadBloggerRankData(lang,sentiment,timeLimit,authorName,location){
        $.get(Feng.ctxPath + '/weibo/blogger/rank?lang='+lang+'&sentiment='+sentiment+'&timeLimit='+timeLimit+'&authorName='+authorName+'&location='+location, function (data) {
            bloggerCharts.setOption({
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {},
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [{
                    type: 'category',
                    data: data.authorName
                }],
                yAxis: [{
                    type: 'value'
                }],
                series: [
                    {
                        name: '正向',
                        type: 'bar',
                        stack: 'Ad',
                        barWidth : 50,
                        emphasis: {
                            focus: 'series'
                        },
                        data: data.positiveCount,
                        itemStyle: {
                            normal: {
                                color: 'green'
                            }
                        }
                    },
                    {
                        name: '中性',
                        type: 'bar',
                        stack: 'Ad',
                        barWidth : 50,
                        emphasis: {
                            focus: 'series'
                        },
                        data: data.neuralCount,
                        itemStyle: {
                            normal: {
                                color: 'blue'
                            }
                        }
                    },
                    {
                        name: '负向',
                        type: 'bar',
                        stack: 'Ad',
                        barWidth : 50,
                        emphasis: {
                            focus: 'series'
                        },
                        data: data.negativeCount,
                        itemStyle: {
                            normal: {
                                color: 'red'
                            }
                        }
                    },
                    // {
                    //     type: 'bar',
                    //     stack: 'Ad',
                    //     label: {
                    //         normal: {
                    //             offset: ['50', '80'],
                    //             show: true,
                    //             position: 'insideBottom',
                    //             formatter: '{c}',
                    //             textStyle: { color: '#000' }
                    //         }
                    //     },
                    //     itemStyle: {
                    //         normal: {
                    //             color: 'rgb(255, 255, 255, 0)'      // 柱状图颜色设为透明
                    //         }
                    //     },
                    //     data: data.weiboCount
                    // }
                ]
            })
        }, 'json')
    }

    //博主地域分布
    var areaMapCharts = echarts.init(document.getElementById('areaMap'), myEchartsTheme);
    function loadAreaMapData(lang,sentiment,timeLimit,authorName,location){
        $.get(Feng.ctxPath + '/weibo/areaMap?lang='+lang+'&sentiment='+sentiment+'&timeLimit='+timeLimit+'&authorName='+authorName+'&location='+location, function (data) {
            areaMapCharts.setOption({
                tooltip: {
                    formatter:function(params,ticket, callback){
                        return params.seriesName+'<br />'+params.name+'：'+params.value
                    }
                },
                visualMap: {
                    min: 0,
                    max: 1500,
                    left: 'left',
                    top: 'bottom',
                    text: ['高','低'],
                    inRange: {
                        color: ['#f3d647', '#dd6d0a']
                    },
                    show:true//图注
                },
                geo: {
                    map: 'china',
                    roam: false,//不开启缩放和平移
                    zoom: 1.23,//视角缩放比例
                    label: {
                        normal: {
                            show: true,
                            fontSize:'10',
                            color: 'rgba(0,0,0,0.7)'
                        }
                    },
                    itemStyle: {
                        normal:{
                            borderColor: 'rgba(0, 0, 0, 0.2)'
                        },
                        emphasis:{
                            areaColor: '#F3B329',//鼠标选择区域颜色
                            shadowOffsetX: 0,
                            shadowOffsetY: 0,
                            shadowBlur: 20,
                            borderWidth: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                },
                series : [
                    {
                        name: '博文数量',
                        type: 'map',
                        geoIndex: 0,
                        data: data.weiboAreaMapData
                    }
                ]
            }),
            $(window).resize(areaMapCharts.resize),
            tableList(data)
        }, 'json')
    }
    //地图表格数据，按数量取Top10
    function tableList(data){
        var appendHTML = "";
        if($(".map-table tbody tr").length>0){
            $(".map-table tbody tr").remove();
        }
        for(var i=0; i<8; i++){
            appendHTML = "<tr><td>"+
                data.weiboAreaMapData[i].name+"</td><td>"+
                data.weiboAreaMapData[i].value+"</td></tr>";
            $(".map-table tbody").append(appendHTML);
        }
    }
    //初始化加载情感走势数据
    loadWeiboTrendData('','','','','');
    //初始化加载情感分析数据
    loadWeiboSentimentData('','','','','');
    //初始化博主排行数据
    loadBloggerRankData('','','','','');
    //初始博主地域分布数据
    loadAreaMapData('','','','','');

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        var lang = $("#lang").val();
        var sentiment = $("#sentiment").val();
        var timeLimit = $("#timeLimit").val();
        var authorName = $("#authorName").val();
        var location = $("#location").val();

        loadWeiboData(lang,sentiment,timeLimit,authorName,location);
        loadWeiboSentimentData(lang,sentiment,timeLimit,authorName,location);
        loadWeiboTrendData(lang,sentiment,timeLimit,authorName,location);
        loadBloggerRankData(lang,sentiment,timeLimit,authorName,location);
        loadAreaMapData(lang,sentiment,timeLimit,authorName,location);
    });

    // 窗口大小改变事件
    window.onresize = function () {
        weiboTrendChart.resize();
        sentimentCharts.resize();
    };
});
