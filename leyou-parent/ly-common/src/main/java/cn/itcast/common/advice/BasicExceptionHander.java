package cn.itcast.common.advice;

import cn.itcast.common.exceptions.LyException;
import cn.itcast.common.vo.ExceptionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author dhp
 * @Date 2020/6/3 16:00
 * @Version 1.0
 */

@ControllerAdvice   //springmvc自定义拦截所有的controller异常，但是不对业务造成任何形式拦截
@Slf4j
public class BasicExceptionHander {

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleException(RuntimeException e) {
//        // 我们暂定返回状态码为400， 然后从异常中获取友好提示信息
//        return ResponseEntity.status(400).body(e.getMessage());
//
//    }

    /**
     * 自定义异常的处理
     * @param e
     * @return
     */
    @ExceptionHandler(LyException.class)
//    public ResponseEntity<String> handleException(LyException e) {
    public ResponseEntity<ExceptionResult> handleException(LyException e) {
        //构建返回对象
        ExceptionResult exceptionResult = new ExceptionResult(e);
//        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(exceptionResult);
    }
}
