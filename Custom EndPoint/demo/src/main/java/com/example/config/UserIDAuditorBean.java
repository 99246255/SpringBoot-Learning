package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 *  注解@CreatedBy和@LastModifiedBy的赋值实现
 */
@Configuration
public class UserIDAuditorBean implements AuditorAware<Long> {
    @Override
    public Long getCurrentAuditor() {
        return 1L;
//        SecurityContext ctx = SecurityContextHolder.getContext();
//        if (ctx == null) {
//            return null;
//        }
//        if (ctx.getAuthentication() == null) {
//            return null;
//        }
//        if (ctx.getAuthentication().getPrincipal() == null) {
//            return null;
//        }
//        Object principal = ctx.getAuthentication().getPrincipal();
//        if (principal.getClass().isAssignableFrom(Long.class)) {
//            return (Long) principal;
//        } else {
//            return null;
//        }
    }
}
