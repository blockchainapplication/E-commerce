package noobchain;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

//	merkleTree
public class MerkleTrees {
	CouponData[] couponListBefore;
	CouponData[] couponListAfter;	
	String root;
	
	//	构造函数
	public MerkleTrees(CouponData[] csvListBefore, CouponData[] csvListAfter) {
		this.couponListBefore = csvListBefore;
		this.couponListAfter = csvListAfter;
	    root = "";
	}
	
	//	将csv中的符合条件的coupon信息输入merkleTree，计算返回merkleTree Root
	public void merkle_tree() {
	    List<String> tempTxList = new ArrayList<String>();
	    boolean bool1;
	    boolean bool2;
	    boolean bool3;
	    for (int i = 0; i < this.couponListAfter.length; i++) {
	    	bool1 = BoolCoupon.dateMatch(couponListAfter[i]);
	    	bool2 = BoolCoupon.sellerMatchDealer(couponListAfter[i]);
	    	bool3 = BoolCoupon.beforeMatchAfter(couponListBefore, couponListAfter[i]);
	    	if(bool1 && bool2 && bool3) {
		    	tempTxList.add(this.couponListAfter[i].get());	    		
	    	}
	    }
	    List<String> newTxList = getNewTxList(tempTxList);
	    while (newTxList.size() != 1) {
	    	newTxList = getNewTxList(newTxList);
	    }
	    this.root = (String) newTxList.get(0);		
	}
	
	//	计算merkleTree Root
	private List<String> getNewTxList(List<String> tempTxList) {
		List<String> newTxList = new ArrayList<String>();
	    int index = 0;
	    while (index < tempTxList.size()) {
	    	String left = (String) tempTxList.get(index);
	    	index++;
	    	String right = "";
	    	if (index != tempTxList.size()) {
	    		right = (String) tempTxList.get(index);
	    	}
	    	String sha2HexValue = getSHA2HexValue(left + right);
	    	newTxList.add(sha2HexValue);
	    	index++;	    	  
	    }
	  return newTxList;		  
	}
	
	//	对节点进行Hash加密
	public String getSHA2HexValue(String str) {
		byte[] cipher_byte;
	    try{
	    	MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(str.getBytes());
	        cipher_byte = md.digest();
	        StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
	        for(byte b: cipher_byte) {
	        	sb.append(String.format("%02x", b&0xff) );
	        }
	        return sb.toString();	    	
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return "";		
	}
	
	//	返回Root
	public String getRoot() {
		return this.root;
	}
        
}