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
    var Religion = {
        tableId:"religionTable"
    };

    /**
     * 初始化表格的列
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
                field: 'sensitive', align: "center", sort: true, title: '敏感类型', templet: function (d) {
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
            {
                field: 'sensitiveWords',align: "center", sort: true, title: '敏感词',minWidth: 200,templet:function (d) {
                    return '<div style="text-align: left">'+d.sensitiveWords+'</div>';
                }},
            {
                field: 'keyWords',align: "center", sort: true, title: '关键词',minWidth: 170,templet:function (d) {
                    return '<div style="text-align: left">'+d.keyWords+'</div>';
                }},
            {field: 'websitename', align: "center", sort: true, title: '新闻来源'},
            {field: 'newsTime', align: "center", sort: true, title: '发布时间'},
            {fixed: 'right',align: "center",toolbar: '#religionTableBar', minWidth: 120,title: '操作'}
        ]];
    };

    /**
     * 渲染表格
     */
    var tableResult = table.render({
        elem: '#' + Religion.tableId,
        url: Feng.ctxPath + '/news/religion/list',
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

    //初始化加载情感分布数据
    loadReligionNewsSensitiveTypeData('','','','','','','','');
    //初始化加载新闻来源数据
    loadReligionNewsSourceData('','','','','','','','');

    //加载宗教新闻数据
    function loadReligionNewsData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit){
        var queryData = {};
        queryData['langType'] = langType;
        queryData['websitename'] = websitename;
        queryData['isSensitive'] = isSensitive;
        queryData['sensitiveWords'] = sensitiveWords;
        queryData['keyWords'] = keyWords;
        queryData['sensitiveCategory'] = sensitiveCategory;
        queryData['timeLimit'] = timeLimit;
        table.reload(Religion.tableId, {
            where: queryData, page: {curr: 1}
        });
    }

    //宗教新闻敏感类型
    var religionNewsSensitiveTypeCharts = echarts.init(document.getElementById('senType'), myEchartsTheme);
    function loadReligionNewsSensitiveTypeData(lang,timeLimit,isSensitive,sensitiveCategory,sensitiveWords,keyWords,websitename){
        $.get(Feng.ctxPath + '/news/religion/sensitive?langType='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory+'&sensitiveWords='+sensitiveWords+'&keyWords='+keyWords+'&websitename='+websitename, function (data) {
            religionNewsSensitiveTypeCharts.setOption({
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                color:["green","blue","red"],
                series : [
                    {
                        name: '敏感分布',
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

    //宗教新闻来源
    var religionSourceCharts = echarts.init(document.getElementById('religionNewsSource'));
    function loadReligionNewsSourceData(lang,timeLimit,isSensitive,sensitiveCategory,sensitiveWords,keyWords,websitename){
        $.get(Feng.ctxPath + '/news/religion/source?langType='+lang+'&timeLimit='+timeLimit+'&isSensitive='+isSensitive+'&sensitiveCategory='+sensitiveCategory+'&sensitiveWords='+sensitiveWords+'&keyWords='+keyWords+'&websitename='+websitename, function (data) {
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

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        var langType = $("#lang").val();
        var websitename = $("#newsSource").val();
        var isSensitive = $("#sensitiveType").val();
        var sensitiveWords = $("#sensitiveWords").val();
        var keyWords = $("#keyWords").val();
        var sensitiveCategory = $("#sensitiveCategory").val();
        var timeLimit = $("#timeLimit").val();
        loadReligionNewsData(langType,websitename,isSensitive,sensitiveWords,keyWords,sensitiveCategory,timeLimit);
        loadReligionNewsSensitiveTypeData(langType,timeLimit,isSensitive,sensitiveCategory,sensitiveWords,keyWords,websitename);
        loadReligionNewsSourceData(langType,timeLimit,isSensitive,sensitiveCategory,sensitiveWords,keyWords,websitename);
    });

    //查看宗教新闻详情
    Religion.openDetailDlg = function (data) {
        func.open({
            title: '详细信息',
            content: Feng.ctxPath + '/home/news/detail?newsId=' + data.newsId+'&lang='+data.langType,
            tableId: Religion.tableId
        });
    };

    /**
     * 点击纠偏
     *
     * @param data 点击按钮时候的行数据
     */
    Religion.openEditDlg = function (data) {
        func.open({
            title: '新闻纠偏',
            content: Feng.ctxPath + '/sensitive/edit?newsId=' + data.newsId,
            tableId: Religion.tableId
        });
    };

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 工具条点击事件
    table.on('tool(' + Religion.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'showReligionDetail') {
            Religion.openDetailDlg(data);
        }else if (layEvent === 'edit') {
            Religion.openEditDlg(data);
        }
    });

    // 窗口大小改变事件
    window.onresize = function () {

    };
});
