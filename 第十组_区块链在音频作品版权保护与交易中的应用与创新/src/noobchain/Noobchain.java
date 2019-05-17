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

//运行方法：使用eclipse打开所在的文件夹，使用"Run"运行，并按提示操作
//注：1）再次运行前先删除已生成的privatekey.txt!
// 2）在交易功能中，若输入姓名后出现错误，请对代码内相应的subString()方法的参数进行适当调整。

public class Noobchain {
	public static ArrayList<Block> blockchain = new ArrayList<Block>(); //定义新区块链
	public static int difficulty = 2;
	
	//定义解密时需要的全局变量
	static String encoded="";
	static int loc=-1;
	static int end=0;
	

	
public static void upload(String[] inf,String[] author, String[] album, String[] album_cover_website) throws Exception { //上传作品信息
	for (int i=0;i<inf.length;i++) {
		File file =new File ("key.txt"); //将私钥记录于文本文件中，每次运行前先删除已生成的privatekey.txt
		file.createNewFile();//创建txt
		FileWriter fileWriter=new FileWriter(file.getName(),true); //true:在每条旧记录后附加新记录
		if (i==0) {
			//新区块入链
			blockchain.add(new Block("作者姓名："+author[i]+"  "+"歌名："+inf[i]+"  "+"专辑名："+album[i]+"  "+"专辑封面网站："+album_cover_website[i],"0"));
			System.out.println("Trying to Mine block 1...");
			blockchain.get(i).mineBlock(difficulty);
			blockchain.get(i).setWebsite(album_cover_website[i]);//保存网页信息
			String rsa= "作者姓名："+author[i]+"  "+"歌名："+inf[i]+"  "+"专辑名："+album[i]+"  "+"专辑封面网站："+album_cover_website[i];
			String rsa2="";
			String rsa1="";
			byte[] buff=rsa.getBytes();
			//System.out.println(buff.length);
			if (buff.length>=234) 
			{rsa2=rsa.substring(175); //由于RSA有最长字符限制117字节，因此将其拆分计算密钥值；substring()的参数根据实际数据调整，太小会使得分块字节数超过限值，太大会造成substring()的越界错误
			rsa1=StringUtils.substringAfterLast(rsa, rsa2);
			}
			else if (buff.length>=100) {
				rsa2=rsa.substring(110);
				rsa1=StringUtils.substringAfterLast(rsa, rsa2);
			}
			else {rsa1=rsa;}
			Map<String,String> map=rsa(rsa1); //分别计算rsa，并用Map传递多个数值
			Map<String,String> map2=rsa(rsa2);
			//对音频信息计算RSA数字签名
			blockchain.get(i).encrypted_data=map.get("enbytes")+map2.get("enbytes"); //将合并后的密钥等相关信息放入区块链内
			System.out.println("密文：");
			System.out.println(map.get("enbytes")+map2.get("enbytes"));
			blockchain.get(i).publickey=map.get("publicKey")+map2.get("publicKey");
			System.out.println("公钥：");
			System.out.println(map.get("publicKey")+map2.get("publicKey"));
			blockchain.get(i).setPrivatekey(map.get("privateKey")+map2.get("privateKey"));
			System.out.println("私钥：");//私钥仅对作者本人可见，对其他人不可见
			System.out.println(map.get("privateKey")+map2.get("privateKey")+"\n");//输出对应数据
			fileWriter.write("publickey"+"1"+":"+map.get("publicKey")+map2.get("publicKey")+"\r\n\r\n");
			fileWriter.write("privatekey"+"1"+":"+map.get("privateKey")+map2.get("privateKey")+"\r\n\r\n");//写入txt文件
			
		}
		else
		{
			blockchain.add(new Block("作者姓名："+author[i]+"  "+"歌名："+inf[i]+"  "+"专辑名："+album[i]+"  "+"专辑封面网站："+album_cover_website[i],blockchain.get(blockchain.size()-1).hash));
			String num=Integer.toString (i+1);
			System.out.println("Trying to Mine block "+num);
			blockchain.get(i).mineBlock(difficulty);
			blockchain.get(i).setWebsite(album_cover_website[i]);//保存网页信息
			String rsa= "作者姓名："+author[i]+"  "+"歌名："+inf[i]+"  "+"专辑名："+album[i]+"  "+"专辑封面网站："+album_cover_website[i];
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
			//对音频信息计算RSA数字签名(RSA有最长字符限制117字节)
			blockchain.get(i).encrypted_data=map.get("enbytes")+map2.get("enbytes");
			System.out.println("密文：");
			System.out.println(map.get("enbytes")+map2.get("enbytes"));
			blockchain.get(i).publickey=map.get("publicKey")+map2.get("publicKey");
			System.out.println("公钥：");
			System.out.println(map.get("publicKey")+map2.get("publicKey"));
			blockchain.get(i).setPrivatekey(map.get("privateKey")+map2.get("privateKey"));
			System.out.println("私钥：");//私钥仅对作者本人可见，对其他人不可见
			System.out.println(map.get("privateKey")+map2.get("privateKey")+"\n");
			fileWriter.write("publickey"+num+":"+map.get("publicKey")+map2.get("publicKey")+"\r\n\r\n");
			fileWriter.write("privatekey"+num+":"+map.get("privateKey")+map2.get("privateKey")+"\r\n\r\n");//写入
					
			}
		fileWriter.close();
	}
	System.out.println("\nBlockchain is valid:"+isChainValid()); //区块链验证

}
	
public static Boolean isChainValid() { //区块链验证
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


public static String Block_Exchange (ArrayList<Block> blockchain) throws Exception { //版权交易
	Scanner in = new Scanner(System.in);
	System.out.println("请输入您所需的音频资源的公钥：");//确认卖方拥有版权（版权认证）
	String pub=in.next();
	System.out.println("请输入您所需的音频资源的私钥以解密：");//确认买方合法(建议从key.txt中直接复制）
	String prv=in.next();
	System.out.println("请输入您的姓名：");//输入前请关闭key.txt,否则输入的信息所生成的RSA无法保存！
	String buyer_name=in.next();
		for (int i=0;i<blockchain.size();i++) { //用公钥定位
		if (blockchain.get(i).publickey.equals(pub)) {
			System.out.println(blockchain.get(i).encrypted_data);
			encoded=blockchain.get(i).encrypted_data;
			loc=i;
			}
	}
	if (loc==-1) {System.out.println("无此音频！");end=1;return " ";
	}
	
	String pkey=blockchain.get(loc).getPrivatekey();
	if (pkey.equals(prv)) { //用私钥解密
		System.out.println(blockchain.get(loc).get_data());
		URI uri = new URI(blockchain.get(loc).getWebsite());//打开对应的网页
		java.awt.Desktop.getDesktop().browse(uri);
	}
	else {
		System.out.println("您输入的私钥错误！");
		end=1;
		return " ";
	}
	String new_data="版权所有者姓名："+buyer_name+"  "+StringUtils.substringAfter(blockchain.get(loc).get_data(),"  ");//删除第一个“  ”前的原作者名，并添加新版权所有人的名字
	File file =new File ("key.txt"); //将私钥记录于文本文件中，每次运行前先删除已生成的privatekey.txt
	FileWriter fileWriter=new FileWriter(file.getName(),true);
	blockchain.add(new Block(new_data,blockchain.get(blockchain.size()-1).hash));
	String num=Integer.toString (blockchain.size());
	System.out.println("Trying to Mine block "+num);
	blockchain.get(blockchain.size()-1).mineBlock(difficulty);
	String rsa2="";
	String rsa1="";
	byte[] buff=new_data.getBytes();
	System.out.println("字节数："+buff.length);
	if (buff.length>=234) //若输入信息后报错，调整substring()的参数
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
	//对音频信息计算RSA数字签名(RSA有最长字符限制117字节)
	blockchain.get(blockchain.size()-1).encrypted_data=map.get("enbytes")+map2.get("enbytes");
	System.out.println("密文：");
	System.out.println(map.get("enbytes")+map2.get("enbytes"));
	blockchain.get(blockchain.size()-1).publickey=map.get("publicKey")+map2.get("publicKey");
	System.out.println("公钥：");
	System.out.println(map.get("publicKey")+map2.get("publicKey"));
	blockchain.get(blockchain.size()-1).setPrivatekey(map.get("privateKey")+map2.get("privateKey"));
	System.out.println("私钥：");
	System.out.println(map.get("privateKey")+map2.get("privateKey"));
	System.out.println(blockchain.get(blockchain.size()-1).get_data());
	fileWriter.write("publickey"+num+":"+map.get("publicKey")+map2.get("publicKey")+"\r\n\r\n");
	fileWriter.write("privatekey"+num+":"+map.get("privateKey")+map2.get("privateKey")+"\r\n\r\n");//写入txt文件
	fileWriter.close();
	System.out.println("\nBlockchain is valid:"+isChainValid()); //区块链验证
//	String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
//	System.out.println("\nThe block chain:");
//	System.out.println(blockchainJson);
	return blockchain.get(loc).get_data();
	}



//public static void music (String name) throws Exception { //按文件名播放/music文件夹中对应的mp3音频文件
//	if (name==" ") {System.out.println("你无权播放!!");}
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

//生成RSA密钥
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
  // 密钥位数
  keyPairGen.initialize(1024);
  // 密钥对
  KeyPair keyPair = keyPairGen.generateKeyPair();

  // 公钥
  PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

  // 私钥
  PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

  String publicKeyString = getKeyString(publicKey);
  //System.out.println("public:\n" + publicKeyString);

  String privateKeyString = getKeyString(privateKey);
  //System.out.println("private:\n" + privateKeyString);

  // 加解密类
  Cipher cipher = Cipher.getInstance("RSA");// Cipher.getInstance("RSA/ECB/PKCS1Padding");

  // 明文
  byte[] plainText = text.getBytes();

  // 加密
  cipher.init(Cipher.ENCRYPT_MODE, publicKey);
  byte[] enBytes = cipher.doFinal(plainText);
 // System.out.println("加密后字符集："+new String(enBytes));
  // 通过密钥字符串得到密钥
  publicKey = getPublicKey(publicKeyString);
  privateKey = getPrivateKey(privateKeyString);

  // 解密
  cipher.init(Cipher.DECRYPT_MODE, privateKey);
  byte[] deBytes = cipher.doFinal(enBytes);

  publicKeyString = getKeyString(publicKey);
  //System.out.println("public:\n" + publicKeyString);

  privateKeyString = getKeyString(privateKey);
  //System.out.println("private:\n" + privateKeyString);

  String s = new String(deBytes);
  //System.out.println(s);

  map.put("enbytes",new String(enBytes)); //使用集合类Map返回多个值
  map.put("publicKey",publicKeyString);
  map.put("privateKey",privateKeyString);
  
  return map;
 }
 
