package cf.potal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.bean.Cert;
import cf.bean.Member;
import cf.bean.MemberCert;
import cf.bean.MemberCertExample;
import cf.bean.MemberExample;
import cf.manager.dao.MemberCertMapper;
import cf.potal.dao.MemberMapper;
import cf.potal.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;
	@Autowired
	MemberCertMapper memberCertMapper;

	public Member queryMember(String loginacct) {
		// TODO Auto-generated method stub
		Member member = memberMapper.selectByName(loginacct);
		return member;
	}

	public void save(Member member) {
		// TODO Auto-generated method stub
		memberMapper.insertSelective(member);
	}

	public void updateAccttype(Member member) {
		// TODO Auto-generated method stub
		MemberExample memberExample = new MemberExample();
		memberExample.createCriteria().andIdEqualTo(member.getId());
		memberMapper.updateByExampleSelective(member, memberExample);
	}

	public List<Cert> queryCert(String accttype) {
		// TODO Auto-generated method stub

		List<Cert> certs = memberMapper.queryCert(accttype);
		return certs;
	}

	public void saveMemberCert(List<MemberCert> memberCerts) {
		// TODO Auto-generated method stub
		for (MemberCert memberCert : memberCerts)
			memberMapper.saveMemberCert(memberCert);
	}

	public void updateMember(Member member) {
		// TODO Auto-generated method stub
		MemberExample memberExample = new MemberExample();
		memberExample.createCriteria().andIdEqualTo(member.getId());
		memberMapper.updateByExampleSelective(member, memberExample);
	}

	public Member getMemberById(Integer memberid) {
		// TODO Auto-generated method stub
		Member member = memberMapper.selectByPrimaryKey(memberid);
		return member;
	}

	public List<MemberCert> getMemberCertById(int memberid) {
		// TODO Auto-generated method stub
		MemberCertExample memberCertExample = new MemberCertExample();
		memberCertExample.createCriteria().andMemberidEqualTo(memberid);
		List<MemberCert> memberCerts = memberCertMapper.selectByExample(memberCertExample);
		return memberCerts;
	}

}
