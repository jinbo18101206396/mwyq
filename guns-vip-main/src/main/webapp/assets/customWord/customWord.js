layui.use(['table', 'admin', 'ax','laydate','form', 'func','upload'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var form = layui.form;
    var func = layui.func;
    var upload = layui.upload;

    /**
     * 定制词表管理
     */
    var CustomWord = {
        tableId: "customWordTable"
    };

    /**
     * 初始化表格的列
     */
    CustomWord.initColumn = function () {
        return [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', hide: true, title: '主键id'},
            {
                field: 'name', align: "center", sort: true, title: '主题词', templet: function (d) {
                    var url = Feng.ctxPath + '/customWord/relate?customWordId=' + d.id+'&lang='+d.lang;
                    return '<a style="color: #01AAED;" href="' + url + '">' + d.name + '</a>';
                }
            },
            {
                field: 'type', align: "center", sort: true, title: '所属领域', templet: function (d) {
                    var url = Feng.ctxPath + '/customWord/field?type=' + d.type +'&lang='+d.lang;
                    if (d.type === '0') {
                        return '<a style="color: #01AAED;" href="' + url + '">民族宗教</a>';
                    } else if(d.type === '1'){
                        return '<a style="color: #01AAED;" href="' + url + '">语言文字</a>';
                    }else if(d.type === '2'){
                        return '<a style="color: #01AAED;" href="' + url + '">国家安全</a>';
                    }else{
                        return '<a style="color: #01AAED;" href="' + url + '">其他</a>';
                    }
                }
            },
            {field: 'userName',align: "center",sort: true, title: '创建人'},
            {field: 'createTime',align: "center", sort: true, title: '创建时间'},
            {field: 'updateTime',align: "center", sort: true, title: '更新时间'},
            {field: 'status',align: "center", sort: true, templet: '#statusTpl', title: '状态'},
            {fixed: 'right',align: "center",toolbar: '#tableBar', title: '操作', width:150}
        ]];
    };

    /**
     * 点击查询按钮
     */
    CustomWord.search = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['type'] = $("#type").val();
        queryData['status'] = $("#status").val();
        queryData['timeLimit'] = $("#timeLimit").val();
        queryData['lang'] = $("#lang").val();
        queryData['userName'] = $("#userName").val();
        table.reload(CustomWord.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    CustomWord.openAddDlg = function () {
        func.open({
            title: '添加主题词',
            content: Feng.ctxPath + '/customWord/add',
            tableId: CustomWord.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    CustomWord.openEditDlg = function (data) {
        func.open({
            title: '修改主题词',
            content: Feng.ctxPath + '/customWord/edit?id=' + data.id,
            tableId: CustomWord.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    CustomWord.exportExcel = function () {
        var checkRows = table.checkStatus(CustomWord.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 导入excel
     */
    var importExcel = upload.render({
        elem: '#btnImp'
        , url: '/excel/uploadExcel'
        ,accept: 'file'
        , done: function (res) {
            table.reload(CustomWord.tableId, {url: Feng.ctxPath + "/excel/getUploadData"});
        }
        , error: function () {
            //请求异常回调
        }
    });

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CustomWord.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/customWord/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(CustomWord.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 修改主题词状态
     */
    CustomWord.changeStatus = function (customWordId, checked) {
        var ajax = new $ax(Feng.ctxPath + "/customWord/changeStatus", function (data) {
            Feng.success("修改成功!");
        }, function (data) {
            Feng.error("修改失败!" + data.responseJSON.message);
            table.reload(CustomWord.tableId);
        });
        ajax.set("customWordId", customWordId);
        ajax.set("status", checked);
        ajax.start();
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + CustomWord.tableId,
        url: Feng.ctxPath + '/customWord/list',
        title: '主题词表',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: CustomWord.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        CustomWord.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        CustomWord.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        CustomWord.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + CustomWord.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            CustomWord.openEditDlg(data);
        } else if (layEvent === 'delete') {
            CustomWord.onDeleteItem(data);
        }
    });

    // 修改主题状态
    form.on('switch(status)', function (obj) {
        var customWordId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;
        CustomWord.changeStatus(customWordId, checked);
    });
});
