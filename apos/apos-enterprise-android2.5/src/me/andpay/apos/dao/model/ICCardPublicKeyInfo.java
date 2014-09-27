package me.andpay.apos.dao.model;

import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;

@TableName(name = "ICCardPublicKeyInfo", version = 1)
public class ICCardPublicKeyInfo {

	@ID
	@Column
	private Integer idICCardPublicKeyInfo;
	@Column
	private String expirationDate;
	@Column
	private String exponent;
	@Column
	private String hashAlgorithmIndicator;
	@Column
	private String indexText;
	@Column
	private String modulus;
	@Column
	private String signatureAlgorithmIndicator;
	@Column
	private String rid;
	@Column
	private String sha1CheckSum;

	public Integer getIdICCardPublicKeyInfo() {
		return idICCardPublicKeyInfo;
	}

	public void setIdICCardPublicKeyInfo(Integer idICCardPublicKeyInfo) {
		this.idICCardPublicKeyInfo = idICCardPublicKeyInfo;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getExponent() {
		return exponent;
	}

	public void setExponent(String exponent) {
		this.exponent = exponent;
	}

	public String getHashAlgorithmIndicator() {
		return hashAlgorithmIndicator;
	}

	public void setHashAlgorithmIndicator(String hashAlgorithmIndicator) {
		this.hashAlgorithmIndicator = hashAlgorithmIndicator;
	}

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}

	public String getSignatureAlgorithmIndicator() {
		return signatureAlgorithmIndicator;
	}

	public void setSignatureAlgorithmIndicator(
			String signatureAlgorithmIndicator) {
		this.signatureAlgorithmIndicator = signatureAlgorithmIndicator;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getSha1CheckSum() {
		return sha1CheckSum;
	}

	public void setSha1CheckSum(String sha1CheckSum) {
		this.sha1CheckSum = sha1CheckSum;
	}

	public String getIndexText() {
		return indexText;
	}

	public void setIndexText(String indexText) {
		this.indexText = indexText;
	}

}
