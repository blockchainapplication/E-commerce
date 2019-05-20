package online_education;
import java.security.MessageDigest;

public class StringUtil
{
	/**
	 * 
	 * @param input
	 * @return
	 */
  public static String applySha256(String input)
  {
    try
    {
      MessageDigest t1 = MessageDigest.getInstance("SHA-256");
      byte[] hash = t1.digest(input.getBytes("UTF-8"));
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < hash.length; i++)
      {
        String hex = Integer.toHexString(0xFF & hash[i]);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }
      return hexString.toString();
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
