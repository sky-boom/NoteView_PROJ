
//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['element', 'layer'], function() {
    var element = layui.element;
    var layer = layui.layer;
    //…
});

/**
 *  ************************目录视图点击事件****************************
 */

//*******右键菜单*******

//屏蔽浏览器右键菜单
document.oncontextmenu = function(){
    return false;
}
//按下鼠标右键(dir)
$(document).on("mousedown", "#dir-system li a, #dir-system dd a",function(e){
    //文件系统选中效果
    $("#dir-system li, #dir-system dd").removeClass("selected");
    $(this).parent().addClass("selected");

    var key = e.which; //获取鼠标键位
    if(key == 3)  //(1:代表左键； 2:代表中键； 3:代表右键)
    {
        //获取右键点击坐标
        var x = e.clientX;
        var y = e.clientY;

        $("#rightmouse").show().css({left:x + 10 - 130,top:y + 3 - 80});  //这个-120和-70原因不明
    }
});

//点击任意部位隐藏右键菜单
$(document).click(function(){
    $("#rightmouse").hide();
});

//******** 剪切板(移动) *******
$(function (){
    var cut = undefined; //剪切板

    //剪切监听
    $("#rightmouse #cut").click(function (){
        cut = $("#dir-system .selected");
        layer.msg("成功剪切", {icon: 1});
        console.log(cut);
    });

    //移动监听
    $("#rightmouse #move").click(function (){
        //如果目标target不是目录
        if(typeof $("#dir-system .selected").attr("dir_id") == "undefined" ) {
            layer.msg("请选择目录后再移动!", {icon: 0});
            return ;
        }
        //判断是目录剪切还是file剪切
        if(typeof $(cut).attr("dir_id") == "undefined")
            cutFile2target(cut, $("#dir-system .selected"));
        else
            cutDir2target(cut, $("#dir-system .selected"));
    });

    //剪切板内容全部移动到目标目录
    function cutDir2target(cutDom, targetDom){
        if(typeof cutDom == "undefined"){
            layer.msg("剪切板中没有内容", {icon:0});
            return false;
        }

        //如果不存在子目录
        var isEmpty = false;
        if($(targetDom).children("ul").length === 0) {
            $(targetDom).append("<ul></ul>");
            isEmpty = true;
        }

        //开始移动
        $.post({
            url: "/dir/move",
            data:{
                "currId" : $(cutDom).attr("dir_id"),
                "targetId" : $(targetDom).attr("dir_id"),
                "isDir" : true
            },
            success:function (data) {
                //如果当前目录下面没有目录
                console.log($(".selected>ul").length);
                if(isEmpty) {
                    $(".selected>a>i").removeClass("fa-folder-o");
                    $(".selected>a>i").addClass("fa-folder-open");
                }

                if($(cutDom).siblings().length === 0)    //如果剪切的目标隔壁没有其他文件夹了
                    $(cutDom).parent().remove();    //拜拜
                $(targetDom).children("ul").append($(cutDom));

                //更新图标，如果该目录下没有子目录，则将其更改fa图标
                $("#dir-system li a").each(function(){
                    if($(this).parent().children("ul").length == 0) {
                        $(this).children("i").removeClass("fa-folder fa-folder-open").addClass("fa-folder-o");
                    }
                });
                cut = undefined;    //清除剪切板
                layer.msg("移动成功!", {icon:1});
            }
        });

    }

    function cutFile2target(cutDom, targetDom){
        if(typeof cutDom == "undefined"){
            layer.msg("剪切板中没有内容", {icon:0});
            return false;
        }

        //如果不存在子目录
        var isEmpty = false;
        if($(targetDom).children("ul").length === 0) {
            $(targetDom).append("<ul></ul>");
            isEmpty = true;
        }

        //开始移动
        $.post({
            url: "/dir/move",
            data:{
                "currId" : $(cutDom).attr("art_id"),
                "targetId" : $(targetDom).attr("dir_id"),
                "isDir" : false
            },
            success:function (data) {
                //如果当前目录下面没有目录
                console.log($(".selected>ul").length);
                if(isEmpty) {
                    $(".selected>a>i").removeClass("fa-folder-o");
                    $(".selected>a>i").addClass("fa-folder-open");
                }

                if($(cutDom).siblings().length === 0)    //如果剪切的目标隔壁没有其他文件夹了
                    $(cutDom).parent().remove();    //删除<ul>
                else
                    $(cutDom).remove();     //只删除<li>
                $(targetDom).children("ul").append($(cutDom).prop("outerHTML").replace(/dd/g, "li"));

                //更新图标，如果该目录下没有子目录，则将其更改fa图标
                $("#dir-system li a").each(function(){
                    if($(this).parent().children("ul").length == 0) {
                        $(this).children("i").removeClass("fa-folder fa-folder-open").addClass("fa-folder-o");
                    }
                });
                cut = undefined;    //清除剪切板
                layer.msg("移动成功!", {icon:1});
            }
        });
    }
});


