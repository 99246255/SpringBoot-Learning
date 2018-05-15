package com.demo.auth.web;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 测试的
 */
@RestController
@EnableResourceServer    // 必须有
public class UserController {

    @RequestMapping("/user")
    public Principal resource(Principal principal) {
        return principal;
    }

}
