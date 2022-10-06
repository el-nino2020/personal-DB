# personal-DB:cloud::cd:

## 需求

- :bulb:任何项目都需要有一个明确的需求，因此阐明这个小项目的设计初衷非常有必要——至少我是这么认为的
- 这两年，随着CS专业的学习和为组装自己第一台主机做准备，我对于磁盘（固态硬盘或者机械硬盘）的数据损坏问题越来越关注
- 本来打算买机械硬盘搭建RAID，但看到一则评论，大意是搭建一个家用NAS一年的电费将近8千块，瞬间打消了我这个念头
- 但同时，互联网巨头（如谷歌、亚马逊、阿里等）使用RAID等技术保证其云服务器数据的可靠性。因此，为何不将自己的数据存放在云盘（网盘）中？这样能够保证数据的**持久性**，且云盘会员（为大容量付费）的价格远低于家用NAS方案。
- 但放在服务器上，数据的**隐私性**难以保证。因此，需要使用某种加密手段。
- 故，可以在本地对文件进行加密，并将加密后的文件传输至网盘



## 原理/设计

- 前提：数据是冷数据（写频率很低的数据）。加密后的文件一旦上传，很长时间内都不会再上传新的版本——这是出于带宽、本地主机性能和文件重要性等多因素的考虑
- 使用加密压缩的形式对文件加密，具体来说，使用RAR，有以下优点：
    - 压缩文件可以在命令行执行
    - RAR有**恢复记录**这一功能，该功能可视作保险，防止数据因网络传输和云服务提供商的失职造成损坏——数据无价
- 压缩密码使用随机生成的长ASCII字符串——任何使用密钥的加密算法的安全性都是基于**随机密钥**，方便人记忆的密码同样方便（字典）破解
- 该项目中设计的数据库`cloud_backup`**完全不符合**数据库设计理论，该数据库中每张表具有相同的结构，唯一不同的就是表名，其目的是使一张表对应一个云盘中的文件夹，而表中的记录对应该文件夹中的一个文件。具体来说，`cloud_backup.games`对应云盘中`/cloud_backup/games/`文件夹，该表记录的`id`字段对应文件夹下的某一文件名（我准备加密文件名）
- 每一张表有如下字段：
    - `id`，主键，对应云盘中的文件名，其值不具备任何可读性
    - `filename`，原始文件名。在该字段上建立索引，以快速查询
    - `lastmodified`，日期类型，记录最后修改时间
    - `md5value`，原始文件的32个字符长度的md5值，检验文件是否被云盘供应商篡改
    - `passwd`，**明文**存储对应文件压缩包的密码——由于密码是随机生成的，人不可能记住，尽管我知道明文存储并不明智
- 由于文件密码是明文存储，数据库及其数据本身的访问需要加密
- 要经常备份数据库:exclamation::exclamation::exclamation:，因为这是打开文件的==唯一途径==
- 其他一些技巧、注意事项：
    - 使用DML操作数据库时要开启事务
    - 为了防止（对抗）云盘供应商的审查导致数据丢失，在重新上传下载的文件时应做修改，使云服务器上不存在相同的md5值。同时，压缩时应该加密文件名。
    - 出于（数据）安全性考虑，应该在不同的云盘中保留多份文件副本，防止某云盘提供商出问题


## 总结
- 这是个小项目，基本不涉及复杂的技术，但关键在于项目设计和实现流程自动化

