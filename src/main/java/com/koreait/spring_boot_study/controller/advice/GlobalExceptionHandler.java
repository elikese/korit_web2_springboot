package com.koreait.spring_boot_study.controller.advice;

import com.koreait.spring_boot_study.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 예외는 catch되지 않으면 계속 전파(호출한 쪽으로 돌아간다)됩니다.
// 컨트롤러까지 전파되었지만 catch가 없었음 -> dispather servlet에 catch가 존재
// catch 하면,
// 1. RestControllerAdvice어노테이션을 가진 클래스를 찾음(-> 핸들러를 찾음)
// 2. 전파되어 온 예외의 클래스를 처리할 수 있는 컨트롤러를 찾는다.
// 3. 찾으면 해당 컨트롤러를 실행!
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 게시글을 찾을 수 없음(404)
    // 조회불가(404)
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> handlePostNotFound(
            PostNotFoundException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFound(
            ProductNotFoundException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(ProductInsertException.class)
    public ResponseEntity<?> handleProductError(
            ProductInsertException e
    ) {
        // 권한이 없었다 -> 403
        // 필드가 누락 -> 400
        // 유니크 위반 -> 409
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(PostInsertException.class)
    public ResponseEntity<?> handlePostError(
            PostInsertException e
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }


    // validation 예외처리 핸들러(추가, 수정) - 400
    // validation에 실패하면 MethodArgumentNotValidException을 던지게 됨
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationHandler(
            MethodArgumentNotValidException e
    ) {
        // 해당 Dto에 Validation 어노테이션이 붙은 필드를 모두 검사
        // 중요) 여러개중에 한가지 필드만 에러에 추가되는게 아님
        // ErrorMap을 리턴할건데, 필드가 여러개니까 Map이 여러개
        // 리턴값이 Map이 여러개 들어간 List리턴
        List<Map<String, String>> errorResp = null;
        
        // dto로 변환할때 발생한 모든 에러메세지를 담은 객체
        BindingResult bindingResult = e.getBindingResult();

        if (bindingResult.hasErrors()) {
            errorResp = bindingResult.getFieldErrors() // 필드에러들을 List로 리턴
                    .stream() // [객체1, 객체2....]
                    .map(fieldError -> Map.of(
                            fieldError.getField(), fieldError.getDefaultMessage()
                    )) // [Map1, Map2....] -> List<Map<String,Stirng>>
                    .collect(Collectors.toList());
        }
        /*
            [
                {
                    "name" : "이름은 비울 수 없습니다."
                },
                {
                    "price" : "가격은 음수일 수 없습니다."
                }
            ]
        */
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResp);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<?> handleRefreshTokenException(
            RefreshTokenException e
    ) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }

}
