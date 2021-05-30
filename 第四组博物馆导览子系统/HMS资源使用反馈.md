# HMS资源使用反馈

### 一.集成地图服务

#### 1.配置AppGallery Connect

1.注册成为开发者后添加项目应用，后生成签名证书指纹。

2.获取指纹后在项目设置中配置SHA256证书指纹。

![企业微信截图_16177081838073](https://i.loli.net/2021/05/30/QbqFjEKomUiwA2v.png)

#### 2.集成HMS Core SDK

1.拷贝“agconnect-services.json”文件到应用级根目录下。

![image-20210529185421103](https://i.loli.net/2021/05/29/RdKQiBfuArWYLX6.png)

2.在Android Studio项目级"build.gradle"文件中添加HUAWEI agcp插件以及Maven代码库

![image-20210529185723254](https://i.loli.net/2021/05/29/qu5ByfdaWvm6r9D.png)

3.在应用级"build.gradle"添加依赖，apply plugin添加配置，android中配置签名

![image-20210529190014129](https://i.loli.net/2021/05/29/XaMIEK9wYRTFBVz.png)

![image-20210529190047727](https://i.loli.net/2021/05/29/npyrf9Oj2XIJtKN.png)

4.配置AndroidManifest.xml文件

![image-20210529190147251](https://i.loli.net/2021/05/29/u8zbhHFnjUxRsdr.png)

#### 3.配置混淆脚本

1.打开应用级根目录下的混淆配置文件“proguard-rules.pro”，需要加入排除HMS Core SDK的混淆配置。

![image-20210529190318421](https://i.loli.net/2021/05/29/jDJE3p6qIfoL1xS.png)

### 4.添加权限

1.在“AndroidManifest”中添加权限

![image-20210529190439403](https://i.loli.net/2021/05/29/E86w3FiMeYvP4tJ.png)

配置完成后即可试用地图服务功能

![image-20210529192245990](https://i.loli.net/2021/05/29/hvLw9KVpWCE4mJS.png)

### 二.集成定位服务

#### 配置AppGallery Connect，集成HMS Core，配置混淆脚本与地图集成服务类似

调用

- [requestLocationUpdates](https://developer.huawei.com/consumer/cn/doc/development/HMSCore-References-V5/fusedlocationproviderclient-0000001050746169-V5#ZH-CN_TOPIC_0000001050746169__section1210118391289)()

- [getLastLocation](https://developer.huawei.com/consumer/cn/doc/development/HMSCore-References-V5/fusedlocationproviderclient-0000001050746169-V5#ZH-CN_TOPIC_0000001050746169__section1167913136559)()

  即可返回当前位置经纬度信息并且实时更新位置信息。

![locationgetlast](https://i.loli.net/2021/05/29/Q67n28TbeaVGLks.jpg)