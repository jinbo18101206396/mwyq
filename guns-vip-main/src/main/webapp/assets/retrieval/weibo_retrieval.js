layui.use(['table', 'ax', 'func', 'layer', 'element', 'form', 'carousel'], function () {
    var $ = layui.$;
    var func = layui.func;

    //初始化加载数据
    loadWeiboData("中国", "", "cn","all", "all", "month");

    //加载微博概览数据
    function loadBasicData(cnWord, minWord, weibosNum) {
        $("#basicDataDiv").append("<span style=\"font-size:15px;font-weight:bold;\">中文关键词：</span><span style=\"font-size:15px;color: red;\">" + cnWord + "</span>\n" +
            "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">民文关键词：</span><span style=\"font-size:15px;color: red;\">" + minWord + "</span>\n" +
            "<span style=\"font-size:15px;font-weight:bold;margin-left: 40px;\">微博总数：</span><span style=\"font-size:15px;color: red;\">" + weibosNum + "</span>\n");
    }

    //加载微博列表数据
    function loadWeiboList(weiboList,cnWord,minWord) {
        let minWords = []
        if (minWord.includes(",")) {
            minWords = minWord.split(",")
        } else {
            minWords.push(minWord)
        }
        var addhtml = "";
        if (weiboList != null && weiboList != "undefined") {
            for (var j = 0; j < weiboList.length; j++) {
                var weibo = weiboList[j];
                console.log(weibo)
                var sentiment = weibo.sentiment;
                var weiboId = weibo.id;
                var content = weibo.content;
                content = content.replace(cnWord, '<span style="color:red">' + cnWord + '</span>')
                for (let i = 0; i < minWords.length; i++) {
                    min = minWords[i];
                    content = content.replace(min, '<span style="color:red">' + min + '</span>')
                }
                var authorName = weibo.author_name;
                var url = weibo.article_url;
                var location = weibo.location;
                if(location == "" || location == null){
                    location = "未知";
                }
                var time = weibo.create_time;
                time = time.substr(0, 10)
                var likeCount = weibo.like_count;
                var commentCount = weibo.comment_count;
                var transmitCount = weibo.transmit_count;

                if (sentiment == "3") {
                    sentiment = "<span style=\"color:green\">正向</span>";
                } else if (sentiment == '2') {
                    sentiment = "<span style=\"color:red\">负向</span>";
                } else {
                    sentiment = "<span style=\"color:green\">中性</span>";
                }

                addhtml += "<div style='width: 100%;margin-bottom: 60px;text-align: left;vertical-align: middle;'>" +
                    "<a href=/retrieval/weibo/detail/page?id=" + weiboId + " className=\"layui-table-link\" target=\"_blank\"><div  style=\"width: 100%;float:left;font:13px '微软雅黑';color: #61B2FC;text-align: left;margin-bottom: 20px;\">" + content + "</div>\n" +
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">" + authorName + "</div>" +
                    "<div  style=\"width: 10%;float:left;font:12px '微软雅黑';text-align: center;\">" + location + "</div>" +
                    "<div  style=\"width: 10%;float:left;font:12px '微软雅黑';text-align: center;\">" + sentiment + "</div>" +
                    "<div  style=\"width: 15%;float:left;font:11px '微软雅黑';text-align: center;\">" + time + "</div>\n" +
                    "<div  style=\"width: 15%;float:left;font:12px '微软雅黑';text-align: center;\">点赞量：" + likeCount + "</div>" +
                    "<div  style=\"width: 15%;float:left;font:12px '微软雅黑';text-align: center;\">评论量：" + commentCount + "</div>" +
                    "<div  style=\"width: 15%;float:left;font:12px '微软雅黑';text-align: center;\">转发量：" + transmitCount + "</div>" +
                    "</div> <hr />"
            }
        }
        $("#weiboListDiv").append(addhtml);
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

    //加载热门微博数据
    function loadHotWeibo(hotWeiboList) {
        if (hotWeiboList != null && hotWeiboList != "undefined") {
            var hotWeiboDiv = "";
            for (var k = 0; k < hotWeiboList.length; k++) {
                var hotWeibo = hotWeiboList[k];
                var sentiment = hotWeibo.sentiment;
                var content = hotWeibo.content;
                var authorName = hotWeibo.author_name;
                var url = hotWeibo.article_url;
                var location = hotWeibo.location;
                var likeCount = hotWeibo.like_count;
                var commentCount = hotWeibo.comment_count;
                var transmitCount = hotWeibo.transmit_count;

                if (sentiment == "3") {
                    sentiment = "正向";
                } else if (sentiment == '2') {
                    sentiment = "负向";
                } else {
                    sentiment = "中性";
                }

                hotWeiboDiv += "<div style='margin-bottom: 50px;'>" +
                    "<a href=" + url + " className=\"layui-table-link\" target=\"_blank\"><div  style=\"width: 100%;float:left;font:13px '微软雅黑';color: #61B2FC;text-align: left;margin-bottom: 10px;\">" + content + "</div>\n" +
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">" + authorName + "</div>" +
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">" + sentiment + "</div>" +
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">点赞量：" + likeCount + "</div>" +
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">评论量：" + commentCount + "</div>" +
                    "<div  style=\"width: 20%;float:left;font:12px '微软雅黑';text-align: center;\">转发量：" + transmitCount + "</div>" +
                    "</div> <hr />"
            }
            $("#hotWeiboDiv").append(hotWeiboDiv);
        }
    }

    function loadWeiboData(keyword, blogger, srclang,tgtlang, sensitive, cycle) {
        $.get(Feng.ctxPath + '/retrieval/search/weibo/es?keyword=' + keyword + '&blogger=' + blogger + '&srclang=' + srclang + '&tgtlang=' + tgtlang+ '&sensitive=' + sensitive + '&cycle=' + cycle, function (data) {
            console.log(data)
            var cnWord = data.cnWord;
            var minWord = data.minWord;
            var weiboList = data.weiboList;
            var hotWeiboList = data.hotWeiboList;
            var positiveNum = data.positiveNum;
            var negativeNum = data.negativeNum;
            var neutralNum = data.neutralNum;
            var weibosNum = data.weibosNum;

            //基本数据展示
            loadBasicData(cnWord, minWord, weibosNum);
            //微博列表展示
            loadWeiboList(weiboList,cnWord,minWord);
            //情感分析饼图
            loadWeiboEmotion(positiveNum, negativeNum, neutralNum);
            //热门微博列表
            loadHotWeibo(hotWeiboList);
        }, 'json');
    }

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        var keyword = $("#weibo_key_words").val();
        var blogger = $("#blogger").val();
        var srclang = $("#weibo_src_lang").val();
        var tgtlang = $("#weibo_tgt_lang").val();
        var sensitive = $("#weibo_sensitive").val();
        var cycle = $("#weibo_cycle").val();
        if ($.trim(keyword) == "") {
            alert("微博关键词不能为空！");
            return;
        }
        //清空div
        $("#basicDataDiv").html("");
        $("#weiboListDiv").html("");
        $("#hotWeiboDiv").html("");

        loadWeiboData(keyword, blogger, srclang,tgtlang, sensitive, cycle);
    });
});
