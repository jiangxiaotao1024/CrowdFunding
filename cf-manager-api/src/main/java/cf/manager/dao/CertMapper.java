package cf.manager.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cf.bean.AccountTypeCert;
import cf.bean.Cert;
import cf.bean.CertExample;

public interface CertMapper {
	long countByExample(CertExample example);

	int deleteByExample(CertExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(Cert record);

	int insertSelective(Cert record);

	List<Cert> selectByExample(CertExample example);

	Cert selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Cert record, @Param("example") CertExample example);

	int updateByExample(@Param("record") Cert record, @Param("example") CertExample example);

	int updateByPrimaryKeySelective(Cert record);

	int updateByPrimaryKey(Cert record);

	List<Cert> queryCert(@Param("startIndex") int startIndex, @Param("pagesize") int pagesize);

	int queryCount();

	List<Cert> queryCertByWord(@Param("startIndex") int startIndex, @Param("pagesize") int pagesize,
			@Param("word") String word);

	int queryCountByWord(String word);

	void deleteByBatch(@Param("ids") List<Integer> ids);

	List<Cert> selectAll();

	void insertAtc(AccountTypeCert accountTypeCert);

	List<AccountTypeCert> selectAtc();

	void deleteAtc();

	List<Map<String, Object>> getCersByMember(int memberid);

}