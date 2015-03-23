/**
 * Created by Administrator on 2015-02-03.
 */
var midMenuGrpRest = {
	contextPath : null,
	topMenuGrpSeq : null,
	showMidMenuGrpGrid: function () {
		var gridParams = {
			url : midMenuGrpRest.contextPath + "/rest/menu/getMidMenuGrps",
			gridId : '#midMenuGrpGrid',
			post : {
				topMenuGrpSeq : midMenuGrpRest.topMenuGrpSeq
			},
			formatters : {
				moveMidMenuGrpPage : gridColFormatter.lowMidMenuGrpPage,
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
					$('#hdnMidMenuGrpSeq').val(midMenuGrpData['midMenuGrpSeq']);
				}
			},
			resetFormFunction : function (e, rows) {
				formUtils.reset('midMenuGrpFrm');
			},
			delRowFunction : function () {
				$(this).find(".command-delete").on("click", function (e) {
					var topMenuGrpSeq = $(this).attr("data-topMenuGrpSeq");
					var midMenuGrpSeq = $(this).attr("data-midMenuGrpSeq");
					location.href = midMenuGrpRest.contextPath + '/mng/config/lowMenu?topMenuGrpSeq=' + topMenuGrpSeq + '&midMenuGrpSeq=' + midMenuGrpSeq;
				}).end();
			}
		}
		gridUtils.showGrid(gridParams);
	},
	saveMidMenuGrp : function () {
		$("#midMenuGrpFrm").ajaxSubmit({
			url : this.contextPath + '/rest/menu/saveMidMenuGrp',
			type : 'post',
			beforeSubmit : function (arr, $form, options) {

			},
			success : function (datas) {
				if (datas['resultCode'] == 'success') {

					gridUtils.deselectGrid('#midMenuGrpGrid');
					formUtils.reset('midMenuGrpFrm');
					$('#hdnMidMenuGrpSeq').val('');
					gridUtils.reloadGrid('#midMenuGrpGrid');
				} else {
					alert('아이디 또는 패스워드가 일치하지 않습니다.');
					return;
				}
			}
		});
	},
	delAccount : function (topMenuGrpSeq) {
		$.ajax({
			url: this.contextPath + '/rest/menu/delMidMenuGrp',
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
			$('#hdnRegDate').val(new Date());
			$('#hdnMidMenuGrpSeq').val('');
			formUtils.reset('midMenuGrpFrm');
			gridUtils.deselectGrid('#midMenuGrpGrid');
		});

		$('#btnSave').click(function () {
			midMenuGrpRest.saveMidMenuGrp();
		});
	}
};