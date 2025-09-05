package kr.co.iei.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.member.model.vo.Member;

@Mapper
public interface MemberDao {

	Member selectOneMember(Member m);

	int insertMember(Member m);

	int updateMember(Member m);

	int deleteMember(int memberNo);

	List selectAllMember();

	int changeMemberGrade(Member m);

	int updatePasswordByEmail(String memberEmail, String newPassword);

}
