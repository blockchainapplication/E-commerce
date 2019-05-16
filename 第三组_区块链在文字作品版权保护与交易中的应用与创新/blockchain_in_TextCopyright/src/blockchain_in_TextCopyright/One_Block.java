package blockchain_in_TextCopyright;

import java.util.ArrayList;
import java.util.List;

public class One_Block {

	public int index;
	public long timestamp;
	public String preHash;
	public List<String> text;
	public String merkleroot;
	public String hash;
	public int nonce;
	
	public One_Block(int index, String preHash, List<String> text) {
		this.index=index;
		this.timestamp=System.currentTimeMillis();
		this.preHash=preHash;
		this.text=text;
		this.merkleroot=root(text);

	}
	
	//���ı�ת��Ϊ�б������
	public static List<String> input_text(String ISBN, String name, String author, String publicer, String time, String feature){
		List<String> Text = new ArrayList<String>();
		Text.add(ISBN);
		Text.add(name);
		Text.add(author);
		Text.add(publicer);
		Text.add(time);
		Text.add(feature);
		
		return Text;
	}
	
	//�������
	public String root(List<String> text) {
		Merkle_Tree merkleTrees = new Merkle_Tree(text);
        merkleTrees.merkle_tree();
        return merkleTrees.getRoot();
	}
	
	public String calculate_hash() {
		return Get_Hash.Get(String.valueOf(timestamp)+preHash+merkleroot+String.valueOf(nonce));
	}

	//��֤��ϣֵ�Ƿ�����ǰ�漸�����ض�������0
	public boolean isHashValid(String hash, int difficulty) {
        String prefix = repeat("0", difficulty);
        return hash.startsWith(prefix);
    }
	
	public String repeat(String str, int repeat) {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            buf.append(str);
        }
        return buf.toString();
	}

	//�������
	public void get_header() {
		System.out.println("Index: "+index);
		System.out.println("Timestamp: "+timestamp);
		System.out.println("PreHash: "+preHash);
		System.out.println("Merkleroot: "+merkleroot);
		System.out.println("ThisHash: "+hash);
		System.out.println("Nonce: "+nonce);
	}
	
	public void get_text() {
		System.out.println("����"+index);
		System.out.println("ISBN: "+text.get(0));
		System.out.println("��Ʒ��: "+text.get(1));
		System.out.println("����: "+text.get(2));
		System.out.println("������: "+text.get(3));
		System.out.println("����ʱ��: "+text.get(4));
		System.out.println("����: "+text.get(5));
	}
}
