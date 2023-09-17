package com.insta.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.insta.dto.ApiResponse;

@ControllerAdvice
public class GlobalException {

    /* User Exception Handler */
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> userExceptionHandler(UserException ue, WebRequest req) {
        ErrorDetails err = new ErrorDetails(ue.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    /* Post Exception Handler */
    @ExceptionHandler(PostException.class)
    public ResponseEntity<ErrorDetails> postExceptionHandler(PostException pe, WebRequest req) {
        ErrorDetails err = new ErrorDetails(pe.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    /* Comment Exception Handler */
    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ErrorDetails> commentExceptionHandler(CommentException ce, WebRequest req) {
        ErrorDetails err = new ErrorDetails(ce.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    /* Story Exception Handler */
    @ExceptionHandler(StoryException.class)
    public ResponseEntity<ErrorDetails> storyExceptionHandler(StoryException se, WebRequest req) {
        ErrorDetails err = new ErrorDetails(se.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    /* Argument Not Valid Exception Handler */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException me,
            WebRequest req) {
        ErrorDetails err = new ErrorDetails(me.getBindingResult().getFieldError().getDefaultMessage(),
                "Invalid Argument", LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    // Default Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> ExceptionHandler(Exception ue, WebRequest req) {
        ErrorDetails err = new ErrorDetails(ue.getMessage(), req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
