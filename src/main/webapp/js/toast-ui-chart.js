/**
 * Toast UI Chart 로드를 위해 작성한 js 파일입니다.
 * 작성자 : 유승진
 * 작성일 : 2019년 07월 26일
 */
function loadPieChart(pieContainer, pieData) {
	var options3 = {
			chart: {width: 500, height: 300},
			series: {
				radiusRange: ['50%', '100%']
			},
			legend: {
				showCheckbox: false
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
	}
	var pieData = JSON.parse(pieData);
	pieChart = tui.chart.pieChart(pieContainer, pieData, options3);
}

function loadBarChart(priorChartContainer, percentChartContainer, priorData, percentData) {
	var options = {
		    chart: {width: 500, height: 140},
		    series: {
		    	stackType: 'percent'
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
	var options2 = {
		    chart: {width: 500, height: 140},
		    series: { stackType: 'percent' },
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
	var priorData = JSON.parse(priorData);
	var percentData = JSON.parse(percentData);
	priorChart = tui.chart.barChart(priorChartContainer, priorData, options);
	percentChart = tui.chart.barChart(percentChartContainer, percentData, options2);
}
