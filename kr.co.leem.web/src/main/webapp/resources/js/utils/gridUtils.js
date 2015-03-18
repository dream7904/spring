/**
 * Created by Administrator on 2015-02-03.
 */

var gridUtils = {
	showGrid : function (gridParams) {
		$(gridParams['gridId']).bootgrid({
			ajax: true,
			post: function () {

				return {};
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
	}
}

var gridColFormatter = {
	commands : function (column, row) {
		return '<button type="button" class="btn bootgrid btn-outline btn-danger btn-xs command-delete" data-accountId="' + row.accountId + '"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>';
	},
	regDate : function (column, row) {
		return row['regDate'];
	},
	name : function (column, row) {
		return row['accountName'];
	}
}