package kr.or.ddit.likes.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import kr.or.ddit.board_write.dao.IBoard_WriteDao;
import kr.or.ddit.board_write.model.Board_WriteVo;
import kr.or.ddit.likes.dao.ILikesDao;
import kr.or.ddit.users.model.UserVo;

@Service
public class LikesService implements ILikesService{

	@Resource(name="likesDao")
	private ILikesDao likeDao;
	
	@Resource(name="board_WriteDao")
	private IBoard_WriteDao writeDao; 
	
	@Override
	public int likeCheck(Map<String, Object> like) {
		
		int res = 0;
		
		Board_WriteVo writeVo = new Board_WriteVo();
		HttpSession session = null;
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("write_id", writeVo.getWrite_id());
		resultMap.put("user_email",userVo.getUser_email());
		
		int result = likeDao.likeCheck(resultMap);
		
		if(result == 0) {
			//res = likeDao.likeAdd(writeVo.getWrite_id());
		}else {
			//res = likeDao.likeDown(writeVo.getWrite_id());
		}
		
		return res;
	}

	@Override
	public int likeAdd(Board_WriteVo vo) {
		return likeDao.likeAdd(vo);
	}

	@Override
	public int likeDown(Board_WriteVo vo) {
		return likeDao.likeDown(vo);
	}

	@Override
	public int likeCnt(int write_id) {
		return likeDao.likeCnt(write_id);
	}

	@Override
	public int whoLikeAdd(Board_WriteVo vo) {
		return likeDao.whoLikeAdd(vo);
	}

	@Override
	public int whoLikeDown(Board_WriteVo vo) {
		return likeDao.whoLikeDown(vo);
	}

	@Override
	public int likePushCheck(Board_WriteVo vo) {
		return likeDao.likePushCheck(vo);
	}

}
