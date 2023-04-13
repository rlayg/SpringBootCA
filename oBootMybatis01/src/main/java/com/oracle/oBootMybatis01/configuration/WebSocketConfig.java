package com.oracle.oBootMybatis01.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.oracle.oBootMybatis01.handler.SocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Autowired
	SocketHandler socketHandler;
	
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 소켓에서는 socketHandler가 Server역할
		// 누군가  URL /chating --> socketHandler 발동
		registry.addHandler(socketHandler, "/chatting");	// chating을 치면 socketHandler을 서버프로그램으로 발동시키겟다
		
		
	}

}
