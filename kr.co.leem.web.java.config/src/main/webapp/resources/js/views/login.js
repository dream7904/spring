/**
 * Created by Administrator on 2015-02-03.
 */
var loginRest = {
	contextPath : null,
	login : function () {
		$("#loginFrm").ajaxSubmit({
			url : this.contextPath + '/rest/home/login',
			type : 'post',
			beforeSubmit : function (arr, $form, options) {
				if (stringUtils.isEmpty($('#accountId').val())) {
					alert('ID를 입력해 주세요.');
					return false;
				}

				if (stringUtils.isEmpty($('#password').val())) {
					alert('비밀번호를 입력해 주세요.');
					return false;
				}
			},
			success : function (datas) {
				console.log(datas)
				if (datas.resultCode == 'success') {
					location.href = loginRest.contextPath + '/mng/config/topMenuGrp'
				} else {
					alert('아이디 또는 패스워드가 일치하지 않습니다.');
					return;
				}
			}
		});
	},
	setDefault : function () {
		$.ajax({
			url: this.contextPath + '/rest/home/setDefaultAccount',
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({

			}),
			type: "post",
			dataType: "json",
			success: function (datas) {
				if (datas.resultCode == 'success') {

				} else {
					alert('서버와의 통신중에 오류가 발생했습니다.');
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				console.log(jqXHR)
				alert('서버와의 통신중에 오류가 발생했습니다.');
			}
		});
	},
	setEvents : function () {
		$('#accountId').keypress(function (e) {
			if (e.which == 13)  {
				loginRest.login();
			}
		});

		$('#password').keypress(function (e) {
			if (e.which == 13)  {
				loginRest.login();
			}
		});

		$('#btnLogin').click(function () {
			loginRest.login();
		});
	}
}