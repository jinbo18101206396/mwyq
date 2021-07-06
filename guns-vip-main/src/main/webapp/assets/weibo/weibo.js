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
            {field: 'content', align: "left", sort: true, title: '微博内容', minWidth:450},
            {field: '',align: "left",toolbar: '#transBar', title: '译', width:50},
            {field: 'sentiment', align: "center", sort: true, title: '情感类型', templet: function (d) {
                if(d.sentiment == 3){
                    return "<p style='color:green;font-weight: bold'>正向</p>>";
                }else if (d.sentiment === 2) {
                    return "<p style='color:blue;font-weight: bold'>中性</p>";
                } else if(d.sentiment === 1){
                    return "<p style='color:red;font-weight: bold'>负向</p>";
                }else{
                    return "<p style='font-weight: bold'>其他</p>";
                }
            }},
            {field: 'lang', align: "center", sort: true, title: '博文语言', templet: function (d) {
                    if(d.lang == 'cn'){
                        return "<p style='color:black;font-weight: bold'>中文</p>>";
                    }else if (d.lang == 'zang') {
                        return "<p style='color:black;font-weight: bold'>藏文</p>";
                    } else if(d.lang == 'wei'){
                        return "<p style='color:black;font-weight: bold'>维文</p>";
                    }else if(d.lang == 'meng'){
                        return "<p style='color:black;font-weight: bold'>蒙文</p>";
                    }else{
                        return "<p style='color:blue;font-weight: bold'>其他</p>";
                    }
                }},
            {field: 'authorId', align: "center", sort: true, title: '博主'},
            {field: 'commentCount', align: "center", sort: true, title: '评论量'},
            {field: 'likeCount', align: "center", sort: true, title: '点赞量'},
            {field: 'transmitCount', align: "center", sort: true, title: '转发量'},
            {field: 'createTime', align: "center", sort: true, title: '发布时间'},
            {field: 'deviceType', align: "center", sort: true, title: '设备类型'},
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
            content: Feng.ctxPath + '/weibo/translate?id=' + data.id + '&lang=' + data.lang,
            area: ['auto', '100px'],
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
        range: true,
        max: Feng.currentDate()
    });
    // /**
    //  * 点击查询按钮
    //  */
    // Weibo.search = function () {
    //     var queryData = {};
    //     queryData['condition'] = $("#condition").val();
    //     table.reload(Weibo.tableId, {
    //         where: queryData, page: {curr: 1}
    //     });
    // };


    //加载微博数据
    function loadWeiboData(lang,sentiment,timeLimit) {
        var queryData = {};
        queryData['lang'] = lang;
        queryData['sentiment'] = sentiment;
        queryData['timeLimit'] = timeLimit;
        table.reload(Weibo.tableId, {
            where: queryData, page: {curr: 1}
        })
    }
    //加载微博情感趋势数据
    var weiboTrendChart = echarts.init(document.getElementById('weiboTrend'));
    function loadWeiboTrendData(lang,sentiment,timeLimit){
        $.get(Feng.ctxPath + '/weibo/sentiment/trend?lang=' +lang+ '&sentiment' +sentiment+ '&timeLimit' +timeLimit, function (data) {
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
                        name: '负向微博',
                        type: 'line',
                        data: data.senNum
                    },
                    {
                        name: '中性微博',
                        type: 'line',
                        data: data.neuNum
                    },
                    {
                        name: '正向微博',
                        type: 'line',
                        data: data.forNum
                    }
                ]
            })
        }, 'json');
    }
    //加载微博情感分布数据
    var sentimentCharts = echarts.init(document.getElementById('weiboSentiment'), myEchartsTheme);
    function loadWeiboSentimentData(lang,sentiment,timeLimit){
        $.get(Feng.ctxPath + '/weibo/sentiment/type?lang='+lang+'&sentiment'+sentiment+'&timeLimit'+timeLimit, function (data) {
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

    //地图数据
    // var dataList=[
    //     {name: '北京', value: 421},
    //     {name: '天津', value: 343},
    //     {name: '新疆', value:276},
    //     {name: '重庆', value: 185},
    //     {name: '河北', value: 141},
    //     {name: '河南', value: 90},
    //     {name: '云南', value: 75},
    //     {name: '辽宁', value: 68},
    //     {name: '黑龙江', value: 50},
    //     {name: '湖南', value: 14},
    //     {name: '安徽', value: randomValue()},
    //     {name: '山东', value: randomValue()},
    //     {name: '上海', value: randomValue()},
    //     {name: '江苏', value: randomValue()},
    //     {name: '浙江', value: randomValue()},
    //     {name: '江西', value: randomValue()},
    //     {name: '湖北', value: randomValue()},
    //     {name: '广西', value: randomValue()},
    //     {name: '甘肃', value: randomValue()},
    //     {name: '山西', value: randomValue()},
    //     {name: '内蒙古', value: randomValue()},
    //     {name: '陕西', value: randomValue()},
    //     {name: '吉林', value: randomValue()},
    //     {name: '福建', value: randomValue()},
    //     {name: '贵州', value: randomValue()},
    //     {name: '广东', value: randomValue()},
    //     {name: '青海', value: randomValue()},
    //     {name: '西藏', value: randomValue()},
    //     {name: '四川', value: randomValue()},
    //     {name: '宁夏', value: randomValue()},
    //     {name: '海南', value: randomValue()},
    //     {name: '台湾', value: randomValue()},
    //     {name: '香港', value: randomValue()},
    //     {name: '澳门', value: randomValue()},
    //     {name:"南海诸岛",value:0}
    // ];
    // function randomValue() {
    //     return Math.round(Math.random()*1000);
    // }

    // //地图表格数据，按数量取Top10
    // function tableList(){
    //     var appendHTML = "";
    //     if($(".map-table tbody tr").length>0){
    //         $(".map-table tbody tr").remove();
    //     }
    //     for(var i=0; i<7; i++){
    //         appendHTML = "<tr><td>"+
    //             dataList[i].name+"</td><td>"+
    //             dataList[i].value+"</td></tr>";
    //         $(".map-table tbody").append(appendHTML);
    //     }
    // }
    // tableList();

    //话题地图
    var areaMapCharts = echarts.init(document.getElementById('areaMap'));
    var areaMapOption = {
        tooltip: {
            formatter:function(params,ticket, callback){
                return params.seriesName+'<br />'+params.name+'：'+params.value
            }//数据格式化
        },
        visualMap: {
            min: 0,
            max: 1500,
            left: 'left',
            top: 'bottom',
            text: ['高','低'],//取值范围的文字
            inRange: {
                color: ['#f3d647', '#dd6d0a']//取值范围的颜色
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
                name: '博主数量',
                type: 'map',
                geoIndex: 0,
                // data: data.weiboUserMapdata
            }
        ]
    };
    areaMapCharts.setOption(areaMapOption);//先渲染出空地图
    areaMapCharts.hideLoading();
    areaMapCharts.on('click', function (params) {
        alert(params.name+":"+params.value);
    });
    //请求地图数据
    $.ajax({
        url: Feng.ctxPath + '/weibo/areaMap',
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            var options = areaMapCharts.getOption();
            console.log(options);
            options.series[0].data = data.weiboUserMapData;
            areaMapCharts.hideLoading();
            areaMapCharts.setOption(options);
            $(window).resize(areaMapCharts.resize);
            tableList(data);
        }
    })

    // //鼠标移入表格，实现地图区域高亮
    // $(".map-table tbody").find('tr').on('mouseenter',function(){
    //     var hang = $(this).prevAll().length;
    //     areaMapCharts.dispatchAction({ type: 'highlight', name:weiboUserMapData[hang].name});//选中高亮
    // });
    // $(".map-table tbody").find('tr').on('mouseleave',function(){
    //     var hang = $(this).prevAll().length;
    //     areaMapCharts.dispatchAction({ type: 'downplay', name:weiboUserMapData[hang].name});//取消高亮
    // });

    //地图表格数据，按数量取Top10
    function tableList(data){
        var appendHTML = "";
        if($(".map-table tbody tr").length>0){
            $(".map-table tbody tr").remove();
        }
        for(var i=0; i<7; i++){
            appendHTML = "<tr><td>"+
                data.weiboUserMapData[i].name+"</td><td>"+
                data.weiboUserMapData[i].value+"</td></tr>";
            $(".map-table tbody").append(appendHTML);
        }
    }

    //博主影响力分布
    var followersCharts = echarts.init(document.getElementById('followers'), myEchartsTheme);
    function loadFollowersData(){
        $.get(Feng.ctxPath + '/weibo/author', function (data) {
            followersCharts.setOption({
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['粉丝数量', '微博数量']
                },
                tooltip: {
                  show: true,
                  feature: {
                      dataView: {show: true, readOnly: false},
                      magicType:{show: true, type: ['line', 'bar']},
                      restore: {show: true},
                      saveAsImage: {show: true}
                  }
                },
                calculable: true,
               xAxis: {
                   type: 'category',
                   data: data.authorName
               },
                yAxis: {
                   type: 'value'
                },
                series: [
                    {
                        name: '粉丝数量',
                        type: 'bar',
                        data: data.followersCount,
                    },
                    {
                        name: '微博数量',
                        type: 'bar',
                        data: data.weiboCount,
                    }
                ]
            })

        }, 'json')
    }
    //初始化加载粉丝分布数据
    loadFollowersData();
    //初始化加载情感走势数据
    loadWeiboTrendData('','','');
    //初始化加载情感分析数据
    loadWeiboSentimentData('','','');



    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        var lang = $("#lang").val();
        var sentiment = $("#sentiment").val();
        var timeLimit = $("#timeLimit").val();
        loadWeiboData(lang,sentiment,timeLimit);
        loadWeiboSentimentData(lang,sentiment,timeLimit);
        loadWeiboTrendData(lang,sentiment,timeLimit)
    });

    // 窗口大小改变事件
    window.onresize = function () {
        weiboTrendChart.resize();
        sentimentCharts.resize();
    };
});
