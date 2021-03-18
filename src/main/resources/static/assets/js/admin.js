
//JavaScript代码区域
layui.use('element', function () {
    var element = layui.element;

});

function loadLayUITable(table, url){
    table.render({
        elem: '#test'
        , url: url
        // , toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
        // , defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
        //     title: '添加'
        //     , layEvent: 'add_record'
        //     , icon: 'layui-icon-addition'
        // }]
        , title: '用户数据表'
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'ID', width: 80, sort: true}
            , {field: 'title', title: '计划名称', width: 250, edit: 'text'}
            , {field: 'description', title: '待完成项', edit: 'text', minWidth: 150}
            , {field: 'progress', title: '完成进度', width: 100, edit: 'text'}
            , {field: 'anticipation', title: '预期进度', edit: 'text', width: 100}
            , {field: 'remarks', title: '描述', edit: 'text'}
            , {field: 'finished', title: '是否完成', width: 110, templet: '#checkboxTpl', unresize: true}
            , {fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
        ]]
        , page: true
    });
    //初始时间显示
    var myDate = new Date;
    var year = myDate.getFullYear(); //获取当前年
    var mon = myDate.getMonth() + 1; //获取当前月
    var date = myDate.getDate(); //获取当前日
    $('#currtime').html(year + "-" + mon + "-" + date);
}

//table，后面是laydate
layui.use(['laydate', 'table'], function () {

    var table = layui.table;
    var form = layui.form;
    var $ = layui.$;
    loadLayUITable(table,"/admin/sche/today");
    //选择今天数据
    $('#query-today').click(function (){
        loadLayUITable(table, "/admin/sche/today");
    });
    //添加数据
    $('#add-record').click(function (){
        $.post({
            url: "/admin/sche/add",
            success:function (data) {
                loadLayUITable(table,"/admin/sche/today");
            }
        });
    });
    //监听单元格编辑
    table.on('edit(test)', function (obj) {
        var value = obj.value //得到修改后的值
            , id = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段
        var map = {};
        map[obj.field] = value;
        map["id"] = id.id;

        $.post({
            url: "/admin/sche/edit",
            data: {
                "map" : JSON.stringify(map)
            },
            success: function (data) {
                // layer.msg('[ID: ' + id.id + '] ' + field + ' 字段更改为：' + value);
            }
        });
    });

    //头工具栏事件
    // table.on('toolbar(test)', function (obj) {
    //     var checkStatus = table.checkStatus(obj.config.id);
    //     switch (obj.event) {
    //         case 'getCheckData':
    //             var data = checkStatus.data;
    //             layer.alert(JSON.stringify(data));
    //             break;
    //         case 'getCheckLength':
    //             var data = checkStatus.data;
    //             layer.msg('选中了：' + data.length + ' 个');
    //             break;
    //         case 'nowDate':
    //             loadLayUITable(table, "/admin/sche/today");
    //             break;
    //
    //         //自定义头工具栏右侧图标 - 添加
    //         case 'add_record':
    //             $.post({
    //                 url: "/admin/sche/add",
    //                 success:function (data) {
    //                     location.reload();
    //                 }
    //             });
    //             break;
    //     }
    //     ;
    // });

    //监听行工具事件
    table.on('tool(test)', function(obj){
        var data = obj.data;
        //console.log(obj)
        if(obj.event === 'del'){
            layer.confirm('是否删除该条记录', function(index){
                $.post({
                    url: "/admin/sche/del",
                    data: {
                        "id" : data.id
                    },
                    success:function (data) {
                        location.reload();
                    }
                });
            });
        }
    });

    //监听完成按钮操作
    form.on('switch(finished)', function (obj2) {
        //finished=this.value
        layer.tips(obj2.elem.checked ? "完成的不错哦" : "哈哈，还需努力", obj2.othis);
        var map = {};
        map[this.title] = obj2.elem.checked ? 1 : 0;
        map["id"] = this.name;

        $.post({
            url: "/admin/sche/edit",
            data: {
                "map" : JSON.stringify(map)
            },
            success: function (data) {
                // layer.msg('[ID: ' + id.id + '] ' + field + ' 字段更改为：' + value);
            }
        });
    });

    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#date' //指定元素
        , range: true
        , trigger: "click"//加入click事件
        , done: function(value, date, endDate){     //选择后回调

            table.reload('test', {
                url: "/admin/sche/range"
                ,where: {    //设定异步数据接口的额外参数
                    "start" : date.year + "-" + date.month + "-" + date.date + " 00:00:00",
                    "end" : endDate.year + "-" + endDate.month + "-" + endDate.date + " 23:59:59"
                }
                , done: function(){
                    if(date.year === endDate.year && date.month === endDate.month && date.date === endDate.date)
                        $('#currtime').html(date.year + "-" + date.month + "-" + date.date);
                    else
                        $('#currtime').html(value);
                }
            });

        }
    });
});


//控制侧边栏点击显示div
$(function () {
    $("#result .list:not(:first)").hide();
    //获取点击事件的对象
    $("#accordion dd").click(function () {

        // console.log($("#accordion li a").index(this));
        // console.log($(this).siblings());
        // console.log("事件触发");
        //获取要显示或隐藏的对象
        var divShow = $("#result").children('.list');
        // console.log(divShow);
        //判断当前对象是否被选中，如果没选中的话进入if循环
        if (!$(this).hasClass('selected')) {
            //获取当前对象的索引
            var index = $("#accordion dd").index(this);
            //当前对象添加选中样式并且其同胞移除选中样式；
            $(this).addClass('selected').siblings('dd').removeClass('selected');
            //索引对应的div块显示
            var curr = divShow[index];
            $(curr).show();
            $(curr).addClass('layui-anim layui-anim-fadein')
            //索引对应的div块的同胞隐藏
            $(curr).siblings('.list').hide();
            $(curr).siblings('.list').remove('layui-anim layui-anim-fadein');
        }
    });
});