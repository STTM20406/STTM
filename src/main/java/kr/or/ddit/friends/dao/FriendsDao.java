package kr.or.ddit.friends.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.friends.model.ChatFriendsVo;
import kr.or.ddit.friends.model.FriendsVo;

@Repository
public class FriendsDao implements IFriendsDao{
	
	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;

	@Override
	public List<ChatFriendsVo> friendList(String user_email) {
		return sqlSession.selectList("friend.allFriendList",user_email);
	}
}
