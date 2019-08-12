<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script src="/js/radialIndicator.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		var radialObj = radialIndicator('#clock', {
		    radius: 60,
		    barWidth: 5,
		    barColor: '#FF0000',
		    minValue: 0,
		    maxValue: 60,
		    fontWeight: 'normal',
		    roundCorner: true,
		    format: function (value) {
		        var date = new Date();
		        return date.getHours() + ':' + date.getMinutes();
		    }
		});
			 
		setInterval(function () {
		    radialObj.value(new Date().getSeconds() + 1);
		}, 1000);
	});
</script>	
	
<section>
	<div id="clock"></div>
</section>




	