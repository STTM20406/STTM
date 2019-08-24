package kr.or.ddit.notification.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotificationController {

	@RequestMapping("/notification")
	public String notifiList() {
		
		return "";
	}
	
}
