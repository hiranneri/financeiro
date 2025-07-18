package br.com.mesadigital.financeiro.controller.exceptions;

import br.com.mesadigital.financeiro.utils.DataUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por realizar o tratamento global de exceções lançadas nos controllers
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DetalhesErroException> handleBadRequestException(BadRequestException badRequest) {
        return new ResponseEntity<DetalhesErroException>(
                new DetalhesErroException(
                        "Bad Request Exception",
                        DataUtils.toPTBR(LocalDateTime.now()),
                        HttpStatus.BAD_REQUEST.value(),
                        badRequest.getMessage()
                ), HttpStatus.BAD_REQUEST
        );
    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                                         HttpStatusCode statusCode, WebRequest request) {
        String mensagemErroEnvio = "Erro no envio dos dados";

        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;


        List<FieldError> camposComErro = exception.getBindingResult().getFieldErrors();
        String campos = camposComErro.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String mensagensCampo = camposComErro.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        DetalhesValidacaoException detalhesValidacaoException = new DetalhesValidacaoException(
                mensagemErroEnvio,
                DataUtils.toPTBR(LocalDateTime.now()),
                HttpStatus.BAD_REQUEST.value(),
                mensagensCampo,
                campos
        );

        return createResponseEntity(detalhesValidacaoException, headers, statusCode, request);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return new ResponseEntity<Object>(
                new DetalhesErroException(
                        "Bad Request Exception",
                        DataUtils.toPTBR(LocalDateTime.now()),
                        HttpStatus.BAD_REQUEST.value(),
                        "Não foi possível ler os dados enviados"
                ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DetalhesErroException> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException) {

        return new ResponseEntity<DetalhesErroException>(
                new DetalhesErroException(
                        "Bad Request",
                        DataUtils.toPTBR(LocalDateTime.now()),
                        HttpStatus.BAD_REQUEST.value(),
                        "Já existe um registro com os dados enviados"
                ),HttpStatus.BAD_REQUEST
        );
    }


}
