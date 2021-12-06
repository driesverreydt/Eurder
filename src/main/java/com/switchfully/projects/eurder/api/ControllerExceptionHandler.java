package com.switchfully.projects.eurder.api;


import com.switchfully.projects.eurder.domain.user.InvalidEmailStructureException;
import com.switchfully.projects.eurder.security.AuthorisationNotGrantedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(AuthorisationNotGrantedException.class)
    protected void authorizationNotGranted(AuthorisationNotGrantedException ex,
                                         HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(InvalidEmailStructureException.class)
    protected void invalidEmailStructure(InvalidEmailStructureException ex,
                                         HttpServletResponse response) throws IOException {
        badRequest(ex, response);
    }

    private void badRequest(Exception ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
