package kr.co.iei.membership.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.membership.model.dao.MemberShipDao;
import kr.co.iei.membership.model.vo.MemberShip;

@Service
public class MemberShipService {
	@Autowired
	private MemberShipDao membershipDao;

	public List<MemberShip> selectAllMembership(MemberShip ms) {
		List<MemberShip> list = membershipDao.selectAllMembership(ms);
		return list;
	}
}
