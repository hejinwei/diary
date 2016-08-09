var upyunVersionStr = '!thumbnail';

/**
 * 添加上传插件
 * @param isSingel true:单张图片
 * @param maxImageNum 最多几张图片
 * @param buttonId 按钮id
 * @param fileListId 上传成功后，往fileListId的innerHtml中写<img>
 * @param hiddenInputName 上传成功后，往<input type="hidden" name="xxx" />中写的xxx
 * @param imageType 图片类型，整数类型，后台定义，如头像是100等...
 * @param alreadyExistImages 像编辑页面等，以前可能有图片，这里是图片数据
 */
function addImageUploader(isSingel, maxImageNum, buttonId, fileListId, hiddenInputName, imageType, alreadyExistImages, contextPath) {

	// 初始化Web Uploader
	var uploader = WebUploader.create({

	    // 选完文件后，是否自动上传。
	    auto: true,
	    
	    // 是否开启同时多个文件
	    multiple : !isSingel, // 单张图片的时候不开启，多张的时候开启
	    
	    fileNumLimit : maxImageNum,

	    // swf文件路径
	    swf: contextPath + '/static/webuploader/Uploader.swf',

	    // 文件接收服务端。
	    server: contextPath + '/upload/image/1?imageType=' + imageType,

	    // 只允许选择图片文件。
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    },
	
		compress: {
			width: 800,
			height: 800,
			
			// 图片质量，只有type为`image/jpeg`的时候才有效。
		    quality: 90,

		    // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
		    allowMagnify: false,

		    // 是否允许裁剪。
		    crop: false,

		    // 是否保留头部meta信息。
		    preserveHeaders: true,

		    // 如果发现压缩后文件大小比原来还大，则使用原来图片
		    // 此属性可能会影响图片自动纠正功能
		    noCompressIfLarger: true,

		    // 单位字节，如果图片大小小于此值，不会采用压缩。
		    compressSize: 200
		}
	});

	uploader.addButton({
		id : '#' + buttonId, 
		innerHTML : '<img style="width:100px;height:100px;" src="' + contextPath  + '/static/images/upload.png">'
	});
	
	// 当文件被加入队列之前触发，此事件的handler返回值为false，则此文件不会被添加进入队列
	uploader.on( 'beforeFileQueued', function( file ) {
		var existImages = $('div[name="' + buttonId + '_' + fileListId + '"]').length;
		if (existImages >= maxImageNum) {
			 alert("已超过最大图片数，请删除一张后再传");
			 return false;
		}
	});
	
	// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ) {
	});

	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file, serverData ) {
		addOneImage(serverData._raw, file.id);
	});

	// 文件上传失败，显示上传出错。
	uploader.on( 'uploadError', function( file ) {
		try {
			uploader.removeFile(uploader.getFile(file.id));
		} catch (e) {
			//alert(e.message);
		}
	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
	});
	
	function addOneImage(imgSrc, fileid) {
		$div = $('<div name="' + buttonId + '_' + fileListId + '" style="display: inline;" />');
	    $img = $('<img width="100px" height="100px" style="vertical-align:baseline;position:relative;" >');
	    
	    $deleteIcon = $('<img style="width:10px;height:10px;position:relative;left:-10px;bottom:90px;cursor:pointer;" src="' + contextPath + '/static/images/delete.png">');
	    
	    $deleteIcon.click( function(){
	    	try {
				uploader.removeFile(uploader.getFile(fileid));
			} catch (e) {
				//alert(e.message);
			}
			
			this.parentNode.remove();
	    	}
	    ); 
	    
	    $inputFilepath = $('<input type="hidden" id="' + hiddenInputName + '" name="' + hiddenInputName + '" />');
	    var versionIndex = imgSrc.indexOf(upyunVersionStr);
	    if (versionIndex > -1) {
	    	var exceptVersionIndexImgSrc = imgSrc.substring(0,versionIndex);
	    	$inputFilepath.val(exceptVersionIndexImgSrc);
	    } else {
	    	$inputFilepath.val(imgSrc);
	    }
	    
	    $inputFilepath.appendTo($div);
	    $img.appendTo($div);
	    $deleteIcon.appendTo($div);
	    $div.appendTo($('#' + fileListId));

	    // 创建缩略图
	    $img.attr( 'src', imgSrc );
	}
	
	// 已经传上去的图片，编辑页面使用
	if (alreadyExistImages != null && alreadyExistImages != ''
		&& alreadyExistImages != undefined) {
		
		if (typeof(alreadyExistImages) == 'object') {
			for (var i = 0; i < alreadyExistImages.length; i++) {
				//addOneImage(alreadyExistImages[i] + upyunVersionStr, null);
				addOneImage(alreadyExistImages[i], null);
			}
		} else {
			if (alreadyExistImages.length > 2 && alreadyExistImages.substr(0,1) == '[' && alreadyExistImages.substr(-1,1) == ']') {
				alreadyExistImages = alreadyExistImages.substr(1, alreadyExistImages.length-2).split(',');
				for (var i = 0; i < alreadyExistImages.length; i++) {
					//addOneImage(alreadyExistImages[i] + upyunVersionStr, null);
					addOneImage(alreadyExistImages[i], null);
				}
			} else {
				//addOneImage(alreadyExistImages + upyunVersionStr, null);
				addOneImage(alreadyExistImages, null);
			}
		}
	}
	
}

