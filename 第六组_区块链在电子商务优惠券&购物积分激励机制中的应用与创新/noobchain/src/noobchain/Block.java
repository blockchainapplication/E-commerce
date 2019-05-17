package noobchain;

import java.util.Date;

//	区块结构
public class Block {
	public String hash;
	public String previousHash;
	private String merkleTreeRoot;
	private long timeStamp;
	private int nonce;
	private String couponDataPath;
	
	//	构造函数
	public Block(String merkleTreeRoot, String previousHash, String file_name) {
		this.merkleTreeRoot = merkleTreeRoot;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
		this.couponDataPath = file_name;
	}
	
	//	计算区块的Hash值
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				merkleTreeRoot 
				);
		return calculatedhash;
	}
	
	//	计算符合难度的nonce值
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0');
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	
	public String returnPath() {
		return couponDataPath;
	}
}
