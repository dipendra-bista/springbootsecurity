package com.punojsoft.springbootsecurity.bootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/logout").permitAll();
        http.formLogin();
        /**
         * Session management related code
         */
        //allowing anly two session
        http.sessionManagement().maximumSessions(2);
        /**
         * session creation control
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        /**
         * session fixation attack
         */
        http.sessionManagement().sessionFixation().migrateSession();
        /**
         * prevent url from sending session information
         */
        http.sessionManagement().enableSessionUrlRewriting(false);
        /**
         * setting up session invalid and expired url
         */
        http.sessionManagement().invalidSessionUrl("/invalid-session")
                .maximumSessions(2).expiredUrl("/expired-session");
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
