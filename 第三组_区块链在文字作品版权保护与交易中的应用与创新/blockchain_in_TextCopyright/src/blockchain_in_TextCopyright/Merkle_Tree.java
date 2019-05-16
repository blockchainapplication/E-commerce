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
     * ���������õ���
     */
    public void merkle_tree() {

        List<String> tempTxList = new ArrayList<String>();

        for (int i = 0; i < this.txList.size(); i++) {
            tempTxList.add(this.txList.get(i));
        }

        List<String> newTxList = getNewTxList(tempTxList);

        //ִ��ѭ����ֱ��ֻʣ��һ��hashֵ
        while (newTxList.size() != 1) {
            newTxList = getNewTxList(newTxList);
        }

        this.root = newTxList.get(0);
    }

    /*
     * ������Ҷ�������Ϲ�ϣ��
     */
    private List<String> getNewTxList(List<String> tempTxList) {

        List<String> newTxList = new ArrayList<String>();
        int index = 0;
        while (index < tempTxList.size()) {
            // �������Ҷ
            String left = tempTxList.get(index);
            index++;
            // �������Ҷ
            String right = "";
            if (index != tempTxList.size()) {
                right = tempTxList.get(index);
            }
            // ���ַ���ϣ��
            String Value = Get_Hash.Get(left + right);
            newTxList.add(Value);
            index++;

        }

        return newTxList;
    }

    /*
         * ��ø�
     */
    public String getRoot() {
        return this.root;
    }
}
