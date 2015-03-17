/**
 * Created by Administrator on 2015-02-04.
 */

var chartUtils = {
	allChartDatas : null,
	seriesColors : null,
	allSourceDatas : null,
	allCompareDatas : null,
	drawChart : function (chartDatas, status) {
		var chartPlaceHolderId = '.' + status + 'PlaceHolder';
		var overviewPlaceHolderId = '.' + status + 'Overview';
		$(chartPlaceHolderId).show();
		$(overviewPlaceHolderId).show();

		var plot = $.plot(chartPlaceHolderId, chartDatas, chartUtils.getOptions(status));

		var series = plot.getData();

		chartUtils.seriesColors = new Array(series.length);

		for (var i = 0; i < series.length; i++) {
			var color = [];

			color.push('0xffffff');

			chartUtils.seriesColors.push(color);
		}

		$.each(plot.getAxes(), function (i, axis) {

			if (axis.show == false)
				return;

			if (i == 'xaxis') {
				return;
			}

			var box = axis.box;
			var yAxisIdx = axis.n;
			$('.flot-y' + yAxisIdx + '-axis').find('.tickLabel').each(function () {
				$(this).css('color', series[(yAxisIdx - 1)].color);
			});

		});

		var overview = $.plot(overviewPlaceHolderId, chartDatas, chartUtils.getOverviewOptions());
		var previousPoint = null;

		$(chartPlaceHolderId).bind("plotselected", function (event, ranges) {
			$.each(plot.getXAxes(), function(_, axis) {
				var opts = axis.options;
				opts.min = ranges.xaxis.from;
				opts.max = ranges.xaxis.to;
			});

			plot.setupGrid();
			plot.draw();
			plot.clearSelection();

			overview.setSelection(ranges, true);
		}).bind("plothover", function (event, pos, item) {
			$("#x").text(pos.x.toFixed(2));
			$("#y").text(pos.y.toFixed(2));

			if (item) {
				if (previousPoint != item.dataIndex) {
					previousPoint = item.dataIndex;
					$("#tooltip").remove();

					var dateValue = new Date($.format.date(item.datapoint[0], 'yyyy-MM-dd HH:mm:ss'));

					var value = item.datapoint[1];

					chartUtils.showTooltip(item.pageX, item.pageY,
						item.series.label + " : " + dateValue + "  " + value);
				}
			} else {
				$("#tooltip").remove();
				previousPoint = null;
			}
		});

		$(overviewPlaceHolderId).bind("plotselected", function (event, ranges) {
			plot.setSelection(ranges);
		});
	},
	getOverviewOptions : function () {
		return {
			series: {
				lines: {
					show: true,
					lineWidth: 1
				},
				shadowSize: 0
			},
			legend : {
				show:false
			},
			xaxis: {
				position : 'bottom',
				mode: "time",
				ticks: []
			},
			yaxis: {
				show : false
			},
			selection: {
				mode: "x"
			}
		}
	},
	showTooltip : function (x, y, contents) {
		$('<div id="tooltip">' + contents + '</div>').css( {
			position: 'absolute',
			display: 'none',
			top: y + 5,
			left: x + 5,
			border: '1px solid #fdd',
			padding: '2px',
			'background-color': '#fee',
			opacity: 0.80
		}).appendTo("body").fadeIn(200);
	},

	getOptions : function (status) {
		function weekendAreas(axes) {
			var markings = [];
			var d = new Date(axes.xaxis.min);

			d.setUTCDate(d.getDate() - ((d.getDay() + 1) % 7));
			d.setUTCHours(d.getHours());
			d.setUTCMinutes(d.getMinutes());
			d.setUTCSeconds(d.getSeconds());

			var i = d.getMilliseconds();

			do {
				markings.push({ xaxis: { from: i, to: i + 2 * 24 * 60 * 60 * 1000 } });
				i += 7 * 24 * 60 * 60 * 1000;
			} while (i < axes.xaxis.max);

			return markings;
		}

		function setYAxisPosition() {
			var yAxisPositions = new Array();
			for (var i = 0; i < chartUtils.allChartDatas.length; i++) {
				var position = 'left';
				if (i % 2 == 0) {
					position = 'left';
				} else {
					position = 'right';
				}
				yAxisPositions.push({position : position})
			}

			return yAxisPositions;
		}

		var options = {
			legend : {
				container : $('.' + status + 'ChartLegendStage'),
				noColumns : 10
			},
			series: {
				lines: { show: true },
				points: { show: true }
			},
			xaxis: {
				timezone: "browser",
				position : 'bottom',
				mode: "time"
			},
			yaxes : setYAxisPosition(),
			selection: {
				mode: "x"
			},

			grid: {
				hoverable: true,
				clickable: true,
				markings: weekendAreas
			}
		}

		return options;
	},

	getChartDatas : function (chartObj) {
		var datas = null;
		var status = chartObj.status;
		var url = stringUtils.isEmpty(restPack.getModuleId()) ? restPack.contextPath + '/rest/battery/getAllDetailHistories' : restPack.contextPath + '/rest/battery/getAllDetailModuleHistories'

		$.ajax({
			url: url,
			contentType: "application/json; charset=utf-8",
			data: JSON.stringify({
				serial : restPack.serial,
				moduleId : restPack.getModuleId(),
				compareBatSerial : chartObj.compareBatId,
				compareModuleId : chartObj.compareModuleId,
				fromDate : chartObj.fromDate,
				toDate : chartObj.toDate
			}),
			type: "post",
			dataType: "json",
			success: function (datas) {
				chartUtils.allSourceDatas = datas.rows;
				chartUtils.allCompareDatas = datas.compareRows;
				var allDatas = chartUtils.setChartData(chartObj.status, chartObj.colModelDatas);

				chartUtils.allChartDatas = allDatas;

				$('#' + chartObj.status + 'ChartStage').show();

				chartUtils.drawChart(allDatas, chartObj.status);
				// 초기 표시시 배터리 전압, 배터리 전류, 최저전압, 최고전압, 셀중 최저온도, 셀중 최고온도 기본 표시
			},
			error: function (jqXHR, textStatus, errorThrown) {
				alert('서버와의 통신중에 오류가 발생했습니다.');
			}
		});
	},
	setChartData : function (status, colModelDatas, allSourceDatas, allCompareDatas) {
		if (allSourceDatas == undefined || allSourceDatas == null || allSourceDatas.length < 1) {
			allSourceDatas = chartUtils.allSourceDatas;
		}

		if (allCompareDatas == undefined || allCompareDatas == null || allCompareDatas.length < 1) {
			allCompareDatas = chartUtils.allCompareDatas;
		}


		var defaultColNames = null;

		var defaultColModels = null;

		if (stringUtils.isEmpty(restPack.getModuleId())) {
			defaultColNames = ['배터리 전압', '배터리 전류', '최저전압', '최고전압', '셀중 최저온도', '셀중 최고온도'];
			defaultColModels = [
				'dcVoltage', 'dcRatedCurrent', 'minVoltage', 'maxVoltage', 'minTempValue', 'maxTempValue'
			];
		} else {
			defaultColNames = [
				'1번셀 전압', '2번셀 전압', '3번셀 전압', '4번셀 전압', '5번셀 전압',
				'6번셀 전압', '7번셀 전압', '8번셀 전압', '9번셀 전압'];
			defaultColModels = [
				'cellVoltage01', 'cellVoltage02', 'cellVoltage03', 'cellVoltage04', 'cellVoltage05',
				'cellVoltage06', 'cellVoltage07', 'cellVoltage08', 'cellVoltage09'
			];
		}
		if (colModelDatas != null) {
			if (colModelDatas.colNames != null && colModelDatas.colNames.length > 0) {
				for (var i = 0; i < colModelDatas.colNames.length; i++) {
					defaultColNames.push(colModelDatas.colNames[i]);
				}
			}

			if (colModelDatas.colModels != null && colModelDatas.colModels.length > 0) {
				for (var i = 0; i < colModelDatas.colModels.length; i++) {
					defaultColModels.push(colModelDatas.colModels[i])
				}
			}
		}

		var allDatas = new Array();

		var serial = arrayUtils.isEmpty(allSourceDatas) ? null : allSourceDatas[0].serial;

		for (var i = 0; i < defaultColModels.length; i++) {
			var modelArrays = new Array();
			var key = defaultColModels[i];

			var name = arrayUtils.isEmpty(allCompareDatas) ? defaultColNames[i] : defaultColNames[i] + ' (Source)';
			var batStats = allSourceDatas;


			for (var j = 0; j < batStats.length; j++) {
				var date = batStats[j].regDate;

				var value = batStats[j][key];
				var data = [date, value];

				modelArrays.push(data);
			}

			allDatas.push({label : name, data : modelArrays, xaxis: 1, yaxis: i + 1});
		}

		if (arrayUtils.isNotEmpty(allCompareDatas)) {
			var compareBatId = allCompareDatas[0].serial;

			for (var i = 0; i < defaultColModels.length; i++) {
				var modelArrays = new Array();
				var key = defaultColModels[i];
				var name =  defaultColNames[i] + ' (Target)';
				var compareBatStats = allCompareDatas;

				for (var j = 0; j < compareBatStats.length; j++) {
					var date = compareBatStats[j].regDate;
					var value = compareBatStats[j][key];
					var data = [date, value];

					modelArrays.push(data);
				}

				allDatas.push({label : name, data : modelArrays, xaxis: 1, yaxis: defaultColModels.length + (i + 1)});
			}
		}

		return allDatas;
	},
	getSelectedChkboxColDatas : function (element) {
		var colNames = [];
		var colModels = [];
		//#tblDetailChartColSelector
		$(element + ' input[type=checkbox]:checked').each(function () {
			var colName =  $(this).attr('data-colName');
			var colModelName = $(this).attr('data-colModelName');
			colNames.push(colName);
			colModels.push(colModelName);
		});

		return {
			colNames : colNames,
			colModels : colModels
		}
	}
};