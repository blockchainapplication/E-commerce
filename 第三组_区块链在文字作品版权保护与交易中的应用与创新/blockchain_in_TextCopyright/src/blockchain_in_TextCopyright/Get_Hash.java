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
			//����SHA-256����ϢժҪ
		str_md = MessageDigest.getInstance("SHA-256");
		//��string�ý��� ת��Ϊ�ֽ����Ͳ�����SHA-256����
        str_md.update(my_string.getBytes("UTF-8"));
        //�����ֽ����ͽ��
        str_dig=str_md.digest();
        //�������� ����ת��Ϊ16���Ƶ��ַ���
        for (int i=0; i<str_dig.length; i++){
        	//ת����16�����ַ���
        	temp=Integer.toHexString(str_dig[i] & 0xFF);
        	if (temp.length()==1){
        		//1�õ�һλ�Ľ��в�0����
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
