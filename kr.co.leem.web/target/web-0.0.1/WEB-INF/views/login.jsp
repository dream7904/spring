<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/templates/includeHeader.jsp"%>

<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
		<%--<link rel="icon" href="../../favicon.ico">--%>

		<meta charset="UTF-8" http-equiv="X-UA-Compatible" content="IE=Edge"/>
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>

		<title>Signin Template for Bootstrap</title>

		<!-- Bootstrap core CSS -->
		<link href="${contextPath}/resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<!-- Bootstrap theme -->
		<link href="${contextPath}/resources/lib/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="screen" href="${contextPath}/resources/css/login.css" />
		<!-- Custom styles for this template -->
		<link href="${contextPath}/resources/css/login.css" rel="stylesheet">

		<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
		<!--[if lt IE 9]><script src="${contextPath}/resources/lib/bootstrap/js/ie8-responsive-file-warning.js"></script><![endif]-->
		<script src="${contextPath}/resources/lib/bootstrap/js/ie-emulation-modes-warning.js"></script>

		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->

		<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.form.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.timer.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.blockUI.js"></script>

		<script type="text/javascript" src="${contextPath}/resources/js/utils/stringUtils.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/utils/arrayUtils.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/utils/chartUtils.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/utils/dateFormat.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/utils/datepickerUtils.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/utils/gridUtils.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/utils/htmlUtils.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/utils/objectUtils.js"></script>
		<script type="text/javascript" src="${contextPath}/resources/js/utils/dateUtils.js"></script>

		<script type="text/javascript" src="${contextPath}/resources/js/views/login.js"></script>
	</head>

	<body>

	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Please Sign In</h3>
					</div>
					<div class="panel-body">
						<form  id="loginFrm" name="loginFrm" role="form">
							<fieldset>
								<div class="form-group">
									<input type="text" id="accountId" name="accountId" class="form-control" placeholder="E-mail" autofocus>
								</div>
								<div class="form-group">
									<input type="password" id="password" name="password" class="form-control" placeholder="Password" value="">
								</div>
								<div class="checkbox">
									<label>
										<input name="remember" type="checkbox" value="Remember Me">Remember Me
									</label>
								</div>
								<!-- Change this to a button or input when using this as a form -->
								<%--<a href="#" class="btn btn-lg btn-success btn-block">Login</a>--%>
								<button id="btnLogin" class="btn btn-lg btn-success btn-block" type="button">Sign in</button>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="${contextPath}/resources/lib/bootstrap/js/ie10-viewport-bug-workaround.js"></script>

	<script type="text/javascript">
		$(function() {
			$(document).ajaxStart(function () {
				$.blockUI();
			});

			// CSRF Protection Key Send
			$(document).ajaxSend(function(e, xhr, options) {
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");

				xhr.setRequestHeader(header, token);
			});

			$(document).ajaxStop(function () {
				$.unblockUI();
			});

			$(document).ajaxError(function (event, jqxhr, settings, thrownError ) {
				if (jqxhr.status == 403) {
					alert('올바르지 않은 접근이거나, 토큰 또는 세션이 만료되었습니다.\n로그인 페이지로 이동합니다.');
					//location.href = '${contextPath}/login';
				}

				$.unblockUI();
			});

			loginRest.contextPath = '${contextPath}';
			loginRest.setEvents();
			loginRest.setDefault();
		});
	</script>
	</body>
</html>

