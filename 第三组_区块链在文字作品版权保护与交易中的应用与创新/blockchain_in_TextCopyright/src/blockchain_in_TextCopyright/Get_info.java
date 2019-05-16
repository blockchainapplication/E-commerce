package blockchain_in_TextCopyright;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Get_info {

	public static List<String> get_info() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(".\\hanhan.csv"));//书信息文件
		String line=null;
		line=reader.readLine();
		line=reader.readLine();
		String[] item1=null;
		item1=line.split(",");
		//System.out.println(item1[1]);
		
		BufferedReader reader2 = new BufferedReader(new FileReader(".\\file.txt"));//特征文件
		String line2=null;
		int x=5;
		while(x>0) {
			line2=reader2.readLine(); //读到是特征的那行
			x--;
		}
		//System.out.println(line2);
		List<String> items = new ArrayList<String>();
		items.add(item1[1]);
		items.add(item1[2]);
		items.add(item1[3]);
		items.add(item1[4]);
		items.add(item1[5]);
		items.add(line2);
		
		return items;
	}

}
