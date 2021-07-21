package com.william.boss.filter;

import com.william.boss.auth.JwtUtil;
import com.william.boss.constant.CommonConstants;
import com.william.boss.enums.ResponseCodeEnum;
import com.william.boss.exception.BusinessException;
import com.william.boss.properties.SelfDefineProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author john
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource(name = "bossUserDetailsService")
    private UserDetailsService userDetailsService;

    @Resource
    private SelfDefineProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 不校验静态资源和swagger接口
        for (String ignore: properties.getIgnoreSources()) {
            if (request.getRequestURI().contains(ignore)){
                chain.doFilter(request, response);
                return;
            }
        }

        String token = request.getHeader(properties.getAuthConfig().getApiTokenHeader());
        // 测试token不校验
        if (CommonConstants.TOKEN.equals(token)) {
            chain.doFilter(request, response);
            return;
        }


        ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
        // 只认证,不鉴权
        if (!StringUtils.isEmpty(token)) {
            JwtUtil.getInfoFromToken(token);
            chain.doFilter(request, wrapperResponse);
            // 把响应体写回到输出流
            wrapperResponse.copyBodyToResponse();
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                // 用户身份认证
//                if (userDetails != null) {
//                    // 用户权限校验
//                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//                    for (GrantedAuthority authority:authorities) {
//                        if (authority instanceof SimpleGrantedAuthority) {
//                            String role = authority.getAuthority();
//                            // 跟缓存里面的权限数据比较, 有权限放行
//                            boolean hasAuthority = true;
//                            if (hasAuthority) {
//                                chain.doFilter(request, response);
//                            } else {
//                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//                            }
//                        }
//                    }
//
//                    // 将用户信息存入 authentication,方便后续校验
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
        } else {
            // 测试先放过token
            chain.doFilter(request, response);
            wrapperResponse.copyBodyToResponse();
//            throw new BusinessException(ResponseCodeEnum.TOKEN_ERROR);
        }
    }



}
