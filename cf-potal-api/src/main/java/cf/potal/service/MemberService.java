package cf.potal.service;

import java.util.List;

import cf.bean.Cert;
import cf.bean.Member;
import cf.bean.MemberCert;

public interface MemberService {

	Member queryMember(String loginacct);

	void save(Member member);

	void updateAccttype(Member member);

	List<Cert> queryCert(String accttype);

	void saveMemberCert(List<MemberCert> memberCerts);

	void updateMember(Member member);

	Member getMemberById(Integer memberid);

	List<MemberCert> getMemberCertById(int memberid);

}
