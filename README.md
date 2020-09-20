# mcbot

基于PicqBotX与mirai框架开发的QQ机器人

---

## 现有功能

/bangumi   开启新番更新提醒(大陆未购买版权则不会提醒)，排除你不喜欢的新番如:/bangumi rm 240835

/setu   涩图！可加关键字，r18五五开检索。如：/setu yjx4

/amq   到点了，网抑云开启 (AMQ <anti-motivational quotes>)

/startserver   开启服务端，现在支持服务端：AcademyCraft 如：/startserver AcademyCraft

/anime   以图搜番 食用方法：/anime [图片]

/list   查看在线玩家列表w

/miband   查看老王今日运动数据

/lsb   调用涩图API次数

/hitokoto   一言

/rcon   Minecraft服务端RCON命令（管理员权限）

/bind   QQ绑定Minecraft账号。私聊我，如：/bind yjx4 password

/password   修改Minecraft账户密码，私聊我，如：/password yjx4prprpr


## 隐藏功能
1. 群复读
2. OCR识别需要涩图，调用二次封装 [ocr-http-api]( https://github.com/ColorfulGhost/ocr-http-api) 
3. 开启Minecraft服务器是调用[MCS](https://github.com/Suwings/MCSManager) 接口启动Minecraft服务端
4. 涩图API地址：https://api.lolicon.app/
5. miband 获取小米手环4数据 [miband4](https://github.com/ColorfulGhost/miband4) 二次开发对外提供HTTP接口
6. bangumi订阅数据源来自：http://bgmlist.com/

## 环境
- JDK 1.8
- Redis
- Python 3.6+
- minecraft Server RCON
- MySQL
- CUDA
- easyocr
...


## 其他
 如果服务器上有显卡 显存在4G以上 显存小于2G就洗洗睡了，只能CPU运算。当然显存越大识别速度越快。 
 [ocr-http-api]( https://github.com/ColorfulGhost/ocr-http-api) 
 可以开启GPU加速，CPU实在太慢了而且OCR时候CPU占满会影响其他程序运行