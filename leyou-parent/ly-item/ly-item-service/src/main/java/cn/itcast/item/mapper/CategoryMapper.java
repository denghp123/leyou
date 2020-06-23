package cn.itcast.item.mapper;

import cn.itcast.common.mapper.BaseMapper;
import cn.itcast.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/5 10:09
 * @Version 1.0
 */
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("SELECT t.id,t.`name`,t.parent_id,t.is_parent,t.sort from tb_category t,tb_category_brand b where t.id = b.category_id  and brand_id =#{pid}")
    List<Category> selectCategoryByPid(@Param("pid") Long pid);
}
