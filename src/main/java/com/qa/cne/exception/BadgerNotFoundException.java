package com.qa.cne.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Badger not found")
public class BadgerNotFoundException extends RuntimeException {

}
