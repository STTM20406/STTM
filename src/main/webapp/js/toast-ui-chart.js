/**
 * Toast UI Chart 로드를 위해 작성한 js 파일입니다.
 * 작성자 : 유승진
 * 작성일 : 2019년 07월 26일
 */
function loadPieChart(pieContainer, pieData, width, height) {
	var options3 = {
			chart: {"width": width, "height": height},
			series: {
				radiusRange: ['40%', '100%'],
				showLabel: true,
				showLegend: true
			},
			legend: {
				showCheckbox: false,
				align: 'outer'
			},
			tooltip: {
		        grouped: false
		        ,
		        template: function(category, item) {
		        	var ratio = Math.floor(item.ratio*100);
		        	var tem = '<div class="tui-chart-default-tooltip">' + 
		        			  '<div class="tui-chart-tooltip-body">' +
		        			  '<span class="tui-chart-legend-rect bar" style=\"' + item.cssText + '\"></span>' + 
		        			  '<span>' + item.legend + '</span>' +
		        			  '<span class="tui-chart-tooltip-value">' + item.value + '개 (' + ratio + '%)' + '</span>' +
		        			  '</div>' + '</div>' + '</div>';
		        	return tem;
		        },
		        offsetX: 20,
		        offsetY: -10
		    }
			,
		    chartExportMenu: {
		    	visible: false
		    }
	}
	var pieChart = tui.chart.pieChart(pieContainer, pieData, options3);
	return pieChart; 
}

function loadPriorChart(priorChartContainer, priorData, width, height) {
	var options = {
		    chart: {"width": width, "height": height},
		    series: {
		    	stackType: 'percent',
		    	showLabel: true
		    },
		    tooltip: {
		        grouped: false
		        ,
		        template: function(category, item) {
		        	var ratio = Math.floor(item.ratio*100);
		        	var tem = '<div class="tui-chart-default-tooltip">' + 
		        			  '<div class="tui-chart-tooltip-head show">' +
		        			  category + 
		        			  '</div>' +
		        			  '<div class="tui-chart-tooltip-body">' +
		        			  '<span class="tui-chart-legend-rect bar" style=\"' + item.cssText + '\"></span>' + 
		        			  '<span>' + item.legend + '</span>' +
		        			  '<span class="tui-chart-tooltip-value">' + item.value + '개 (' + ratio + '%)' + '</span>' +
		        			  '</div>' + '</div>' + '</div>';
		        	return tem;
		        },
		        offsetX: 20,
		        offsetY: -10
		    },
		    legend: {
		    	align: 'top',
		    	showCheckbox: false
		    }
	};
	
	var priorChart = tui.chart.barChart(priorChartContainer, priorData, options);
	return priorChart;
	
}
function loadPercentChart(percentChartContainer, percentData, width, height) {
	var options2 = {
		    chart: {"width": width, "height": height},
		    series: { stackType: 'percent', showLabel: true },
		    tooltip: { grouped: false,
		        template: function(category, item) {
		        	var tem = '<div class="tui-chart-default-tooltip">' + 
		        			  '<div class="tui-chart-tooltip-head show">' +
		        			  category + 
		        			  '</div>' +
		        			  '<div class="tui-chart-tooltip-body">' +
		        			  '<span class="tui-chart-legend-rect bar" style=\"' + item.cssText + '\"></span>' + 
		        			  '<span>' + item.legend + '</span>' +
		        			  '<span class="tui-chart-tooltip-value">' + item.value + '%' + '</span>' +
		        			  '</div>' + '</div>' + '</div>';
		        	return tem;
		        			  
		        }
		    },
		    legend: {
		    	align: 'top',
		    	showCheckbox: false
		    }
	};
	var percentChart = tui.chart.barChart(percentChartContainer, percentData, options2);
	return percentChart;
};

function loadListChart(listChartContainer, listData, width, height) {
	var options2 = {
			chart: {"width": width, "height": height},
			series: { stackType: 'percent' },
			tooltip: { grouped: false,
				template: function(category, item) {
		        	var ratio = Math.floor(item.ratio*100);
		        	var tem = '<div class="tui-chart-default-tooltip">' + 
		        			  '<div class="tui-chart-tooltip-head show">' +
		        			  category + 
		        			  '</div>' +
		        			  '<div class="tui-chart-tooltip-body">' +
		        			  '<span class="tui-chart-legend-rect bar" style=\"' + item.cssText + '\"></span>' + 
		        			  '<span>' + item.legend + '</span>' +
		        			  '<span class="tui-chart-tooltip-value">'+ ratio + '%' + '</span>' +
		        			  '</div>' + '</div>' + '</div>';
		        	return tem;
		        }
			},
			legend: {
				align: 'top',
				showCheckbox: false
			}
	};
	var listChart = tui.chart.barChart(listChartContainer, listData, options2);
	return listChart;
};
