package kr.or.ddit.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;

import kr.or.ddit.users.model.UserVo;

public class ProfileView implements View {

	private static final Logger logger = LoggerFactory.getLogger(ProfileView.class);
	
	@Override
	public String getContentType() {
		return "img";
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.debug("♬♪♩ profileView.render() ");
		
		UserVo userVo = (UserVo) model.get("userVo");
		
		//path 정보로 file을 읽어서 
		ServletOutputStream sos = response.getOutputStream();
		FileInputStream fis = null;
		String filePath = userVo.getUser_path();
		
		//실제로 해당 파일이있는지 없는지 체크..!
		if(filePath == null){
			filePath =  request.getServletContext().getRealPath("/img/사진없음.jpg");
		} else {
			File checkFile = new File(filePath);
			
			if(!checkFile.exists()) {
				filePath =  request.getServletContext().getRealPath("/img/사진없음.jpg");
			}
		}
		//화면에 띄워주는 부분!
		File file = new File(filePath);
		fis = new FileInputStream(file);
		byte[] buffer = new byte[512];
		
		//다읽으면 -1 반환, 	
		//response 객체에 스트림으로 ? 써준다?
		while(fis.read(buffer, 0, 512) != -1){
			sos.write(buffer);
		}
		fis.close();
		sos.close();
	}
	
	
	

}
