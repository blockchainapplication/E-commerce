/**
	 * 用于图片版权认定的区块链系统
	 * 组长：查伦
	 * 组员：杨添，姚逸磊，程启伦
	 */
package blockchain;

import java.util.ArrayList;
import java.io.*;
import com.google.gson.GsonBuilder;

/**
 *主程序逻辑：
 *			1.创建区块链中用户users
 *			2.生成image类登记图片信息，将需要登记版权的图片与链中所有的图片进行相似度比对（感知哈希函数）
 *			3.通过检测，输出哈希值
 *			4.将该图片的所有属性依次搭建默克尔树，返回默克尔树根哈希值
 *			5.构建区块，矿工开始挖矿，挖矿时间最短者获得记录权，获得该图片类中的coins
 *			6.添加区块到链中，完成认证
 */
public class BlockChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();// 创建区块链动态列表
	public static ArrayList<MerkleTrees> merkleTrees = new ArrayList<MerkleTrees>();// 创建默克尔树动态列表

	public static void main(String[] args) {

//创建链中用户
		User user1 = new User("User1",200);
		User user2 = new User("User2",300);
		User user3 = new User("User3",500);

//登记需要进行版权认证的图片
		Image image1 = new Image(user3,150,150,"purple","",300);

//读入通过相似度检测的哈希值		
		 String pathname = "Hash.txt"; 
	        try (FileReader reader = new FileReader(pathname);
	             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
	        ) {
	            String line;
	            while ((line = br.readLine()) != null) {
	            image1.imagehash = line;//赋值给image类属性
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
//检测通过，输出哈希值，搭建默克尔树		
		user3.number_coins -= image1.bonuscoins;
		MerkleTrees merkleTrees1 = new MerkleTrees(image1, 1);
		merkleTrees1.merkle_tree();// 默克尔树构造
		merkleTrees.add(merkleTrees1);// 将构造好的默克尔树添加到默克尔树列表

//矿广开始挖矿
		Block B1_1 = new Block(merkleTrees1.getRoot(), "0", blockchain.size() + 1,user1.name);//第一个矿工
		System.out.println("Miner one is trying to Mine block 1_1... ");
		B1_1.mineBlock(600/user1.number_coins);
		Block B1_2 = new Block(merkleTrees1.getRoot(), "0", blockchain.size() + 1,user2.name);//第二个矿工
		System.out.println("Miner two is trying to Mine block 1_2... ");
		B1_2.mineBlock(600/user2.number_coins);
		System.out.print("\n");

//时间短者获得上链记录区块权力，获得快中图片版权币
		if(B1_1.productiontime<B1_2.productiontime) {
			blockchain.add(B1_1);
			user1.number_coins += image1.bonuscoins;
			System.out.println("Miner1 is faster!!");
		}
		else {
			blockchain.add(B1_2);
			user2.number_coins += image1.bonuscoins;
			System.out.println("Miner2 is faster!!");}

//准备输出
		System.out.println("\nBlockchain is Valid: " + isChainValid());// 输出区块链是否有效

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);// 依次输出链中区块

		String merkletreeJson = new GsonBuilder().setPrettyPrinting().create().toJson(merkleTrees);
		System.out.println("\nThe MerkleTree: ");
		System.out.println(merkletreeJson);// 依次输出区块中默克尔树种存储的图片版权简介及树根哈希值
	}

	/**
	 * 检验区块及区块链是否有效
	 */
	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;

		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// 检验本区块哈希值是否:
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// 检验上一个区块的哈希值是否等于本区块中存储的上一个区块哈希值（previousHash）
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			// 检验数据是否被篡改
			if (!merkleTrees.get(i).getRoot().contentEquals(currentBlock.merkletreeroot)) {
				System.out.println("The block has been changed");
				return false;
			}
		}
		return true;
	}
}