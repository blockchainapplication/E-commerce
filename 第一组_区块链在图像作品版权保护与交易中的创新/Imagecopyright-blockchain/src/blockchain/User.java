package blockchain;

import java.util.Date;

public class User {
//pos 根据版权比数量确定挖矿概率 比较时间，时间短者获得记录权力，同时获取块中版权币
//块中默克尔树加入记录者名字，确定是谁挖到该块
	public String name;
	public long user_id;
	public int number_coins;
	
	
	public User(String name, int number_coins) {
		this.name = name;
		this.user_id = new Date().getTime();
		this.number_coins = number_coins;
	}
}