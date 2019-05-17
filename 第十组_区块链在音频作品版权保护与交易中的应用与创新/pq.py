
# -*- coding: utf-8 -*-

import requests

from bs4 import BeautifulSoup

import json

print("开始抓取数据，请保持网络畅通。。。")

url='https://music.163.com/album?id=72301648'

header = {'Accept': '*/*', 'Accept-Encoding': 'gzip, deflate', 'Accept-Language': 'zh-CN,zh;q=0.9',

                  'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36'}

def gethtml(url):     #获取专辑的html

    try:

        response=requests.request('get',url,headers=header,timeout=30)

        response.raise_for_status()

        response.encoding=response.apparent_encoding

        html=response.content

    except:

        print("error：网络原因爬取出错！")

    return html
# -*- coding: utf-8 -*-

import requests

from bs4 import BeautifulSoup

import json

print("开始抓取数据，请保持网络畅通。。。")

url='https://music.163.com/album?id=72301648'

header = {'Accept': '*/*', 'Accept-Encoding': 'gzip, deflate', 'Accept-Language': 'zh-CN,zh;q=0.9',

                  'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36'}

def gethtml(url):     #获取专辑的html

    try:

        response=requests.request('get',url,headers=header,timeout=30)

        response.raise_for_status()

        response.encoding=response.apparent_encoding

        html=response.content

    except:

        print("error：网络原因爬取出错！")

    return html

def resolvehtml(html):   #解析专辑的html,返回最终要输出的id(数字类型)，name，title,专辑中的歌曲数量

    index=BeautifulSoup(html,"html.parser")

    lsul=index.find_all(class_="f-hide")[0]    #返回的是无序列表，歌曲的id和name<li><a href="/song?id=1301575103">暗夜曙光</a>，用列表存储

    title=(index.title.string)[:-21]   #专辑的标题,字符串切片去掉最后21位

    songid = []

    songname = []

    songnum=len(lsul)

    for i in range(songnum):

        songid.append(lsul.find_all("a")[i]["href"]) #取出所有a标签的href属性,并且存储为列表类型

        songname.append((lsul.find_all("a")[i].string))#取出所有a标签的内容，并且存储为列表类型

        songid[i]=int((str(songid[i]))[9:]) #将id信息改为纯数字id

    return songid,songname,title,songnum

def getcomment(url):

    a=resolvehtml(gethtml(url))

    songid=a[0]

    songname=a[1]

    title=[2]

    songnum=a[3]

    url=[]

    commentnum=[]

    hotcomment=[]

    for i in range(songnum):#执行该专辑歌曲数次程序

        url.append("http://music.163.com/api/v1/resource/comments/R_SO_4_"+str(songid[i]))

        r=gethtml(url[i])

        dic=json.loads(r)

        commentnum.append(dic['total'])

        hotcom = dic['hotComments']

        if (len(hotcom) == 0):

            hotcomment.append("最热评论：无")

        else:

            hotdic = hotcom[0]

            hotcomment.append(hotdic["content"])

    return songname,songid,songnum,hotcomment,commentnum#返回一张专辑爬取到的所有有用信息，id，name，专辑歌曲数，热评，热评数

def albuminfo():

    url = "https://music.163.com/artist/album?id=12639&limit=200&offset=0"#霹雳布袋戏的所有专辑页url

    menuid = []

    menuname = []

    menutime = []

    r=gethtml(url)

    txt1=BeautifulSoup(r,'html.parser')

    for i in range(181):  # 优化不够，后面出新专辑的时候改变参数

        id =txt1.find_all(class_="tit s-fc0")

        menuid.append(id[i]["href"])

        menuid[i] =str(menuid[i])[10:]

        name = txt1.find_all(class_="dec dec-1 f-thide2 f-pre")

        menuname.append(name[i]["title"])

        time = txt1.find_all(class_="s-fc3")

        menutime.append(time[i].string)

    return menuname,menuid,menutime

fp = open("test.txt", 'a',encoding="utf-8")

def main():

    result=albuminfo()

    for i in range(181):

        url='https://music.163.com/album?id='+(result[1])[i]

        fp.write(str(getcomment(url)))

    fp.close()

main()


def resolvehtml(html):   #解析专辑的html,返回最终要输出的id(数字类型)，name，title,专辑中的歌曲数量

    index=BeautifulSoup(html,"html.parser")

    lsul=index.find_all(class_="f-hide")[0]    #返回的是无序列表，歌曲的id和name<li><a href="/song?id=1301575103">暗夜曙光</a>，用列表存储

    title=(index.title.string)[:-21]   #专辑的标题,字符串切片去掉最后21位

    songid = []

    songname = []

    songnum=len(lsul)

    for i in range(songnum):

        songid.append(lsul.find_all("a")[i]["href"]) #取出所有a标签的href属性,并且存储为列表类型

        songname.append((lsul.find_all("a")[i].string))#取出所有a标签的内容，并且存储为列表类型

        songid[i]=int((str(songid[i]))[9:]) #将id信息改为纯数字id

    return songid,songname,title,songnum

