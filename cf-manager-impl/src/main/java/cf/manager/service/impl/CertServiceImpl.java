package cf.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.bean.AccountTypeCert;
import cf.bean.Cert;
import cf.bean.CertExample;
import cf.manager.dao.CertMapper;
import cf.manager.service.CertService;

@Service
public class CertServiceImpl implements CertService {

	@Autowired
	CertMapper certMapper;

	public List<Cert> queryCert(int startIndex, int pagesize) {
		// TODO Auto-generated method stub
		List<Cert> certs = certMapper.queryCert(startIndex, pagesize);
		return certs;
	}

	public int queryCount() {
		// TODO Auto-generated method stub
		int count = certMapper.queryCount();
		return count;
	}

	public List<Cert> queryCertByWord(int startIndex, int pagesize, String word) {
		// TODO Auto-generated method stub
		List<Cert> certs = certMapper.queryCertByWord(startIndex, pagesize, word);
		return certs;
	}

	public int queryCountByWord(String word) {
		// TODO Auto-generated method stub
		int count = certMapper.queryCountByWord(word);
		return count;
	}

	public void addCert(String name) {
		// TODO Auto-generated method stub
		Cert cert = new Cert();
		cert.setName(name);
		certMapper.insertSelective(cert);
	}

	public Cert selectById(int id) {
		// TODO Auto-generated method stub
		Cert cert = certMapper.selectByPrimaryKey(id);
		return cert;
	}

	public void updateCert(int id, String name) {
		// TODO Auto-generated method stub
		Cert cert = new Cert();
		cert.setId(id);
		cert.setName(name);
		CertExample certExample = new CertExample();
		certExample.createCriteria().andIdEqualTo(id);
		certMapper.updateByExample(cert, certExample);
	}

	public void deleteRole(int id) {
		// TODO Auto-generated method stub
		certMapper.deleteByPrimaryKey(id);
	}

	public void deleteByBatch(List<Integer> ids) {
		// TODO Auto-generated method stub
		certMapper.deleteByBatch(ids);
	}

	public List<Cert> selectAll() {
		// TODO Auto-generated method stub
		List<Cert> certs = certMapper.selectAll();
		return certs;
	}

	public List<AccountTypeCert> selectAtc() {
		// TODO Auto-generated method stub
		List<AccountTypeCert> accountTypeCerts = certMapper.selectAtc();
		return accountTypeCerts;
	}

	public void updateAtc(List<AccountTypeCert> accountTypeCerts) {
		// TODO Auto-generated method stub
		certMapper.deleteAtc();
		for (AccountTypeCert accountTypeCert : accountTypeCerts) {
			certMapper.insertAtc(accountTypeCert);
		}
	}

	public List<Map<String, Object>> getCertsByMember(int memberid) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> certs = certMapper.getCersByMember(memberid);
		return certs;
	}

}
