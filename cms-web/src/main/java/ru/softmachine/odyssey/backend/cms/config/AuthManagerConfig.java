package com.aspnt.mddl.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import com.aspnt.mddl.repository.EntityDefRepository;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

@Configuration
public class AuthManagerConfig {

    @Bean
    public AuthorizationManager<MethodInvocation> authorizationManager(EntityDefRepository entityDefRepository) {
        return new CustomAuthorizationManager<>(entityDefRepository);
    }

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public Advisor authorizationManagerBeforeMethodInterception(AuthorizationManager<MethodInvocation> authorizationManager) {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern("com.aspnt.mddl.controller.*");
        return new AuthorizationManagerBeforeMethodInterceptor(pointcut, authorizationManager);
    }
}
