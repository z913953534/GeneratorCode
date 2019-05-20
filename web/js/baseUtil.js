var baseUtil = {
	baseXHR : function(actionName, formData, params) {
		var xhr = $.ajax({
			url : actionName,
			type : "post",
			dataType : "json",
			data : formData
		});
		return xhr;
	},
	/**
	 * map对象序列化
	 * 
	 * @param obj
	 * @returns {String}
	 */
	mapSerialize : function(obj) {
		var str = "";
		$.each(obj, function(i, v) {
			str += "&" + i + "=" + v;
		});
		if (str.length > 0) {
			str = str.substring(1);
		}
		return str;
	}
}