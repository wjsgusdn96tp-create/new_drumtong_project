package kr.co.iei.membership.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.membership.model.dao.MemberShipDao;

@Service
public class MemberShipService {
	@Autowired
	private MemberShipDao membershipDao;

	public List selectAllMembership() {
		List list = membershipDao.selectAllMembership();
		return list;
	}
}
