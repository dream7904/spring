/**
 * Created by Administrator on 2015-02-03.
 */
var htmlUtils = {
	selCommonsElement : function (elementId, optionValues, valueKeyNm, textKeyNm, selectedValue) {
		$(elementId).children().remove();

		if (optionValues == null || optionValues.length < 1) {
			$('<option>').val('').text('없음').appendTo(elementId);
			return;
		}

		for (var i = 0; i < optionValues.length; i++) {
			var option = optionValues[i];
			$('<option>').val(option[valueKeyNm]).text(option[textKeyNm]).appendTo(elementId);
		}

		if (stringUtils.isNotEmpty(selectedValue)) {
			$(elementId).val(selectedValue);
		}
	},
	selSimpleCommonsElement : function (elementId, optionValues, selectedValue, enableAll) {
		$(elementId).children().remove();

		if (optionValues == null || optionValues.length < 1) {
			$('<option>').val('').text('없음').appendTo(elementId);
			return;
		}

		if (enableAll == true) {
			$('<option>').val('').text('전체').appendTo(elementId);
		}

		for (var i = 0; i < optionValues.length; i++) {
			var option = optionValues[i];
			$('<option>').val(option).text(option).appendTo(elementId);
		}

		if (stringUtils.isNotEmpty(selectedValue)) {
			$(elementId).val(selectedValue);
		}
	}
};

var formUtils = {
	resetForm: function (id) {
		$("form#" + id).each(function () {
			this.reset();
		});
	},
	btnState: function (id, isEnable) {
		if (isEnable) {
			$(id).prop('disabled', false);
			$(id).css('opacity', 1.0);
		} else {
			$(id).prop('disabled', true);
			$(id).css('opacity', 0.5);
		}
	},
	reset: function (id) {
		$("form#" + id).each(function () {
			this.reset();
		});
	}
};

var dialogUtils = {
	showModalDialog : function (className, type, selectedElement) {
		$(className).dialog({
			resizable: false,
			height : '140',
			modal: true,
			buttons: {
				"확인" : function() {
					if (type == '') {

					}
					dialogUtils.confirmFunction(1);
				},
				"저장" : function() {
					dialogUtils.confirmFunction(1);
				},
				"취소" : function() {
					dialogUtils.cancelFunction();
				}
			}
		});
	},
	cancelFunction : function () {
		$( "#dialog-confirm" ).dialog( "close" );
	},
	confirmFunction : function (type) {

		$( "#dialog-confirm" ).dialog( "close" );
	}
}