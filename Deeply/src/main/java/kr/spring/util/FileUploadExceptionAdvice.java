package kr.spring.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FileUploadExceptionAdvice {
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Map<String,String>> handleMaxSizeException(MaxUploadSizeExceededException exc) {
		Map<String,String> mapJson = new HashMap<String,String>();
		mapJson.put("result", "파일이 최대 업로드 크기 제한을 초과했습니다.");
        return ResponseEntity.status(500).body(mapJson);
    }
}
