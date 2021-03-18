//**************************LayUI******************************
//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use('element', function() {
	var element = layui.element;

	//…
});
//年月选择器
layui.use('laydate', function() {
	var laydate = layui.laydate;
	//年月选择器
	laydate.render({
		elem: '.if-Date',
		type: 'date',
		range: '至'
	});
});

//**************************************************************

//tab选项卡切换
$(function() {
	$("#result .list:not(:first)").hide();
	//获取点击事件的对象
	$("#accordion li").click(function() {

		// console.log($("#accordion li a").index(this));
		// console.log($(this).siblings());
		// console.log("事件触发");
		//获取要显示或隐藏的对象
		var divShow = $("#result").children('.list');
		// console.log(divShow);
		//判断当前对象是否被选中，如果没选中的话进入if循环
		if (!$(this).hasClass('selected')) {
			//获取当前对象的索引
			var index = $("#accordion li").index(this);
			//当前对象添加选中样式并且其同胞移除选中样式；
			$(this).addClass('selected').siblings('li').removeClass('selected');
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

//markdown转换显示, 转换显示完后迁移目录
var testEditor;
$(function() {
	testEditor = editormd.markdownToHTML("text-content", { //注意：这里是上面DIV的id
		htmlDecode: "style,script,iframe,br",
		lineNumbers : true,
		emoji: true,
		taskList: true,
		tex: true, // 默认不解析
		flowChart: true, // 默认不解析
		sequenceDiagram: true, // 默认不解析
		codeFold: true
	});
	moveTOC();
	blogtype();
});
function moveTOC(){
	$("#content-toc").append($(".markdown-toc"));
}
function blogtype() {
	$('#content-toc li').hover(function() {
		$(this).addClass('current');
		var num = $(this).attr('data-index');
		$('.slider').css({
			'top': ((parseInt(num) - 1) * 40) + 'px'
		});
	}, function() {
		$(this).removeClass('current');
		// $('.slider').css({ 'top': slider });
	});
}

//锚点缓慢跳转
$(document).on("click", ".markdown-toc-list a", function () {
	if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
		var $target = $(this.hash);
		$target = $target.length && $target || $('[name="' + this.hash.slice(1) + '"]');
		if ($target.length) {
			var targetOffset = $target.offset().top;
			$('html,body').animate({
					scrollTop: targetOffset
				},
				300);
			return false;
		}
	}
});

/**
 *	********************************* ajax列出文章列表 ，增删改查 *********************************
 */
var curr = 0, total = 0, start = 0, page = 10;	//当前页(从0开始)，文章总数，文章分页起始个数，每页显示个数
var keyword = '', dir_id = 0;	//关键字，所属目录id
//初始化参数
function initParam(){
	this.curr = 0;
	this.start = 0;
	this.keyword = '';
	dir_id = 0;
}
//********条件表单*********

//获取目录, layui弹出层获取值
$(document).on("click", "#dir-info input", function (){
	// console.log("准备弹出");
	layer.open({
		type: 1,
		title: false,
		closeBtn: 0,
		btn: ['确定', '取消'],
		shadeClose: true,
		content: getDirFrame(),    //接收dom对象
		yes: function(index, layero){
			//添加逻辑代码
			var dir_id = $("#dirframe .selected").attr("dir_id");
			var dir_name = $(".selected>a>span").text();
			// console.log(dir_id + ", " + dir_name);
			$("#dir-info").append('<input type="hidden" name="dir_id" value="' + dir_id + '">');
			$("#dir-info input[name='dir_name']").val(dir_name);
			layer.close(index);
		}
	});
});
layui.use('form', function() {
	var form = layui.form;
	//监听提交
	form.on('submit(formDemo)', function(data) {
		initParam();
		// layer.msg(JSON.stringify(data.field));
		var info = data.field;
		keyword = info.keyword;
		dir_id = info.dir_id;

		showArtList();
		return false;
	});
});
//重置条件表单
$("button[name='reset']").click(function (){
	initParam();
	showArtList();
	console.log("充值了");
});
//选择第n页
function getPage(curr){
	if(curr * 10 > total)	//如果curr已经是最后一页
	{
		layer.msg("当前已是最后一页!", {icon: 0});
		return false;
	}
	this.curr = curr;
	//执行ajax
	start = curr * 10;
	showArtList();
}

//****主体函数****
function showArtList(){
	// console.log("curr=" + curr + ", total=" + total + ", start=" + start + ", page=" + page);
	$.post({
		url: "/content/mylist",
		data: {
			"start" : start,
			"page" : page,
			"keyword" : keyword,
			"dir_id" : dir_id
		},
		dataType: "json",
		success:function (data) {		//List<Object> = [[list], int]
			//先删除原来的页面
			$("#content-file li").remove();
			for(var item of data[0]) {
				var html = getListCardHtml(item.art_id, item.title, item.type, timeFormat(item.create_time), item.hits, item.comments_num, item.likes);
				$("#content-file").append(html);
			}
			total = data[1];
			$("#pagination").html(getPaginationHTML(data[1]));

			//每执行一次分页，调整变量
			start += page;
			//分页完毕，跳转锚点
			location.href = "#";
			//分页选中
			$("#pagination a").removeClass("active");
			$("a[page=" + curr + "]").addClass("active");
		}
	});
}
function getListCardHtml(art_id, title, type, create_time, hits, comments_num, likes) {
	var html = 
		'<li>\n' +
		'	<div class="content-card" art_id="' + art_id + '">\n' +
		'		<p class="cont-title"><a href="/content/blog/' + art_id + '" target="_blank">' + title + '</a></p>\n' +
		'		<span class="layui-badge layui-bg-green">' + (type === 0 ? "原创" : "转载") + '</span>\n' +
		'		<div class="bottom-mess">\n' +
		'			<span>' + create_time + '</span>\n' +
		'			<span class="border">|</span>\n' +
		'			<i class="fa fa-eye"></i>\n' +
		'			<span>' + hits + '</span>\n' +
		'			<i class="fa fa-commenting-o"></i>\n' +
		'			<span>' + comments_num + '</span>\n' +
		'			<i class="fa fa-heart-o"></i>\n' +
		'			<span>' + likes + '</span>\n' +
		'			<span class="bottom-util">\n' +
		'				<a href="/content/edit/' + art_id + '">编辑</a>\n' +
		'				<a href="/content/blog/' + art_id + '" target="_blank">浏览</a>\n' +
		'				<div href="#" class="menu-icon">\n' +
		'					<i class="fa fa-list-ul"></i>\n' +
		'					<dl class="menu-sm">\n' +
		'						<dd><a class="getDir"><i class="fa fa-plus"></i>添加至…</a></dd>\n' +
		'						<dd><a class="delDir"><i class="fa fa-trash-o"></i>删除</a></dd>\n' +
		'					</dl>\n' +
		'				</div>' +
		'			</span>\n' +
		'		</div>\n' +
		'	</div>\n' +
		'</li>';
	return html;
}

function getPaginationHTML(len){
	var html = '<li><a>«</a></li>';
	for (var i = 0; i < Math.ceil(len / page); i++){
		if(i === 0)
			html += '<li><a page="' + i + '" class="active" onclick="getPage(' + i + ')">' + (i + 1) + '</a></li>';
		else
			html += '<li><a page="' + i + '" onclick="getPage(' + i + ')">' + (i + 1) + '</a></li>';
	}
	html +=
		'<li><a onclick="getPage(' + curr + 1 + ')">下一页</a></li>' +
		'<li><a>»</a></li>';

	return html;
}

function timeFormat(timestamp){
	// timestamp = "2021-01-29T09:31:48.000+00:00";
	timestamp.match(/(.+)T(.+)\..*/);
	var dateTimeStamp = RegExp.$1 + " " + RegExp.$2;
	return dateTimeStamp;	//2021-01-29 09:31:48
}
//删除文章
$(document).on("click", ".bottom-util .delDir", function (){
	var card = getParentByClass($(this), "content-card");
	var art_id = $(card).attr("art_id");
	console.log("查找到要删除的id是: " + art_id);
	layer.confirm('确定删除吗？', function(index){
		//do something
		$.post({
			url: "/content/del/" + art_id,
			success:function (data) {
				//dom也删除
				$(card).parent().remove();	//card的上一级是li标签
				layer.msg('删除成功!', {icon: 1});
				layer.close(index);
			}
		});
	});
});

/**
 * 	************************************ 列表右侧小型菜单, 主要是添加目录（真的很长啊）　************************************************
 */
var art_id = -1;	//记录选中的art_id
$(document).on("click", ".menu-icon", function(){
	// var X = $(this).offset().top;
	// var Y = $(this).offset().left;
	var X = $(this).position().top - 90;
	var Y = $(this).position().left;
	// console.log($(this).children());
	// console.log($(".menu-sm"));
	$(this).children(".menu-sm").css("top", X);
	$(this).children(".menu-sm").css("left", Y);
	$(".menu-sm").hide();	//全部隐藏后再显现
	$(this).children(".menu-sm").show();
	return false;
});
//点击任意地方隐藏
$(document).click(function(){
	$(".menu-sm").hide();
});
//获取目录, layui弹出层获取值
$(document).on("click", ".getDir", function (){
	var currDom = this;
	// console.log("准备弹出");
	layer.open({
		type: 1,
		title: false,
		closeBtn: 0,
		btn: ['确定', '取消'],
		shadeClose: true,
		content: getDirFrame(),    //接收dom对象
		yes: function(index, layero){
			//添加逻辑代码
			var dir_id = $("#dirframe .selected").attr("dir_id");
			var art_id = getParentByClass(currDom, "content-card").attr("art_id");
			// layer.msg("dir_id = " + dir_id + ", art_id = " + art_id);
			$.post({
				url: "/dir/updatedoc",
				data:{
					"dir_id" : dir_id,
					"art_id" : art_id
				},
				success:function (data) {
					layer.msg('已成功添加至目录视图', {icon: 1});
				}
			});
			layer.close(index);
		}
	});
});
//循环查找父元素
function getParentByClass(currDom, targetClass){

	if(typeof($(currDom).attr('class')) == "undefined")
		return getParentByClass($(currDom).parent(), targetClass);

	if($(currDom).attr('class') === $("body").attr('class'))	//如果当前dom是body，说明没找到
		return null;

	if($(currDom).attr('class').indexOf(targetClass) >= 0 )	//出口。如果当前dom是目标类，
		return currDom;

	// console.log($(currDom).attr('class'));
	// console.log($(currDom).attr('class').indexOf(targetClass) >= 0);

	return getParentByClass($(currDom).parent(), targetClass);
}
//具体目录
function getDirFrame(){
	$.get({
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
$(document).on("click", "#dir-system li a.dir", function (){
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
			'   <a class="dir">' +
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