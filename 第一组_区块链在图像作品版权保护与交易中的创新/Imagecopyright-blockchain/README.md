文件夹中包含：
========
java工程文件，Stephen-Flower.jpg（目标图片），image（存储通过爬取获得的图片的文件夹），image_a（包含爬取图片，用于比对运行结果的文件夹），gson-2.6.2.jar，zxing-zxing-3.2.1.jar（需要导入的外部包）
，运行结果示例文件夹中包含QRcode_a.jpg（用于比对运行结果的图片），combinelma_a.jpg（用于比对运行结果的图片），Hash_a.txt（用于比对运行的结果的txt文件），

程序运行环境:
Java JDK-12.0.1，编译器Eclipse，需要导入的library有gson-2.6.2和zxing-zxing-3.2.1（已经附在文件夹中）。

请将程序下载并解压至D盘目录下，保证工程路径为：D:\imagecopyright-blockchain

程序运行步骤：
第一步：打开Eclipse，点击File-Open File选中D盘中的工程，点击导入工程Imagecopyright-blockchain

第二步：右键点击屏幕左侧package explorer中该工程下的src的build path，选择add external library，选中文件夹中的gson-2.6.2.jar文件，点击导入。

第三步：同上操作，导入zxing-zxing-3.2.1.jar包。

第四步：运行package crawler下的crawler进行图片爬取，图片会存储在工程文件的image文件夹中。我们在文件夹中另建了一个文件夹image_a，里面包含了正确运行程序后得到的图片，可比较运行结果，检查正常与否。

第五步：运行package imageprocess的PHASH通过感知哈希函数进行图片相似度计算。该程序的目标在于计算目标图片（Stephen-Flower.jpg）与image文件夹（通过爬虫获得的图片）中所有的图片的相似度。通过了该相似度检测以后，会在工程目录下生成示例图片的二维码（QRcode.jpg）、二维码合成版（combinelma.jpg）以及对应二维码合成版的哈希值的txt文件（Hash.txt）。程序运行正确的结果已经在给定文件夹中展示，可供进行比对。

第六步：运行package blockchain下的BlockChain，该段程序进行挖矿与信息上链操作。

如果运行遇到问题，请及时与我们取得联系，谢谢！
邮箱：784854380@qq.com
