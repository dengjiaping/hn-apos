package me.andpay.apos.cdriver.model;

public class AposIcPublicKey {

	private String expirationDate;
	private String exponent;
	private String hashAlgorithmIndicator;
	private String index;
	private String modulus;
	private String signatureAlgorithmIndicator;
	private String rid;
	private String sha1CheckSum;

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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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

}
