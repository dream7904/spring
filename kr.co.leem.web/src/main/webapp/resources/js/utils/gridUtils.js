/**
 * Created by Administrator on 2015-02-03.
 */

var gridUtils = {
	showGrid : function (gridParams) {
		$(gridParams['gridId']).bootgrid({
			ajax: true,
			post: function () {
				return gridParams.post;
			},
			url: gridParams['url'],
			align: "center",
			headerAlign: "center",
			selection: true,
			multiSelect: false,
			rowSelect: true,
			keepSelection: true,
			formatters : gridParams.formatters
		}).on("selected.rs.jquery.bootgrid", gridParams.selectedRowFunction).on("deselected.rs.jquery.bootgrid", gridParams.resetFormFunction).on("loaded.rs.jquery.bootgrid", gridParams.delRowFunction);
	},
	deselectGrid : function (gridId) {
		$(gridId).bootgrid("deselect");
	}
}

var gridColFormatter = {
	commands : function (column, row) {
		return '<button type="button" class="btn bootgrid btn-outline btn-danger btn-xs command-delete" data-accountId="' + row['accountId'] + '"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>';
	},
	regDateYmd : function (column, row) {

		return $.format.date(row['regDate'], 'yyyy-MM-dd');
	},
	enabled : function (column, row) {
		if (row['enabled'] == true) {
			return '사용';
		} else {
			return '미사용';
		}
	},
	moveMidMenuGrpPage : function (column, row) {
		return '<button type="button" class="btn bootgrid btn-outline btn-danger btn-xs command-delete" data-topMenuGrpSeq="' + row['topMenuGrpSeq'] + '"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></button>';
	},
	lowMidMenuGrpPage : function (column, row) {
		return '<button type="button" class="btn bootgrid btn-outline btn-danger btn-xs command-delete" data-topMenuGrpSeq="' + row['midMenuGrpSeq'] + '"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></button>';
	}

}