package kr.co.iei.membershiprecode.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.membershiprecode.model.dao.MemberShipRecodeDao;
import kr.co.iei.membershiprecode.model.vo.MemberShipRecode;

@Service
public class MemberShipRecodeService {
	@Autowired
	private MemberShipRecodeDao membershiprecodeDao;

	public int insertRecode(MemberShipRecode recode) {
		int result = membershiprecodeDao.insertRecode(recode);
		return result;
	}
}
