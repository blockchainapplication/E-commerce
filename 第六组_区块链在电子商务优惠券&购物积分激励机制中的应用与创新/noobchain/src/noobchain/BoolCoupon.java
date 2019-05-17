package noobchain;

//	用于判断入链的coupon信息是否正确
public class BoolCoupon {

//	判断coupon的发行（领取/使用）时间是否在对应的截至日期之前
	public static boolean dateMatch(CouponData couponData) {
		boolean bool = couponData.occur_time.before(couponData.expire_time);
		return bool;
	}

//	判断表中coupon的seller和dealer是否匹配
	public static boolean sellerMatchDealer(CouponData couponData) {
		boolean bool = (couponData.ID.subSequence(4, 8).equals("J001") && (
					    couponData.ID.subSequence(0, 4).equals("S001") || 
			            couponData.ID.subSequence(0, 4).equals("S002") || 
			            couponData.ID.subSequence(0, 4).equals("S003")))||(
			            couponData.ID.subSequence(4, 8).equals("J002") && (
			            couponData.ID.subSequence(0, 4).equals("S004") || 
					    couponData.ID.subSequence(0, 4).equals("S005")));
		return bool;
	}
	
//	判断后一张表中的coupon ID 是否在前一张表中出现
	public static boolean beforeMatchAfter(CouponData[] couponDataBefore, CouponData couponDataAfter) {
		boolean bool = false;
		for(int i=0; i<couponDataBefore.length; i++) {
			if(couponDataAfter.ID == couponDataBefore[i].ID) {
				bool = true;
				continue;
			}
		}
		return bool;
	}
}


