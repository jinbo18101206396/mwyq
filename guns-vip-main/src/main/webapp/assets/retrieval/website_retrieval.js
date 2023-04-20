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
    loadWebsiteNews("教育", "all", "all", "month");

    //页面返回时加载数据
    var keyWords = $("#keyWords").val();
    var langType = $("#langType").val();
    $("#website_lang").find("option[value='" + langType + "']").prop("selected", true);
    loadWebsiteNews(keyWords, langType, '', '');

    //加载微博概览数据
    function loadBasicData(keyword, queryString, newsNum, cycle) {
        $("#basicDataDiv").append(
            "<span style=\"font-size:15px;font-weight:bold;\">中文关键词：</span><span style=\"font-size:18px;color: red;\">" + keyword + "</span>" +
            "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">民文关键词：</span><span style=\"font-size:18px;color: red;\">" + queryString + "</span>\n" +
            "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">新闻总数：</span><span style=\"font-size:18px;color: red;\">" + newsNum + "</span>\n"
            // "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">检索周期：</span><span style=\"font-size:18px;color: red;\">" + cycle + "</span>\n"
            // "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">敏感类型：</span><span style=\"font-size:18px;color: red;\">全部</span>\n"
        );
    }

    //加载新闻列表数据
    function loadNewsList(keyWords, minWord, newsArray) {
        let minWords = []
        if (minWord.includes(",")) {
            minWords = minWord.split(",")
        } else {
            minWords.push(minWord)
        }
        var addhtml = "";
        if (newsArray.length > 0) {
            for (let j = 0; j < newsArray.length; j++) {
                var newsObject = newsArray[j];
                var newsTitle = newsObject.news_title;
                newsTitle = newsTitle.replace(keyWords, '<span style="color:red">' + keyWords + '</span>')
                for (let i = 0; i < minWords.length; i++) {
                    min = minWords[i];
                    newsTitle = newsTitle.replace(min, '<span style="color:red">' + min + '</span>')
                }
                var newsUrl = newsObject.news_url
                var newsTime = newsObject.news_time;
                newsTime = newsTime.substr(0, 10)
                var sensitive = newsObject.is_sensitive

                if (sensitive == "1") {
                    sensitive = "<span style=\"color:blue\">中性</span>";
                } else if (sensitive == "2") {
                    sensitive = "<span style=\"color:red\">负向</span>";
                } else {
                    sensitive = "<span style=\"color:green\">正向</span>";
                }
                addhtml += "<div style=\"width:100%;height:4%;text-align:left;vertical-align: center;\">" +
                    "<a href=" + newsUrl + " className=\"layui-table-link\" target=\"_blank\">" +
                    // "<a href=\"/retrieval/news/detail/page?newsId=" + newsId + "&langType=" +langType+"&newsTitle="+newsTitle+"&newsUrl="+newsUrl+"&keyWords="+keyWords+ " \">" +
                    "<div style=\"width:80%;float:left;font:40px;color: #00a0e9;display:block;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\">" + newsTitle + "</div>" +
                    "<div style=\"width:10%;float:left;font:20px;\">" + sensitive + "</div>" +
                    "<div style=\"width:10%;float:left;font:20px;\">" + newsTime + "</div>" +
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
    function loadHotNews(keyWords, hotNewsArray) {
        var hotNewsDiv = "";
        if (hotNewsArray.length > 0) {
            for (let k = 0; k < hotNewsArray.length; k++) {
                var hotNewsObject = hotNewsArray[k];
                var title = hotNewsObject.news_title;
                var newsTime = hotNewsObject.news_time;
                newsTime = newsTime.substr(0, 10)
                var newsUrl = hotNewsObject.news_url;
                hotNewsDiv += "<div style=\"width:100%;margin-bottom:5px;text-align:left;vertical-align:middle;\">" +
                    "<a href=" + newsUrl + " className=\"layui-table-link\" target=\"_blank\">" +
                    // "<a href=\"/retrieval/news/detail/page?newsId=" + newsId + "&keyWords=" + keyWords + "&langType=" + langType + "\">" +
                    "<div style=\"width:80%;float:left;font:20px;color:#1E90FF;display:block;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;\">" + title + "</div>" +
                    "<div style=\"width:20%;float:right;font:10px;\">" + newsTime + "</div>" +
                    "</a>" +
                    "</div><hr/>";
            }
        }
        $("#hotNewsDiv").append(hotNewsDiv);
    }

    function loadWebsiteNews(keyword, lang, sensitive, cycle) {
        $.get(Feng.ctxPath + '/retrieval/search/news/es?keyword=' + keyword + '&lang=' + lang + '&sensitive=' + sensitive + '&cycle=' + cycle, function (data) {
            var newsArray = data.newsArray;
            var hotNewsArray = data.hotNewsArray;
            var newsNum = data.newsNum;
            var cnWord = data.cnWord;
            var minWord = data.minWord;
            var positiveNum = data.positiveNum;
            var negativeNum = data.negativeNum;
            var neutralNum = data.neutralNum;

            $("#newsListDiv").html("");
            $("#hotNewsDiv").html("");
            $("#basicDataDiv").html("");

            //加载新闻概览
            loadBasicData(cnWord, minWord, newsNum, cycle);
            //加载新闻列表数据
            loadNewsList(cnWord, minWord, newsArray);
            //加载热门新闻列表
            loadHotNews(cnWord, hotNewsArray);
            //情感分析饼图
            loadWeiboEmotion(positiveNum, negativeNum, neutralNum);
        }, 'json');
    }

    $('#btnSearch').click(function () {
        var keyword = $("#key_words").val();
        var lang = $("#website_lang").val();
        var sensitive = $("#sensitive").val();
        var cycle = $("#website_cycle").val();

        if ($.trim(keyword) == "" || $.trim(lang) == "") {
            alert("关键词和检索语言不能为空！");
            return;
        }
        loadWebsiteNews(keyword, lang, sensitive, cycle);
    });
});
