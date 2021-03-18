/*!
 * editormd图片粘贴上传插件
 *
 * @file   image-handle-paste.js
 * @author codehui
 * @date   2018-11-07
 * @link   https://www.codehui.net
 */

(function() {

	var factory = function(exports) {
		var $ = jQuery; // if using module loader(Require.js/Sea.js).
		var pluginName = "image-handle-paste"; // 定义插件名称

		//图片粘贴上传方法
		exports.fn.imagePaste = function(Editor) {	//原有参数
			var _this = this;
			var cm = _this.cm;
			// var settings = _this.settings;
			// var editor = _this.editor;
			var classPrefix = _this.classPrefix;
			var id = _this.id;
			// var insertValue = _this.insertValue();
			// console.log(Editor);
			// console.log(Editor.id);

			var doc = document.getElementById(id);	//Editor
			// console.log(doc);
			doc.addEventListener('paste', function (event) {
				var items = (event.clipboardData || window.clipboardData).items;
				var file = null;
				if (items && items.length) {
					// 搜索剪切板items
					for (var i = 0; i < items.length; i++) {
						if (items[i].type.indexOf('image') !== -1) {
							file = items[i].getAsFile();
							break;
						}
					}
				} else {
					console.log("当前浏览器不支持");
					return;
				}
				if (!file) {
					console.log("粘贴内容非图片");
					return;
				}
				uploadImg(file,_this);	//Editor
			});
			var dashboard = document.getElementById(id)
			dashboard.addEventListener("dragover", function (e) {
				e.preventDefault()
				e.stopPropagation()
			})
			dashboard.addEventListener("dragenter", function (e) {
				e.preventDefault()
				e.stopPropagation()
			})
			dashboard.addEventListener("drop", function (e) {
				e.preventDefault()
				e.stopPropagation()
				var files = this.files || e.dataTransfer.files;
				uploadImg(files[0],_this);	//Editor
			})
		};
		// ajax上传图片 可自行处理
		var uploadImg = function(file, Editor) {
			var formData = new FormData();
			var suffix = file.name.split(".").pop();	//png
			var name = "img_" + new Date().Format("yyyyMMddHHmmss");	//img_20210208...
			var fileName = name + "." + suffix;
			formData.append('editormd-image-file', file, fileName);

			$.ajax({
				url: Editor.settings.imageUploadURL,
				type: 'post',
				data: formData,
				processData: false,
				contentType: false,
				dataType: 'json',
				success: function (msg) {

					var success=msg['success'];
					if(success==1){
						var url=msg["url"];
						if(/\.(png|jpg|jpeg|gif|bmp|ico)$/.test(url)){
							Editor.insertValue("![图片alt]("+msg["url"]+" ''图片title'')");
						}else{
							Editor.insertValue("[下载附件]("+msg["url"]+")");
						}
					}else{
						console.log(msg);
						alert("上传失败");
					}
				}
			});
		};
		//日期格式化
		Date.prototype.Format = function (fmt) { //author: meizz
			var o = {
				"M+": this.getMonth() + 1, //月份
				"d+": this.getDate(), //日
				"H+": this.getHours(), //小时
				"m+": this.getMinutes(), //分
				"s+": this.getSeconds(), //秒
				"q+": Math.floor((this.getMonth() + 3) / 3), //季度
				"S": this.getMilliseconds() //毫秒
			};
			if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
			for (var k in o)
				if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			return fmt;
		}
	};

	// CommonJS/Node.js
	if (typeof require === "function" && typeof exports === "object" && typeof module === "object") {
		module.exports = factory;
	} else if (typeof define === "function") // AMD/CMD/Sea.js
	{
		if (define.amd) { // for Require.js

			define(["editormd"], function(editormd) {
				factory(editormd);
			});

		} else { // for Sea.js
			define(function(require) {
				var editormd = require("./../../editormd");
				factory(editormd);
			});
		}
	} else {
		factory(window.editormd);
	}

})();
