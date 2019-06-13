package com.pp.myretail.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Mongo unavailable please contact admin")
public class MongoUnavailableException extends Exception{
}
