# culturalHeritage

#### 项目介绍
爬取非遗项目并记录相应传承人信息， 模拟统计非遗项目的访问（浏览人数）并存储在区块链中。查询对应非遗项目然后改修访问人数更新到区块链中，并模拟分布式节点存储，和分布节点数据同步 。

#### 软件架构
传统java项目


#### 安装教程
下载
1. 下载JDK和开发工具 环境变量配置。首先配置JAVA_HOME:右键单击计算机图标，接着点击菜单最下方的属性菜单项，或者连续打开控制面板--系统和安全--系统也可以。在弹出的对话框中单击”高级“选项卡，接着点击下方的环境变量
在环境变量对话框中的系统变量中点击“新建”按钮，在弹出的新建系统变量里的变量名中输入JAVA_HOME，在变量值中输入JDK的根目录：比如:  D:\Software\Java\jdk1.8.0_60 ，然后点击确定，返回环境变量对话框。
在系统变量中查看是否有“classpath”变量，如果没有，则新建这个变量，变量名classpath  变量值 .;%JAVA_HOME%\lib; 注意，此变量值以英文句点符号开始，以分好结束。然后点击确定，返回环境变量对话框。
最后，在系统变量里面找到Path变量，注意，这次是点击编辑按钮，在弹出的对话框中的变量值的最后，一定是最后，
添加如下字符串：;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin。注意，前面第一个是分号。如果没有Path变量，则添加Path变量，添加步骤和前面一样
2. eclipse的控制栏中的file，找到import和export，这两个就是项目的导入和导出。
我们点击import,选择并点开 General ---> Exising Projects into Workspace  ---> Next --->Browse ---> 找到电脑里需要导入的项目--->确定--->Finish
按照以上的步骤，就可以实现项目的导入。
在项目导入之后肯能出现的问题 第一种情况：项目导入之后报错（没有找到正确的类）
我们需要配置Jdk 选择刚才导入的项目 右击---->Build Path--->Configure Build Path--->选择Libraries ---> JRE System Library[jdk的版本] ---> Edit---> Workspace default JRE (我们的jdk版本) ---> Finish--->apply and Close  完成
第二种情况：项目导入之后 会出现乱码
选择刚才导入的项目 右击---> 找到Properties --->选择Resource ---> Eclipse 默认编码是GBK ,我们修改为UTF-8 . ---> apply and Close  完成

3.选中项目中lib 文件夹中jar 包-- >右击选中Build Path ---> add to Build path  添加jar包到当前项目中

####  代码执行流程
1.
2.
3.


#### 使用说明

Noobchain.main  run as 运行  

#### 参与贡献



