package kr.co.iei.membership.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.membership.model.vo.MemberShip;

@Mapper
public interface MemberShipDao {

	List<MemberShip> selectAllMembership(MemberShip ms);

}
