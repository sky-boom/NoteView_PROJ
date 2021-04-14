// $(window).resize(function () {          //当浏览器大小变化时
// 		//浏览器高度 - header(50px) - footer(24px) = 编辑器应有的高度
// 		var height = parseInt($(document).height() - 50 - 24);	
// 		$('#test-editormd').height(height);
// });
$(function () {
    document.getElementsByTagName("body")[0].style.height = document.body.scrollHeight + "px";
    var testEditor = editormd("test-editormd", {
        placeholder: "从这里开始开启MarkDown之旅!!",
        width: "100%",
        height: 680,
        path: '/markdown/lib/',
        tex: true,
        emoji: true,
        lineNumbers: false,
        syncScrolling: "single",
        editorTheme: "neo", 	//左侧编辑器主题
        saveHTMLToTextarea : true,  //允许获取左侧HTML文章样式
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUpload: true,
        imageUploadURL: "/img/upload",
        onload: function () {
            // 引入插件 执行监听方法
            editormd.loadPlugin("/markdown/plugins/image-handle-paste/image-handle-paste", function () {
                testEditor.imagePaste();
            });
        }
    });
});

//发布弹出
$(function (){

    $("#curtain, #content").hide(); //隐藏幕布和内容

    $("#publish").click(function (){
        $("#curtain, #content").show();
    });

    $("#curtain, #concel").click(function() {
        $("#curtain, #content").hide();
    });
});

//添加文章小标签样式
var tag_count = $("#show_tags div").length; //全局个数，也是编号
$(function (){
    //添加标签
    $("#add_tags").click(function (){
       if(tag_count >= 3)
       {
           $("#add_tip").html("只允许添加3个标签").show().delay(3000).fadeOut("slow");
           return false;
       }
       var tag = $("input[name='search_tags']").val();
       if(tag != "") {
           var html =
               '      <div>\n' +
               '           <input type="hidden" name="tags[' + tag_count + ']" value="' + tag + '">\n' +
               '           <span>' + tag + '</span>\n' +
               '           <a>×</a>\n' +
               '      </div>'
           $("#show_tags").append(html);
           tag_count ++;
       }
    });
    //删除标签
    $("#show_tags").delegate("a", "click", function (){
        $(this).parent().fadeOut(500, function (){
            $(this).remove(); //删除整个div
        });
        tag_count --;
    });
});

layui.use(['form', 'element'], function(){
    var element = layui.element;
    var form = layui.form;
    //监听提交
    form.on('submit', function(data){
        var info = data.field;
        console.log(info);
        /**
         * info对象完整属性如下：[√][-]是article的属性，[×]不是article的属性，[-]尚未添加的属性
         *
         *  info.title          [√]
         *  info.type           [√]     0原创，1转载
         *
         *  info.dir_id         [×]     结合dir-sysstem使用
         *  info.dir_name       [×]
         *  info.tags[0]        [×]     最后要合并为data.tags
         *  info.tags[1]..      [×]     最后要合并为data.tags
         *  info.search_tags    [×]
         *  info.isEdit         [x]     标记是编辑模式or发布模式, 0 or 1
         *  info.art_id         [x]     编辑模式下才有的art_id
         *
         *  info.create_time    [-]     无需添加，默认为NULL即可
         *  info.update_time    [-]     无需添加，默认为NULL即可
         *  info.user_id        [-]     由controller层赋值
         *  info.tags           [-]     待合并
         *  info.content        [-]     由Jquery获取
         *  info.published      [-]     存入草稿为0，发布时为1
         *  info.comments_num   [-]     发布时初始化为0
         *  info.hits           [-]     发布时初始化为0
         *  info.likes          [-]     发布时初始化为0
         *
         */
        var tags = "";   //合并标签，分隔符","
        for (var i = 0; i <= 2; i ++){
            if("tags[" + i + "]" in info) {
                if(i != 0){
                    tags += ",";
                }
                tags += info["tags[" + i + "]"];
            }
        }
        var content = $("#test-editormd-markdown-doc").text();     //获取md文本
        //如果内容不包含[TOC],那就让它包含
        if(content.search(/^.*\n*\[TOC\]\n/) === -1)     //如果不能匹配
            content = "[TOC]\n" + content;

        $.post({
            url: "/content/publish",
            data: {
                "title": info.title,
                "tags" : tags,
                "content" : content,
                "type" : info.type,
                "dir_id": info.dir_id,
                "isEdit": info.isEdit == 1 ? true : false,
                "art_id": info.art_id
            },
            success:function (data) {
                alert("发布成功");
                console.log("准备跳转id=" + data);
                window.location.href = '/content/blog/' + data;
            }
        });
        return false;   //截断常规form提交，否则会出现2次提交的情况
    });
});

