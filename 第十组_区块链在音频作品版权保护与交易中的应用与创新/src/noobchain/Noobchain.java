package noobchain;
import java.util.ArrayList;

import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;
import javazoom.jl.player.Player;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.Cipher;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import java.io.BufferedInputStream;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.Date;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

//���з�����ʹ��eclipse�����ڵ��ļ��У�ʹ��"Run"���У�������ʾ����
//ע��1���ٴ�����ǰ��ɾ�������ɵ�privatekey.txt!
// 2���ڽ��׹����У���������������ִ�����Դ�������Ӧ��subString()�����Ĳ��������ʵ�������

public class Noobchain {
	public static ArrayList<Block> blockchain = new ArrayList<Block>(); //������������
	public static int difficulty = 2;
	
	//�������ʱ��Ҫ��ȫ�ֱ���
	static String encoded="";
	static int loc=-1;
	static int end=0;
	

	
public static void upload(String[] inf,String[] author, String[] album, String[] album_cover_website) throws Exception { //�ϴ���Ʒ��Ϣ
	for (int i=0;i<inf.length;i++) {
		File file =new File ("key.txt"); //��˽Կ��¼���ı��ļ��У�ÿ������ǰ��ɾ�������ɵ�privatekey.txt
		file.createNewFile();//����txt
		FileWriter fileWriter=new FileWriter(file.getName(),true); //true:��ÿ���ɼ�¼�󸽼��¼�¼
		if (i==0) {
			//����������
			blockchain.add(new Block("����������"+author[i]+"  "+"������"+inf[i]+"  "+"ר������"+album[i]+"  "+"ר��������վ��"+album_cover_website[i],"0"));
			System.out.println("Trying to Mine block 1...");
			blockchain.get(i).mineBlock(difficulty);
			blockchain.get(i).setWebsite(album_cover_website[i]);//������ҳ��Ϣ
			String rsa= "����������"+author[i]+"  "+"������"+inf[i]+"  "+"ר������"+album[i]+"  "+"ר��������վ��"+album_cover_website[i];
			String rsa2="";
			String rsa1="";
			byte[] buff=rsa.getBytes();
			//System.out.println(buff.length);
			if (buff.length>=234) 
			{rsa2=rsa.substring(175); //����RSA����ַ�����117�ֽڣ���˽����ּ�����Կֵ��substring()�Ĳ�������ʵ�����ݵ�����̫С��ʹ�÷ֿ��ֽ���������ֵ��̫������substring()��Խ�����
			rsa1=StringUtils.substringAfterLast(rsa, rsa2);
			}
			else if (buff.length>=100) {
				rsa2=rsa.substring(110);
				rsa1=StringUtils.substringAfterLast(rsa, rsa2);
			}
			else {rsa1=rsa;}
			Map<String,String> map=rsa(rsa1); //�ֱ����rsa������Map���ݶ����ֵ
			Map<String,String> map2=rsa(rsa2);
			//����Ƶ��Ϣ����RSA����ǩ��
			blockchain.get(i).encrypted_data=map.get("enbytes")+map2.get("enbytes"); //���ϲ������Կ�������Ϣ������������
			System.out.println("���ģ�");
			System.out.println(map.get("enbytes")+map2.get("enbytes"));
			blockchain.get(i).publickey=map.get("publicKey")+map2.get("publicKey");
			System.out.println("��Կ��");
			System.out.println(map.get("publicKey")+map2.get("publicKey"));
			blockchain.get(i).setPrivatekey(map.get("privateKey")+map2.get("privateKey"));
			System.out.println("˽Կ��");//˽Կ�������߱��˿ɼ����������˲��ɼ�
			System.out.println(map.get("privateKey")+map2.get("privateKey")+"\n");//�����Ӧ����
			fileWriter.write("publickey"+"1"+":"+map.get("publicKey")+map2.get("publicKey")+"\r\n\r\n");
			fileWriter.write("privatekey"+"1"+":"+map.get("privateKey")+map2.get("privateKey")+"\r\n\r\n");//д��txt�ļ�
			
		}
		else
		{
			blockchain.add(new Block("����������"+author[i]+"  "+"������"+inf[i]+"  "+"ר������"+album[i]+"  "+"ר��������վ��"+album_cover_website[i],blockchain.get(blockchain.size()-1).hash));
			String num=Integer.toString (i+1);
			System.out.println("Trying to Mine block "+num);
			blockchain.get(i).mineBlock(difficulty);
			blockchain.get(i).setWebsite(album_cover_website[i]);//������ҳ��Ϣ
			String rsa= "����������"+author[i]+"  "+"������"+inf[i]+"  "+"ר������"+album[i]+"  "+"ר��������վ��"+album_cover_website[i];
			String rsa2="";
			String rsa1="";
			byte[] buff=rsa.getBytes();
			//System.out.println(buff.length);
			if (buff.length>=234) 
			{rsa2=rsa.substring(175);
			rsa1=StringUtils.substringAfterLast(rsa, rsa2);
			}
			else if (buff.length>=100) {
				rsa2=rsa.substring(110);
				rsa1=StringUtils.substringAfterLast(rsa, rsa2);
			}
			else {rsa1=rsa;}
			Map<String,String> map=rsa(rsa1); 
			Map<String,String> map2=rsa(rsa2);
			//����Ƶ��Ϣ����RSA����ǩ��(RSA����ַ�����117�ֽ�)
			blockchain.get(i).encrypted_data=map.get("enbytes")+map2.get("enbytes");
			System.out.println("���ģ�");
			System.out.println(map.get("enbytes")+map2.get("enbytes"));
			blockchain.get(i).publickey=map.get("publicKey")+map2.get("publicKey");
			System.out.println("��Կ��");
			System.out.println(map.get("publicKey")+map2.get("publicKey"));
			blockchain.get(i).setPrivatekey(map.get("privateKey")+map2.get("privateKey"));
			System.out.println("˽Կ��");//˽Կ�������߱��˿ɼ����������˲��ɼ�
			System.out.println(map.get("privateKey")+map2.get("privateKey")+"\n");
			fileWriter.write("publickey"+num+":"+map.get("publicKey")+map2.get("publicKey")+"\r\n\r\n");
			fileWriter.write("privatekey"+num+":"+map.get("privateKey")+map2.get("privateKey")+"\r\n\r\n");//д��
					
			}
		fileWriter.close();
	}
	System.out.println("\nBlockchain is valid:"+isChainValid()); //��������֤

}
	
public static Boolean isChainValid() { //��������֤
	Block currentBlock;
	Block previousBlock;
	String hashTarget = new String (new char [difficulty]).replace('\0','0');
	for(int i=1; i<blockchain.size();i++) {
		currentBlock=blockchain.get(i);
		previousBlock=blockchain.get(i-1);
		if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
			System.out.println("Current hashes not equal");
			return false;
		}
		if(!previousBlock.hash.equals(currentBlock.previousHash)) {
			System.out.println("Previous hashes not equal");
			return false;
		}
		if(!currentBlock.hash.substring(0,difficulty).equals(hashTarget)) {
			System.out.println("This block hasn't been mined");
			return false;
			}
		}
	return true;
	}


