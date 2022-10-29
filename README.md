# personal-DB:cloud::cd:

## 目录
[toc]

## 需求

- :bulb:任何项目都需要有一个明确的需求，因此阐明这个小项目的设计初衷非常有必要——至少我是这么认为的
- 这两年，随着CS专业的学习和为组装自己第一台主机做准备，我对于磁盘（固态硬盘或者机械硬盘）的数据损坏问题越来越关注
- 本来打算买机械硬盘搭建RAID，但看到一则评论，大意是搭建一个家用NAS一年的电费将近8千块，瞬间打消了我这个念头
- 但同时，互联网巨头（如谷歌、亚马逊、阿里等）使用RAID等技术保证其云服务器数据的可靠性。因此，为何不将自己的数据存放在云盘（网盘）中？这样能够保证数据的**持久性**，且云盘会员（为大容量付费）的价格**远低于**家用NAS方案。
- 但放在服务器上，数据的**隐私性**难以保证。因此，需要使用某种加密手段。
- 故，可以在本地对文件进行加密，并将加密后的文件传输至网盘



## 原理/设计

- 前提：数据是冷数据（写频率很低的数据）。加密后的文件一旦上传，很长时间内都不会再上传新的版本——这是出于（上下行）带宽、本地主机性能和文件重要性等多因素的考虑
- 使用加密压缩的形式对文件加密，具体来说，使用RAR，有以下优点：
    - 压缩文件可以在命令行执行
    - RAR有**恢复记录**这一功能，该功能可视作保险，防止数据因网络传输或云服务提供商的失职（incompetence，无能）造成损坏
- 压缩密码使用随机生成的长ASCII字符串——任何使用密钥的加密算法的安全性都是基于**随机密钥**，方便人记忆的密码同样方便（字典）破解
- 该项目中设计的数据库`cloud_backup`基本符合数据库设计理论，该数据库中有两张表，一张为`files`，另一张为`directories`。它们的作用都是自解释的。
- `files`表有如下字段：
    - `id`，主键，对应云盘中的文件名的一部分（云盘上的每一个文件的名称都形如`files_<id>.rar`），该值本身是AUTO_INCREMENT的，没啥特殊意义
    - `filename`，原始文件名。在该字段上建立索引，以快速查询
    - `lastmodified`，日期类型，记录最后修改时间
    - `md5value`，原始文件的32个字符长度的md5值，检验文件是否被云盘供应商篡改
    - `passwd`，**明文**存储对应文件压缩包的密码——由于密码是随机生成的，人不可能记住，尽管我知道明文存储并不明智
    - `note`，关于文件的一些说明，允许为空。例如，假设文件原本在`games`文件夹中，则该字段可以包括游戏别名, 游戏公司等
    - `filesize`，压缩文件的大小
    - `dirid`，是`directories.id`的外键
- `directories`表有如下字段：
    - `dirname`，唯一约束，文件原本应该属于哪个文件夹
    - `note`，关于文件夹的一些说明，允许为空。
    - `id`，主键，AUTO_INCREMENT，没啥特殊意义

- 由于文件密码是明文存储，数据库及其数据本身的访问需要加密
- 要经常备份数据库:exclamation::exclamation::exclamation:，因为这是打开文件的==唯一途径==
- 其他一些技巧、注意事项：
    - 使用DML操作数据库时要开启事务
    - 为了防止（对抗）云盘供应商的审查导致数据丢失，凡是从别人分享的链接获得的文件，都要**及时**下载到本地，使用该程序重新生成压缩文件后上传。这样，原文件和重新生成的文件将是两份完全不同的文件。
    - 出于（数据）安全性考虑，应该在不同的云盘中保留多份文件副本，防止某云盘提供商出问题——不要把鸡蛋:egg:放在一个篮子里

## 环境
- OS: Windows 10
- Oracle JDK 8
- mysql Ver 14.14 Distrib 5.7.19, for Win64 (x86_64)

## 如何使用
- 依次运行`init1_create_DB_usr.sql`和`init2_create_table.sql`，请确保运行命令时的用户有足够的权限
- 按照注释，修改`/src/common/Param.java`中的各项参数
- 在`/src/`目录下运行`compileAndRun.bat`

## 总结
- 这是个小项目，基本不涉及复杂的技术，但关键在于项目设计和实现流程自动化



# 开发历程

- 2022-10-22：按照最初的设计开发、测试完毕
- 2022-10-29：将原先完全不符合数据库设计理论的数据库重新设计，并调整程序代码（重构？应该算不上），开发、测试完毕。用户界面依旧是命令行，接下来要使用Swing实现GUI

