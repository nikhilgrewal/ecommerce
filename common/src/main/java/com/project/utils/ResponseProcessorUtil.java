package com.project.utils;

import com.project.response.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.project.response.Response.*;

@Log4j2
public class ResponseProcessorUtil {

    public static <T> ResponseEntity<?> processResult(Map<Response,Object> response) {
        if (response.containsKey(ERROR)) {
            HttpStatus httpStatus = (HttpStatus) response.get(ERROR_CODE);
            return ResponseEntity.status(httpStatus.value()).body(response.get(ERROR));
        }
        if (response.containsKey(DATA)) {
            return ResponseEntity.ok(response.get(DATA));
        }
        return (ResponseEntity<?>) ResponseEntity.ok();
    }
}