public static String Block_Exchange (ArrayList<Block> blockchain) throws Exception { //��Ȩ����
	Scanner in = new Scanner(System.in);
	System.out.println("���������������Ƶ��Դ�Ĺ�Կ��");//ȷ������ӵ�а�Ȩ����Ȩ��֤��
	String pub=in.next();
	System.out.println("���������������Ƶ��Դ��˽Կ�Խ��ܣ�");//ȷ���򷽺Ϸ�(�����key.txt��ֱ�Ӹ��ƣ�
	String prv=in.next();
	System.out.println("����������������");//����ǰ��ر�key.txt,�����������Ϣ�����ɵ�RSA�޷����棡
	String buyer_name=in.next();
		for (int i=0;i<blockchain.size();i++) { //�ù�Կ��λ
		if (blockchain.get(i).publickey.equals(pub)) {
			System.out.println(blockchain.get(i).encrypted_data);
			encoded=blockchain.get(i).encrypted_data;
			loc=i;
			}
	}
	if (loc==-1) {System.out.println("�޴���Ƶ��");end=1;return " ";
	}
	
	String pkey=blockchain.get(loc).getPrivatekey();
	if (pkey.equals(prv)) { //��˽Կ����
		System.out.println(blockchain.get(loc).get_data());
		URI uri = new URI(blockchain.get(loc).getWebsite());//�򿪶�Ӧ����ҳ
		java.awt.Desktop.getDesktop().browse(uri);
	}
	else {
		System.out.println("�������˽Կ����");
		end=1;
		return " ";
	}
	String new_data="��Ȩ������������"+buyer_name+"  "+StringUtils.substringAfter(blockchain.get(loc).get_data(),"  ");//ɾ����һ����  ��ǰ��ԭ��������������°�Ȩ�����˵�����
	File file =new File ("key.txt"); //��˽Կ��¼���ı��ļ��У�ÿ������ǰ��ɾ�������ɵ�privatekey.txt
	FileWriter fileWriter=new FileWriter(file.getName(),true);
	blockchain.add(new Block(new_data,blockchain.get(blockchain.size()-1).hash));
	String num=Integer.toString (blockchain.size());
	System.out.println("Trying to Mine block "+num);
	blockchain.get(blockchain.size()-1).mineBlock(difficulty);
	String rsa2="";
	String rsa1="";
	byte[] buff=new_data.getBytes();
	System.out.println("�ֽ�����"+buff.length);
	if (buff.length>=234) //��������Ϣ�󱨴�����substring()�Ĳ���
	{rsa2=new_data.substring(175);
	rsa1=StringUtils.substringAfterLast(new_data, rsa2);
	}
	else if (buff.length>=100) {
		rsa2=new_data.substring(110);
		rsa1=StringUtils.substringAfterLast(new_data, rsa2);
	}
	else {rsa1=new_data;}
	Map<String,String> map=rsa(rsa1); 
	Map<String,String> map2=rsa(rsa2);
	//����Ƶ��Ϣ����RSA����ǩ��(RSA����ַ�����117�ֽ�)
	blockchain.get(blockchain.size()-1).encrypted_data=map.get("enbytes")+map2.get("enbytes");
	System.out.println("���ģ�");
	System.out.println(map.get("enbytes")+map2.get("enbytes"));
	blockchain.get(blockchain.size()-1).publickey=map.get("publicKey")+map2.get("publicKey");
	System.out.println("��Կ��");
	System.out.println(map.get("publicKey")+map2.get("publicKey"));
	blockchain.get(blockchain.size()-1).setPrivatekey(map.get("privateKey")+map2.get("privateKey"));
	System.out.println("˽Կ��");
	System.out.println(map.get("privateKey")+map2.get("privateKey"));
	System.out.println(blockchain.get(blockchain.size()-1).get_data());
	fileWriter.write("publickey"+num+":"+map.get("publicKey")+map2.get("publicKey")+"\r\n\r\n");
	fileWriter.write("privatekey"+num+":"+map.get("privateKey")+map2.get("privateKey")+"\r\n\r\n");//д��txt�ļ�
	fileWriter.close();
	System.out.println("\nBlockchain is valid:"+isChainValid()); //��������֤
//	String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//	System.out.println("\nThe block chain:");
//	System.out.println(blockchainJson);
	return blockchain.get(loc).get_data();
	}



