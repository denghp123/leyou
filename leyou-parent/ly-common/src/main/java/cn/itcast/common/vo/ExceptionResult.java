package cn.itcast.common.vo;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * @Author dhp
 * @Date 2020/6/4 1:07
 * @Version 1.0
 */

@Data
public class ExceptionResult {

    private int status;
    private String message;
    private String timestemp;

    public ExceptionResult(LyException em) {
        this.status = em.getStatus();
        this.message = em.getMessage();
        this.timestemp = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }
}
