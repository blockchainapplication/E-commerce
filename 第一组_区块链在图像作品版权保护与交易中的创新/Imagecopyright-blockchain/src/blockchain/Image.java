package blockchain;

import java.util.Date;
//用于遍历输出的图像类

public class Image {
	public User user;
	public int width;
	public int length;
	public long Id;
	public String imagename;
	public String imagehash;
	public int bonuscoins;
	
	public Image(User user, int width, int length, String imagename, String imagehash, int bonuscoins)
	{
		this.user = user;
		this.width = width;
		this.length = length;
		this.imagename = imagename;
		this.Id = new Date().getTime();
		this.imagehash = imagehash;
		this.bonuscoins = bonuscoins;
	}
}
