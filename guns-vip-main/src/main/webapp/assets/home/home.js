layui.use(['table', 'ax', 'treetable','laydate', 'func', 'layer', 'element','form'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;
    var layer = layui.layer;
    var element = layui.element;
    var form = layui.form;
    var laydate = layui.laydate;

    var Topic = {
        tableId: "topicTable"
    };
    var News = {
        tableId:"newsTable"
    };
    var Sensitive = {
        tableId:"sensitiveTable"
    };
    var CustomWord = {
        tableId:"customWordTable"
    };
    var Religion = {
        tableId:"religionTable"
    };
    /**
     * 热门话题表格的列
     */
    Topic.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'topicId',align: "center", hide: true, title: '话题id'},
            {
                field: 'topicName',align: "center", sort: true, title: '话题名称',minWidth: 400,templet:function (d) {
                    var topicId = d.topicId;
                    var topicName = d.topicName;
                    var clusterTopicName = d.clusterTopicName;
                    var langType = d.langType;
                    var topwords = d.topwords;
                    var newsCount = d.newsCount;
                    var newsTime = d.newsTime;
                    var summarize = d.summarize;

                    if(topicName.includes("20%")){
                        topicName = topicName.replace("20%"," ")
                    }
                    if(clusterTopicName.includes("20%")){
                        clusterTopicName = clusterTopicName.replace("20%"," ")
                    }

                    var url = Feng.ctxPath + '/home/topic/detail?topicId=' + topicId +'&topicName='+topicName+'&clusterTopicName='+clusterTopicName+'&langType=' + langType+'&topwords='+topwords+'&newsCount='+newsCount+'&newsTime='+newsTime+'&summarize='+summarize;
                    return '<div style="text-align: left"><a style="color: #01AAED;" href="'+url+'" target=\"_blank\">'+d.topicName+'</a></div>';
                }
            },
            {field: 'newsCount',align: "center", sort: true, title: '新闻数量',width:100},
            {field: 'topwords',align: "center", sort: true, title: '关键词',minWidth: 300},
            {field: 'producedtime',align: "center", sort: true, title: '发布时间'},
        ]];
    };

    /**
     * 热门新闻表格的列
     */
    News.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', align: "center", hide: true, title: '新闻Id'},
            {
                field: 'title', align: "center", sort: true, title: '新闻标题',minWidth: 370, templet: function (d) {
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
                        return "<p>政治</p>";
                    }
                }
            },
            {field: 'websitename', align: "center", sort: true, title: '新闻来源'},
            {field: 'newsTime', align: "center", sort: true, title: '发布时间'},
            {fixed: 'right',align: "center",toolbar: '#newsTableBar', minWidth: 120,title: '操作'}
        ]];
    };

    /**
     * 敏感新闻表格的列
     */
    Sensitive.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', align: "center", hide: true, title: '新闻Id'},
            {
                field: 'title', align: "center", sort: true, title: '新闻标题',minWidth: 370, templet: function (d) {
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
            {fixed: 'right',align: "center",toolbar: '#sensitiveTableBar', minWidth: 120,title: '操作'}
        ]];
    };

    /**
     * 宗教新闻表格的列
     */
    Religion.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', align: "center", hide: true, title: '新闻Id'},
            {
                field: 'title', align: "center", sort: true, title: '新闻标题',minWidth: 370, templet: function (d) {
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
                        return "<p style='font-weight: bold'>其他</p>";
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
                    }else{
                        return "<p>其他</p>";
                    }
                }
            },
            {field: 'websitename', align: "center", sort: true, title: '新闻来源'},
            {field: 'newsTime', align: "center", sort: true, title: '发布时间'},
            {fixed: 'right',align: "center",toolbar: '#religionTableBar', minWidth: 120,title: '操作'}
        ]];
    };

    /**
     * 定制新闻表格的列
     */
    CustomWord.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', align: "center", hide: true, title: '新闻Id'},
            {
                field: 'title', align: "center", sort: true, title: '新闻标题',minWidth: 370, templet: function (d) {
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
            {fixed: 'right',align: "center",toolbar: '#customWordTableBar', minWidth: 120,title: '操作'}
        ]];
    };

    // 渲染表格（热门话题新闻）
    var tableResult = table.render({
        elem: '#' + Topic.tableId,
        url: Feng.ctxPath + '/topic/hot/list?langType='+$("#lang_type option:selected").val(),
        title: '话题列表',
        page: true,
        height : 500,
        cellMinWidth: 100,
        cols: Topic.initColumn(),
        initSort:{
            field:'newsTime',
            type:'desc'
        }
    });

    //主题标签
    $.get(Feng.ctxPath + '/customWord/tab', function (data) {
        $("#newsShow").css("display", "none");//解决display样式变形
        $("#sensitiveShow").css("display", "none");
        loadNewsCategryData('cn',''); //初始化加载新闻类别数据
        loadTopicCount('cn','',''); //初始化加载话题数量数据

        //查询条件
        $("#senTypeSearch").hide();
        $("#senCategorySearch").hide();
        $("#websiteSearch").hide();

        var customWordIdArray = data.ids;
        var customWordNameArray = data.names;
        var len = 0
        for(var i=0;i<customWordNameArray.length;i++){
            if(len++ < 6){
                $("#customWordTab").append("<li custom_word_id="+customWordIdArray[i]+">"+customWordNameArray[i]+"</li>");
            }
        }
    }, 'json');

    //加载热门话题数据
    function loadHotTopicData(lang,timeLimit,topwords){
        table.render({
            elem: '#' + Topic.tableId,
            url: Feng.ctxPath + '/topic/hot/list?langType='+lang+'&timeLimit='+timeLimit+'&topwords='+topwords,
            title: '热门话题',
            page: true,
            height : 500,
            cellMinWidth: 100,
            cols: Topic.initColumn(),
            initSort:{
                field:'newsTime',
                type:'desc'
            }
        });
    };

    //加载热门新闻数据
    function loadHotNewsData(lang,timeLimit,isSensitive,sensitiveCategory){
        table.render({
            elem: '#' + News.tableId,
            url: Feng.ctxPath + '/news/hot/list?langType='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory,
            title: '热门新闻',
            page: true,
            height : 500,
            cellMinWidth: 100,
            cols: News.initColumn(),
            initSort:{
                field:'newsTime',
                type:'desc'
            }
        });
    };

    //加载敏感新闻数据
    function loadSensitiveNewsData(lang,timeLimit,sensitiveCategory,websitename){
        table.render({
            elem: '#' + Sensitive.tableId,
            url: Feng.ctxPath + '/news/home/sensitive/list?isSensitive=2&timeLimit='+timeLimit+'&langType='+lang+'&sensitiveCategory='+sensitiveCategory+'&websitename='+websitename,
            title: '敏感新闻',
            page: true,
            height : 500,
            cellMinWidth: 100,
            cols: Sensitive.initColumn(),
            initSort:{
                field:'newsTime',
                type:'desc'
            }
        });
    };

    //加载宗教新闻数据
    function loadReligionNewsData(lang,timeLimit,isSensitive,sensitiveCategory){
        table.render({
            elem: '#' + Religion.tableId,
            url: Feng.ctxPath + '/news/religion/list?langType='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory,
            title: '宗教新闻',
            page: true,
            height : 500,
            cellMinWidth: 100,
            cols: Religion.initColumn(),
            initSort:{
                field:'newsTime',
                type:'desc'
            }
        });
    };

    //加载定制新闻数据
    function loadCustomWordNewsData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory){
        table.render({
            elem: '#' + CustomWord.tableId,
            url: Feng.ctxPath + '/customWord/relate/list?modular=1&id='+customWordId+'&lang='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory,
            title: '定制新闻',
            page: true,
            height : 500,
            cellMinWidth: 100,
            cols: CustomWord.initColumn(),
            initSort:{
                field:'newsTime',
                type:'desc'
            }
        });
    }

    var index = 0;
    var customWordId = "";
    //主题标签事件
    element.on('tab(all)', function(data){
        index = data.index;
        var lang =  $("#lang_type option:selected").val();
        var timeLimit = $("#timeLimit").val();
        var isSensitive = $("#sensitive_type").val();
        var sensitiveCategory = $("#sensitive_category").val();
        var websitename = $("#websitename").val();
        var topwords = $("#topwords").val();

        if(index === 0){ //热门话题
            $("#newsTableDiv").hide();
            $("#sensitiveTableDiv").hide();
            $("#religionTableDiv").hide();
            $("#customWordTableDiv").hide();
            $("#topicTableDiv").show();

            $("#newsShow").css("display", "none");
            $("#sensitiveShow").css("display", "none");
            $("#topicShow").css("display", "block");

            //查询条件
            $("#senTypeSearch").hide();
            $("#senCategorySearch").hide();
            $("#websiteSearch").hide();
            $("#topwordsSearch").show();

            //统计图
            loadHotTopicData(lang,timeLimit,topwords);
            loadNewsCategryData(lang,timeLimit);
            loadTopicCount(lang,timeLimit,topwords);
        }else if(index === 1){//热门新闻(新闻来源、敏感类型)
            $("#topicTableDiv").hide();
            $("#sensitiveTableDiv").hide();
            $("#religionTableDiv").hide();
            $("#customWordTableDiv").hide();
            $("#newsTableDiv").show();

            $("#topicShow").css("display", "none");
            $("#sensitiveShow").css("display", "none");
            $("#newsShow").css("display", "block");

            //查询条件
            $("#websiteSearch").hide();
            $("#topwordsSearch").hide();
            $("#senTypeSearch").show();
            $("#senCategorySearch").show();

            loadHotNewsData(lang,timeLimit,isSensitive,sensitiveCategory);
            loadHotNewsSourceData(lang,timeLimit,isSensitive,sensitiveCategory);
            loadHotNewsSensitiveTypeData(lang,timeLimit,isSensitive,sensitiveCategory);
        }else if(index === 2){//敏感新闻
            $("#topicTableDiv").hide();
            $("#newsTableDiv").hide();
            $("#religionTableDiv").hide();
            $("#customWordTableDiv").hide();
            $("#sensitiveTableDiv").show();

            $("#topicShow").css("display", "none");
            $("#newsShow").css("display", "none");
            $("#sensitiveShow").css("display", "block");

            //查询条件
            $("#senTypeSearch").hide();
            $("#topwordsSearch").hide();
            $("#senCategorySearch").show();
            $("#websiteSearch").show();

            loadSensitiveNewsData(lang,timeLimit,sensitiveCategory,websitename);
            loadSensitiveNewsSourceData(lang,timeLimit,sensitiveCategory,websitename);
            loadSensitiveCategoryData(lang,timeLimit,sensitiveCategory,websitename);
        }else if(index === 3){//“宗教新闻” 变更为 “新词术语”
            $("#topicTableDiv").hide();
            $("#newsTableDiv").hide();
            $("#sensitiveTableDiv").hide();
            $("#customWordTableDiv").hide();
            $("#religionTableDiv").show();

            $("#topicShow").css("display", "none");
            $("#sensitiveShow").css("display", "none");
            $("#newsShow").css("display", "block");

            //查询条件
            $("#websiteSearch").hide();
            $("#topwordsSearch").hide();
            $("#senTypeSearch").show();
            $("#senCategorySearch").show();

            loadReligionNewsData(lang,timeLimit,isSensitive,sensitiveCategory);
            loadReligionNewsSourceData(lang,timeLimit,isSensitive,sensitiveCategory);
            loadReligionNewsSensitiveTypeData(lang,timeLimit,isSensitive,sensitiveCategory);
        }else {//定制新闻
            $("#topicTableDiv").hide();
            $("#newsTableDiv").hide();
            $("#sensitiveTableDiv").hide();
            $("#religionTableDiv").hide();
            $("#customWordTableDiv").show();

            $("#topicShow").css("display", "none");
            $("#sensitiveShow").css("display", "none");
            $("#newsShow").css("display", "block");

            //查询条件
            $("#websiteSearch").hide();
            $("#topwordsSearch").hide();
            $("#senTypeSearch").show();
            $("#senCategorySearch").show();

            customWordId = $(this).attr('custom_word_id');
            loadCustomWordNewsData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory);
            loadCustomWordNewsSourceData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory)
            loadCustomWordNewsSensitiveTypeData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory);
        }
    });

    //切换语言
    form.on('select(lang_type)', function(data){
        var lang = data.value;
        var timeLimit = $("#timeLimit").val();
        var isSensitive = $("#sensitive_type").val();
        var sensitiveCategory = $("#sensitive_category").val();
        var topwords = $("#topwords").val();
        var websitename = $("#websitename").val();

        if(index === 0){ //热门话题
            loadHotTopicData(lang,timeLimit,topwords);
            loadNewsCategryData(lang,timeLimit);
            loadTopicCount(lang,timeLimit,topwords);
        }else if(index === 1){//热门新闻
            loadHotNewsData(lang,timeLimit,isSensitive,sensitiveCategory);
            loadHotNewsSourceData(lang,timeLimit,isSensitive,sensitiveCategory);
            loadHotNewsSensitiveTypeData(lang,timeLimit,isSensitive,sensitiveCategory);
        }else if(index === 2){//敏感新闻
            loadSensitiveNewsData(lang,timeLimit,sensitiveCategory,websitename);
            loadSensitiveNewsSourceData(lang,timeLimit,sensitiveCategory,websitename);
            loadSensitiveCategoryData(lang,timeLimit,sensitiveCategory,websitename);
        }else if(index === 3){//宗教新闻
            loadReligionNewsData(lang,timeLimit,isSensitive,sensitiveCategory);
            loadReligionNewsSourceData(lang,timeLimit,isSensitive,sensitiveCategory)
            loadReligionNewsSensitiveTypeData(lang,timeLimit,isSensitive,sensitiveCategory);
        }else{//定制新闻
            loadCustomWordNewsData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory);
            loadCustomWordNewsSourceData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory)
            loadCustomWordNewsSensitiveTypeData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory);
        }
    });

    //地图数据
    var dataList=[
        {name: '北京', value: 1421},
        {name: '天津', value: 1343},
        {name: '新疆', value:1276},
        {name: '重庆', value: 1185},
        {name: '河北', value: 1141},
        {name: '河南', value: 1090},
        {name: '云南', value: 1075},
        {name: '辽宁', value: 1068},
        {name: '黑龙江', value: 1050},
        {name: '湖南', value: 1014},
        {name: '安徽', value: randomValue()},
        {name: '山东', value: randomValue()},
        {name: '上海', value: randomValue()},
        {name: '江苏', value: randomValue()},
        {name: '浙江', value: randomValue()},
        {name: '江西', value: randomValue()},
        {name: '湖北', value: randomValue()},
        {name: '广西', value: randomValue()},
        {name: '甘肃', value: randomValue()},
        {name: '山西', value: randomValue()},
        {name: '内蒙古', value: randomValue()},
        {name: '陕西', value: randomValue()},
        {name: '吉林', value: randomValue()},
        {name: '福建', value: randomValue()},
        {name: '贵州', value: randomValue()},
        {name: '广东', value: randomValue()},
        {name: '青海', value: randomValue()},
        {name: '西藏', value: randomValue()},
        {name: '四川', value: randomValue()},
        {name: '宁夏', value: randomValue()},
        {name: '海南', value: randomValue()},
        {name: '台湾', value: randomValue()},
        {name: '香港', value: randomValue()},
        {name: '澳门', value: randomValue()},
        {name:"南海诸岛",value:0}
    ];
    function randomValue() {
        return Math.round(Math.random()*1000);
    }

    //地图表格数据，按数量取Top10
    function tableList(){
        var appendHTML = "";
        if($(".map-table tbody tr").length>0){
            $(".map-table tbody tr").remove();
        }
        for(var i=0; i<8; i++){
            appendHTML = "<tr><td>"+
                dataList[i].name+"</td><td>"+
                dataList[i].value+"</td></tr>";
            $(".map-table tbody").append(appendHTML);
        }
    }
    tableList();

    //话题地图
    var topicMapCharts = echarts.init(document.getElementById('topicMap'));
    var topicMapOption = {
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
                color: ['#e0ffff', '#006edd']//取值范围的颜色
            },
            show:true//图注
        },
        geo: {
            map: 'china',
            roam: false,//不开启缩放和平移
            // zoom:1.23,//视角缩放比例
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
                name: '新闻数量',
                type: 'map',
                geoIndex: 0,
                data:dataList
            }
        ]
    };
    topicMapCharts.setOption(topicMapOption);
    topicMapCharts.on('click', function (params) {
        alert(params.name+":"+params.value);
    });

    //鼠标移入表格，实现地图区域高亮
    $(".map-table tbody").find('tr').on('mouseenter',function(){
        var hang = $(this).prevAll().length;
        topicMapCharts.dispatchAction({ type: 'highlight', name:dataList[hang].name});//选中高亮
    });
    $(".map-table tbody").find('tr').on('mouseleave',function(){
        var hang = $(this).prevAll().length;
        topicMapCharts.dispatchAction({ type: 'downplay', name:dataList[hang].name});//取消高亮
    });

    //新闻分布统计
    var newsDistributionCharts = echarts.init(document.getElementById('newsDistribution'));
    newsDistributionCharts.showLoading();
    $.get(Feng.ctxPath + '/news/distribution', function (data) {
        newsDistributionCharts.hideLoading();
        newsDistributionCharts.setOption({
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                top:'30px',
                data:['中文','蒙古文','藏文','维吾尔文','外蒙古文']
            },
            calculable : true,
            series : [
                {
                    name: '新闻分布',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '58%'],
                    data:data.newsDistributionData,
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

    //敏感分布统计（全局）
    var senDistributionCharts = echarts.init(document.getElementById('senDistribution'));
    senDistributionCharts.showLoading();
    $.get(Feng.ctxPath + '/news/sensitive/distribution', function (data) {
        senDistributionCharts.hideLoading();
        senDistributionCharts.setOption({
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                top:'30px',
                data:['敏感新闻','中性新闻','正向新闻']
            },
            calculable : true,
            series : [
                {
                    name: '情感分布',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '58%'],
                    data:data.senDistributionData,
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

    //新闻统计
    var newsStaticChart = echarts.init(document.getElementById('newsStatic'));
    newsStaticChart.showLoading();
    $.get(Feng.ctxPath + '/news/static', function (data) {
        newsStaticChart.hideLoading();
        newsStaticChart.setOption({
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data:['中文', '蒙古文', '藏文', '维吾尔文','外蒙古文']
            },
            toolbox: {
                show : true,
                feature : {
                    restore : {show: true},
                    magicType : {show: true, type: ['line']}
                }
            },
            calculable : true,
            dataZoom : {
                show : true,
                realtime : true,
                start : 0,
                end :5
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
                    name: '中文',
                    type: 'bar',
                    data: data.cnNum
                },
                {
                    name: '蒙古文',
                    type: 'bar',
                    data: data.mengNum
                },
                {
                    name: '藏文',
                    type: 'bar',
                    data: data.zangNum
                },
                {
                    name: '维吾尔文',
                    type: 'bar',
                    data: data.weiNum
                },
                {
                    name: '外蒙古文',
                    type: 'bar',
                    data: data.waimengNum
                },
            ]
        })
    }, 'json');

    //新闻来源（全局）
    var newsWebCharts = echarts.init(document.getElementById('newWeb'));
    newsWebCharts.showLoading();
    $.get(Feng.ctxPath + '/news/source/global', function (data) {
        newsWebCharts.hideLoading();
        newsWebCharts.setOption({
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
                    data:data.newsSourceGlobalData,
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

    //敏感统计
    var sensitiveTrendChart = echarts.init(document.getElementById('sensitiveTrend'));
    sensitiveTrendChart.showLoading();
    $.get(Feng.ctxPath + '/news/trend', function (data) {
        sensitiveTrendChart.hideLoading();
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
                    magicType : {show: true, type: ['line']}
                }
            },
            calculable : true,
            dataZoom : {
                show : true,
                realtime : true,
                start : 0,
                end : 8
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
                    type: 'bar',
                    data: data.senNum
                },
                {
                    name: '中性新闻',
                    type: 'bar',
                    data: data.neuNum
                },
                {
                    name: '正向新闻',
                    type: 'bar',
                    data: data.forNum
                }
            ]
        })
    }, 'json');

    //新闻类别统计
    var newsCategoryChart = echarts.init(document.getElementById('newsCategory'));
    function loadNewsCategryData(lang,timeLimit){
        newsCategoryChart.showLoading();
        $.get(Feng.ctxPath + '/news/category?langType='+lang+'&timeLimit='+timeLimit, function (data) {
            newsCategoryChart.hideLoading();
            newsCategoryChart.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    show:false,
                    orient: 'vertical',
                    right: 10,
                    top: 20,
                    bottom: 20,
                    data: data.names
                },
                series: [
                    {
                        name: '新闻类别',
                        type: 'pie',
                        radius: '70%',
                        center: ['50%', '50%'],
                        data: data.newsCategoryData,
                        emphasis: {
                            itemStyle: {
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

    //话题数量
    var topicCountChart = echarts.init(document.getElementById('topicCount'));
    function loadTopicCount(lang,timeLimit,topwords){
        topicCountChart.showLoading();
        $.get(Feng.ctxPath + '/topic/count?langType='+lang+'&timeLimit='+timeLimit+'&topwords='+topwords, function (data) {
            topicCountChart.hideLoading();
            topicCountChart.setOption({
                tooltip: {
                    trigger: 'axis',
                    position: function (pt) {
                        return [pt[0], '10%'];
                    }
                },
                grid: {
                    top:'8%',
                },
                toolbox: {
                    feature: {
                        restore: {},
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: data.date
                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '50%']
                },
                dataZoom: [{
                    type: 'inside',
                    start: 0,
                    end: 100
                }, {
                    start: 0,
                    end: 100,
                    handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                    handleSize: '70%',
                    handleStyle: {
                        color: '#fff',
                        shadowBlur: 3,
                        shadowColor: 'rgba(0, 0, 0, 0.6)',
                        shadowOffsetX: 2,
                        shadowOffsetY: 2
                    }
                }],
                series: [
                    {
                        name: '',
                        type: 'line',
                        smooth: true,
                        symbol: 'none',
                        sampling: 'average',
                        itemStyle: {
                            color: 'rgb(255, 70, 131)'
                        },
                        areaStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: 'rgb(255, 158, 68)'
                            }, {
                                offset: 1,
                                color: 'rgb(255, 70, 131)'
                            }])
                        },
                        data: data.count
                    }
                ]
            })
        }, 'json');
    }

    //热门新闻来源
    var hotNewsSourceCharts = echarts.init(document.getElementById('newsSource'));
    function loadHotNewsSourceData(lang,timeLimit,isSensitive,sensitiveCategory){
        hotNewsSourceCharts.showLoading();
        $.get(Feng.ctxPath + '/news/source?langType='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory, function (data) {
            hotNewsSourceCharts.hideLoading();
            hotNewsSourceCharts.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        name: '新闻来源',
                        type: 'pie',
                        radius: '65%',
                        center: ['50%', '50%'],
                        data:data.newsSourceData,
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

    //敏感新闻来源
    var sensitiveSourceCharts = echarts.init(document.getElementById('senSource'));
    function loadSensitiveNewsSourceData(lang,timeLimit,sensitiveCategory,websitename){
        sensitiveSourceCharts.showLoading();
        $.get(Feng.ctxPath + '/news/source?isSensitive=2&langType='+lang+'&timeLimit='+timeLimit+'&sensitiveCategory='+sensitiveCategory+'&websitename='+websitename, function (data) {
            sensitiveSourceCharts.hideLoading();
            sensitiveSourceCharts.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        name: '新闻来源',
                        type: 'pie',
                        radius: '75%',
                        center: ['50%', '50%'],
                        data:data.newsSourceData,
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

    //宗教新闻来源
    var religionSourceCharts = echarts.init(document.getElementById('newsSource'));
    function loadReligionNewsSourceData(lang,timeLimit,isSensitive,sensitiveCategory){
        religionSourceCharts.showLoading();
        $.get(Feng.ctxPath + '/news/religion/source?langType='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory, function (data) {
            religionSourceCharts.hideLoading();
            religionSourceCharts.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        name: '新闻来源',
                        type: 'pie',
                        radius: '75%',
                        center: ['50%', '50%'],
                        data:data.religionNewsSourceData,
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

    //定制新闻来源
    var customwordNewsSourceCharts = echarts.init(document.getElementById('newsSource'));
    function loadCustomWordNewsSourceData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory){
        customwordNewsSourceCharts.showLoading();
        $.get(Feng.ctxPath + '/customWord/relate/news/source?modular=1&id='+customWordId+'&lang='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory, function (data) {
            customwordNewsSourceCharts.hideLoading();
            customwordNewsSourceCharts.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series : [
                    {
                        name: '新闻来源',
                        type: 'pie',
                        radius: '75%',
                        center: ['50%', '50%'],
                        data:data.newsSourceData,
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

    //热门新闻敏感类型
    var hotNewsSensitiveTypeCharts = echarts.init(document.getElementById('sensitiveType'), myEchartsTheme);
    function loadHotNewsSensitiveTypeData(lang,timeLimit,isSensitive,sensitiveCategory){
        hotNewsSensitiveTypeCharts.showLoading();
        $.get(Feng.ctxPath + '/news/sensitive/type?langType='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory, function (data) {
            hotNewsSensitiveTypeCharts.hideLoading();
            hotNewsSensitiveTypeCharts.setOption({
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

    //宗教新闻敏感类型
    var religionNewsSensitiveTypeCharts = echarts.init(document.getElementById('sensitiveType'), myEchartsTheme);
    function loadReligionNewsSensitiveTypeData(lang,timeLimit,isSensitive,sensitiveCategory){
        religionNewsSensitiveTypeCharts.showLoading();
        $.get(Feng.ctxPath + '/news/religion/sensitive?langType='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory, function (data) {
            religionNewsSensitiveTypeCharts.hideLoading();
            religionNewsSensitiveTypeCharts.setOption({
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

    //定制新闻敏感类型
    var customWordNewsSensitiveTypeCharts = echarts.init(document.getElementById('sensitiveType'), myEchartsTheme);
    function loadCustomWordNewsSensitiveTypeData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory){
        customWordNewsSensitiveTypeCharts.showLoading();
        $.get(Feng.ctxPath + '/customWord/relate/sensitive/type?modular=1&id='+customWordId+'&lang='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory, function (data) {
            customWordNewsSensitiveTypeCharts.hideLoading();
            customWordNewsSensitiveTypeCharts.setOption({
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

    //敏感类别
    var sensitiveCategoryCharts = echarts.init(document.getElementById('senCategory'));
    function loadSensitiveCategoryData(lang,timeLimit,sensitiveCategory,websitename){
        sensitiveCategoryCharts.showLoading();
        $.get(Feng.ctxPath + '/news/home/sensitive/category?isSensitive=2&langType='+lang+'&timeLimit='+timeLimit+'&sensitiveCategory='+sensitiveCategory+'&websitename='+websitename, function (data) {
            sensitiveCategoryCharts.hideLoading();
            sensitiveCategoryCharts.setOption({
                title: {
                    text: ''
                },
                tooltip: {
                    trigger : 'item'
                },
                grid:{
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true,
                    height:220
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
                    name : '',
                    type : 'value',
                    boundaryGap : [ 0, 0.01 ]
                } ],
                series: [{
                    type: 'bar',
                    barWidth : 50,
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
                                    "#1E90FF"
                                ];
                                return colorList[params.dataIndex];
                            },
                            label : {
                                show : true,
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

    // 热门新闻工具条点击事件
    table.on('tool(' + News.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'showNewsDetail') {
            News.openDetailDlg(data);
        }
    });

    // 敏感新闻工具条点击事件
    table.on('tool(' + Sensitive.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if(layEvent === 'showSensitiveDetail'){
            Sensitive.openDetailDlg(data);
        }
    });

    // 定制新闻工具条点击事件
    table.on('tool(' + Religion.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'showReligionDetail') {
            Religion.openDetailDlg(data);
        }
    });

    // 定制新闻工具条点击事件
    table.on('tool(' + CustomWord.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'showCustomWordDetail') {
            CustomWord.openDetailDlg(data);
        }
    });

    //查看热门新闻详情
    News.openDetailDlg = function (data) {
        func.open({
            title: '详细信息',
            content: Feng.ctxPath + '/home/news/detail?newsId=' + data.newsId+'&lang='+data.langType,
            tableId: News.tableId
        });
    };

    //查看敏感新闻详情
    Sensitive.openDetailDlg = function (data) {
        func.open({
            title: '详细信息',
            content: Feng.ctxPath + '/home/news/detail?newsId=' + data.newsId+'&lang='+data.langType,
            tableId: Sensitive.tableId
        });
    };

    //查看宗教新闻详情
    Religion.openDetailDlg = function (data) {
        func.open({
            title: '详细信息',
            content: Feng.ctxPath + '/home/news/detail?newsId=' + data.newsId+'&lang='+data.langType,
            tableId: Religion.tableId
        });
    };

    //查看定制新闻详情
    CustomWord.openDetailDlg = function (data) {
        func.open({
            title: '详细信息',
            content: Feng.ctxPath + '/home/news/detail?newsId=' + data.newsId+'&lang='+data.langType,
            tableId: CustomWord.tableId
        });
    };

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    //热门话题搜索
    Topic.search = function () {
        var lang = $("#lang_type").val();
        var timeLimit = $("#timeLimit").val();
        var topwords = $("#topwords").val();

        loadHotTopicData(lang,timeLimit,topwords);
        loadNewsCategryData(lang,timeLimit);
        loadTopicCount(lang,timeLimit,topwords);
    };
    //热门新闻搜索
    News.search = function () {
        var lang = $("#lang_type").val();
        var timeLimit = $("#timeLimit").val();
        var isSensitive = $("#sensitive_type").val();
        var sensitiveCategory = $("#sensitive_category").val();

        loadHotNewsData(lang,timeLimit,isSensitive,sensitiveCategory);
        loadHotNewsSourceData(lang,timeLimit,isSensitive,sensitiveCategory);
        loadHotNewsSensitiveTypeData(lang,timeLimit,isSensitive,sensitiveCategory);
    };
    //敏感新闻搜索
    Sensitive.search = function () {
        var lang = $("#lang_type").val();
        var timeLimit = $("#timeLimit").val();
        var sensitiveCategory = $("#sensitive_category").val();
        var websitename = $("#websitename").val();
        loadSensitiveNewsData(lang,timeLimit,sensitiveCategory,websitename);
        loadSensitiveNewsSourceData(lang,timeLimit,sensitiveCategory,websitename);
        loadSensitiveCategoryData(lang,timeLimit,sensitiveCategory,websitename);
    };
    //宗教新闻搜索
    Religion.search = function () {
        var lang = $("#lang_type").val();
        var timeLimit = $("#timeLimit").val();
        var isSensitive = $("#sensitive_type").val();
        var sensitiveCategory = $("#sensitive_category").val();

        loadReligionNewsData(lang,timeLimit,isSensitive,sensitiveCategory);
        loadReligionNewsSourceData(lang,timeLimit,isSensitive,sensitiveCategory)
        loadReligionNewsSensitiveTypeData(lang,timeLimit,isSensitive,sensitiveCategory);
    };

    //定制新闻搜索
    CustomWord.search = function () {
        var lang = $("#lang_type").val();
        var timeLimit = $("#timeLimit").val();
        var isSensitive = $("#sensitive_type").val();
        var sensitiveCategory = $("#sensitive_category").val();

        loadCustomWordNewsData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory);
        loadCustomWordNewsSourceData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory)
        loadCustomWordNewsSensitiveTypeData(customWordId,lang,timeLimit,isSensitive,sensitiveCategory);
    };

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        if(index === 0){ //热门话题搜索
            Topic.search();
        }else if(index === 1){//热门新闻搜索
            News.search();
        }else if(index === 2){//敏感新闻搜索
            Sensitive.search();
        }else if(index === 3){//宗教新闻
            Religion.search();
        }else{//定制新闻搜索
            CustomWord.search();
        }
    });
});
