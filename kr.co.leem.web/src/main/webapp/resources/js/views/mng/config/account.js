/**
 * Created by Administrator on 2015-02-03.
 */
var userRest = {
	contextPath : null,
	showUserGrid: function () {
		var gridParams = {
			url : userRest.contextPath + "/rest/account/getAccounts",
			gridId : '#usersGrid',
			formatters : {commands : gridColFormatter.commands, regDate : gridColFormatter.regDate, name : gridColFormatter.name},
			selectedRowFunction : function (e, rows) {
				if (rows[0] != null) {
					var userData = rows[0];
					$('#txtAccountId').val(userData['accountId']);
					$('#txtPassword').val(userData['password']);
					$('#txtName').val(userData['name']);
					$('#txtPostNum').val(userData['postNum']);
					$('#txtAddress').val(userData['address']);
					$('#txtAddrDetail').val(userData['addrDetail']);
					$('#txtTelNum').val(userData['telNum']);
					$('#txtHpNum').val(userData['hpNum']);

					$('#hdnSeq').val(userData['seq']);
				}
			},
			resetFormFunction : function (e, rows) {
				formUtils.reset('userFrm');
			},
			delRowFunction : function () {
				$(this).find(".command-delete").on("click", function (e) {
					if (confirm("데이터를 삭제하시겠습니까?")) {
						var accountId = $(this).attr("data-accountId");

						userRest.delAccount(accountId);
					}
				}).end();
			}
		}
		gridUtils.showGrid(gridParams);
	},
	saveUser : function () {
		$("#userFrm").ajaxSubmit({
			url : this.contextPath + '/rest/account/saveAccount',
			type : 'post',
			beforeSubmit : function (arr, $form, options) {

			},
			success : function (datas) {
				if (datas.resultCode == 'success') {
					location.href = loginRest.contextPath + '/main/index'
				} else {
					alert('아이디 또는 패스워드가 일치하지 않습니다.');
					return;
				}
			}
		});
	},
	delAccount : function (accountId) {
		$.ajax({
			url: this.contextPath + '/rest/account/delAccount',
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				accountId : accountId
			}),
			type: "post",
			dataType: "json",
			success: function (datas) {
				var resultCode = datas['resultCode'];

				if (resultCode == 'success') {
					alert('삭제가 완료 되었습니다.');
				} else if (resultCode == 'isSystemAdministrator') {
					alert('시스템 관리자는 삭제할 수 없습니다.');
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
		$('#btnNew').click(function () {
			formUtils.reset('userFrm');
		});

		$('#btnSave').click(function () {
			userRest.saveUser();
		});
	}
}
