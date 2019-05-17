package noobchain;

import java.util.Date;

//	每一条coupon信息的结构
public class CouponData {
	Date occur_time;
	String ID;
	int money;
	Date expire_time;
	String user;
	String status;
	
	//	用于输出coupon的所有信息至merkleTree的一个节点
	public String get() {
		String data = new String("NaN");
		data = occur_time.toString() + ID + String.valueOf(money) + expire_time.toString() + user + status;
		return data;
	}
	
	//	用于coupon信息查询
	public void getInfo() {
		System.out.print("Coupon ID: " + ID + "\n");
		System.out.print("Occur time: " + occur_time.toString() + "\n");
		System.out.print("Expire time: " + expire_time.toString() + "\n");
		System.out.print("Seller: " + ID.substring(0, 4) + "\n");
		System.out.print("Dealer: " + ID.substring(4, 8) + "\n");
		System.out.print("User: " + user + "\n");
		System.out.print("Status: " + status + "\n");
		System.out.println();
	}
}
