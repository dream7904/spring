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
					메뉴 관리
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="dataTable_wrapper">
						<table id="midMenuGrpGrid" class="table table-condensed table-hover table-striped">
							<thead>
							<tr>
								<th data-column-id="midMenuGrpSeq" data-type="numeric" data-identifier="true" data-align="center" data-header-align="center"
								    data-sortable="false">Seq
								</th>
								<th data-column-id="name" data-align="center" data-header-align="center"
								    data-sortable="false">이름
								</th>

								<th data-column-id="description" data-align="center" data-header-align="center"
								    data-sortable="false">내용
								</th>

								<th data-column-id="url" data-align="center" data-header-align="center"
								    data-sortable="false">URL
								</th>

								<th data-column-id="ord" data-align="center" data-header-align="center"
								    data-sortable="false">순서
								</th>

								<th data-column-id="regDate" data-formatter="regDateYmd" data-align="center" data-header-align="center"
								    data-sortable="false">등록일
								</th>

								<th data-column-id="enabled" data-formatter="enabled" data-align="center" data-header-align="center"
								    data-sortable="false">사용여부
								</th>
								<%--<th data-column-id="userGrpNm" data-align="center" data-formatter="userGrpNm" data-header-align="center"
								    data-sortable="false">권한
								</th>--%>

								<th data-column-id="commands" data-formatter="moveMidMenuGrpPage" data-align="center" data-header-align="center"
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
					메뉴 정보
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<form role="form" id="midMenuGrpFrm" name="midMenuGrpFrm" method="post">
						<div class="form-group">
							<label for="txtName">메뉴명</label>
							<input type="text" class="form-control" id="txtName" name="name" placeholder="메뉴명을 입력해 주세요."/>
						</div>
						<div class="form-group">
							<label for="txtDescription">내용</label>
							<input type="text" class="form-control" id="txtDescription" name="description" placeholder="내용을 입력해 주세요."/>
						</div>
						<div class="form-group">
							<label for="txtUrl">URL</label>
							<input type="text" class="form-control" id="txtUrl" name="url" placeholder="URL을 입력해 주세요."/>
						</div>
						<div class="form-group">
							<label for="txtOrd">순서</label>
							<input type="text" class="form-control" id="txtOrd" name="ord" placeholder="순서를 입력해 주세요."/>
						</div>
						<div class="form-group">
							<label for="selEnabled">사용여부</label>
							<select class="form-control" id="selEnabled" name="enabled">
								<option value="true">사용</option>
								<option value="false">미사용</option>
							</select>
						</div>

						<input type="hidden" id="hdnRegDate" name="regDate" value="">
						<input type="hidden" id="hdnMidMenuGrpSeq" name="midMenuGrpSeq" value="">
						<input type="hidden" id="hdnTopMenuGrpSeq" name="tmgSeq" value="${param.topMenuGrpSeq}">
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
		midMenuGrpRest.contextPath = '${contextPath}';
		midMenuGrpRest.topMenuGrpSeq = ${param.topMenuGrpSeq}
		midMenuGrpRest.showMidMenuGrpGrid();
		midMenuGrpRest.setEvents();
	});
</script>