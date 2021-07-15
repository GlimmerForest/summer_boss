package com.william.boss.config;

import com.william.boss.auth.TokenErrorEntryPoint;
import com.william.boss.filter.JwtAuthenticationTokenFilter;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
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
    private TokenErrorEntryPoint tokenErrorEntryPoint;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource(name = "bossUserDetailsService")
    private UserDetailsService userDetailsService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        //使用自己的前置拦截器(如果前置拦截器把认证和鉴权的事做了,感觉就没必要用SpringSecurity了,所以这里只做认证,把鉴权交给SpringSecurity)
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            // 定制我们自己的 session 策略：调整为让 Spring Security 不创建和使用 session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // 默认登录页面'/login' 默认用户名/密码字段 username/password
            .and().formLogin()
            // 请求进行拦截 验证 accessToken
            .and().authorizeRequests()
            //登录接口/页面、swagger可任意访问
            .antMatchers("/boss/login/**","/doc.html").permitAll()
            // 功能页面需要鉴权
            .antMatchers("/boss/**").authenticated()
            // 测试接口可任意访问
            .antMatchers("/test/**").permitAll()
            ///其他请求都可以访问,包括swagger js css等资源文件
            .anyRequest().permitAll()
            .and().exceptionHandling().authenticationEntryPoint(tokenErrorEntryPoint)
            .and().exceptionHandling().accessDeniedHandler(accessDeniedHandlerImpl())
            //解决跨域
            .and().cors()
            // 关闭csrf防护
            .and().csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandlerImpl() {
        return new AccessDeniedHandlerImpl();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
