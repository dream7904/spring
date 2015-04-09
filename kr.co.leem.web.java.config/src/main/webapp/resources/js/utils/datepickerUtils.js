/**
 * Created by Administrator on 2015-02-03.
 */
var datePickerUtils = {
	setDatepicker : function (element, format) {
		format = stringUtils.defaultIfEmpty(format, 'yy-mm-dd');

		$(element).datepicker();
		$(element).datepicker("option", "dateFormat", format);
	}
};