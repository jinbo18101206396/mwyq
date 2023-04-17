layui.use(['table', 'ax', 'func', 'layer', 'element', 'form', 'carousel'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;
    var layer = layui.layer;
    var element = layui.element;
    var form = layui.form;
    var laydate = layui.laydate;

    //初始化加载数据
    loadWebsiteNews("中国", "cn-meng", '', '');

    //页面返回时加载数据
    var keyWords = $("#keyWords").val();
    var langType = $("#langType").val();
    $("#website_lang").find("option[value='" + langType + "']").prop("selected", true);
    loadWebsiteNews(keyWords, langType, '', '');

    //加载微博概览数据
    function loadBasicData(keyword, queryString, newsNum) {
        $("#basicDataDiv").append(
            "<span style=\"font-size:15px;font-weight:bold;\">中文关键词：</span><span style=\"font-size:18px;color: red;\">" + keyword + "</span>" +
            "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">民文关键词：</span><span style=\"font-size:18px;color: red;\">" + queryString + "</span>\n" +
            "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">新闻总数：</span><span style=\"font-size:18px;color: red;\">" + newsNum + "</span>\n"
            // "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">敏感类型：</span><span style=\"font-size:18px;color: red;\"></span>\n" +
            // "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">检索周期：</span><span style=\"font-size:18px;color: red;\"></span>\n"
        );
    }

    //加载新闻列表数据
    function loadNewsList(keyWords, newsList) {
        var langType = $("#website_lang").val();
        var addhtml = "";
        if (newsList != null && newsList != "undefined") {
            for (var j = 0; j < newsList.length; j++) {
                var news = newsList[j];
                var content = news.newsContent;
                var title = news.newsTitle;
                var newsId = news.newsId;
                var newsTime = news.newsTime;
                addhtml += "<div style=\"width:100%;height:4%;text-align:left;vertical-align: center;\">" +
                    "<a href=\"/retrieval/news/detail/page?newsId=" + newsId + "&keyWords=" + keyWords + "&langType=" + langType + "\">" +
                    "<div style=\"width:80%;float:left;font:40px;color: #00a0e9;display:block;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\">" + title + "</div>" +
                    "<div style=\"width:20%;float:left;font:20px;\">" + newsTime + "</div>" +
                    "</a>" +
                    "</div><hr/>";
            }
        }
        $("#newsListDiv").append(addhtml);
    }

    //加载倾向性分析数据
    var sensitiveChart = echarts.init(document.getElementById('senChartDiv'));
    function loadWeiboEmotion(positive, negative, neutral) {
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
                data: ['正向', '中性', '负向']
            },
            color: ["green", "blue", "red"],
            series: [
                {
                    name: '倾向性',
                    type: 'pie',
                    radius: '80%',
                    center: ['55%', '55%'], //图的位置，距离左跟上的位置
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: true,
                            position: 'inside',
                            formatter: '{d}%',//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。{d}数据会根据value值计算百分比

                            textStyle: {
                                align: 'center',
                                baseline: 'middle',
                                fontFamily: '-webkit-pictograph',
                                color: '#3cefe6',
                                fontSize: 12,
                                fontWeight: 'bolder'
                            }
                        }
                    },
                    data: [
                        {value: positive, name: '正向'},
                        {value: neutral, name: '中性'},
                        {value: negative, name: '负向'}
                    ]
                }
            ]
        };
        sensitiveChart.setOption(option);
    }

    //加载热门新闻列表
    function loadHotNews(keyWords, hotNewsList) {
        var langType = $("#website_lang").val();
        var hotNewsDiv = "";
        for (var k = 0; k < hotNewsList.length; k++) {
            var hotNews = hotNewsList[k];
            var title = hotNews.newsTitle;
            var newsId = hotNews.newsId;
            var newsTime = hotNews.newsTime;
            hotNewsDiv += "<div style=\"width:100%;margin-bottom:5px;text-align:left;vertical-align:middle;\">" +
                "<a href=\"/retrieval/news/detail/page?newsId=" + newsId + "&keyWords=" + keyWords + "&langType=" + langType + "\">" +
                "<div style=\"width:70%;float:left;font:20px;color:#1E90FF;display:block;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\">" + title + "</div>" +
                "<div style=\"width:30%;float:right;font:5px;\">" + newsTime + "</div>" +
                "</a>" +
                "</div><hr/>";
        }
        $("#hotNewsDiv").append(hotNewsDiv);
    }

    function loadWebsiteNews(keyword, lang, sensitive, cycle) {
        $.get(Feng.ctxPath + '/retrieval/search/news?keyword=' + keyword + '&lang=' + lang + '&sensitive=' + sensitive + '&cycle=' + cycle, function (data) {
            var newsList = data.newsList;
            var hotNewsList = data.hotNewsList;
            var newsNum = data.newsNum;
            var queryString = data.queryString;
            $("#newsListDiv").html("");
            $("#hotNewsDiv").html("");
            $("#basicDataDiv").html("");

            //加载新闻概览
            loadBasicData(keyword, queryString, newsNum);
            //加载新闻列表数据
            loadNewsList(keyword, newsList);
            //加载热门新闻列表
            loadHotNews(keyword, hotNewsList);
            //情感分析饼图
            loadWeiboEmotion(data.positiveNum, data.negativeNum, data.neutralNum);
        }, 'json');
    }

    $('#btnSearch').click(function () {
        var keyword = $("#weibo_key_words").val();
        var lang = $("#website_lang").val();
        var sensitive = $("#weibo_sensitive").val();
        var cycle = $("#weibo_cycle").val();

        if ($.trim(keyword) == "" || $.trim(lang) == "" || $.trim(sensitive) == "" || $.trim(cycle) == "") {
            alert("请输入必要的检索条件！");
            return;
        }
        loadWebsiteNews(keyword, lang, sensitive, cycle);
    });
});
