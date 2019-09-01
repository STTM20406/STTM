<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<tiles:insertAttribute name="header"/>
<div id="wrapper">
	<%@include file="/WEB-INF/views/common/baseLeft.jsp"%>
	<div class="container">
		<tiles:insertAttribute name="body"/>
	</div>
</div>
<tiles:insertAttribute name="tail"/>