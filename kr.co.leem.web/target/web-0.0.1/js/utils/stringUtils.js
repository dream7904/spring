/**
 * Created by Administrator on 2015-02-03.
 */
var stringUtils = {
	isEmpty: function (strValue) {
		return strValue == undefined || strValue == null || strValue == '' || strValue == 'empty' ? true : false;
	},
	isNotEmpty: function (strValue) {
		return stringUtils.isEmpty(strValue) == false ? true : false;
	},
	defaultIfEmpty: function (strValue, defaultValue) {
		defaultValue = stringUtils.isEmpty(defaultValue) ? '' : defaultValue;

		return stringUtils.isEmpty(strValue) ? defaultValue : strValue;
	}
};