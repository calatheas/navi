package com.calathea.navi.exception;

import com.calathea.navi.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * ConstraintViolationException 발생시 응답 처리
     * 1. 처리 방법
     *     - ConstraintViolationException.getMessage() 에 여러 필드의 위반 사항이 들어와서 사용자에게 위반사항을 한번에 전달
     * 2. 발생 요건
     *     - org.springframework.validation.annotation.Validated
     *     - org.springframework.web.bind.annotation.PathVariable
     *     - org.springframework.web.bind.annotation.RequestParam
     *     - javax.validation.constraints.*
     * 3. 발생되지 않는 경우
     *     - PathVariable 이 필수인데 빈 문자열이거나 공백으로 들어오는 경우 -> 없는 것으로 판단하고 url 매핑이 안됨
     *     - RequestParam 이 필수인데 없는 경우(null 인 경우) -> MissingServletRequestParameterException 발생
     * 4. 개발 가이드
     *     - PathVariable, RequestParam 은 모두 선택입력으로 변경하고 ConstraintViolationException 에서 처리될 수 있게 validation 로직을 구현하는 게 바람직함
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException constraintViolationException) {
        return ResponseEntity.badRequest().body(CommonResponse.onBadRequest(constraintViolationException.getMessage()));
    }

    /**
     * MissingServletRequestParameterException 발생시 응답 처리
     *     - RequestParam 필수항목이 없는 경우, 1개씩 발생함
     *     - 필수를 해제하고 ConstraintViolationException 으로 처리하는게 바람직함
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameterException(final MissingServletRequestParameterException missingServletRequestParameterException) {
        return ResponseEntity.badRequest().body(CommonResponse.onBadRequest(missingServletRequestParameterException.getMessage()));
    }

    /**
     * MethodArgumentNotValidException 발생시 응답 처리
     * 1. 처리 방법
     *     - MethodArgumentNotValidException 에 여러 필드의 위반 사항이 들어와서 사용자에게 위반사항을 한번에 전달
     *     - MethodArgumentNotValidException.BindingResult.FieldErrors
     * 2. 발생 요건
     *     - javax.validation.Valid
     *     - org.springframework.web.bind.annotation.RequestBody
     *     - javax.validation.constraints.*
     * 3. 발생되지 않는 경우
     *     - request body 전체가 안들어 오는 경우 HttpMessageNotReadableException 발생 (빈 json string 이라도 들어오면 MethodArgumentNotValidException 발생)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(final MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrorList = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        for (final FieldError element : fieldErrorList) {
            log.error(element.getField()+":"+element.getCode());
        }

        return ResponseEntity.badRequest().body(CommonResponse.onBadRequest(fieldErrorList.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))));
    }
}
