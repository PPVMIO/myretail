package com.pp.myretail.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Request is not formatted correctly, please check your request and try again")
public class BadRequestException extends Exception {
}