//********普通点击********

//文件系统单击选中效果
$(document).on("click", "#dir-system li a.dir", function (){
    $("#rightmouse").hide();
    $("#dir-system li").removeClass("selected");
    $(this).parent().addClass("selected");
    //选出该目录下的所有file
    var dir_id = $(this).parent("li").attr("dir_id");
    if(typeof dir_id != "undefined")
        getAllDocByDirId(dir_id);
    return false;   //阻止触发父点击事件
});

//文件系统下拉效果
$(document).on("click", "#dir-system li a.dir>i", function (){
    //修改图标
    $(this).toggleClass("fa-folder");
    $(this).toggleClass("fa-folder-open");
    //下拉效果
    $(this).parent().parent().children("ul").slideToggle('fast');
    //文件系统选中效果
    $("#dir-system li").removeClass("selected");
    $(this).parent().parent().addClass("selected");
    // console.log("我被点击啦");
    return false;   //阻止触发父点击事件
});

//文件系统双击事件
$(document).on("dblclick", "#dir-system li a.dir", function (){
    //修改图标
    $(this).children("i").toggleClass("fa-folder");
    $(this).children("i").toggleClass("fa-folder-open");
    //下拉效果
    $(this).parent().children("ul").slideToggle('fast');
    //文件系统选中效果
    $("#dir-system li").removeClass("selected");
    $(this).parent().addClass("selected");
    // console.log("我被点击啦");
});

/**
 *  ************************构造目录视图****************************
 */

    /*
        [
          {
            "dir_id": 1,
            "dir_name": "test",
            "parent_dir_id": -1,
            "children": [...],
            "files": [...]
          },
          {
            "dir_id": 2,
            "dir_name": "spring",
            "parent_dir_id": -1,
            "children": [...],
            "files": [...]
          }
       ]
     */

function getChildHtml(TreeNode, level) {
    var html = '<ul>';
    for(var node of TreeNode)   //遍历每个目录
    {
        //获取基础信息+child
        /**
         *  <li class="level-x" dir_id="" parent_id="" >
         *      <a>
         *          <i class="fa fa-folder"></i><span>dir_name</span>
         *      </a>
         *      <ul>
         *          <li class="level-x" dir_id="" parent_id="" >...
         *      </ul>
         *      <ul>
         *          <li class=level-x><a href="">file1</a></li>
         *          <li class=level-x><a href="">file2</a></li>
         *      </ul>
         *  </li>
         */
        html +=
            '<li class="level-' + level + '" dir_id="' + node.dir_id + '" parent_id="' + node.parent_dir_id + '" >' +
            '   <a class="dir">' +
            '       <i class="fa fa-folder"></i><span>' + node.dir_name + '</span>' +
            '   </a>';
        if(node.children.length > 0)
        {
            html += getChildHtml(node.children, level + 1);  //递归添加子目录
        }
        if(node.docs.length > 0)
        {
            html += getDocsHtml(node.docs, level + 1);    //添加文件
        }
        html += '</li>';

    }
    html += '</ul>';

    return html;
}

function getDocsHtml(docs, level){
    var html = '<ul class="files">';
    for (var f of docs){   //f:File
        html +=
            '<li class="level-' + level + '" art_id="' + f.art_id + '">' +
            '   <a href="/content/blog/' + f.art_id + '" target="_blank">' +
            '       <i class="fa fa-file"></i><span>' + f.title + '</span>' +
            '   </a>' +
            '</li>'
    }
    html += '</ul>';
    return html;
}
/**
 *  ************************file-system 增删改查****************************
 */
//********************** dir *********************

//ajax获取json，交由getChildTree()构造前端目录树
function getTree() {
    $.get({
        url: "/dir/getall",
        dataType:'json',
        success:function (data) {
            $("#dir-system").append(getChildHtml(data, 1));
            //文件系统的折叠展开功能
            $(function(){
                $("#dir-system ul>li>ul").hide();	//2级菜单之后都隐藏(只显示1级菜单)

                //更新图标，如果该目录下没有子目录，则将其更改fa图标
                $("#dir-system li a.dir").each(function(){
                    if($(this).parent().children("ul").length == 0) {
                        $(this).children("i").removeClass("fa-folder").addClass("fa-folder-o");
                    }
                });
                //点击空白区域取消选择
                $("#dir-system").on("click", function (){
                    // console.log("div被点击");
                    $("#dir-system li").removeClass("selected");
                });

            });
            // console.log(getChildHtml(data, 1));
        }
    });
}

