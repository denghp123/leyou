package cn.itcast.item.service;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import cn.itcast.common.vo.PageResult;
import cn.itcast.item.mapper.BrandCategoryMapper;
import cn.itcast.item.mapper.BrandMapper;
import cn.itcast.pojo.Brand;
import cn.itcast.pojo.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/9 11:22
 * @Version 1.0
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;


    @Autowired
    private BrandCategoryMapper brandCategoryMapper;


    public PageResult<Brand> queryBrandForPage(boolean desc, Integer page, int rows, String sortBy, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤查询
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter",key.toUpperCase());
        }

        //排序
        if (StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");

            example.setOrderByClause(orderByClause);
        }

        List<Brand> brandsList = brandMapper.selectByExample(example);
        //强壮性判断

        if (CollectionUtils.isEmpty(brandsList)){
            throw   new LyException(ExceptionEnum.BRANDS_NOT_FOUND);
        }

        //解析分页结果

        PageInfo<Brand> brandsPageInfo = new PageInfo<>(brandsList);

        //封装返回

        return new PageResult<>(brandsPageInfo.getTotal(),brandsList);
    }


    @Transactional
    public void addBrand(Brand brand, List<Long> cids) {

            //新增品牌
            int count = brandMapper.insertSelective(brand);
            if (count != 1){
                throw new LyException(ExceptionEnum.BRANDS_INSERT_ERROR);
            }
            //关联品牌和分类的中间表
            for (Long cid : cids) {
                count =brandMapper.insertCategory(cid,brand.getId());

                if (count != 1){
                    throw new LyException(ExceptionEnum.BRANDS_INSERT_ERROR);
                }

            }
    }
    
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        //修改品牌
        int count = brandMapper.updateByPrimaryKeySelective(brand);

        if (count != 1){
            throw new LyException(ExceptionEnum.BRANDS_UPDATE_ERROR);
        }
        //中间表修改
        //根据品牌id查询商品分类id
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setBrandId(brand.getId());
        //一个品牌对应多个分类   修改之前的样子
        List<CategoryBrand> select = brandCategoryMapper.select(categoryBrand);

        //从数据库中查出来的分类
        List<Long> bcids = new ArrayList<>();
        select.forEach(a->{
            bcids.add(a.getCategoryId());
        });

        for (Long cid : cids) {
            if (!bcids.contains(cid)){
                CategoryBrand categoryBrand1 = new CategoryBrand();
                categoryBrand1.setCategoryId(cid);
                categoryBrand1.setBrandId(brand.getId());
                //新增
                int insert = brandCategoryMapper.insert(categoryBrand1);
                if (insert != 1){
                    throw new LyException(ExceptionEnum.BRANDS_INSERT_ERROR);
                }

            }

        }
        //之前添加的分类，修改之后没有了，就删除
        for (Long bcid : bcids) {
            if (!cids.contains(bcid)){
                CategoryBrand categoryBrand1 = new CategoryBrand();
                categoryBrand1.setCategoryId(bcid);
                categoryBrand1.setBrandId(brand.getId());
                int delete = brandCategoryMapper.delete(categoryBrand1);
                if (delete != 1){
                    throw new LyException(ExceptionEnum.BRANDS_INSERT_ERROR);
                }

            }
        }




    }
}
