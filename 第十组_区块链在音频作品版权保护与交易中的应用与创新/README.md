//环境依赖
Eclipse Java 2019-03
Jdk-11.0.3

//环境搭建方法：
打开Eclipse Java，点击File，选择Open Files from File System, 选中当前项目所在文件夹并打开；
打开项目后，右键点击项目，选择build path，configure build path，在右面窗口选择Libraries，add library...，选择jre system library。

//运行方法：使用eclipse打开所在的文件夹，使用"Run"运行，系统会自动生成区块链；
接着按照提示操作：先输入交易次数，再输入公钥、私钥, 如果错误，系统提示：“无此音频！” 
如果正确，系统提示“请输入您的姓名”，此后会输出所查询音频的名称、所属专辑、专辑封面和演唱者，会构建一个新的区块，此时版权所有者将变为“请输入您的姓名”中的姓名。

//注：1）再次运行前先删除已生成的privatekey.txt!
// 2）在交易功能中，若输入姓名后出现错误，请对代码内相应的subString()方法的参数进行适当调整。

运行结果：
爬虫数据（利用pq.py抓取）
![](https://github.com/blockchainapplication/E-commerce/blob/master/%E7%AC%AC%E5%8D%81%E7%BB%84_%E5%8C%BA%E5%9D%97%E9%93%BE%E5%9C%A8%E9%9F%B3%E9%A2%91%E4%BD%9C%E5%93%81%E7%89%88%E6%9D%83%E4%BF%9D%E6%8A%A4%E4%B8%8E%E4%BA%A4%E6%98%93%E4%B8%AD%E7%9A%84%E5%BA%94%E7%94%A8%E4%B8%8E%E5%88%9B%E6%96%B0/%E7%88%AC%E8%99%AB.PNG)

加密后输出的区块：
![](https://github.com/blockchainapplication/E-commerce/blob/master/%E7%AC%AC%E5%8D%81%E7%BB%84_%E5%8C%BA%E5%9D%97%E9%93%BE%E5%9C%A8%E9%9F%B3%E9%A2%91%E4%BD%9C%E5%93%81%E7%89%88%E6%9D%83%E4%BF%9D%E6%8A%A4%E4%B8%8E%E4%BA%A4%E6%98%93%E4%B8%AD%E7%9A%84%E5%BA%94%E7%94%A8%E4%B8%8E%E5%88%9B%E6%96%B0/%E8%BF%90%E8%A1%8C%E7%BB%93%E6%9E%9C1.PNG)