//获取目录
//layui弹出层获取值
$("#dir_info").on("click", "#getDir", function (){
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        btn: ['确定', '取消'],
        shadeClose: true,
        content: getDirFrame(),    //接收dom对象
        yes: function(index, layero){
            /*
                结构描述：
                <li class="level-2 selected">
                    <a href="#">
                        <i "fa"></i>
                        <span>mysql2222</span>
                    </a>
                </li>
             */
            var dir_name = $(".selected>a>span").text();
            console.log(dir_name);
            var dir_id = $(".selected").attr("dir_id");
            console.log(dir_id);
            var html =
                '<input id="getDir" type="text" name="dir_name" lay-verify="title" autocomplete="off" placeholder="点击选择" class="layui-input" readonly>' +
                '<input type="hidden" name="dir_id" value="' + dir_id + '">';
            $("#dir_info").html(html);
            $("#dir_info input[name='dir_name']").val(dir_name);
            layer.close(index);
        }
    });
});

//具体目录
function getDirFrame(){
    $.post({
        url: "/dir/getall",
        dataType:'json',
        success:function (data) {
            $("#dir-system").html(getChildHtml(data, 1));
            //文件系统的折叠展开功能
            $(function(){
                //更新图标，如果该目录下没有子目录，则将其更改fa图标
                $("#dir-system li a").each(function(){
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
        }
    });
    return $("#dirframe");
}
//文件系统选中效果
$(document).on("click", "#dir-system li a", function (){
    $(".rightmouse").hide();
    $("#dir-system li").removeClass("selected");
    $(this).parent().addClass("selected");

    return false;   //阻止触发父点击事件
});
//构造目录
function getChildHtml(TreeNode, level)
{
    var html = '<ul>';
    for(var node of TreeNode)   //遍历每个目录
    {
        //获取基础信息+child
        html +=
            '<li class="level-' + level + '" dir_id="' + node.dir_id + '" parent_id="' + node.parent_dir_id + '" >' +
            '   <a>' +
            '       <i class="fa fa-folder"></i><span>' + node.dir_name + '</span>' +
            '   </a>';
        if(node.children.length > 0)
        {
            html += getChildHtml(node.children, level + 1);  //递归添加子目录
        }
        html += '</li>';
    }
    html += '</ul>';

    return html;
}
//实现图片
// document.querySelector('#editDiv').addEventListener('paste',function(e){
// 	 var cbd = e.clipboardData;
// 			var ua = window.navigator.userAgent;
// 			// 如果是 Safari 直接 return
// 			if ( !(e.clipboardData && e.clipboardData.items) ) {
// 					return ;
// 			}
// 			// Mac平台下Chrome49版本以下 复制Finder中的文件的Bug Hack掉
// 			if(cbd.items && cbd.items.length === 2 && cbd.items[0].kind === "string" && cbd.items[1].kind === "file" &&
// 					cbd.types && cbd.types.length === 2 && cbd.types[0] === "text/plain" && cbd.types[1] === "Files" &&
// 					ua.match(/Macintosh/i) && Number(ua.match(/Chrome\/(\d{2})/i)[1]) < 49){
// 					return;
// 			}
// 			for(var i = 0; i < cbd.items.length; i++) {
// 					var item = cbd.items[i];
// 					if(item.kind == "file"){
// 							var blob = item.getAsFile();
// 							if (blob.size === 0) {
// 									return;
// 							}
//// blob 就是从剪切板获得的文件 可以进行上传或其他操作
////-----------------------与后台进行交互 start-----------------------
// var data = new FormData();
// data.append('discoverPics', blob);
// $.ajax({
// 		url: '/discover/addDiscoverPicjson.htm',
// 		type: 'POST',
// 		cache: false,
// 		data: data,
// 		processData: false,
// 		contentType: false,
// 		success:function(res){
// 			var obj = JSON.parse(res);
// 			var wrap = $('#editDiv');
// 			var file = obj.data.toString();
// 			var img = document.createElement("img");
// 				img.src = file;
// 		wrap.appendChild(img);
// 		},error:function(){

// 		}
// })
////-----------------------与后台进行交互 end-----------------------*/
///-----------------------不与后台进行交互 直接预览start-----------------------
// 			var reader = new FileReader();
// 			var imgs = new Image();
// 			imgs.file = blob;
// 			reader.onload = (function(aImg) {
// 					return function(e) {
// 						aImg.src = e.target.result;
// 					};
// 				})(imgs);
// 				reader.readAsDataURL(blob);
// 				document.querySelector('#editDiv').appendChild(imgs);
// 				//-----------------------不与后台进行交互 直接预览end-----------------------
// 				}
// 		}
// }, false);
