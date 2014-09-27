package me.andpay.apos.common.contextdata;

import java.util.Map;
import java.util.Set;

/**
 * 机构信息
 * 
 * @author cpz
 * 
 */
public class PartyInfo {

	/**
	 * 机构编号
	 */
	private String partyId;

	/**
	 * 机构名称
	 */
	private String partyName;

	private Set<String> roles;

	private Map<String, String> privileges;
	/**
	 * 扩展配置参数
	 */
	private Map<String, String> extFuncConfigs;

	private Set<String> msrTypes;

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Map<String, String> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Map<String, String> privileges) {
		this.privileges = privileges;
	}

	public Map<String, String> getExtFuncConfigs() {
		return extFuncConfigs;
	}

	public void setExtFuncConfigs(Map<String, String> extFuncConfigs) {
		this.extFuncConfigs = extFuncConfigs;
	}

	public Set<String> getMsrTypes() {
		return msrTypes;
	}

	public void setMsrTypes(Set<String> msrTypes) {
		this.msrTypes = msrTypes;
	}

}
