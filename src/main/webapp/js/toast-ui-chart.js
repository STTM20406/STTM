/**
 * Toast UI Chart 로드를 위해 작성한 js 파일입니다.
 * 작성자 : 유승진
 * 작성일 : 2019년 07월 26일
 */
tui.chart.registerTheme('customTheme', {
	chart: {
		background: "#e1e1e1"
	}
});
function loadPieChart(pieContainer, pieData, width, height) {
	
	var options3 = {
			chart: {
				"width": width, 
				"height": height,
			    format: function(value, chartType, areaType, valuetype, legendName) {
		            if (areaType === 'makingSeriesLabel') { // formatting at series area
		                value = value + '%';
		            }
		            return value;
		        }},
			series: {
				radiusRange: ['40%', '100%'],
				showLabel: true,
				showLegend: true
			},
			legend: {
				showCheckbox: false,
				align: 'center'
			},
			tooltip: {
		        grouped: false
		        ,
		        template: function(category, item) {
		        	var ratio = Math.round(item.ratio*100);
		        	var pieList = pieData.pieData;
		        	
		        	var legend = item.legend;
		        	var keyword = "";
		        		switch(legend){
							case "완료된 업무":
			        			keyword = "cmp";
			        			break;
			        		case "계획된 업무":
			        			keyword = "plan";
			        			break;
			        		case "마감일 지난 업무":
			        			keyword = "overdue";
			        			break;
			        		case "마감일 없는 업무":
			        			keyword = "nodeadline";
			        			break;
		        		}
		        		var temp = "";
		        		var items = pieList[keyword];
		        	$(pieList[keyword]).each(function(){
		        		temp += "<span>" + "["+ this.wrk_grade +"] " + this.prj_nm + " > " + this.wrk_lst_nm + " > " + this.wrk_nm + "</span>" + "<br>";
		        	});
		        	var tem = '<div class="tui-chart-default-tooltip">' + 
		        			  '<div class="tui-chart-tooltip-body">' +
		        			  '<span class="tui-chart-legend-rect bar" style=\"' + item.cssText + '\"></span>' + 
		        			  '<span>' + item.legend + '</span>' +
		        			  '<span class="tui-chart-tooltip-value">' + items.length + '개 (' + ratio + '%)' + '</span>' + '<br><br>' + 
		        			   temp +
		        			  '</div>' + '</div>' + '</div>';
		        	return tem;
		        },
		        offsetX: 20,
		        offsetY: -10
		    }
			,
		    chartExportMenu: {
		    	visible: false
		    },
		    theme: 'customTheme'
	}
	var pieChart = tui.chart.pieChart(pieContainer, pieData, options3);
	return pieChart; 
}

function loadPriorChart(priorChartContainer, priorData, width, height) {
	var options = {
		    chart: {
		    	"width": width,
		    	"height": height,
			    format: function(value, chartType, areaType, valuetype, legendName) {
		            if (areaType === 'makingSeriesLabel') { // formatting at series area
		                value = value + '개';
		            }
		            return value;
		        }},
		    series: {
		    	stackType: 'percent', showLabel: true
		    },
		    tooltip: {
		        grouped: false
		        ,
		        template: function(category, item) {
		        	var ratio = Math.round(item.ratio*100);
		        	var temp = "";
		        	var data = priorData.priorData;
		        	var cat = data[category];
		        	var items = cat[item.legend];
		        	$(items).each(function() {
		        		temp += "<span>" + this.prj_nm + " > " + this.wrk_lst_nm + " > " + this.wrk_nm + "</span>" + "<br>";
		        	});
		        	var tem = '<div class="tui-chart-default-tooltip">' + 
		        			  '<div class="tui-chart-tooltip-head show">' +
		        			  category + 
		        			  '</div>' +
		        			  '<div class="tui-chart-tooltip-body">' +
		        			  '<span class="tui-chart-legend-rect bar" style=\"' + item.cssText + '\"></span>' + 
		        			  '<span>' + item.legend + '</span>' +
		        			  '<span class="tui-chart-tooltip-value">' + item.value + '개 (' + ratio + '%)' + '</span><br><br>' +
		        			  temp +
		        			  '</div>' + '</div>' + '</div>';
		        	return tem;
		        },
		        offsetX: 50,
		        offsetY: -150
		    },
		    legend: {
		    	align: 'top',
		    	showCheckbox: false
		    },
		    theme: 'customTheme'
	};
	var priorChart = tui.chart.barChart(priorChartContainer, priorData, options);
	return priorChart;
}

