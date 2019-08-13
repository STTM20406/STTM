package kr.or.ddit.vote_part.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.vote_part.dao.IVote_PartDao;
import kr.or.ddit.vote_part.model.Vote_PartVo;

@Service
public class Vote_PartService implements IVote_PartService{
	@Resource(name="vote_PartDao")
	IVote_PartDao vote_PartDao;
	
	@Override
	public Vote_PartVo checkVote(Map<String, Object> paramMap) {
		return vote_PartDao.checkVote(paramMap);
	}
	
	@Override
	public int vote(Vote_PartVo vote_PartVo) {
		return vote_PartDao.vote(vote_PartVo);
	}
}
