package ua.nure.tanasiuk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.nure.tanasiuk.response.OperationResponse;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.nure.tanasiuk.commons.Constants.ErrorCodes.UNAUTHORIZED_ERROR_CODE;
import static org.apache.commons.lang.StringUtils.EMPTY;

public class Http401AuthenticationEntryPointImpl extends Http401AuthenticationEntryPoint {
    private final ObjectMapper mapper;

    public Http401AuthenticationEntryPointImpl(ObjectMapper mapper) {
        super(EMPTY);
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException e) throws IOException, ServletException {
        OperationResponse failure = OperationResponse.failure(UNAUTHORIZED_ERROR_CODE);
        response.getWriter().write(mapper.writeValueAsString(failure.getBody()));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().flush();
    }
}
