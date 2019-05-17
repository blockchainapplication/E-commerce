package noobchain;

import java.util.Scanner;

//	用于对区块链中的优惠券进行按条件筛选，用于优惠券溯源
public class SelectCoupon {
	
	@SuppressWarnings("resource")
	public static void selectCoupon(CouponData[] couponData) {
		System.out.println();

		//	基本信息备注
		System.out.println("=========================================================================================");
		Scanner sc = new Scanner(System.in); 
		System.out.println("There are 5 Sellers: S001, S002, S003, S004, S005.");
		System.out.println("And 2 Dealers: J001, J002 in block chain. ");
		System.out.println("And each e-coupon has 2 Status: FREE & USED.");
		System.out.println("Please note that,");
		System.out.println("J001 only distributes the e-coupons issued by S001, S002, S003;");
		System.out.println("JOO2 only distributes the e-coupons issued by S004, S005.");
		System.out.println("seller can deliver e-coupons by themsleves.");
		System.out.println("'User' is 'None' means this e-coupon was not received.");
		System.out.println("=========================================================================================");
		
		//	输入想要筛选的商家和经销商
		System.out.println("Please input Seller & Dealer to selet those Coupons !");
		System.out.print("Seller: ");
		String Seller = sc.nextLine(); 
		System.out.print("Dealer: ");
		String Dealer = sc.nextLine();	
		
		//	输出所有符合条件的coupon信息
		System.out.println("=========================================================================================");
		System.out.println("The selected e-coupon list is below:");
		System.out.println("=========================================================================================");
		int sumNotReceive = 0;
		int sumFree = 0;
		int sumUsed = 0;
		int sum = 0;
		for(int i = 0; i< couponData.length; i++ ) {
			if(Seller.equals(couponData[i].ID.substring(0, 4)) && Dealer.equals(couponData[i].ID.substring(4, 8))) {
				couponData[i].getInfo();
				sum ++;
				if(couponData[i].user.equals("None")) {
					sumNotReceive ++;}
				else {
					if(couponData[i].status.equals("FREE")) {
						sumFree ++;}
					else {sumUsed ++;}}
			}
		}
		
		//	如果输出信息为0条，执行此代码告知用户信息输入有误
		if(sum == 0) {
			System.out.println("No e-coupon was selected, maybe the input is wrong.");
			System.out.println("=========================================================================================");}
		//	输出优惠券列表中，有几张未领取，几张已领取但未使用，几张已使用
		else {
			System.out.println("=========================================================================================");
			System.out.print("The sum number of e-coupons issued by " + Seller + " and distributed by " + Dealer + " is: " + sum);
			System.out.println();
			System.out.println("And");
			System.out.print("    " + sumNotReceive + " of them were not received;");
			System.out.println();
			System.out.print("    " + sumFree + " of them were received but not used;");		
			System.out.println();
			System.out.print("    " + sumUsed + " of them were used.");	
			System.out.println();
			System.out.println("=========================================================================================");}
	}	
}
