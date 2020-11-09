package cn.itcast.item.mapper;

import cn.itcast.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/9 11:22
 * @Version 1.0
 */
public interface BrandMapper extends Mapper<Brand> {

    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategory(@Param("cid") Long cid,@Param("bid") Long bid);

    @Update("UPDATE tb_category_brand SET  category_id = '#{cid}' WHERE brand_id = '#{bid}'")
    int updateBrandCategory(@Param("cid") Long cid,@Param("bid") Long bid);


    @Select("select t.* from tb_brand t LEFT JOIN tb_category_brand tb on tb.brand_id = t.id where tb.category_id = #{cid}")
    List<Brand> queryBrandByCid(@Param("cid") Long cid);


}
