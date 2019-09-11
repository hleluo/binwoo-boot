1、初始账号和密码：root / U9yK%K
初始化client_id：client，client_secret：9Jv$P1

2、用户认证：/oauth/token，参数如下：
grant_type:password
client_id:aaa
client_secret:aaa
username:root
password:U9yK%K
grant_type:password #登录认证方式，可选参数，默认为用户密码认证方式，如需扩展，参考AuthTokenPasswordIntegrator
domain:OA           #系统类型，可选参数，如OA、ACCIDENT等不同系统
platform:WEB        #平台类型，可选参数，如WEB、ANDROID等不同运行平台

参数domain和platform用于控制不同系统、不同平台的角色、菜单和权限

3、刷新Token，/oauth/token，参数如下：
grant_type:refresh_token
client_id:aaa
client_secret:aaa
refresh_token:${refresh_token}

4、client_credentials认证，/oauth/token，参数如下：
grant_type:client_credentials
client_id:aaa
client_secret:aaa

5、校验Token，/oauth/check_token，参数如下：
token:${token}

AuthorizationServerConfig的checkTokenAccess设置为permitAll()；

6、获取code，/oauth/authorize，参数如下：
response_type:code
client_id:aaa
redirect_uri:http://baidu.com
scope:write

http://localhost:9979/oauth/authorize?response_type=code&client_id=aaa&scope=write&redirect_uri=http://baidu.com

7、认证code，/oauth/token，参数如下：
grant_type:authorization_code
code:BFG4NF
client_id:aaa
client_secret:aaa
redirect_uri:http://baidu.com

http://localhost:9979/oauth/token?grant_type=authorization_code&code=BFG4NF&client_id=aaa&client_secret=aaa&redirect_uri=http://baidu.com

8、用户和客户端数据加载见包com.binwoo.oauth.detail下的先关类；
9、Token额外信息扩展见com.binwoo.oauth.token.AuthTokenEnhancer；
10、AuthorizationServer异常见：AuthExceptionTranslator和com.binwoo.oauth.exception下的类；
11、ResourceServer异常见：ResourceTokenEntryPoint和ResourceAccessDeniedHandler；
12、数据返回实体为HttpResponse<T>，消息可通过HttpCodeManager控制HttpExceptionCode和Msg的对应关系，再通过HttpResponseBuilder构建返回实体；