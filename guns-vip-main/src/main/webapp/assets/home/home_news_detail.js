/**
 * 详情对话框
 */
var HomeNewsInfoDlg = {
    data: {
        newsContent: "",
        keyWords: ""
    }
};

layui.use(['form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;

    //获取新闻内容、关键词
    var ajax = new $ax(Feng.ctxPath + "/news/dialog/detail?newsId=" + Feng.getUrlParam("newsId"));
    var result = ajax.start();
    $("#newsContent").html(result.data.newsContent);
    $("#sensitiveWords").html(result.data.sensitiveWords);
    $("#keyWords").html(result.data.keyWords);

    var PersonChart = echarts.init(document.getElementById("person"));
    var LocationChart = echarts.init(document.getElementById("location"));
    var OrganizationChart = echarts.init(document.getElementById("organization"));

    //获取词云数据
    $.get(Feng.ctxPath + '/entity/wordcloud?newsId='+Feng.getUrlParam("newsId"), function (data) {

        //人物词云
        PersonChart.setOption({
            title:{
                text:'人物词云',
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
                data:data.person
            }]
        });
        //地点词云
        LocationChart.setOption({
            title:{
                text:'人物词云',
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
                data:data.location
            }]
        });
        //组织机构词云
        OrganizationChart.setOption({
            title:{
                text:'人物词云',
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
                data:data.organization
            }]
        });
    },"json");
});