// 新建目录
function create_dir(){
    // var curr = $("#dir-system .selected>a>span").text();
    layer.prompt({
        formType: 0,
        value: '',
        title: '新建文件夹'
    }, function(value, index, elem){    //得到输入的value

        var parent_id = $(".selected").attr("dir_id");  //当前选中的id，作为新目录的父id
        if($("#dir-system .selected").length == 0)  //如果需要在根目录上添加，则父目录记为-1
            parent_id = -1;

        $.post({
            url: "/dir/add",
            data:{
                "dir_name" : value,
                "parent_id": parent_id
            },
            success:function (data) {	//data: dir_item
                var lv = 0;
                if(parent_id == -1)
                    lv = 1;
                else
                    lv = parseInt($(".selected").prop("className").replace(/[^0-9]/ig, "")) + 1;
                var html =
                    '<li class="level-' + lv + '" dir_id="' + data.dir_id + '" parent_id="' + data.parent_dir_id + '" >' +
                    '   <a class="dir">' +
                    '       <i class="fa fa-folder"></i><span>' + data.dir_name + '</span>' +
                    '   </a>' +
                    '</li>';
                // console.log(html);
                if($("#dir-system>ul").length == 0) { //如果是全空目录
                    // console.log("执行了第一条");
                    $("#dir-system").html('<ul>' + html + '</ul>');
                }else if($(".selected").length == 0) { //如果当前没有选任何目录，即默认选择根目录
                    // console.log("执行了第二条");
                    $("#dir-system>ul").append(html);
                }else if($(".selected>ul").length == 0) { //如果当前目录下面没有目录
                    // console.log("执行了第三条");
                    $(".selected").append('<ul>' + html + '</ul>');
                    $(".selected>a>i").removeClass("fa-folder-o");
                    $(".selected>a>i").addClass("fa-folder-open");
                }else {
                    // console.log("执行了第四条");
                    $(".selected>ul").append(html);
                }
                //更新图标，如果该目录下没有子目录，则将其更改fa图标
                $("#dir-system li a").each(function(){
                    if($(this).parent().children("ul").length == 0) {
                        $(this).children("i").removeClass("fa-folder").addClass("fa-folder-o");
                    }
                });
            }
        });
        layer.close(index);
    });
}

//删除目录
function delete_dir(){
    var dir_id = $(".selected").attr("dir_id");
    if(typeof(dir_id) == "undefined")
    {
        layer.msg('请选择一个文件夹', {icon: 2});
        return false;
    }
    layer.confirm('确定删除吗？', function(index){
        //do something
        var length = $("#dir-system .selected").children("ul").length;
        var dir_id = $(".selected").attr("dir_id");
        if(length > 0)
        {
            layer.confirm('该目录存在子目录(文件), 确定删除吗？', function(index)
            {
                toDelete(dir_id);
                layer.close(index);
            });
        }
        else
        {
            toDelete(dir_id);
            layer.close(index);
        }
    });
}

//删除目录
function toDelete(dir_id){
    $.post({
        url: "/dir/delete",
        data:{
            "dir_id" : dir_id
        },
        success:function (data) {	//data: treeNode
            $(".selected").remove();
            layer.msg('删除成功!', {icon: 1});
        }
    });
}

//重命名目录
function rename_dir(){
    var dir_id = $(".selected").attr("dir_id");
    if(typeof(dir_id) == "undefined")
    {
        layer.msg('请选择一个文件夹', {icon: 2});
        return false;
    }
    var curr = $("#dir-system .selected>a>span").text();
    layer.prompt({
        formType: 0,
        value: curr,
        title: '重命名'
    }, function(value, index, elem){
        $.post({
            url: "/dir/rename",
            data:{
                "dir_id" : dir_id,
                "new_name" : value
            },
            success:function (data) {	//data: treeNode
                $(".selected>a>span").text(value);
            }
        });

        layer.close(index);
    });
}

