# java keytool证书工具生成证书
 按win键+R，弹出运行窗口，输入 cmd 回车，打开命令行窗户，输入如下命令：
 keytool -genkey -alias keystore -keyalg RSA -keysize 1024 -dname "CN=chenyu, OU=dazd,O=dazd, L=hangzhou, ST=zhejiang, C=CN" -keypass 888888 -validity 365 -keystore keystore.jks -storepass 123456

说明：
详情参考 https://my.oschina.net/frankies/blog/344914

   -keypass  123456              这个证书的私钥密码为123456 
   -keystore prospectlib         证书库的名称为prospectlib 
   -storepass 123456             证书库的访问密码为123456 
   dname的值详解： 
   　　CN(Common Name名字与姓氏) 
   　　OU(Organization Unit组织单位名称) 
   　　O(Organization组织名称) 
   　　L(Locality城市或区域名称) 
   　　ST(State州或省份名称) 
   　　C(Country国家名称） 
 
 