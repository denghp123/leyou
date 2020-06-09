package cn.itcast.item.mapper;

import cn.itcast.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author dhp
 * @Date 2020/6/9 11:22
 * @Version 1.0
 */
public interface BrandMapper extends Mapper<Brand> {

    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategory(@Param("cid") Long cid,@Param("bid") Long bid);
}
