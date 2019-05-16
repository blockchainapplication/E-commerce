package blockchain;

import java.security.MessageDigest;

/**
 * 定义用于计算哈希值的函数
 */
public class getShavalue {
	public static String applySha256(String str) {
		byte[] cipher_byte;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");//返回实现指定摘要算法的 MessageDigest 对象
			md.update(str.getBytes());//得到一个操作系统默认的编码格式的字节数组,使用指定的字节更新摘要
			cipher_byte = md.digest();
			StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
			for (byte b : cipher_byte) {
				sb.append(String.format("%02x", b & 0xff));//x代表以十六进制形式输出,02代表不足两位，前面补0输出，如果超过两位，则以实际输出
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();//try出现异常，自动执行catch，初始化exception的e，e是此对象引用名臣，在命令行打印出错的位置及原因
		}

		return "";
	}
}
