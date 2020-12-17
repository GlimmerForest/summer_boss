package com.william.boss.auth;

import com.william.boss.enums.ResponseCodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wang691
 */
@Component
public class TokenErrorEntryPoint implements AuthenticationEntryPoint {

	/**
	 * Always returns a 600 error code to the client.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException {
		response.sendError(ResponseCodeEnum.TOKEN_ERROR.getCode(), ResponseCodeEnum.TOKEN_ERROR.getMessage());
	}

}
