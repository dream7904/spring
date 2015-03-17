<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<%@include file="../includeHeader.jsp" %>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

	<title>Theme Template for Bootstrap</title>

	<!-- Bootstrap core CSS -->
	<!-- Bootstrap Core CSS -->
	<link href="${contextPath}/resources/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">

	<!-- MetisMenu CSS -->
	<link href="${contextPath}/resources/lib/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

	<!-- Timeline CSS -->
	<link href="${contextPath}/resources/lib/sbadmin/css/timeline.css" rel="stylesheet">

	<!-- Custom CSS -->
	<link href="${contextPath}/resources/lib/sbadmin/css/sb-admin-2.css" rel="stylesheet">

	<!-- Morris Charts CSS -->
	<link href="${contextPath}/resources/lib/morrisjs/morris.css" rel="stylesheet">

	<!-- Custom Fonts -->
	<link href="${contextPath}/resources/lib/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

	<link href="${contextPath}/resources/lib/bootgrid/jquery.bootgrid.css" rel="stylesheet">

	<link href="${contextPath}/resources/css/default.css" rel="stylesheet">

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->

	<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.numeric.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/lib/jquery/ui/jquery-ui.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.validate.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/lib/jquery/additional-methods.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.timer.js"></script>

	<script type="text/javascript" src="${contextPath}/resources/lib/bootgrid/jquery.bootgrid.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/lib/jquery/jquery.blockUI.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/lib/atmosphere/jquery.atmosphere.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/utils.js"></script>


	<script type="text/javascript">
		var timer = null;

		$(document).ajaxStart(function () {
			$.blockUI({
				theme: false,
				baseZ: 9999
			});
		});

		// CSRF Protection Key Send
		$(document).ajaxSend(function (e, xhr, options) {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");

			xhr.setRequestHeader(header, token);
		});

		$(document).ajaxStop(function () {
			$.unblockUI();
		});

		$(document).ajaxError(function (event, jqxhr, settings, thrownError) {
			if (jqxhr.status == 403) {
				alert('올바르지 않은 접근이거나, 토큰 또는 세션이 만료되었습니다.\n로그인 페이지로 이동합니다.');
				location.href = '${contextPath}/login';
			}

			$.unblockUI();
		});
	</script>
</head>
<body>

<div id="wrapper">
	<tiles:insertAttribute name="header"/>
	<tiles:insertAttribute name="contents"/>
</div>


<script src="${contextPath}/resources/lib/bootstrap/js/bootstrap.min.js"></script>

<script src="${contextPath}/resources/lib/metisMenu/dist/metisMenu.js"></script>

<script src="${contextPath}/resources/lib/raphael/raphael-min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="${contextPath}/resources/lib/sbadmin/js/sb-admin-2.js"></script>

</body>
</html>
