package kr.co.iei.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.member.model.dao.MemberDao;
import kr.co.iei.member.model.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao;

	public Member login(Member m) {
		Member member = memberDao.selectOneMember(m);
		return member;
	}// login
	
	@Transactional
	public int insertMember(Member m) {
		int result = memberDao.insertMember(m);
		return result;
	}// insertMember
	
	public Member selectOneMember(String checkEmail) {
		Member m = new Member();
		m.setMemberEmail(checkEmail);
		Member member = memberDao.selectOneMember(m);
		return member;
	}// selectOneMember

	@Transactional
	public int updateMember(Member m) {
		int result = memberDao.updateMember(m);
		return result;
	}// updateMember

	@Transactional
	public int deleteMember(int memberNo) {
		int result = memberDao.deleteMember(memberNo);
		return result;
	}
	
}// MemberService Class
