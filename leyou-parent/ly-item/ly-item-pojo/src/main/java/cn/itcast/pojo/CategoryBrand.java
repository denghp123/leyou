package cn.itcast.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * @Author dhp
 * @Date 2020/6/9 17:59
 * @Version 1.0
 */
@Data
@Table(name = "tb_category_brand")
public class CategoryBrand {
    private Long categoryId;
    private Long brandId;
}
