package cf.util;

import java.util.ArrayList;
import java.util.List;

import cf.bean.AccountTypeCert;
import cf.bean.MemberCert;
import cf.bean.User;

public class Data {
	List<User> userList = new ArrayList<User>();
	List<Integer> ids = new ArrayList<Integer>();
	List<AccountTypeCert> accountTypeCerts = new ArrayList<AccountTypeCert>();
	List<MemberCert> memberCerts = new ArrayList<MemberCert>();

	public List<MemberCert> getMemberCerts() {
		return memberCerts;
	}

	public void setMemberCerts(List<MemberCert> memberCerts) {
		this.memberCerts = memberCerts;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public List<AccountTypeCert> getAccountTypeCerts() {
		return accountTypeCerts;
	}

	public void setAccountTypeCerts(List<AccountTypeCert> accountTypeCerts) {
		this.accountTypeCerts = accountTypeCerts;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
