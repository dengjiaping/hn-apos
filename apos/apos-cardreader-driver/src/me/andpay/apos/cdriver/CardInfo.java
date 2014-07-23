package me.andpay.apos.cdriver;

import java.io.Serializable;


/**
 * 解析的卡信息
 * @author cpz
 *
 */
public class CardInfo implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2640889130321742937L;

	private String formatID;
	
	private String ksn;
	
	private String encTracks;
	
	private int track1Length;
	
	private int track2Length;
	
	private int track3Length;
	
	private String randomNumber;
	
	private String  maskedPAN;
	
	private String  expiryDate;
	
	private String cardHolderName;
	
	private byte[] pin;
	//hex
	private String pinRandNumber;

	public String getFormatID() {
		return formatID;
	}

	public void setFormatID(String formatID) {
		this.formatID = formatID;
	}

	public String getKsn() {
		return ksn;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}

	public String getEncTracks() {
		return encTracks;
	}

	public void setEncTracks(String encTracks) {
		this.encTracks = encTracks;
	}

	public int getTrack1Length() {
		return track1Length;
	}

	public void setTrack1Length(int track1Length) {
		this.track1Length = track1Length;
	}

	public int getTrack2Length() {
		return track2Length;
	}

	public void setTrack2Length(int track2Length) {
		this.track2Length = track2Length;
	}

	public int getTrack3Length() {
		return track3Length;
	}

	public void setTrack3Length(int track3Length) {
		this.track3Length = track3Length;
	}

	public String getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(String randomNumber) {
		this.randomNumber = randomNumber;
	}

	public String getMaskedPAN() {
		return maskedPAN;
	}

	public void setMaskedPAN(String maskedPAN) {
		this.maskedPAN = maskedPAN;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public byte[] getPin() {
		return pin;
	}

	public void setPin(byte[] pin) {
		this.pin = pin;
	}

	public String getPinRandNumber() {
		return pinRandNumber;
	}

	public void setPinRandNumber(String pinRandNumber) {
		this.pinRandNumber = pinRandNumber;
	}
	
	

}
