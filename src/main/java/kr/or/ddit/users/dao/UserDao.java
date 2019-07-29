package kr.or.ddit.users.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;

@Repository
public class UserDao implements IUserDao{
	
	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSession;
	
	/**
	 * 
	* Method : insertUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param userVo
	* @return
	* Method 설명 : 사용자 입력
	 */
	@Override
	public int insertUser(UserVo userVo) {
		return sqlSession.insert("user.insertUser",userVo);
	}
	
	/**
	 * 
	* Method : userList
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @return
	* Method 설명 : 전체 사용자 조회
	 */
	@Override
	public List<UserVo> userList() {
		return sqlSession.selectList("user.userList");
	}
	
	/**
	 * 
	* Method : getUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param user_email
	* @return
	* Method 설명 : 사용자 정보 조회
	 */
	@Override
	public UserVo getUser(String user_email) {
		return sqlSession.selectOne("user.getUser",user_email);
	}
	
	/**
	 * 
	* Method : userPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param pageVo
	* @return
	* Method 설명 : 사용자 페이징 리스트 조회
	 */
	@Override
	public List<UserVo> userPagingList(PageVo pageVo) {
		return sqlSession.selectList("user.userPagingList",pageVo);
	}
	
	/**
	 * 
	* Method : userCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @return
	* Method 설명 : 사용자 전체수 조회
	 */
	@Override
	public int userCnt() {
		return sqlSession.selectOne("user.userCnt");
	}
	
	/**
	 * 
	* Method : updateUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param userVo
	* @return
	* Method 설명 : 사용자 정보 업데이트
	 */
	@Override
	public int updateUser(UserVo userVo) {
		return sqlSession.update("user.updateUser",userVo);
	}
	
	/**
	 * 
	* Method : updateUserPass
	* 작성자 : 김경호
	* 변경이력 : 2019-07-27
	* @param user_pass
	* @return
	* Method 설명 : 사용자 비밀번호 업데이트
	 */
	@Override
	public int updateUserPass(UserVo userVo) {
		return sqlSession.update("user.updateUserPass",userVo);
	}
	
	/**
	 * 
	* Method : updateUserStatus
	* 작성자 : 김경호
	* 변경이력 : 2019-07-29
	* @param userVo
	* @return
	* Method 설명 : 사용자 상태 업데이트(휴면 계정 설정)
	 */
	@Override
	public int updateUserStatus(UserVo userVo) {
		return sqlSession.update("user.updateUserStatus",userVo);
	}

	/**
	 * 
	* Method : deleteUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param user_email
	* @return
	* Method 설명 : 사용자 삭제
	 */
	@Override
	public int deleteUser(String user_email) {
		return sqlSession.delete("user.deleteUser",user_email);
	}
	
	/**
	 * 
	* Method : userListForPassEncrypt
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @return
	* Method 설명 : 비밀번호 암호화 적용대상 사용자 전체 조회
	 */
	@Override
	public List<UserVo> userListForPassEncrypt() {
		return sqlSession.selectList("user.userListForPassEncrypt");
	}
	
	/**
	 * 
	* Method : updateUserEncryptPass
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param userVo
	* @return
	* Method 설명 : 사용자 비밀번호 암호화 적용
	 */
	@Override
	public int updateUserEncryptPass(UserVo userVo) {
		return sqlSession.update("user.updateUserEncryptPass",userVo);
	}


}
