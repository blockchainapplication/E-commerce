package blockchain;

import java.util.ArrayList;
import java.util.List;

public class MerkleTrees {

	Image Image;// 需要存储的加密图片构成的列表
	String root;// 以哈希值形式呈现的默克尔树根
	int index;// 默克尔树在链中的序号

	/**
	 * 初始化默克尔树
	 */
	public MerkleTrees(Image Image, int index) {
		this.Image = Image;
		this.root = "";
		this.index = index;
	}

	/**
	 * 生成默克尔树的迭代函数
	 */
	private List<String> getNewList(List<String> tempList) {

		List<String> newList = new ArrayList<String>();
		int index = 0;
		while (index < tempList.size()) {
			// 左子树赋值
			String left = tempList.get(index);
			index++;
			// 右字数赋值
			String right = "";//如若遇到奇数个节点，用空值进行补足
			if (index != tempList.size()) {
				right = tempList.get(index);
			}
			// 左右字数合并取哈希值
			String sha2HexValue = getShavalue.applySha256(left + right);
			newList.add(sha2HexValue);
			index++;

		}

		return newList;
	}

	/**
	 * 迭代构造默克尔树
	 */
	public void merkle_tree() {

		List<String> tempList = new ArrayList<String>();
		
			tempList.add(this.Image.user.name);
			tempList.add(this.Image.imagename);
			tempList.add(this.Image.imagehash);
		
		
		List<String> hashList = new ArrayList<String>();
		for (int i = 0; i < tempList.size(); i++){
			String shavalue = getShavalue.applySha256(tempList.get(i));
			hashList.add(shavalue);
		}
		
		List<String> newList = getNewList(hashList);

		while (newList.size() != 1) {
			newList = getNewList(newList);
		} // 迭代合并直到列表中值得数量为1

		this.root = newList.get(0);// 将哈希值赋值给树根
	}

	public String getRoot() {
		return this.root;
	}

}