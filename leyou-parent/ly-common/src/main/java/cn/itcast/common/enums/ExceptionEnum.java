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
    GOODS_DETAIL_NOT_FOND(404,"商品详情没有找到！"),
    GOODS_SKU_NOT_FOND(404,"商品SKU没有找到！"),
    GOODS_STOCK_NOT_FOND(404,"商品库存信息没有找到！"),
   GOODS_NOT_FOUND(404,"商品没有找到！"),
   BRANDS_NOT_FOUND(404,"品牌数据不存在！"),
   INVALID_FILE_TYPE(400,"文件类型不存在，上传失败！"),
   ADD_GOODS_ERROR(500,"商品新增失败！"),
   SKU_PARAM_ERROR(500,"SKU参数不能为空！"),
    FILE_UPLOAD_ERROR(500,"文件上传失败！"),
    BRANDS_UPDATE_ERROR(400,"品牌修改失败！"),
    BRANDS_DELETE_ERROR(400,"品牌删除失败！"),
    SPEC_GROUP_NOT_FOUND(404,"商品规格不存在！"),
    SPEC_PARAMS_NOT_FOUND(404,"商品规格参数不存在！"),
    ADD_SPEC_PARAMS(400,"商品规格参数错数，新增失败！"),
    UPDATE_SPEC_PARAMS(400,"商品规格参数错数，编辑修改失败！"),
    ADD_SPEC_GROUP(400,"商品规格分组参数有误，新增分组失败！"),
    UPDATE_SPEC_GROUP(400,"商品规格分组参数有误，编辑失败！"),
    DELETE_SPEC_GROUP(500,"服务器内部错误，删除失败！"),
    DELETE_SPEC_PARAMS(500,"服务器内部错误，删除失败！"),
   BRANDS_INSERT_ERROR(400,"品牌参数有误，新增失败！"),;



    private int status;
    private String message;

    ExceptionEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
