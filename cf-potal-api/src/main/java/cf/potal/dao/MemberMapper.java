package cf.potal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cf.bean.Cert;
import cf.bean.Member;
import cf.bean.MemberCert;
import cf.bean.MemberExample;

public interface MemberMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Member record);

	int insertSelective(Member record);

	Member selectByPrimaryKey(Integer id);

	List<Member> selectAll();

	int updateByPrimaryKey(Member record);

	int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

	int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

	Member queryMebmerlogin(Map<String, Object> paramMap);

	void updateAcctType(Member loginMember);

	void updateBasicinfo(Member loginMember);

	void updateEmail(Member loginMember);

	void updateAuthstatus(Member loginMember);

	Member getMemberById(Integer memberid);

	List<Map<String, Object>> queryCertByMemberid(Integer memberid);

	Member selectByName(String loginacct);

	List<Cert> queryCert(String accttype);

	void saveMemberCert(MemberCert memberCert);

}