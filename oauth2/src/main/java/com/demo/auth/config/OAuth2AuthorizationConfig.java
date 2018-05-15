package com.demo.auth.config;

import com.demo.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * oauth2配置
 * 客户端的4种授权模式参考http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html
 * 数据库字段参考http://andaily.com/spring-oauth-server/db_table_description.html
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    DataSource dataSource;

    /**
     * jwt 可自定义
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(getKeyPair("123456"));
        return converter;
    }
//    @Bean
//    protected JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
//            /***
//             * 重写增强token方法,用于自定义一些token返回的信息
//             */
//            @Override
//            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//                String userName = authentication.getUserAuthentication().getName();
//                User user = (User) authentication.getUserAuthentication().getPrincipal();// 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
//                /** 自定义一些token属性 ***/
//                final Map<String, Object> additionalInformation = new HashMap<>();
//                additionalInformation.put("userName", userName);
//                additionalInformation.put("roles", user.getAuthorities());
//                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
//                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
//                return enhancedToken;
//            }
//
//        };
//        converter.setSigningKey("123");
//        return converter;
//    }


    public static KeyPair getKeyPair(String password){
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(password == null){
            password = "123456";
        }
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        return keyPairGenerator.genKeyPair();
    }
    /**
     * 配置 oauth_client_details【client_id和client_secret等】信息的认证【检查ClientDetails的合法性】服务
     * 设置 认证信息的来源：数据库 (可选项：数据库和内存,使用内存一般用来作测试)
     * 自动注入：ClientDetailsService的实现类 JdbcClientDetailsService (检查 ClientDetails 对象)
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
//        clients.inMemory().withClient("acme").secret("acmesecret")
//                .authorizedGrantTypes("authorization_code", "refresh_token",
//                        "password")
//                .autoApprove(true)
//                .scopes("openid");
    }


    /**
     * token 存储
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        TokenStore tokenStore = new JwtTokenStore(jwtAccessTokenConverter());
        return tokenStore;
    }
//    /**
//     * 可拓展jdbcClientDetailsService实现
//     */
//    @Bean
//    public JdbcClientDetailsService jdbcClientDetailsService(){
//        return new JdbcClientDetailsService(dataSource);
//    }
    /**
     * oauth_code mysql存储
     * grant_type为"authorization_code"时,该表中才会有数据产生
     * @return
     */
    @Bean
    public JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }
    /**
     * 密码模式下配置认证管理器 AuthenticationManager,并且设置 AccessToken的存储介质tokenStore,如果不设置，则会默认使用内存当做存储介质。
     * 而该AuthenticationManager将会注入 2个Bean对象用以检查(认证)
     * 1、ClientDetailsService的实现类 JdbcClientDetailsService (检查 ClientDetails 对象)
     * 2、UserDetailsService的实现类 CustomUserDetailsService (检查 UserDetails 对象)
     *
     *
     * 此处由于是使用jwt,不需要配置token的存储，以后考虑用redis，参考张军的实现
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
//        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore).userDetailsService(userDetailsService);
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(tokenStore())
                .authorizationCodeServices(jdbcAuthorizationCodeServices())
                .userDetailsService(userDetailsService);
//        endpoints.setClientDetailsService(jdbcClientDetailsService());
    }

    /**
     * 加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     *  配置：安全检查流程
     *  默认过滤器：BasicAuthenticationFilter
     *  1、oauth_client_details表中clientSecret字段加密【ClientDetails属性secret】
     *  2、CheckEndpoint类的接口 oauth/check_token 无需经过过滤器过滤，默认值：denyAll()
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
        security.allowFormAuthenticationForClients();//允许客户表单认证
        security.passwordEncoder(passwordEncoder());//设置oauth_client_details中的密码编码器
        security.checkTokenAccess("permitAll()");//对于CheckEndpoint控制器[框架自带的校验]的/oauth/check端点允许所有客户端发送器请求而不会被Spring-security拦截
    }

//    public static void main(String[] args) {
//        String a = new BCryptPasswordEncoder().encode("a");
//        System.out.println(a);
//        KeyPair keyPair = getKeyPair("123456");
//        System.out.println("-----BEGIN PUBLIC KEY-----\n" + new String(Base64.encode(keyPair.getPublic().getEncoded())) + "\n-----END PUBLIC KEY-----");
//    }
}