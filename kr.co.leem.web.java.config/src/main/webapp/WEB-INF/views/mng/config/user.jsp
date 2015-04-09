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
			<h1 class="page-header">Tables</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					사용자 관리
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="dataTable_wrapper">
						<table id="usersGrid" class="table table-condensed table-hover table-striped">
							<thead>
							<tr>
								<th data-column-id="accountId" data-identifier="true" data-align="center" data-header-align="center"
								    data-sortable="false">계정
								</th>
								<th data-column-id="name" data-align="center" data-header-align="center"
								    data-sortable="false">이름
								</th>

								<th data-column-id="telNum" data-align="center" data-header-align="center"
								    data-sortable="false">전화번호
								</th>

								<th data-column-id="hpNum" data-align="center" data-header-align="center"
								    data-sortable="false">핸드폰번호
								</th>

								<th data-column-id="address" data-align="center" data-header-align="center"
								    data-sortable="false">주소
								</th>

								<th data-column-id="regDate" data-formatter="date" data-align="center" data-header-align="center"
								    data-sortable="false">주소
								</th>
								<%--<th data-column-id="userGrpNm" data-align="center" data-formatter="userGrpNm" data-header-align="center"
								    data-sortable="false">권한
								</th>--%>

								<th data-column-id="commands" data-formatter="commands" data-align="center" data-header-align="center"
								    data-sortable="false">Commands
								</th>
							</tr>
							</thead>
						</table>
					</div>
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