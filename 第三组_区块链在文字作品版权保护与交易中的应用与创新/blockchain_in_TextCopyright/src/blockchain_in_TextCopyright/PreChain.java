package blockchain_in_TextCopyright;

import java.util.ArrayList;
import java.util.List;

public class PreChain {

	public static List<One_Block> generate(){
		List<One_Block> Chain=new ArrayList<One_Block>();
		//创建第一个区块
		List<String> Text1=One_Block.input_text("9787201126227","《零下一度》","韩寒"," 天津人民出版社","2018/3/1","pre0");
		One_Block Block1=new One_Block(Chain.size(),"000",Text1);
		Block1.nonce=1;
		Block1.hash=Block1.calculate_hash();
		Chain.add(Block1);
		//创建第二个区块
		List<String> Text2=One_Block.input_text("9787201126203","《像少年啦飞驰》","韩寒"," 天津人民出版社","2018/3/1","pre1");
		One_Block Block2=new One_Block(Chain.size(),Chain.get(Chain.size()-1).hash,Text2);
		Block2.nonce=1;
		Block2.hash=Block2.calculate_hash();
		Chain.add(Block2);
		//创建第三个区块
		List<String> Text3=One_Block.input_text("9787550025394","《毒2》","韩寒","  百花洲文艺出版社","2018/3/1","pre2");
		One_Block Block3=new One_Block(Chain.size(),Chain.get(Chain.size()-1).hash,Text3);
		Block3.nonce=1;
		Block3.hash=Block3.calculate_hash();
		Chain.add(Block3);
		
		return Chain;
	}
}
