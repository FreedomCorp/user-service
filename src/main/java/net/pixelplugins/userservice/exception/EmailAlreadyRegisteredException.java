package net.pixelplugins.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FOUND, reason = "Esse email já foi registrado em nosso sistema")
public class EmailAlreadyRegisteredException extends Exception {
}
