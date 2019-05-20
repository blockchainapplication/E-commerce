package online_education;

import java.util.Date;



public class Block {
	public String hash;
	public String previousHash;
	private String data;
	private long timeStamp;
	private int nonce;

	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();

		this.hash = calculateHash();
	}

	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256(
				this.previousHash + Long.toString(this.timeStamp) + Integer.toString(this.nonce) + this.data);
		return calculatedhash;
	}

	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\000', '0');
		while (!this.hash.substring(0, difficulty).contentEquals(target)) {
			this.nonce += 1;
			this.hash = calculateHash();
		}
		System.out.println("Block Mined!!!:" + this.hash);
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}

}
