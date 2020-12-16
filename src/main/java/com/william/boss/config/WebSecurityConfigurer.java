package com.william.boss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author john
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        //使用自己的前置拦截器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 定制我们自己的 session 策略：调整为让 Spring Security 不创建和使用 session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 请求进行拦截 验证 accessToken
        http.authorizeRequests()
                //指定需要拦截的uri
                .antMatchers("/api-user/web/**").authenticated()
                ///其他请求都可以访问
                .anyRequest().permitAll()
                .and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//身份访问异常
                .accessDeniedHandler(restAccessDeniedHandler)//权限访问异常
                //解决跨域
                .and()
                .cors()
                // 关闭csrf防护
                .and()
                .csrf()
                .disable();
    }

    @Bean
    protected UserDetailsService customUserService() {//注册UserDetailsService的bean
        return new CustomUserService();
    }

    //SpringBoot2.x后需要使用BCrypt强哈希方法来加密密码，如果不加的话登录不上并且控制台会有警告Encoded password does not look like BCrypt
    @Bean
    public BCryptPasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(PasswordEncoder());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
