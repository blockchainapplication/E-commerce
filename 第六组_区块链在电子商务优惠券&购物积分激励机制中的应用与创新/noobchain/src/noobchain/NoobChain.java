package noobchain;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
//import com.google.gson.GsonBuilder;

//区块链
public class NoobChain {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	
	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
		
			//判断区块的当前区块哈希值是否匹配
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");
				return false;
			}
			
			//判断区块的前一个区块哈希值是否匹配
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}

			//判断区块的难度是否匹配
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
	
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
	
	//主函数
	public static void main(String[] args) throws IOException, ParseException {
		
		//第一个区块 
		System.out.println("Trying to Mine block 1... ");
		CouponData[] createCoupon = new CouponData[100];
		createCoupon = ReadData.GetData("优惠券创建.csv");
		MerkleTrees createCouponTree = new MerkleTrees(createCoupon,createCoupon);
		createCouponTree.merkle_tree();
		Block  block1 = new Block(createCouponTree.getRoot(), "0", "优惠券创建.csv");
		addBlock(block1);
		
		//第二个区块
		System.out.println("Trying to Mine block 2... ");
		CouponData[] receiveCoupon = new CouponData[100];
		receiveCoupon = ReadData.GetData("优惠券领取.csv");
		MerkleTrees receiveCouponTree = new MerkleTrees(receiveCoupon,receiveCoupon);
		receiveCouponTree.merkle_tree();
		Block block2 = new Block(receiveCouponTree.getRoot(),blockchain.get(blockchain.size()-1).hash, "优惠券领取.csv");
		addBlock(block2);
		
		//第三个区块
		System.out.println("Trying to Mine block 3... ");
		CouponData[] useCoupon = new CouponData[100];
		useCoupon = ReadData.GetData("优惠券使用.csv");
		MerkleTrees useCouponTree = new MerkleTrees(useCoupon,useCoupon);
		useCouponTree.merkle_tree();
		Block block3 = new Block(useCouponTree.getRoot(),blockchain.get(blockchain.size()-1).hash, "优惠券使用.csv");
		addBlock(block3);
		
		System.out.println("\nBlockchain is Valid: " + isChainValid());
		
		String blockchainJson = StringUtil.getJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
		
		//信息查询
		SelectCoupon.selectCoupon(ReadData.GetData(block3.returnPath()));
	}
}
 