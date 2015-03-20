/**
 * Created by Administrator on 2015-02-03.
 */
var lowMenuRest = {
	contextPath : null,
	topMenuGrpSeq : null,
	showLowMenuGrid: function () {
		var gridParams = {
			url : lowMenuRest.contextPath + "/rest/menu/getLowMenus",
			gridId : '#lowMenuGrid',
			post : {
				topMenuGrpSeq : lowMenuRest.topMenuGrpSeq,
				midMenuGrpSeq : lowMenuRest.midMenuGrpSeq
			},
			formatters : {
				moveMidMenuGrpPage : gridColFormatter.moveMidMenuGrpPage,
				regDateYmd : gridColFormatter.regDateYmd,
				enabled : gridColFormatter.enabled
			},
			selectedRowFunction : function (e, rows) {
				if (rows[0] != null) {
					var midMenuGrpData = rows[0];

					$('#txtName').val(midMenuGrpData['name']);
					$('#txtDescription').val(midMenuGrpData['description']);
					$('#txtUrl').val(midMenuGrpData['url']);
					$('#txtOrd').val(midMenuGrpData['ord']);
					$('#selEnabled').val('' + midMenuGrpData['enabled']);
					$('#hdnRegDate').val(new Date(midMenuGrpData['regDate']));

				}
			},
			resetFormFunction : function (e, rows) {
				formUtils.reset('midMenuGrpFrm');
			},
			delRowFunction : function () {
				$(this).find(".command-delete").on("click", function (e) {
					var topMenuGrpSeq = $(this).attr("data-topMenuGrpSeq");
					location.href = lowMenuRest.contextPath + 'mng/config/midMenuGrp?topMenuSeq=' + topMenuGrpSeq;
				}).end();
			}
		}
		gridUtils.showGrid(gridParams);
	},
	saveLowMenu : function () {
		$("#lowMenuFrm").ajaxSubmit({
			url : this.contextPath + '/rest/menu/saveLowMenu',
			type : 'post',
			beforeSubmit : function (arr, $form, options) {

			},
			success : function (datas) {
				if (datas['resultCode'] == 'success') {
					alert('저장이 완료 되었습니다.');
				} else {
					alert('아이디 또는 패스워드가 일치하지 않습니다.');
					return;
				}

				lowMenuRest.showLowMenuGrid();
				formUtils.reset('lowMenuFrm');
			}
		});
	},
	delAccount : function (lowMenuSeq) {
		$.ajax({
			url: this.contextPath + '/rest/menu/delLowMenu',
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				lowMenuSeq : lowMenuSeq
			}),
			type: "post",
			dataType: "json",
			success: function (datas) {
				var resultCode = datas['resultCode'];

				if (resultCode == 'success') {
					alert('삭제가 완료 되었습니다.');
				} else {
					alert('서버와의 통신중에 오류가 발생했습니다.');
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				alert('서버와의 통신중에 오류가 발생했습니다.');
			}
		});
	},
	setEvents : function () {
		$('#hdnRegDate').val(new Date());
		$('#btnNew').click(function () {
			formUtils.reset('lowMenuFrm');
		});

		$('#btnSave').click(function () {
			lowMenuRest.saveLowMenu();
		});
	}
};