//********************* file *********************
//获取全部File，添加至右侧列表
function getAllDoc(){
    $.post({
        url: "/dir/getdoc",
        success:function (data) {
            var root = '<dl class="files" id="file-root" hidden>';  //筛选父目录为-1的file
            for( var info of data ) {
                var html =
                    '<li>\n' +
                    '	<div class="dir-file">\n' +
                    '		<p class="title">\n' +
                    '			<a href="/content/blog/' + info.art_id + '" target="_blank">' + info.title + '</a>\n' +
                    '		</p>\n' +
                    '		<div class="operation">\n' +
                    '			<span class="">\n' +
                    '			  <a href="/content/edit/' + info.art_id + '">编辑</a>\n' +
                    '			  <a href="/content/blog/' + info.art_id + '" target="_blank">查看</a>\n' +
                    '			  <a onclick="hideDoc(' + info.art_id + ')">隐藏</a>\n' +
                    '			</span>\n' +
                    '		</div>\n' +
                    '		<div class="update">\n' +
                    '			<span>最近更新: <span>' + data2ago(info.update_time) + '</span></span>\n' +
                    '		</div>\n' +
                    '	</div>\n' +
                    '</li>'
                $("#file-list").append(html);
                //筛选父目录为-1的doc，放到dir-system
                if(info.dir_id === -1) {
                    root +=
                        '<dd class="level-1" art_id="' + info.art_id + '">' +
                        '   <a href="/content/blog/' + info.art_id + '" target="_blank">' +
                        '       <i class="fa fa-file"></i><span>' + info.title + '</span>' +
                        '   </a>' +
                        '</dd>'
                }
            }
            root += '</dl>';
            setTimeout(function (){
                $("#dir-system").append(root);
            }, 100);
        }
    });
}

//获取某目录下的doc，添加至右侧列表
function getAllDocByDirId(dir_id){
    $.post({
        url: "/dir/doc/" + dir_id,
        success:function (data) {
            $("#file-list li").remove();
            for( var info of data ) {
                var html =
                    '<li>\n' +
                    '	<div class="dir-file">\n' +
                    '		<p class="title">\n' +
                    '			<a href="/content/blog/' + info.art_id + '" target="_blank">' + info.title + '</a>\n' +
                    '		</p>\n' +
                    '		<div class="operation">\n' +
                    '			<span class="">\n' +
                    '			  <a href="/content/edit/' + info.art_id + '">编辑</a>\n' +
                    '			  <a href="/content/blog/' + info.art_id + '">查看</a>\n' +
                    '			  <a onclick="delete_file(' + info.art_id + ')">移除</a>\n' +
                    '			</span>\n' +
                    '		</div>\n' +
                    '		<div class="update">\n' +
                    '			<span>最近更新: <span>' + data2ago(info.update_time) + '</span></span>\n' +
                    '		</div>\n' +
                    '	</div>\n' +
                    '</li>'
                $("#file-list").append(html);
            }
        }
    });
}

//日期转成“几天前”格式
function data2ago(pretime) {
    // pretime = "2021-01-29T09:31:48.000+00:00";
    pretime.match(/(.+)T(.+)\..*/);
    var dateTimeStamp = RegExp.$1 + " " + RegExp.$2;

    var minute = 1000 * 60;
    var hour = minute * 60;
    var day = hour * 24;
    var halfamonth = day * 15;
    var month = day * 30;

    //这是第一次打开页面时调用
    return getDate(dateTimeStamp);

    function getDate(dateTimeStamp) {

        if (dateTimeStamp == undefined) {

            return false;
        } else {
            dateTimeStamp = dateTimeStamp.replace(/\-/g, "/");

            var sTime = new Date(dateTimeStamp).getTime(); //把时间pretime的值转为时间戳

            var now = new Date().getTime(); //获取当前时间的时间戳

            var diffValue = now - sTime;

            if (diffValue < 0) {
                console.log("结束日期不能小于开始日期！");
            }

            var monthC = diffValue / month;
            var weekC = diffValue / (7 * day);
            var dayC = diffValue / day;
            var hourC = diffValue / hour;
            var minC = diffValue / minute;

            if (monthC >= 1) {
                return parseInt(monthC) + "个月前";
            } else if (weekC >= 1) {
                return parseInt(weekC) + "周前";
            } else if (dayC >= 1) {
                return parseInt(dayC) + "天前";
            } else if (hourC >= 1) {
                return parseInt(hourC) + "小时前";
            } else if (minC >= 1) {
                return parseInt(minC) + "分钟前";
            } else {
                return("刚刚");
            }
        }
    }
}

//是否显示parent_id为-1的file
$("#dir-header-button").click(function (){
    $("#file-root").toggle();   //显现隐藏
});

//隐藏file
function hideDoc(art_id){
    layer.confirm('确定隐藏吗？', function(index){
        //其实就是把doc的父id设置为-1
        $.post({
            url: "/dir/hidedoc/" + art_id,
            success:function (data) {
                layer.alert('隐藏成功!', {icon: 1});
                layer.close(index);
            }
        });
    });
}