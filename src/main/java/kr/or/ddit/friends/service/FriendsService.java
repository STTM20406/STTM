package kr.or.ddit.friends.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.friends.dao.IFriendsDao;
import kr.or.ddit.friends.model.ChatFriendsVo;
import kr.or.ddit.friends.model.FriendsVo;

@Service
public class FriendsService implements IFriendsService{

	@Resource(name="friendsDao")
	private IFriendsDao friendsDao;
	
	@Override
	public List<ChatFriendsVo> friendList(String user_email) {
		List<ChatFriendsVo> list = friendsDao.friendList(user_email);
		return list;
	}

}
