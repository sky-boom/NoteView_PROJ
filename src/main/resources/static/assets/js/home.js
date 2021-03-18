//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use('element', function() {
	var element = layui.element;

	//…
});

$(function() {
	blogtype();
});

// 搜索栏下方选中特效
function blogtype() {
	$('#category li').hover(function() {
		$(this).addClass('current');
		var num = $(this).attr('data-index');
		$('.slider').css({
			'top': ((parseInt(num) - 1) * 40) + 'px'
		});
	}, function() {
		$(this).removeClass('current');
		// $('.slider').css({ 'top': slider });
	});
	$(window).scroll(function(event) {
		var winPos = $(window).scrollTop();
		if (winPos > 750)
			$('#categoryandsearch').addClass('fixed');
		else
			$('#categoryandsearch').removeClass('fixed');
	});
};