/**
 * Created by Administrator on 2015-02-23.
 */
var arrayUtils = {
	isEmpty : function (array) {
		return array == undefined || array == null || array.length == 0;
	},
	isNotEmpty : function (array) {
		return arrayUtils.isEmpty(array) == true ? false : true;
	}
}