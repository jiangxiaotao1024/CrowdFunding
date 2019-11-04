package cf.manager.service;

import java.util.List;
import java.util.Map;

import cf.bean.AccountTypeCert;
import cf.bean.Cert;

public interface CertService {

	List<Cert> queryCert(int startIndex, int pagesize);

	int queryCount();

	List<Cert> queryCertByWord(int startIndex, int pagesize, String word);

	int queryCountByWord(String word);

	void addCert(String name);

	Cert selectById(int id);

	void updateCert(int id, String name);

	void deleteRole(int id);

	void deleteByBatch(List<Integer> ids);

	List<Cert> selectAll();

	List<AccountTypeCert> selectAtc();

	void updateAtc(List<AccountTypeCert> accountTypeCerts);

	List<Map<String, Object>> getCertsByMember(int memberid);

}
