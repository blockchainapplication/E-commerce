package noobchain;
import java.util.Date;

//区块结构定义
public class Block {
	public String hash;
	public String previousHash;
	private String data;
	private long timeStamp;
	private int nonce;
	public String encrypted_data; //密文
	public String publickey; //公钥
	private String privatekey; //私钥
	private String website;//网页

	
	
	
	public String get_data() {String d=data; return d;}
	
   
	
	
	public Block(String data,String previousHash) {
		this.data=data;
		this.previousHash=previousHash;
		this.timeStamp=new Date().getTime();
		this.hash=calculateHash();
		this.encrypted_data=encrypted_data;
		this.publickey=publickey;
		
	}
	public String calculateHash() {
		String calculatedhash=StringUtil.applySha256(
				previousHash+
				Long.toString(timeStamp)+
				Integer.toString(nonce)+
				get_data()
				);
		return calculatedhash;			
	}

	public void mineBlock(int difficulty) {
		String target = new String(new char [difficulty]).replace('\0','0');
		while(!hash.substring(0,difficulty).equals(target)) {
			nonce++;
			hash=calculateHash();
		}
		System.out.println("Block Mined!!!:"+hash);
		
	}




	public String getPrivatekey() {
		return privatekey;
	}




	public void setPrivatekey(String privatekey) {
		this.privatekey = privatekey;
	}

	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website=website;
	}



	




	

}