 public static String[][] getData(File file, int ignoreRows)  //读取EXCEL文件
         throws FileNotFoundException, IOException {
     List<String[]> result = new ArrayList<String[]>();
     int rowSize = 0;
     BufferedInputStream in = new BufferedInputStream(new FileInputStream(
            file));
     // 打开HSSFWorkbook
     POIFSFileSystem fs = new POIFSFileSystem(in);
     HSSFWorkbook wb = new HSSFWorkbook(fs);
     HSSFCell cell = null;
     for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
         HSSFSheet st = wb.getSheetAt(sheetIndex);
         // 第一行为标题，不取
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
                   // 注意：一定要设成这个，否则可能会出现乱码
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
                       // 导入时如果为公式生成的数据则无值
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
		File file = new File("数据.xls");//读取爬虫数据
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
		
		for(int i=0;i<rowLength;i++) { //导入数据
			author[i]=result[i][3];
			inf[i]=result[i][0];
			album[i]=result[i][1];
			album_cover_website[i]=result[i][2];
		}

//		使用爬虫输入需要录入区块链的文本信息
		upload(inf,author,album,album_cover_website); //作品上传
		
			Scanner in = new Scanner(System.in);
			System.out.println("输入交易次数");//确认卖方拥有版权（版权认证）
			int num=in.nextInt();
			for(int i=0;i<num;i++)
			{Block_Exchange(blockchain);//版权交易
			}
			in.close();
		}
		
		
		
	//	String music_name=Block_Exchange(blockchain);  
//		music(music_name);//按MP3文件名播放音频
		
	}



	

		
		







	


