package cn.itcast.common.exceptions;

import cn.itcast.common.enums.ExceptionEnum;
import lombok.Getter;

/**
 * @Author dhp
 * @Date 2020/6/4 0:34
 * @Version 1.0  异常处理类
 */

@Getter
public class LyException extends RuntimeException {
    private int status;

//    public LyException(int status,String message) {
//        super(message);
//        this.status = status;
//    }


    public LyException(ExceptionEnum em) {
        super(em.getMessage());
        this.status = em.getStatus();
    }

//    public LyException(int status,String message, Throwable cause) {
//        super(message, cause);
//        this.status = status;
//    }

    public LyException(ExceptionEnum em, Throwable cause) {
        super(em.getMessage(), cause);
        this.status = em.getStatus();
    }


}
