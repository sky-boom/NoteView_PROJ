layui.use(['util'], function() {
	var util = layui.util;
	util.fixbar();
});

//侧边栏弹出
$(function() {
	$("#loading").fadeOut(500);
});
$('.next').click(function() {
	$('html,body').animate({
		scrollTop: $('#section1').outerHeight() + 1
	}, 600);
});
$('#menu').on('click', function() {
	var mark = $(this).attr('data-mark');
	if (mark === 'false') {
		$(this).removeClass('menu_open').addClass('menu_close');
		//open
		$('#navgation').removeClass('navgation_close').addClass('navgation_open');
		$(this).attr({
			'data-mark': "true"
		});
	} else {
		$(this).removeClass('menu_close').addClass('menu_open');
		//close
		$('#navgation').removeClass('navgation_open').addClass('navgation_close');
		$(this).attr({
			'data-mark': "false"
		});
	}
});

//登录弹出
$(function() {
	var html = '<!-- 幕布 -->\n' +
		'<div class="curtain"></div>\n' +
		'<!-- 内容 -->\n' +
		'<div class="content" id="login_f">\n' +
		'	<h1>Login</h1>\n' +
		'	<div id="loginform">\n' +
		'		<p class="error_msg" id="error_msg"></p>\n' +
		'		<input type="text" required="required" placeholder="用户名" name="account"></input>\n' +
		'		<input type="password" required="required" placeholder="密码" name="password"></input>\n' +
		'		<span class="fl"><input type="checkbox" name="rememberme"/> 记住密码</span>\n' +
		'		<a href="#" class="fr">注册</a>\n' +
		'		<button class="but" onclick="tologin()">登录</button>\n' +
		'	</div>\n' +
		'</div>\n' +
		'<div class="content" id="register_f">\n' +
		'	<h1>Register</h1>\n' +
		'	<div id="registerform">\n' +
		'		<input type="text" required="required" placeholder="请输入用户名" name="u"></input>  \n' +
		'		<input type="password" required="required" placeholder="请输入密码" name="p"></input>  \n' +
		'		<div class="fl">\n' +
		'			<div class="switch-container">\n' +
		'				<input id="switch" type="checkbox" class="switch" />\n' +
		'				<label for="switch"></label>\n' +
		'			</div>\n' +
		'			<span>管理员</span>\n' +
		'		</div>\n' +
		'		<a href="#" class="fr" id="tologin">前往登录</a>\n' +
		'		<button class="but">注册</button>\n' +
		'	</div>\n' +
		'</div>';
	$("#loginframe").html(html);

	$(".curtain, .content").hide(); //隐藏幕布和内容
	$("#loginopen").click(function() {
		$(".curtain, #login_f").fadeIn("slow"); //淡入淡出效果 显示div
		$("#section1").addClass("blur");	//给背景添加模糊
	});
	$(".curtain").click(function() {
		$(".curtain, #login_f, #register_f").fadeOut("slow"); //淡入淡出效果 隐藏div
		$("#section1").removeClass("blur");	//给背景取消模糊
	});
	//注册页
	$("#login_f .fr").click(function(){
		$("#login_f").fadeOut("slow"); //淡入淡出效果 隐藏div
		$("#register_f").fadeIn("slow");
	});
	$("#tologin").click(function(){
		$("#register_f").fadeOut("slow"); //淡入淡出效果 隐藏div
		$("#login_f").fadeIn("slow");
	});
});

//登录验证
function showframe(){
	
}
function tologin(){
	var account = $("input[name='account']").val();
	var pwd = $("input[name='password']").val();

	$.post({
		url: "/tologin",
		data:{
			"account" : account,
			"password": pwd,
		},
		success:function (data) {	//data: boolean
			if(data)
			{
				location.href = "/home";
				return true;
			}
			else
			{
				$("#error_msg").html("用户名或密码错误!");
				return false;
			}
		}
	})

}