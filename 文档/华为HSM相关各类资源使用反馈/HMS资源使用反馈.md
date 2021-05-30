# HMS资源使用反馈

### 一.集成地图服务

#### 1.配置AppGallery Connect

1.注册成为开发者后添加项目应用，后生成签名证书指纹。

2.获取指纹后在项目设置中配置SHA256证书指纹。

- ![avatar](http://bucttalk.online/fourth_group_image/25.png))

#### 2.集成HMS Core SDK

1.拷贝“agconnect-services.json”文件到应用级根目录下。

- ![avatar](http://bucttalk.online/fourth_group_image/26.png)

2.在Android Studio项目级"build.gradle"文件中添加HUAWEI agcp插件以及Maven代码库

![avatar](http://bucttalk.online/fourth_group_image/27.png)

3.在应用级"build.gradle"添加依赖，apply plugin添加配置，android中配置签名

![avatar](http://bucttalk.online/fourth_group_image/28.png)

![avatar](http://bucttalk.online/fourth_group_image/29.png)

4.配置AndroidManifest.xml文件

![avatar](http://bucttalk.online/fourth_group_image/30.png)

#### 3.配置混淆脚本

1.打开应用级根目录下的混淆配置文件“proguard-rules.pro”，需要加入排除HMS Core SDK的混淆配置。

![avatar](http://bucttalk.online/fourth_group_image/31.png)

### 4.添加权限

1.在“AndroidManifest”中添加权限

![avatar](http://bucttalk.online/fourth_group_image/32.png)

配置完成后即可试用地图服务功能

![avatar](http://bucttalk.online/fourth_group_image/33.png)

### 二.集成定位服务

#### 配置AppGallery Connect，集成HMS Core，配置混淆脚本与地图集成服务类似

调用

- [requestLocationUpdates](https://developer.huawei.com/consumer/cn/doc/development/HMSCore-References-V5/fusedlocationproviderclient-0000001050746169-V5#ZH-CN_TOPIC_0000001050746169__section1210118391289)()

- [getLastLocation](https://developer.huawei.com/consumer/cn/doc/development/HMSCore-References-V5/fusedlocationproviderclient-0000001050746169-V5#ZH-CN_TOPIC_0000001050746169__section1167913136559)()

  即可返回当前位置经纬度信息并且实时更新位置信息。

![avatar](http://bucttalk.online/fourth_group_image/34.png)