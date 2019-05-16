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
	
	//����atest
	public String atest(int row,int col){
        try {           
         BufferedReader reade = new BufferedReader(new FileReader("C:\\Users\\14760\\Desktop\\hanhan.csv"));
         String line = null;
         int index=0;
         while((line=reade.readLine())!=null){
             String item[] = line.split(",");//CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з�
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
	
	//����btest
	public String btest(int row,int col){
        try {           
         BufferedReader reade = new BufferedReader(new FileReader("C:\\Users\\14760\\Desktop\\yanzheng.csv"));
         String line = null;
         int index=0;
         while((line=reade.readLine())!=null){
             String item[] = line.split(",");//CSV��ʽ�ļ�Ϊ���ŷָ����ļ���������ݶ����з�
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
			
			//�������ַ����е������ַ��Լ����ֵ�������װ����AlgorithmMap��
			for (int i = 0; i < doc1.length(); i++) {
				char d1 = doc1.charAt(i);
				if(isHanZi(d1)){//�������ֲ�����
					int charIndex = getGB2312Id(d1);//�����ַ���Ӧ��GB2312����
					if(charIndex != -1){
						int[] fq = AlgorithmMap.get(charIndex);
						if(fq != null && fq.length == 2){
							fq[0]++;//���и��ַ�����1
						}else {
							fq = new int[2];
							fq[0] = 1;
							fq[1] = 0;
							AlgorithmMap.put(charIndex, fq);//�����ַ���map
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
				denominator += c[0]*c[1];  //�Լ� x1*x2+y1*y2
				sqdoc1 += c[0]*c[0];         //�Լ� x1*x1+y1*y1
				sqdoc2 += c[1]*c[1];    //�Լ�x2*x2+y2*y2
			}
			
			return denominator / Math.sqrt(sqdoc1*sqdoc2);//���Ҽ���
		} else {
			throw new NullPointerException(" the Document is null or have not cahrs!!");
		}
	}
 
	public static boolean isHanZi(char ch) {
		// �ж��Ƿ���
		return (ch >= 0x4E00 && ch <= 0x9FA5);
		/*if (ch >= 0x4E00 && ch <= 0x9FA5) {//����
			return true;
		}else{
			String str = "" + ch;
			boolean isNum = str.matches("[0-9]+"); 
			return isNum;
		}*/
		/*if(Character.isLetterOrDigit(ch)){
			String str = "" + ch;
			if (str.matches("[0-9a-zA-Z\\u4e00-\\u9fa5]+")){//������
                return true;
            }else return false;
		}else return false;*/
	}
 
	/**
	 * ���������Unicode�ַ�����ȡ����GB2312�������ascii���룬
	 * 
	 * @param ch �����GB2312�����ַ�����ASCII�ַ�(128��)
	 * @return ch��GB2312�е�λ�ã�-1��ʾ���ַ�����ʶ
	 */
	public static short getGB2312Id(char ch) {
		try {
			byte[] buffer = Character.toString(ch).getBytes("GB2312");
			if (buffer.length != 2) {
				// ���������bufferӦ���������ֽڣ�����˵��ch������GB2312���룬�ʷ���'?'����ʱ˵������ʶ���ַ�
				//GB2312�����ǵ�һ�����ֱ�����ұ�׼��
				//���й����ұ�׼�ܾ�1980�귢����1981��5��1�տ�ʼʹ�á�GB2312���빲��¼����6763��������һ������3755����
				//��������3008����ͬʱ��GB2312������¼�˰���������ĸ��ϣ����ĸ������ƽ������Ƭ������ĸ�������������ĸ���ڵ�682��ȫ���ַ���
				//https://www.qqxiuzi.cn/zh/hanzi-gb2312-bianma.php ������GB2312����
				return -1;
			}
			int b0 = (int) (buffer[0] & 0x0FF) - 161; // �����A1��ʼ����˼�ȥ0xA1=161
			int b1 = (int) (buffer[1] & 0x0FF) - 161; 
			return (short) (b0 * 94 + b1);// ��һ���ַ������һ���ַ�û�к��֣����ÿ����ֻ��16*6-2=94������
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
	}
	/*
	 * ǰ���и�����similarity���࣬�������������������ֱ���getSimilarity��isHanzi��getGB2313Id�����������������ٵ�һ��������ʹ�õ�
	 * ������������У���������getSimilarity�����һ��ĺ�����
	 * ������û�н������ķִʣ�ֻ�ǵ�����ͳ�������ı�����ͬ��
	 */
	public static void main(String[] args) {
		similarity test = new similarity();	
		String b = test.btest(1, 1);
		String str1= b;
		
//		������Ҫһ��forѭ���ѣ�

		for (int i =2;i<13;i++) {
			String a = test.atest(i,8);
			String str2= a;
			long start=System.currentTimeMillis();  
			double Similarity=similarity.getSimilarity(str1, str2);
			System.out.println("��ʱ:"+(System.currentTimeMillis()-start)); 
			System.out.println(Similarity);
			if (Similarity > 0.70) 
			{
				System.out.println("���ƶȹ��ߣ���֤ʧ�ܣ�");
				break;
			}
		}
		}
}

