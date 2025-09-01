package kr.co.iei.member.model.service;

import java.util.List;
import java.util.StringTokenizer;

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
	}// deleteMember

	public List selectAllMember() {
		List list = memberDao.selectAllMember();
		return list;
	}// selectAllMember
	
	@Transactional
	public int changeMemberGrade(Member m) {
		int result = memberDao.changeMemberGrade(m);
		return result;
	}

	@Transactional
	public boolean checkedChangeGrade(String no, String grade) {
		StringTokenizer sT1 = new StringTokenizer(no, "/");
		StringTokenizer sT2 = new StringTokenizer(grade, "/");
		int result = 0;
		int count = sT1.countTokens();
		while(sT1.hasMoreTokens()) {
			String stringNo = sT1.nextToken();
			int memberNo = Integer.parseInt(stringNo);
			String StringGrade = sT2.nextToken();
			String memberGrade = StringGrade;
			
			Member m = new Member();
			m.setMemberNo(memberNo);
			m.setMemberGrade(memberGrade);
			result += memberDao.changeMemberGrade(m);
		}
		return result == count;
	}
	
}// MemberService Class
