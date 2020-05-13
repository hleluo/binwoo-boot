#### Client信息验证
```
Client账号密码验证类：com.binwoo.oauth.client.ClientFilter 
拦截器注册：com.binwoo.oauth.config.ClientConfig 
Client账号有效应验证：com.binwoo.oauth.detail.ClientDetailsServiceImpl
Client信息配置见数据库实体Client，如token过期时间等
```
#### User信息验证
```
用户有效应验证：com.binwoo.oauth.detail.UserDetailsServiceAdapter
```
### 多登录方式扩展
```
拦截器：com.binwoo.oauth.security.AuthTokenEndpointFilter
方式定义：com.binwoo.oauth.integrate.AuthTokenParam
验证实现：com.binwoo.oauth.integrate.AuthTokenPasswordIntegrator
1、首先定义验证方式 
如：public static final String AUTH_TYPE_PASSWORD = "password"
2、实现用户信息查询
如：com.binwoo.oauth.integrate.AuthTokenPasswordIntegrator
```
### 异常处理
```
异常枚举类：com.binwoo.oauth.exception.HttpAuthExceptionCode
权限异常：com.binwoo.oauth.security.AuthExceptionTranslator
访问异常：com.binwoo.oauth.security.com.binwoo.oauth.security
Token异常：com.binwoo.oauth.security.ResourceTokenEntryPoint
全局异常处理：com.binwoo.oauth.exception.GlobalExceptionHandler
```
### 返回定义
```
Jackson配置：com.binwoo.oauth.config.JacksonConfig
```
### 接口文档Swagger
```
Swagger配置：com.binwoo.oauth.config.SwaggerConfig
配置项：
swagger.enable=true
```
### OAuth配置
```
JWT秘钥：oauth.jwt.signing-key=4I*#UlD1
资源ID：oauth.resource.id=res-oauth-DmAzd
排除验证的URL：oauth.resource.exclude=/swagger-ui.html,/swagger-resources/**,/images/**,/webjars/**,/v2/api-docs
```
### 账号密码
```
初始账号和密码：root / U9yK%K
初始化client_id：client，client_secret：9Jv$P1
```
### 登录认证
```
用户认证：/oauth/token，参数如下：
grant_type:password
client_id:aaa
client_secret:aaa
username:root
password:U9yK%K
auth_type:password #登录认证方式，可选参数，默认为用户密码认证方式，如需扩展，参考AuthTokenPasswordIntegrator
app_code:OA         #应用代码，可选参数，如OA、ACCIDENT等不同系统
app_type:WEB        #应用类型，可选参数，如WEB、ANDROID等不同运行平台

参数app_code和app_type用于控制不同系统、不同平台的角色、菜单和权限，如OA的WEB平台和IOS端
```
### 刷新Token
```
刷新Token，/oauth/token，参数如下：
grant_type:refresh_token
client_id:aaa
client_secret:aaa
refresh_token:${refresh_token}
```
### client认证
```
client_credentials认证，/oauth/token，参数如下：
grant_type:client_credentials
client_id:aaa
client_secret:aaa
app_code:OA         #应用代码，可选参数，如OA、ACCIDENT等不同系统
app_type:WEB        #应用类型，可选参数，如WEB、ANDROID等不同运行平台
```
### Token校验
```
校验Token，/oauth/check_token，参数如下：
token:${token}

AuthorizationServerConfig的checkTokenAccess设置为permitAll()；
```
### 授权认证
```
获取code，/oauth/authorize，参数如下：
response_type:code
client_id:aaa
redirect_uri:http://baidu.com
scope:write

http://localhost:9979/oauth/authorize?response_type=code&client_id=aaa&scope=write&redirect_uri=http://baidu.com

认证code，/oauth/token，参数如下：
grant_type:authorization_code
code:BFG4NF
client_id:aaa
client_secret:aaa
redirect_uri:http://baidu.com

http://localhost:9979/oauth/token?grant_type=authorization_code&code=BFG4NF&client_id=aaa&client_secret=aaa&redirect_uri=http://baidu.com
```
### 其他
```
用户和客户端数据加载见包com.binwoo.oauth.detail下的先关类； 
Token额外信息扩展见com.binwoo.oauth.token.AuthTokenEnhancer； 
AuthorizationServer异常见：AuthExceptionTranslator和com.binwoo.oauth.exception下的类； 
ResourceServer异常见：ResourceTokenEntryPoint和ResourceAccessDeniedHandler； 
数据返回实体为HttpResponse<T>，消息可通过HttpCodeManager控制HttpExceptionCode和Msg的对应关系，再通过HttpResponseBuilder构建返回实体； 
```