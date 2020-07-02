package cn.itcast.common.vo;

import lombok.Data;

import java.util.Objects;

/**
 * @Author dhp
 * @Date 2020/7/2 10:43
 * @Version 1.0
 */
@Data
public class BaseResult {
    private Integer code;
    private String msg;
    private Objects data;
}
