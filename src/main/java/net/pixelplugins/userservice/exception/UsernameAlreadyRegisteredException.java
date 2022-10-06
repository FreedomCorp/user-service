package net.pixelplugins.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FOUND, reason = "Esse nome de usuário já foi registrado em nosso sistema")
public class UsernameAlreadyRegisteredException extends Exception {

}
