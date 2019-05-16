package blockchain_in_TextCopyright;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mine {

	public static void main(String[] args) throws IOException {
		int difficulty=5;
		List<String> Text=new ArrayList<String>();
		List<One_Block> Chain=PreChain.generate();
		//�������ĸ�����
		One_Block Block4=new One_Block(Chain.size(),Chain.get(Chain.size()-1).hash,Get_info.get_info());
		//����ҷ��ϱ�׼�Ĺ�ϣֵ
		int nonce=0;
		do{
			Block4.nonce=nonce;
			Block4.hash=Block4.calculate_hash();
			nonce++;
		}while(!Block4.isHashValid(Block4.hash, difficulty));
		System.out.println("find hash:"+Block4.hash);
		//����
		Chain.add(Block4);
		
		//���������ͷ
		for(One_Block Block:Chain) {
			System.out.println("-------------------------------------");
			Block.get_header();
			System.out.println("-------------------------------------");
		}
		for(One_Block Block:Chain) {
			System.out.println("-------------------------------------");
			Block.get_text();
			System.out.println("-------------------------------------");
		}

	}
	
}
