package com.miage.altea.tp.battle_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Fight problem")
public class ExceptionNotFound extends Exception {
}