def getcomment(url):

    a=resolvehtml(gethtml(url))

    songid=a[0]

    songname=a[1]

    title=[2]

    songnum=a[3]

    url=[]

    commentnum=[]

    hotcomment=[]

    for i in range(songnum):#执行该专辑歌曲数次程序

        url.append("http://music.163.com/api/v1/resource/comments/R_SO_4_"+str(songid[i]))

        r=gethtml(url[i])

        dic=json.loads(r)

        commentnum.append(dic['total'])

        hotcom = dic['hotComments']

        if (len(hotcom) == 0):

            hotcomment.append("最热评论：无")

        else:

            hotdic = hotcom[0]

            hotcomment.append(hotdic["content"])

    return songname,songid,songnum,hotcomment,commentnum#返回一张专辑爬取到的所有有用信息，id，name，专辑歌曲数，热评，热评数

def albuminfo():

    url = "https://music.163.com/artist/album?id=12639&limit=200&offset=0"
# -*- coding: utf-8 -*-

import requests

from bs4 import BeautifulSoup

import json

print("开始抓取数据，请保持网络畅通。。。")

url='https://music.163.com/album?id=72301648'

header = {'Accept': '*/*', 'Accept-Encoding': 'gzip, deflate', 'Accept-Language': 'zh-CN,zh;q=0.9',

                  'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36'}

def gethtml(url):     #获取专辑的html

    try:

        response=requests.request('get',url,headers=header,timeout=30)

        response.raise_for_status()

        response.encoding=response.apparent_encoding

        html=response.content

    except:

        print("error：网络原因爬取出错！")

    return html

def resolvehtml(html):   #解析专辑的html,返回最终要输出的id(数字类型)，name，title,专辑中的歌曲数量

    index=BeautifulSoup(html,"html.parser")

    lsul=index.find_all(class_="f-hide")[0]    #返回的是无序列表，歌曲的id和name<li><a href="/song?id=1301575103">暗夜曙光</a>，用列表存储

    title=(index.title.string)[:-21]   #专辑的标题,字符串切片去掉最后21位

    songid = []

    songname = []

    songnum=len(lsul)

    for i in range(songnum):

        songid.append(lsul.find_all("a")[i]["href"]) #取出所有a标签的href属性,并且存储为列表类型

        songname.append((lsul.find_all("a")[i].string))#取出所有a标签的内容，并且存储为列表类型

        songid[i]=int((str(songid[i]))[9:]) #将id信息改为纯数字id

    return songid,songname,title,songnum

def getcomment(url):

    a=resolvehtml(gethtml(url))

    songid=a[0]

    songname=a[1]

    title=[2]

    songnum=a[3]

    url=[]

    commentnum=[]

    hotcomment=[]

    for i in range(songnum):#执行该专辑歌曲数次程序

        url.append("http://music.163.com/api/v1/resource/comments/R_SO_4_"+str(songid[i]))

        r=gethtml(url[i])

        dic=json.loads(r)

        commentnum.append(dic['total'])

        hotcom = dic['hotComments']

        if (len(hotcom) == 0):

            hotcomment.append("最热评论：无")

        else:

            hotdic = hotcom[0]

            hotcomment.append(hotdic["content"])

    return songname,songid,songnum,hotcomment,commentnum#返回一张专辑爬取到的所有有用信息，id，name，专辑歌曲数，热评，热评数

def albuminfo():

    url = "https://music.163.com/artist/album?id=12639&limit=200&offset=0"#霹雳布袋戏的所有专辑页url

    menuid = []

    menuname = []

    menutime = []

    r=gethtml(url)

    txt1=BeautifulSoup(r,'html.parser')

    for i in range(181):  # 优化不够，后面出新专辑的时候改变参数

        id =txt1.find_all(class_="tit s-fc0")

        menuid.append(id[i]["href"])

        menuid[i] =str(menuid[i])[10:]

        name = txt1.find_all(class_="dec dec-1 f-thide2 f-pre")

        menuname.append(name[i]["title"])

        time = txt1.find_all(class_="s-fc3")

        menutime.append(time[i].string)

    return menuname,menuid,menutime

fp = open("test.txt", 'a',encoding="utf-8")

def main():

    result=albuminfo()

    for i in range(181):

        url='https://music.163.com/album?id='+(result[1])[i]

        fp.write(str(getcomment(url)))

    fp.close()

main()


    menuid = []

    menuname = []

    menutime = []

    r=gethtml(url)

    txt1=BeautifulSoup(r,'html.parser')

    for i in range(181):  # 优化不够，后面出新专辑的时候改变参数

        id =txt1.find_all(class_="tit s-fc0")

        menuid.append(id[i]["href"])

        menuid[i] =str(menuid[i])[10:]

        name = txt1.find_all(class_="dec dec-1 f-thide2 f-pre")

        menuname.append(name[i]["title"])

        time = txt1.find_all(class_="s-fc3")

        menutime.append(time[i].string)

    return menuname,menuid,menutime

fp = open("test.txt", 'a',encoding="utf-8")

def main():

    result=albuminfo()

    for i in range(181):

        url='https://music.163.com/album?id='+(result[1])[i]

        fp.write(str(getcomment(url)))

    fp.close()

main()
