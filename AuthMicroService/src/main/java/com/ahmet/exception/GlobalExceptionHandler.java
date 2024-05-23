package com.ahmet.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice // -> provides centralized exception handling for RESTful APIs. ||| If we add a 'Rest' in the beginning (@RestControllerAdvice), then we don't have to use '@ResponseBody' in the below methods.
public class GlobalExceptionHandler {

    /**
     * Tüm istisnaların üzerinden geçtiği bir metot oluşturuyorum ve
     * hata mesajını burada dönüyorum. Hata mesajı yakalıyıp hata üretme
     * işini bu metot yapacak:
     */
    private ErrorMessage createErrorMessage(EErrorType errorType, Exception exception) {
        System.out.println("Hata oluştu....: " + exception.getMessage());
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

    /**
     * @ExceptionHandler -> Uygulama içinde oluşacak hatanın türünü bizden
     * alarak onun yakalanmasını sağlar, böylece yakaladığı istisnayı metoda
     * geçer(aşağıdaki metoda parametre olarak geçer).
     * @return
     */
    @ExceptionHandler(Exception.class) // -> will handle any exception that is an instance of 'java.lang.Exception' in our app. || This is a very general Exception handler. It will catch exceptions that we couldn't catch with other specific Exception handler methods that we wrote.
    @ResponseBody // -> means: the return value of the handleException() method will be directly written to the HTTP response body.
    public ResponseEntity<String> handleException(Exception exception) {
        System.out.println("Tespit edilmeyen hata oluştu....: " + exception.getMessage());
        return ResponseEntity.badRequest().body("Uygulamada beklenmeyen bir hata oluştu...: " + exception.getMessage()); // Browser'da görünen mesaj:  Uygulamada beklenmeyen bir hata oluştu...: Ürün eklemede ad bilgisi boş geldiği için hata oluştu.
    }

    @ExceptionHandler(AuthServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleSatisManagerException(AuthServiceException exception) {
        EErrorType errorType = exception.getErrorType();
        HttpStatus httpStatus = errorType.getHttpStatus();
        return new ResponseEntity<>(createErrorMessage(errorType, exception), httpStatus); // bir hata oluştuğunda Json döncek (browser'da). [ ResponseEntity<ErrorMessage> dönüyo, o da bir Json ]
    }

    /**
     * Kullandığımız hataları bu sayfaya yazıyoz
     */

    @ResponseBody
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(InvalidFormatException exception) {
        EErrorType errorType = EErrorType.INVALID_PARAMETER;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        EErrorType errorType = EErrorType.METHOD_MISMATCH_ERROR;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        EErrorType errorType = EErrorType.METHOD_MISMATCH_ERROR;
        return new ResponseEntity<>(createErrorMessage(errorType, exception), errorType.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public final ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        EErrorType errorType = EErrorType.INVALID_PARAMETER;
        List<String> fields = new ArrayList<>();
        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
        ErrorMessage errorMessage = createErrorMessage(errorType, exception);
        errorMessage.setFields(fields);
        return new ResponseEntity<>(errorMessage,errorType.getHttpStatus());
    }

}
