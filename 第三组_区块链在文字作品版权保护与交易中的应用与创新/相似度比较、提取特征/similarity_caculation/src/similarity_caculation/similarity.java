package similarity_caculation;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map; 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class similarity {
	
	//函数atest
	public String atest(int row,int col){
        try {           
         BufferedReader reade = new BufferedReader(new FileReader("C:\\Users\\14760\\Desktop\\hanhan.csv"));
         String line = null;
         int index=0;
         while((line=reade.readLine())!=null){
             String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
          if(index==row-1){
          	System.out.println(item.length);
              if(item.length>=col-1){
                  String last = item[col-1];
                  System.out.println(last);
                  return last;
              }
          }
          index++;
          }      
         } catch (Exception e) {
          e.printStackTrace();
          }
        return atest(0, 0);
  }
	
	//函数btest
	public String btest(int row,int col){
        try {           
         BufferedReader reade = new BufferedReader(new FileReader("C:\\Users\\14760\\Desktop\\yanzheng.csv"));
         String line = null;
         int index=0;
         while((line=reade.readLine())!=null){
             String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
          if(index==row-1){
          	System.out.println(item.length);
              if(item.length>=col-1){
                  String last = item[col-1];
                  System.out.println(last);
                  return last;
              }
          }
          index++;
          }      
         } catch (Exception e) {
          e.printStackTrace();
          }
        return btest(0, 0);
  }


	public static double getSimilarity(String doc1, String doc2) {
		if (doc1 != null && doc1.trim().length() > 0 && doc2 != null&& doc2.trim().length() > 0) {
			
			Map<Integer, int[]> AlgorithmMap = new HashMap<Integer, int[]>();
			
			//将两个字符串中的中文字符以及出现的总数封装到，AlgorithmMap中
			for (int i = 0; i < doc1.length(); i++) {
				char d1 = doc1.charAt(i);
				if(isHanZi(d1)){//标点和数字不处理
					int charIndex = getGB2312Id(d1);//保存字符对应的GB2312编码
					if(charIndex != -1){
						int[] fq = AlgorithmMap.get(charIndex);
						if(fq != null && fq.length == 2){
							fq[0]++;//已有该字符，加1
						}else {
							fq = new int[2];
							fq[0] = 1;
							fq[1] = 0;
							AlgorithmMap.put(charIndex, fq);//新增字符入map
						}
					}
				}
			}
 
			for (int i = 0; i < doc2.length(); i++) {
				char d2 = doc2.charAt(i);
				if(isHanZi(d2)){
					int charIndex = getGB2312Id(d2);
					if(charIndex != -1){
						int[] fq = AlgorithmMap.get(charIndex);
						if(fq != null && fq.length == 2){
							fq[1]++;
						}else {
							fq = new int[2];
							fq[0] = 0;
							fq[1] = 1;
							AlgorithmMap.put(charIndex, fq);
						}
					}
				}
			}
			
			Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();
			double sqdoc1 = 0;
			double sqdoc2 = 0;
			double denominator = 0; 
			while(iterator.hasNext()){
				int[] c = AlgorithmMap.get(iterator.next());
				denominator += c[0]*c[1];  //自加 x1*x2+y1*y2
				sqdoc1 += c[0]*c[0];         //自加 x1*x1+y1*y1
				sqdoc2 += c[1]*c[1];    //自加x2*x2+y2*y2
			}
			
			return denominator / Math.sqrt(sqdoc1*sqdoc2);//余弦计算
		} else {
			throw new NullPointerException(" the Document is null or have not cahrs!!");
		}
	}
 
	public static boolean isHanZi(char ch) {
		// 判断是否汉字
		return (ch >= 0x4E00 && ch <= 0x9FA5);
		/*if (ch >= 0x4E00 && ch <= 0x9FA5) {//汉字
			return true;
		}else{
			String str = "" + ch;
			boolean isNum = str.matches("[0-9]+"); 
			return isNum;
		}*/
		/*if(Character.isLetterOrDigit(ch)){
			String str = "" + ch;
			if (str.matches("[0-9a-zA-Z\\u4e00-\\u9fa5]+")){//非乱码
                return true;
            }else return false;
		}else return false;*/
	}
 
	/**
	 * 根据输入的Unicode字符，获取它的GB2312编码或者ascii编码，
	 * 
	 * @param ch 输入的GB2312中文字符或者ASCII字符(128个)
	 * @return ch在GB2312中的位置，-1表示该字符不认识
	 */
	public static short getGB2312Id(char ch) {
		try {
			byte[] buffer = Character.toString(ch).getBytes("GB2312");
			if (buffer.length != 2) {
				// 正常情况下buffer应该是两个字节，否则说明ch不属于GB2312编码，故返回'?'，此时说明不认识该字符
				//GB2312编码是第一个汉字编码国家标准，
				//由中国国家标准总局1980年发布，1981年5月1日开始使用。GB2312编码共收录汉字6763个，其中一级汉字3755个，
				//二级汉字3008个。同时，GB2312编码收录了包括拉丁字母、希腊字母、日文平假名及片假名字母、俄语西里尔字母在内的682个全角字符。
				//https://www.qqxiuzi.cn/zh/hanzi-gb2312-bianma.php 解释了GB2312编码
				return -1;
			}
			int b0 = (int) (buffer[0] & 0x0FF) - 161; // 编码从A1开始，因此减去0xA1=161
			int b1 = (int) (buffer[1] & 0x0FF) - 161; 
			return (short) (b0 * 94 + b1);// 第一个字符和最后一个字符没有汉字，因此每个区只收16*6-2=94个汉字
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
	}
	/*
	 * 前面有个叫做similarity的类，类里面有三个函数，分别是getSimilarity、isHanzi和getGB2313Id。后面两个函数是再第一个函数中使用的
	 * 下面的主函数中，仅调用了getSimilarity这个大一点的函数。
	 * 本程序并没有进行中文分词，只是单纯的统计两个文本中相同的
	 */
	public static void main(String[] args) {
		similarity test = new similarity();	
		String b = test.btest(1, 1);
		String str1= b;
		
//		这里需要一个for循环把？

		for (int i =2;i<13;i++) {
			String a = test.atest(i,8);
			String str2= a;
			long start=System.currentTimeMillis();  
			double Similarity=similarity.getSimilarity(str1, str2);
			System.out.println("用时:"+(System.currentTimeMillis()-start)); 
			System.out.println(Similarity);
			if (Similarity > 0.70) 
			{
				System.out.println("相似度过高，验证失败！");
				break;
			}
		}
		}
}

