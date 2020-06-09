package cn.itcast.item.service;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import cn.itcast.common.vo.PageResult;
import cn.itcast.item.mapper.BrandMapper;
import cn.itcast.pojo.Brands;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

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


    public PageResult<Brands> queryBrandForPage(boolean desc, Integer page, int rows, String sortBy, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤查询
        Example example = new Example(Brands.class);
        if (StringUtils.isNotBlank(key)){
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo(key,key.toUpperCase());
        }

        //排序
        if (StringUtils.isNotBlank(sortBy)){
            String orderByClause = desc ? "desc" : "asc" ;
            example.setOrderByClause(orderByClause);
        }

        List<Brands> brandsList = brandMapper.selectByExample(example);
        //强壮性判断

        if (CollectionUtils.isEmpty(brandsList)){
            throw   new LyException(ExceptionEnum.BRANDS_NOT_FOUND);
        }

        //解析分页结果

        PageInfo<Brands> brandsPageInfo = new PageInfo<>(brandsList);

        //封装返回

        return new PageResult<>(brandsPageInfo.getTotal(),brandsList);
    }
}
