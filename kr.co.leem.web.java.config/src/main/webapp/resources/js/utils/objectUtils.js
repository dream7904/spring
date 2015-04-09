/**
 * Created by Administrator on 2015-03-05.
 */
var objectUtils = {
	isEmpty : function (value) {
		return value == undefined || value == null ? true : false;
	},
	isNotEmpty : function (value) {
		return objectUtils.isEmpty(value) == false ? true : false;
	}
}