package noobchain;

import java.security.MessageDigest;

public class StringUtil {  //生成哈希值的算法
	public static String applySha256(String input) {
		try {
			MessageDigest t1=MessageDigest.getInstance("SHA-256");
			byte[] hash=t1.digest(input.getBytes("UTF-8"));
			StringBuffer hexString= new StringBuffer();
			for(int i=0;i<hash.length;i++) {
				String hex=Integer.toHexString(0xff & hash[i]);
				if(hex.length()==1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}






