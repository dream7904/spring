/**
 * Created by Administrator on 2015-02-03.
 */

var gridUtils = {
	showGrid : function (gridParams) {
		$(gridParams.gridId).jqGrid('GridUnload');
		$(gridParams.gridId).jqGrid({
			url : gridParams.url,
			datatype : "json",
			postData : gridParams.postDataParams,
			colNames : gridParams.colNames,
			colModel : gridParams.colModels,
			onSelectRow : gridParams.onSelectRow,
			ondblClickRow : gridParams.ondblClickRow,
			gridComplete : gridParams.gridComplete,
			rowNum : gridParams.rowNum,
			pager : gridParams.gridPagerId,
			autowidth : gridParams.autowidth,
			viewrecords : false,
			loadonce : false,
			recordpos : 'left',
			rownumbers : true,
			rownumWidth : gridParams.rownumWidth,
			caption : gridParams.caption
		}).navGrid(gridParams.gridPagerId, {view:false,edit:false,add:false,del:false,search:false, refresh:false});

		var gridIdLabel = gridParams.gridId.substring(1);
		$('#jqgh_' + gridIdLabel + '_rn').text('순번');

		if (gridParams.groupHeaders != null) {
			$(gridParams.gridId).jqGrid('setGroupHeaders', {
				useColSpanStyle: true,
				groupHeaders : gridParams.groupHeaders
			});
		}
	},
	getSelectedChkboxColDatas : function (gridParams, element) {
		$(element + ' input[type=checkbox]:checked').each(function () {
			var colNm =  $(this).attr('data-colName');
			var colModelNm = $(this).attr('data-colModelName');

			var colWidth = $(this).attr('data-colWidth');
			var colModelObj = {
				name : colModelNm, index : colModelNm, align : "center", width : colWidth, sortable : false
			}

			gridParams.colModels.push(colModelObj);
			gridParams.colNames.push(colNm);
		});
	}
}

var gridFormatter = {
	date : function (cellValue, options, rowObject) {
		if (stringUtils.isEmpty(cellValue)) {
			return '';
		}

		var date = new Date(cellValue);
		var dateFormat = stringUtils.defaultIfEmpty(options.colModel.formatoptions.dateFormat, 'yyyy-MM-dd');

		return $.format.date(date, dateFormat);
	},
	solvedAlarm : function (cellValue, options, rowObject) {
		return cellValue == true ? '처리완료' : '미처리'
	}
}