//public static void music (String name) throws Exception { //���ļ�������/music�ļ����ж�Ӧ��mp3��Ƶ�ļ�
//	if (name==" ") {System.out.println("����Ȩ����!!");}
//	else {
//		String music_path="music/";
//		String extension=".mp3";
//		String path=music_path+name+extension;
//		File f=new File(path);
//		FileInputStream fis=new FileInputStream(f);
//		BufferedInputStream stream=new BufferedInputStream(fis);
//		Player player=new Player(stream);
//		player.play();
//		}
//}

//����RSA��Կ
 public static  PublicKey getPublicKey(String key) throws Exception {
  byte[] keyBytes;
  Decoder decoder=Base64.getDecoder();
  keyBytes = decoder.decode(key);

  X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
  KeyFactory keyFactory = KeyFactory.getInstance("RSA");
  PublicKey publicKey = keyFactory.generatePublic(keySpec);
  return publicKey;
 }

 
 public static  PrivateKey getPrivateKey(String key) throws Exception {
  byte[] keyBytes;
  Decoder decoder=Base64.getDecoder();
  keyBytes = decoder.decode(key);

  PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
  KeyFactory keyFactory = KeyFactory.getInstance("RSA");
  PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
  return privateKey;
 }

 
 public static  String getKeyString(Key key) throws Exception {
  byte[] keyBytes = key.getEncoded();
  Encoder encoder=Base64.getEncoder();
  String s = encoder.encodeToString(keyBytes);
  return s;
 }

 public static Map<String,String> rsa(String text) throws Exception {
  Map<String,String> map=new HashMap<String,String>();
  KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
  // ��Կλ��
  keyPairGen.initialize(1024);
  // ��Կ��
  KeyPair keyPair = keyPairGen.generateKeyPair();

  // ��Կ
  PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

  // ˽Կ
  PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

  String publicKeyString = getKeyString(publicKey);
  //System.out.println("public:\n" + publicKeyString);

  String privateKeyString = getKeyString(privateKey);
  //System.out.println("private:\n" + privateKeyString);

  // �ӽ�����
  Cipher cipher = Cipher.getInstance("RSA");// Cipher.getInstance("RSA/ECB/PKCS1Padding");

  // ����
  byte[] plainText = text.getBytes();

  // ����
  cipher.init(Cipher.ENCRYPT_MODE, publicKey);
  byte[] enBytes = cipher.doFinal(plainText);
 // System.out.println("���ܺ��ַ�����"+new String(enBytes));
  // ͨ����Կ�ַ����õ���Կ
  publicKey = getPublicKey(publicKeyString);
  privateKey = getPrivateKey(privateKeyString);

  // ����
  cipher.init(Cipher.DECRYPT_MODE, privateKey);
  byte[] deBytes = cipher.doFinal(enBytes);

  publicKeyString = getKeyString(publicKey);
  //System.out.println("public:\n" + publicKeyString);

  privateKeyString = getKeyString(privateKey);
  //System.out.println("private:\n" + privateKeyString);

  String s = new String(deBytes);
  //System.out.println(s);

  map.put("enbytes",new String(enBytes)); //ʹ�ü�����Map���ض��ֵ
  map.put("publicKey",publicKeyString);
  map.put("privateKey",privateKeyString);
  
  return map;
 }
 
 public static String[][] getData(File file, int ignoreRows)  //��ȡEXCEL�ļ�
         throws FileNotFoundException, IOException {
     List<String[]> result = new ArrayList<String[]>();
     int rowSize = 0;
     BufferedInputStream in = new BufferedInputStream(new FileInputStream(
            file));
     // ��HSSFWorkbook
     POIFSFileSystem fs = new POIFSFileSystem(in);
     HSSFWorkbook wb = new HSSFWorkbook(fs);
     HSSFCell cell = null;
     for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
         HSSFSheet st = wb.getSheetAt(sheetIndex);
         // ��һ��Ϊ���⣬��ȡ
         for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
            HSSFRow row = st.getRow(rowIndex);
            if (row == null) {
                continue;
            }
            int tempRowSize = row.getLastCellNum() + 1;
            if (tempRowSize > rowSize) {
                rowSize = tempRowSize;
            }
            String[] values = new String[rowSize];
            Arrays.fill(values, "");
            boolean hasValue = false;
            for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                String value = "";
                cell = row.getCell(columnIndex);
                if (cell != null) {
                   // ע�⣺һ��Ҫ��������������ܻ��������
                   cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                   switch (cell.getCellType()) {
                   case HSSFCell.CELL_TYPE_STRING:
                       value = cell.getStringCellValue();
                       break;
                   case HSSFCell.CELL_TYPE_NUMERIC:
                       if (HSSFDateUtil.isCellDateFormatted(cell)) {
                          Date date = cell.getDateCellValue();
                          if (date != null) {
                              value = new SimpleDateFormat("yyyy-MM-dd")
                                     .format(date);
                          } else {
                              value = "";
                          }
                       } else {
                          value = new DecimalFormat("0").format(cell
                                 .getNumericCellValue());
                       }
                       break;
                   case HSSFCell.CELL_TYPE_FORMULA:
                       // ����ʱ���Ϊ��ʽ���ɵ���������ֵ
                       if (!cell.getStringCellValue().equals("")) {
                          value = cell.getStringCellValue();
                       } else {
                          value = cell.getNumericCellValue() + "";
                       }
                       break;
                   case HSSFCell.CELL_TYPE_BLANK:
                       break;
                   case HSSFCell.CELL_TYPE_ERROR:
                       value = "";
                       break;
                   case HSSFCell.CELL_TYPE_BOOLEAN:
                       value = (cell.getBooleanCellValue() == true ? "Y"
                              : "N");
                       break;
                   default:
                       value = "";
                   }
                }
                if (columnIndex == 0 && value.trim().equals("")) {
                   break;
                }
                values[columnIndex] = rightTrim(value);
                hasValue = true;
            }
            if (hasValue) {
                result.add(values);
            }
         }
     }
     in.close();
     String[][] returnArray = new String[result.size()][rowSize];
     for (int i = 0; i < returnArray.length; i++) {
         returnArray[i] = (String[]) result.get(i);
     }
     return returnArray;
  }

   public static String rightTrim(String str) {
     if (str == null) {
         return "";
     }
     int length = str.length();
     for (int i = length - 1; i >= 0; i--) {
         if (str.charAt(i) != 0x20) {
            break;
         }
         length--;
     }
     return str.substring(0, length);
  }

	public static void main(String[] args) throws Exception {
		File file = new File("����.xls");//��ȡ��������
	       String[][] result = getData(file, 1);
	       int rowLength = result.length;
//	       for(int i=0;i<rowLength;i++) {
//	           for(int j=0;j<result[i].length;j++) {
//	              System.out.print(result[i][j]+"\t\t");
//	           }
//	           System.out.println();
//	       }

		String[] author= new String[rowLength];
		String[] inf= new String[rowLength];
		String[] album= new String[rowLength];
		String[] album_cover_website= new String[rowLength];
		
		for(int i=0;i<rowLength;i++) { //��������
			author[i]=result[i][3];
			inf[i]=result[i][0];
			album[i]=result[i][1];
			album_cover_website[i]=result[i][2];
		}

//		ʹ������������Ҫ¼�����������ı���Ϣ
		upload(inf,author,album,album_cover_website); //��Ʒ�ϴ�
		
			Scanner in = new Scanner(System.in);
			System.out.println("���뽻�״���");//ȷ������ӵ�а�Ȩ����Ȩ��֤��
			int num=in.nextInt();
			for(int i=0;i<num;i++)
			{Block_Exchange(blockchain);//��Ȩ����
			}
			in.close();
		}
		
		
		
	//	String music_name=Block_Exchange(blockchain);  
//		music(music_name);//��MP3�ļ���������Ƶ
		
	}



	

		
		







	


