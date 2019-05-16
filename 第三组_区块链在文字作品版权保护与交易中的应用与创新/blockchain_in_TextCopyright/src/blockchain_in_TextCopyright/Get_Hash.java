package blockchain_in_TextCopyright;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Get_Hash {

	public static String Get(String my_string) {
		
		MessageDigest str_md;
		byte[] str_dig;
		String result="";
	    String temp=null;
		try{
			//创建SHA-256的信息摘要
		str_md = MessageDigest.getInstance("SHA-256");
		//把string拿进来 转换为字节类型并进行SHA-256更新
        str_md.update(my_string.getBytes("UTF-8"));
        //返回字节类型结果
        str_dig=str_md.digest();
        //遍历数组 把它转换为16进制的字符串
        for (int i=0; i<str_dig.length; i++){
        	//转换成16进制字符串
        	temp=Integer.toHexString(str_dig[i] & 0xFF);
        	if (temp.length()==1){
        		//1得到一位的进行补0操作
        		result+="0";
        	}
        	result+=temp;
        }
        
		}catch(NoSuchAlgorithmException e) {
			
		}catch (UnsupportedEncodingException e) {
			
		}
		
		return result.toString();
	}
}
