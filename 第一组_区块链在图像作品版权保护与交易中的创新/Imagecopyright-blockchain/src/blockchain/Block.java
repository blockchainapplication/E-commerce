package blockchain;

import java.util.Date;

public class Block {
	public String hash;// 本区块的哈希值
	public String previousHash;// 上一个区块的哈希值
	public int index;// 本区块在链中的序号
	public String merkletreeroot;// 本区块默克尔树根哈希值
	public double productiontime;//生成该区块的时间
	private long timeStamp;// 本区块的时间戳
	private int nonce;// 用于POW的随机值
	public String minername;

	/**
	 * 初始化区块
	 */
	public Block(String merkletreeroot, String previousHash, int index, String minername) {
		this.merkletreeroot = merkletreeroot;
		this.previousHash = previousHash;
		this.index = index;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
		this.productiontime = 0;
		this.minername = minername;
	}

	/**
	 * 计算本区块的哈希值
	 */
	public String calculateHash() {
		String calculatedhash = getShavalue
				.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkletreeroot);
		return calculatedhash;
	}

	/**
	 * 定义挖矿函数
	 */
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); // 创造对应难度的字符数组用于比对
		Date begintime = new Date();
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		} // 进行挖矿
		Date endtime = new Date();
		double time = endtime.getTime()- begintime.getTime();
		productiontime = time;
		System.out.println("Block Mined!!! : " + hash);
	}
}
