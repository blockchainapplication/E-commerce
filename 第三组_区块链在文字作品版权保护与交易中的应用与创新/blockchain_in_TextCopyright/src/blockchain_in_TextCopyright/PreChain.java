package blockchain_in_TextCopyright;

import java.util.ArrayList;
import java.util.List;

public class PreChain {

	public static List<One_Block> generate(){
		List<One_Block> Chain=new ArrayList<One_Block>();
		//������һ������
		List<String> Text1=One_Block.input_text("9787201126227","������һ�ȡ�","����"," ������������","2018/3/1","pre0");
		One_Block Block1=new One_Block(Chain.size(),"000",Text1);
		Block1.nonce=1;
		Block1.hash=Block1.calculate_hash();
		Chain.add(Block1);
		//�����ڶ�������
		List<String> Text2=One_Block.input_text("9787201126203","�����������ɳۡ�","����"," ������������","2018/3/1","pre1");
		One_Block Block2=new One_Block(Chain.size(),Chain.get(Chain.size()-1).hash,Text2);
		Block2.nonce=1;
		Block2.hash=Block2.calculate_hash();
		Chain.add(Block2);
		//��������������
		List<String> Text3=One_Block.input_text("9787550025394","����2��","����","  �ٻ������ճ�����","2018/3/1","pre2");
		One_Block Block3=new One_Block(Chain.size(),Chain.get(Chain.size()-1).hash,Text3);
		Block3.nonce=1;
		Block3.hash=Block3.calculate_hash();
		Chain.add(Block3);
		
		return Chain;
	}
}
