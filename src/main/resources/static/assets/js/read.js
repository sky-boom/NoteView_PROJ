
var initTop = parseInt($(".col-side").css("top"));

//监听滚动条
$(window).scroll(function (){

    if($(document).scrollTop() > 31.2)  //31.2: 超出导航栏距离
        $(".col-side").css("position", "fixed");
    else
        $(".col-side").css("position", "static");

    var bottom = $(document).height() - $(document).scrollTop() - $(window).height();

    if(bottom < 110) //110: 超出文章最底部的距离
    {
        // console.log("top值为: " + (initTop - (110 - bottom)));
        console.log(initTop - (110 - bottom) );
        $(".col-side").css("top", initTop - (110 - bottom));
    }
    else
        $(".col-side").css("top", initTop);

    // console.log("**********************************")
    // console.log("滚动距离:"+$(document).scrollTop());
    // console.log("滚动条到底部距离:" + bottom);
    // console.log("document.height()=" + $(document).height() + ", window.height()=" + $(window).height());
    // console.log("文章总长度=" + $(".article-item").height());
    // console.log("header.height=" + $("header").height() + ", footer.height:" + $("footer").height() + ", col-side.height=" + $(".col-side").height());
});