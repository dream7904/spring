/**
 * Created by Administrator on 2015-02-03.
 */
var topMenuGrpRest = {
	contextPath : null,
	showTopMenuGrpGrid: function () {
		var gridParams = {
			url : topMenuGrpRest.contextPath + "/rest/menu/getTopMenuGrps",
			gridId : '#topMenuGrpGrid',
			formatters : {
				moveMidMenuGrpPage : gridColFormatter.moveMidMenuGrpPage,
				regDateYmd : gridColFormatter.regDateYmd,
				enabled : gridColFormatter.enabled
			},
			selectedRowFunction : function (e, rows) {
				if (rows[0] != null) {
					var topMenuGrpData = rows[0];
					$('#txtName').val(topMenuGrpData['name']);
					$('#txtDescription').val(topMenuGrpData['description']);
					$('#txtUrl').val(topMenuGrpData['url']);
					$('#txtOrd').val(topMenuGrpData['ord']);
					$('#selEnabled').val('' + topMenuGrpData['enabled']);
					$('#hdnRegDate').val(new Date(topMenuGrpData['regDate']));
					$('#hdnTopMenuGrpSeq').val(topMenuGrpData['topMenuGrpSeq']);
				}
			},
			resetFormFunction : function (e, rows) {
				formUtils.reset('topMenuGrpFrm');
				$('#hdnTopMenuGrpSeq').val('');
			},
			delRowFunction : function () {
				$(this).find(".command-delete").on("click", function (e) {
					var topMenuGrpSeq = $(this).attr("data-topMenuGrpSeq");
					location.href = topMenuGrpRest.contextPath + '/mng/config/midMenuGrp?topMenuGrpSeq=' + topMenuGrpSeq;
				}).end();
			}
		};

		gridUtils.showGrid(gridParams);
	},
	saveTopMenuGrp : function () {
		$("#topMenuGrpFrm").ajaxSubmit({
			url : this.contextPath + '/rest/menu/saveTopMenuGrp',
			type : 'post',
			beforeSubmit : function (arr, $form, options) {
				if (stringUtils.isEmpty($('#txtName').val())) {
					alert('이름을 입력해 주세요.');
					return false;
				}
			},
			success : function (datas) {
				if (datas['resultCode'] == 'success') {
					alert('저장이 완료되었습니다.');
				} else {
					alert('서버와의 통신중 오류가 발생했습니다.');
				}

				gridUtils.deselectGrid('#topMenuGrpGrid');
				formUtils.reset('topMenuGrpFrm');
				$('#hdnTopMenuGrpSeq').val('');
				gridUtils.reloadGrid('#topMenuGrpGrid');
			}
		});
	},
	delAccount : function (topMenuGrpSeq) {
		$.ajax({
			url: this.contextPath + '/rest/menu/delTopMenuGrp',
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				topMenuGrpSeq : topMenuGrpSeq
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
			formUtils.reset('topMenuGrpFrm');
		});

		$('#btnSave').click(function () {
			topMenuGrpRest.saveTopMenuGrp();
		});
	}
};