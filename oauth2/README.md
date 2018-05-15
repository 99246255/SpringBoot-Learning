#获取token方式
## 1.密码获取token
POST   http://a:a@localhost:30880/oauth/token?grant_type=password&username=user&password=password
Basic Authentication 输入clientDetail的账号密码
或者
POST http://a:a@localhost:30880/oauth/token?grant_type=password&username=user&password=password
## 2.授权码获取访问token 
获取code
GET http://localhost:30880/oauth/authorize?client_id=a&response_type=code&redirect_uri=/user

POST http://a:a@localhost:30880/oauth/token?grant_type=authorization_code&code=MShMJW&redirect_uri=/user

