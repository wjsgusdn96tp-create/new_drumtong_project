package kr.co.iei.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.member.model.vo.Member;

@Mapper
public interface MemberDao {

	Member selectOneMember(Member m);

	int insertMember(Member m);

	int updateMember(Member m);

	int deleteMember(int memberNo);

}
