layui.use(['table', 'ax', 'func', 'layer', 'element','form','carousel'], function () {
    var $ = layui.$;
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        var keyword = $("#weibowords").val();

        if($.trim(keyword) == ""){
            alert("关键字不能为空。");
            return;
        }

        var url = Feng.ctxPath + "search/" + keyword;
        $.get(url,function (data) {
            $("#coreDiv").empty();
            $.each(data,function(i,d){
                var template = $("#contentDiv").clone();
                // 1、标题
                if(d.productName != '' && d.productName != undefined)
                    template.find("#title").html("位置：" + d.productName);
                else{
                    if(type == "file"){
                        template.find("#title").html("文件位置：" + d.id);
                    }else template.find("#title").html(d.id);

                }
                // 2、内容
                template.find("#content").html(d.productDesc + d.content);
                // 3、作者
                if(d.author != '' && d.author != undefined)  template.find("#author").html("作者：" + d.author);
                // template.find("#createTime").text(d.createTime);
                template.show();
                var html = template.html().replace(new RegExp('undefined','gm'),'')
                $("#coreDiv").append(html);
            })
        })
    });
    var table = layui.table;
    var $ax = layui.ax;

    var func = layui.func;
});
