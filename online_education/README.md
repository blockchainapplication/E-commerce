# 项目名
区块链在在线学习监督与评定中的应用与创新

#### 项目介绍
学生的学习计划、进程、成果实时记录在链。
依据学生个人、指导教师、校方、教育主管部门等各相关方的不同权限，
开放基于区块链的学习计划、进程、成果依权查询、监督。
结合智能合约技术实现课程认证，提供学分、课外活动分数等激励政策

#### 软件架构
java传统架构


#### 安装教程

• Eclipse
• jdk-11.0
• gson-2.6.2.jar

运行环境：
jdk 1.8+
配置环境变量：
这台电脑-属性-高级系统设置-高级-环境变量。找到安装java SDK的目录，复制该路径 点击‘系统变量’下的新建，
变量处填写'jAVA_HOME'变量值处直接粘贴，然后点击“确定”，在'系统变量‘点击'新建’,在弹出的窗口中分别输入
变量名：”CLASSPATH”
变量值: “.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;”
（取引号之内的值，包括前面的”.” ，这里%JAVA_HOME%就是引用之前配置好的JAVA_HOME ）

GSON是Google提供的用来在Java对象和JSON数据之间进行映射的
Java类库。我们需要从google上下载Gson Jar ( “gson-2.6.2.jar” ) 并记录保存路径
在Eclipse menu里打开Windows >preferences, 选择Java >Build path > User 
Libraries. 点击 new 建立一个新的User Library 取名为 “gson_lib” 点击确认. 
选中gson_lib，按右边添加JARs找到你存的gson-2.6.2.jar 应用并关闭
添加User Libarary到你的java project中.
右键你的java projact的package选择 package explorer > Build path > Add Libraries. 
选择User Libraries中你新建的 “gson_lib” 然后点击结束在代码中使用它需Import
Import it with import com.google.gson.*

#### 使用说明

启动类 Noobchain.main()运行程序

#### 参与贡献

1. 16124325 徐嘉缘 
2. 16124292 陆文煜
3. 16124286 姜宇
4. 16124308 董亚



