package kr.or.ddit.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

public class HandShakeInterceptor extends HttpSessionHandshakeInterceptor{

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
									Map<String, Object> attributes) throws Exception {
		
		System.out.println("Before Handshake");
		
//		ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
//		System.out.println("URI : " + request.getURI());
//		
//		HttpServletRequest req = ssreq.getServletRequest();
		
//		String ct_id = req.getParameter("ct_id");
//		System.out.println("******************param, id : " + ct_id);
//		
//		attributes.put("ct_id", ct_id);
		
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}
	
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		System.out.println("After HandShake");
		
		super.afterHandshake(request, response, wsHandler, ex);
	}
	
}