function loadProgressChart(progressChartContainer, progressData, width, height) {
	var options = {
			chart: {
				"width": width,
				"height": height,
			    format: function(value, chartType, areaType, valuetype, legendName) {
		            if (areaType === 'makingSeriesLabel') { // formatting at series area
		                value = value + '%';
		            }
		            return value;
		        }},
			series: {
				stackType: 'percent', showLabel: true
			},
			tooltip: {
				grouped: false,
				template: function(category, item) {
					var ratio = Math.round(item.ratio*100);
					var temp = "";
					var data = progressData;
					var legend = item.legend;
		        	var keyword = "";
		        		switch(legend){
							case "완료된 업무":
			        			keyword = "cmpList";
			        			break;
			        		case "계획된 업무":
			        			keyword = "planList";
			        			break;
			        		case "마감일 지난 업무":
			        			keyword = "overdueList";
			        			break;
			        		case "마감일 없는 업무":
			        			keyword = "nodeadlineList";
			        			break;
		        		}
		        		var temp = "";
					var items = data[keyword];
					$(items).each(function() {
						temp += "<span>"+ "["+ this.wrk_grade +"] " + this.prj_nm + " > " + this.wrk_lst_nm + " > " + this.wrk_nm + "</span>" + "<br>";
					});
					var tem = '<div class="tui-chart-default-tooltip">' + 
					'<div class="tui-chart-tooltip-body">' +
					'<span class="tui-chart-legend-rect bar" style=\"' + item.cssText + '\"></span>' + 
					'<span>' + item.legend + '</span>' +
					'<span class="tui-chart-tooltip-value">' + items.length + '개 (' + ratio + '%)' +
					'</span><br><br>' +
					temp +
					'</div>' + '</div>' + '</div>';
					return tem;
				}
			},
			legend: {
				align: 'top',
				showCheckbox: false
			},
		    theme: 'customTheme'
	};
	var progressChart = tui.chart.barChart(progressChartContainer, progressData, options);
	return progressChart;
}

function loadPercentChart(percentChartContainer, percentData, width, height) {
	var options2 = {
		    chart: {
		    	"width": width,
		    	"height": height,
			    format: function(value, chartType, areaType, valuetype, legendName) {
		            if (areaType === 'makingSeriesLabel') { // formatting at series area
		                value = value + '%';
		            }
		            return value;
		        }},
		    series: { stackType: 'percent', showLabel: true },
		    tooltip: { grouped: false,
		        template: function(category, item) {
		        	var temp = "";
		        	var data = percentData.percentData;
		        	var items = data[item.legend];
		        	$(items).each(function() {
		        		temp += "<span>" + "["+ this.wrk_grade +"] " + this.prj_nm + " > " + this.wrk_lst_nm + " > " + this.wrk_nm + "</span>" + "<br>";
		        	});
		        	var tem = '<div class="tui-chart-default-tooltip">' + 
		        			  '<div class="tui-chart-tooltip-head show">' +
		        			  category + 
		        			  '</div>' +
		        			  '<div class="tui-chart-tooltip-body">' +
		        			  '<span class="tui-chart-legend-rect bar" style=\"' + item.cssText + '\"></span>' + 
		        			  '<span>' + item.legend + '</span>' +
		        			  '<span class="tui-chart-tooltip-value">' + item.value + '%' + '</span>' + '<br><br>' +
		        			  temp +
		        			  '</div>' + '</div>' + '</div>';
		        	return tem;
		        			  
		        },
		        offsetX: 50,
		        offsetY: -150
		    },
		    legend: {
		    	align: 'top',
		    	showCheckbox: false
		    },
		    theme: 'customTheme'
	};
	var percentChart = tui.chart.barChart(percentChartContainer, percentData, options2);
	return percentChart;
};

function loadListChart(listChartContainer, listData, width, height) {
	var workList = listData.work;
	var options2 = {
			chart: {
				"width": width,
				"height": height,
			    format: function(value, chartType, areaType, valuetype, legendName) {
		            if (areaType === 'makingSeriesLabel') { // formatting at series area
		                value = value + '%';
		            }
		            return value;
		        }},
			series: { stackType: 'percent', showLabel: true },
			tooltip: { grouped: false,
				template: function(category, item) {
		        	var ratio = Math.round(item.ratio*100);
		        	var temp = "";
		        	
		        	var cat = workList[category];
		        	var legend = item.legend;
		        	var keyword = "";
		        		switch(legend){
							case "완료된 업무":
			        			keyword = "cmpList";
			        			break;
			        		case "계획된 업무":
			        			keyword = "planList";
			        			break;
			        		case "마감일 지난 업무":
			        			keyword = "overdueList";
			        			break;
			        		case "마감일 없는 업무":
			        			keyword = "nodeadlineList";
			        			break;
		        		}
		        		var items = cat[keyword];
		        	$(cat[keyword]).each(function(){
		        		temp += "<span>" + "["+ this.wrk_grade +"] " + this.prj_nm + " > " + this.wrk_lst_nm + " > " + this.wrk_nm + "</span>" + "<br>";
		        	});
		        	var tem = '<div class="tui-chart-default-tooltip">' + 
		        			  '<div class="tui-chart-tooltip-head show">' +
		        			  category + 
		        			  '</div>' +
		        			  '<div class="tui-chart-tooltip-body">' +
		        			  '<span class="tui-chart-legend-rect bar" style=\"' + item.cssText + '\"></span>' + 
		        			  '<span>' + item.legend + '</span>' +
		        			  '<span class="tui-chart-tooltip-value">'+ items.length + "개 (" + ratio + '%' + ")" + '</span>' + '<br><br>' +
		        			  temp
		        			  +
		        			  '</div>' + '</div>' + '</div>';
		        	return tem;
		        },
		        offsetX: -150,
		        offsetY: -10
			},
			legend: {
				align: 'top',
				showCheckbox: false
			},
		    theme: 'customTheme'
	};
	var listChart = tui.chart.barChart(listChartContainer, listData, options2);
	return listChart;
};
