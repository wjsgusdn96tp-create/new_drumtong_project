package kr.co.iei.membershiprecode.model.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.membershiprecode.model.vo.MemberShipRecode;

@Mapper
public interface MemberShipRecodeDao {

	int insertRecode(MemberShipRecode recode);

}
