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
   BRANDS_NOT_FOUND(404,"品牌数据不存在！"),
   INVALID_FILE_TYPE(400,"文件类型不存在，上传失败！"),
    FILE_UPLOAD_ERROR(400,"文件上传失败！"),
    BRANDS_UPDATE_ERROR(400,"品牌修改失败！"),
   BRANDS_INSERT_ERROR(400,"品牌参数有误，新增失败！"),;


    private int status;
    private String message;

    ExceptionEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
