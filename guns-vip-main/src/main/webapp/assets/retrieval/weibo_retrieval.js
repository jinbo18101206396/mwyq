layui.use(['table', 'ax', 'func', 'layer', 'element','form','carousel'], function () {
    var $ = layui.$;
    var func = layui.func;

    //初始化加载数据
    loadWeiboData("中国","author-all","cn","emotion-all","time-all");

    //加载微博概览数据
    function loadBasicData(keyword,allNum,earlyTime,latestTime){
        $("#basicDataDiv").append("<span style=\"font-size:15px;font-weight:bold;\">关键词：</span><span style=\"font-size:15px;color: red;\">"+keyword+"</span>\n" +
            "                        <span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">微博总数：</span><span style=\"font-size:15px;color: red;\">"+allNum+"</span>\n" +
            "                        <span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">起始时间：</span><span style=\"font-size:15px;color: red;\">"+earlyTime+"</span>\n" +
            "                        <span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">终止时间：</span><span style=\"font-size:15px;color: red;\">"+latestTime+"</span>\n"+
            "                        <span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">更新时间：</span><span style=\"font-size:15px;color: red;\">"+latestTime+"</span>");
    }

    //加载微博列表数据
    function loadWeiboList(docResList){
        var addhtml="";
        if(docResList != null && docResList != "undefined"){
            for(var j=0;j<docResList.length;j++){
                var docres = docResList[j];
                var emotion = docres.weibo_emotion;
                if(emotion == "positive"){
                    emotion = "正向";
                }else if(emotion == 'negative'){
                    emotion = "负向";
                }else{
                    emotion = "中性";
                }
                addhtml+="<div style=\"width: 100%;margin-bottom: 50px;text-align: left;vertical-align: middle;\">\n" +
                    "                            <div  style=\"width: 100%;font:15px '微软雅黑';margin-bottom: 15px;\">"+docres.weibo_content+"</div>\n" +
                    "                            <div  style=\"width: 25%;float:left;font:11px '微软雅黑';color: #00a0e9;\">"+docres.weibo_author+"</div>\n" +
                    "                            <div  style=\"width: 15%;float:left;font:11px '微软雅黑';color: #00a0e9;\">"+emotion+"</div>\n" +
                    "                            <div  style=\"width: 15%;float:left;font:11px '微软雅黑';color: #00a0e9;\">"+docres.weibo_time+"</div>\n" +
                    "                            <div  style=\"width: 15%;float:left;font:11px '微软雅黑';color: #00a0e9;\">点赞量："+docres.weibo_likes+"</div>\n" +
                    "                            <div  style=\"width: 15%;float:left;font:11px '微软雅黑';color: #00a0e9;\">评论量："+docres.weibo_comments+"</div>\n" +
                    "                            <div  style=\"width: 15%;float:left;font:11px '微软雅黑';color: #00a0e9;\">转发量："+docres.weibo_forwards+"</div>\n" +
                    "                        </div><hr />";
            }
        }
        $("#weiboListDiv").append(addhtml);
    }

    //加载倾向性分析数据
    var sensitiveChart = echarts.init(document.getElementById('senChartDiv'));
    function loadWeiboEmotion(positive,negative,neutral) {
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            icon: "circle",
            legend: {
                orient: 'vertical',
                left: '5%',  //图例距离左的距离
                y: '5%',  //图例上下居中
                textStyle: { //图例文字的样式
                    fontSize: 17
                },
                data: ['正向','中性','负向']
            },
            color:["green","blue","red"],
            series: [
                {
                    name:'倾向性',
                    type:'pie',
                    radius:'80%',
                    center: ['55%', '55%'], //图的位置，距离左跟上的位置
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: true,
                            position: 'inside',
                            formatter: '{d}%',//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。{d}数据会根据value值计算百分比

                            textStyle : {
                                align : 'center',
                                baseline : 'middle',
                                fontFamily : '-webkit-pictograph',
                                color: '#3cefe6',
                                fontSize : 12,
                                fontWeight : 'bolder'
                            }
                        }
                    },
                    data:[
                        {value:positive, name:'正向'},
                        {value:neutral, name:'中性'},
                        {value:negative, name:'负向'}
                    ]
                }
            ]
        };
        sensitiveChart.setOption(option);
    }

    //加载热门微博数据
    function loadHotWeibo(hotWeiboList) {
        if(hotWeiboList != null && hotWeiboList != "undefined"){
            var hotWeiboDiv = "";
            for(var k=0;k<hotWeiboList.length;k++){
                var hotWeibo = hotWeiboList[k];
                var emotion = hotWeibo.weibo_emotion;
                var time = hotWeibo.weibo_time;
                if(emotion == "positive"){
                    emotion = "正向";
                }else if(emotion == 'negative'){
                    emotion = "负向";
                }else{
                    emotion = "中性";
                }
                hotWeiboDiv += "<div style='margin-bottom: 50px;'>" +
                    "<div  style=\"width: 100%;float:left;font:13px '微软雅黑';color: #61B2FC;text-align: left;margin-bottom: 10px;\">"+hotWeibo.weibo_content+"</div>\n"+
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">"+hotWeibo.weibo_author+"</div>"+
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">"+emotion+"</div>"+
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">点赞量："+hotWeibo.weibo_likes+"</div>"+
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">评论量："+hotWeibo.weibo_comments+"</div>"+
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">转发量："+hotWeibo.weibo_forwards+"</div>"+
                    "</div> <hr />"
            }
            $("#hotWeiboDiv").append(hotWeiboDiv);
        }
    }

    function loadWeiboData(keyword,scope,lang,sensitive,cycle){
        $.get(Feng.ctxPath + '/retrieval/search/weibo?keyword=' + keyword+'&scope='+scope+'&lang='+lang+'&sensitive='+sensitive+'&cycle='+cycle, function (data) {
            //基本数据展示
            loadBasicData(keyword,data.allNum,data.earlyTime,data.latestTime);
            //微博列表展示
            loadWeiboList(data.docResList);
            //情感分析饼图
            loadWeiboEmotion(data.positiveNum,data.negativeNum,data.neutralNum);
            //热门微博列表
            loadHotWeibo(data.hotWeiboList);
        }, 'json');
    }

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        var keyword = $("#weibo_key_words").val();
        var scope = $("#weibo_scope").val();
        var lang = $("#weibo_lang").val();
        var sensitive = $("#weibo_sensitive").val();
        var cycle = $("#weibo_cycle").val();
        if($.trim(keyword) == "" || $.trim(scope) == "" || $.trim(lang) == "" || $.trim(sensitive) == "" || $.trim(cycle) == ""){
            alert("请输入必要的检索条件！");
            return;
        }
        //清空div
        $("#basicDataDiv").html("");
        $("#weiboListDiv").html("");
        $("#hotWeiboDiv").html("");

        loadWeiboData(keyword,scope,lang,sensitive,cycle);
    });
});
