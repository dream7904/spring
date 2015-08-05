<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015-01-07
  Time: 오후 4:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/templates/includeHeader.jsp" %>

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">기본정보 관리</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					프로젝트
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>

	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-green">
				<div class="panel-heading">
					사용자 정보
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<form role="form" id="userFrm" name="userFrm" method="post">
						<div class="form-group">
							<label for="txtAccountId">아이디</label>
							<input type="text" class="form-control" id="txtAccountId" name="accountId" placeholder="아이디를 입력해 주세요.">
						</div>
						<div class="form-group">
							<label for="txtPassword">패스워드</label>
							<input type="password" class="form-control" id="txtPassword" name="password" placeholder="Password">
						</div>
						<div class="form-group">
							<label for="txtName">이름</label>
							<input type="text" class="form-control" id="txtName" name="name" placeholder="이름을 입력해 주세요."/>
						</div>
						<div class="form-group">
							<label for="txtPostNum">우편번호</label>
							<input type="text" class="form-control" id="txtPostNum" name="postNum" placeholder="이름을 입력해 주세요."/>
						</div>
						<div class="form-group">
							<label for="txtAddress">주소</label>
							<input type="text" class="form-control" id="txtAddress" name="address"/>
						</div>
						<div class="form-group">
							<label for="txtAddrDetail">주소상세</label>
							<input type="text" class="form-control" id="txtAddrDetail" name="addrDetail"/>
						</div>
						<div class="form-group">
							<label for="txtTelNum">전화번호</label>
							<input type="text" class="form-control" id="txtTelNum" name="telNum"/>
						</div>
						<div class="form-group">
							<label for="txtHpNum">핸드폰 번호</label>
							<input type="text" class="form-control" id="txtHpNum" name="hpNum"/>
						</div>

						<input type="hidden" id="hdnRegDate" name="regDate" value="">
						<input type="hidden" id="hdnSeq" name="seq" value="">

						<button type="button" id="btnNew" class="btn btn-outline btn-primary">신규</button>
						<button type="button" id="btnSave" class="btn btn-outline btn-success">저장</button>
					</form>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
</div>
<!-- /#page-wrapper -->

<!-- /container -->
<script type="text/javascript">
	$(function () {
		accountRest.contextPath = '${contextPath}';
		accountRest.showAccountGrid();
		accountRest.setEvents();
	});
</script>