layui.use(['table', 'ax', 'func', 'layer', 'element','form','carousel'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;
    var carousel = layui.carousel;

    //建造实例
    carousel.render({
        elem: '#test1'
        ,width: '100%' //设置容器宽度
        ,arrow: 'always' //始终显示箭头
        //,anim: 'updown' //切换动画方式
    });

    //监听轮播切换事件
    carousel.on('change(test1)', function(obj){ //test1来源于对应HTML容器的 lay-filter="test1" 属性值
        console.log(obj.index); //当前条目的索引
        console.log(obj.prevIndex); //上一个条目的索引
        console.log(obj.item); //当前条目的元素对象
    });
});
