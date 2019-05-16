package blockchain_in_TextCopyright;

import java.util.ArrayList;
import java.util.List;

public class Merkle_Tree {

    List<String> txList;
    String root;

    public Merkle_Tree(List<String> txList) {
        this.txList = txList;
        root = "";
    }

    /*
     * 创建树并得到根
     */
    public void merkle_tree() {

        List<String> tempTxList = new ArrayList<String>();

        for (int i = 0; i < this.txList.size(); i++) {
            tempTxList.add(this.txList.get(i));
        }

        List<String> newTxList = getNewTxList(tempTxList);

        //执行循环，直到只剩下一个hash值
        while (newTxList.size() != 1) {
            newTxList = getNewTxList(newTxList);
        }

        this.root = newTxList.get(0);
    }

    /*
     * 把所有叶从下往上哈希化
     */
    private List<String> getNewTxList(List<String> tempTxList) {

        List<String> newTxList = new ArrayList<String>();
        int index = 0;
        while (index < tempTxList.size()) {
            // 获得左子叶
            String left = tempTxList.get(index);
            index++;
            // 获得右子叶
            String right = "";
            if (index != tempTxList.size()) {
                right = tempTxList.get(index);
            }
            // 把字符哈希化
            String Value = Get_Hash.Get(left + right);
            newTxList.add(Value);
            index++;

        }

        return newTxList;
    }

    /*
         * 获得根
     */
    public String getRoot() {
        return this.root;
    }
}
