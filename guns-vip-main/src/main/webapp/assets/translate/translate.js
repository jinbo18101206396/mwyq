layui.use(['table', 'ax', 'func', 'layer', 'element','form'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;
    var layer = layui.layer;
    var element = layui.element;
    var form = layui.form;

    // 点击翻译按钮
    $('#translate').click(function() {
        var transParam={
            "sourceLang":$("#sourceLang option:selected").val(),
            "sourceContent":$("#sourceContent").text(),
            "targetLang":$("#targetLang option:selected").val(),
            "transModel":$("#transModel option:selected").val()
        };
        $.ajax({
            type:"POST",
            url:"/translate/content",
            contentType: "application/json",
            dataType:"json",
            data:JSON.stringify(transParam),
            success:function (data) {
                $("#targetContent").html(data.targetContent);
            }
        });
    });
});
