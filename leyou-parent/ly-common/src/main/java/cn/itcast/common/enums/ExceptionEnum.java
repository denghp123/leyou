package cn.itcast.common.enums;

import lombok.Getter;

/**
 * @Author dhp
 * @Date 2020/6/4 0:42
 * @Version 1.0
 */

@Getter
public enum  ExceptionEnum {
//    枚举项 必须位于类的最前面 最后一个枚举项之后如果有内容 必须加上‘；’
   PRICE_CANNOT_BE_NULL(400,"价格不能为空！"),
   CATEGORY_NOT_FOUND(404,"商品分类没有找到！"),
   BRANDS_NOT_FOUND(404,"品牌数据不存在！");


    private int status;
    private String message;

    ExceptionEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
