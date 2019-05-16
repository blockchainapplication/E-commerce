# 第三组 区块链在文字作品版权保护y与交易中的应用与创新
![Process](https://github.com/blockchainapplication/E-commerce/blob/master/%E7%AC%AC%E4%B8%89%E7%BB%84_%E5%8C%BA%E5%9D%97%E9%93%BE%E5%9C%A8%E6%96%87%E5%AD%97%E4%BD%9C%E5%93%81%E7%89%88%E6%9D%83%E4%BF%9D%E6%8A%A4%E4%B8%8E%E4%BA%A4%E6%98%93%E4%B8%AD%E7%9A%84%E5%BA%94%E7%94%A8%E4%B8%8E%E5%88%9B%E6%96%B0/images/Process.png)
## 1.数据挖掘
### 运行环境：
Anaconda3 Jupyter Notebook<br>
python3.7

### 运行方式：
1.打开“数据挖掘”文件夹
2.将"zuopin.csv"和"data_scrapy.ipynb"文件上传至Jupyter Notebook 平台上<br>
3.打开"data_scrapy.ipynb"<br>
4.安装"Firfox-full-latest-win64"作为爬取数据的浏览器<br>
5.第一段code为读取"zuopin.csv"中的书籍名称<br>
6.第二段code为爬取京东上的文字作品的商品的信息。需要更改"geckodriver.exe"的路径为当前所在路径<br>
例如driver = webdriver.Firefox(executable_path='D:\Install\Anaconda3\geckodriver.exe', firefox_options=options)<br>
7.第三段代码为爬取已选取书籍的内容<br>
8.第四段code则是整理数据并且保存为csv文件。csv文件默认保存在电脑C盘\用户\administer下<br>

运行代码只需要导入"zuopin.csv"和"data_scrapy.ipynb"文件，拿出数据需在目录中提出来或者直接使用文件夹中已经提取出的文件<br>

### 运行结果：
根据输入的书名，可以在cvs文件中看到如下内容被挖掘到<br>
![result1](第三组_区块链在文字作品版权保护与交易中的应用与创新/images/result1.png)

## 2.相似度比较 & 提取特征
### 运行环境
eclipse 2019-03
JavaSE-1.8<br>
再导入jar包<br>
1.在eclipse menu里打开Windows >preferences, 选择Java >Build path > User Libraries. 点击 new 建立一个新的User Library 取名为 “IKAnalyzer” 点击确认. 选中IKAnalyzer，按右边添加JARs找到你存的gson-2.6.2.jar 应用并关闭。<br>
![import_jar_package1](第三组_区块链在文字作品版权保护与交易中的应用与创新/images/import_jar_package1.png)<br>
2.添加User Libarary到你的java project中. 右键你的java projact的package选择 package explorer > Build path > Add Libraries. 选择User Libraries中你新建的 “IKAnalyzer” 然后点击结束。<br>
![import_jar_package2](第三组_区块链在文字作品版权保护与交易中的应用与创新/images/import_jar_package2.png)<br>
导入lucene包同理<br>

### 运行方式：
相似度比较<br>
1.打开“相似度比较、提取特征”文件夹
2.先把需要验证的文本（小说作品这类的）保存到yanzheng.csv文件中，【需保存至csv文件中的第1行、第1列】<br>
3.使用eclipse打开similarity_caculation文件夹<br>
4.更改btest函数中的路径指定到yanzheng.csv<br>
5.更改atest函数中的路径指定到hanhan.csv<br>
6.运行 若需要验证的文本与在库文本相似度高度高于70% 则中止后续对比<br>

提取特征<br>
1.使用eclipse打开tfidf文件夹<br>
2.把上一步中已经通过验证的文本内容保存为txt文本与content文件夹中<br>
3.更改主函数中文件夹的绝对路径，指向content文件夹<br>
4.运行函数时右键点击compute_tfidf.java -> Run As -> Run Configuration ->Common ->Output File 在Output File 前打勾，并输入保存的文件路径例如C:\Users\14760\Desktop\block_area\file.txt<br>
![out_to_file](第三组_区块链在文字作品版权保护与交易中的应用与创新/images/Out_to_file.png)<br>
5.新生成的文件file.txt被使用在下一步中<br>

### 运行结果：
计算得到的相似度：<br>
![result2](第三组_区块链在文字作品版权保护与交易中的应用与创新/images/result2.png)<br>
提取到的特征：<br>
![result3](第三组_区块链在文字作品版权保护与交易中的应用与创新/images/result3.png)

## 3.成块上链
### 运行环境：
eclipse 2019-03
JavaSE-1.8

### 运行方式：
1.打开eclipse，使用Open Projects form File System打开blockchain_in_TextCopyright文件夹<br>
2.展开blockchain_in_TextCopyright找到src中的Get_info.java，将第12行和第20行中的FileReader的读取路径分别改为hanhan.csv和file.txt的所在路径<br>
3.选择Mine.java类，运行，查看控制台即可<br>

### 运行结果：
显示找到的hash值：<br>
![result4](第三组_区块链在文字作品版权保护与交易中的应用与创新/images/result4.png)
输出全部区块链：<br>
![result5](第三组_区块链在文字作品版权保护与交易中的应用与创新/images/result5.png)
输出每个区块中的内容：<br>
![result6](第三组_区块链在文字作品版权保护与交易中的应用与创新/images/result6.png)

