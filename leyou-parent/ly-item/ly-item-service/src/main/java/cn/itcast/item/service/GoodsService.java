package cn.itcast.item.service;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import cn.itcast.common.vo.PageResult;
import cn.itcast.item.mapper.BrandMapper;
import cn.itcast.item.mapper.SpuDetailMapper;
import cn.itcast.item.mapper.SpuMapper;
import cn.itcast.pojo.Brand;
import cn.itcast.pojo.Category;
import cn.itcast.pojo.Spu;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author dhp
 * @Date 2020/6/22 9:56
 * @Version 1.0
 */

@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private SpuDetailMapper spuDetailMapper;

    public PageResult<Spu> querySpuForPage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //搜索条件过滤
        if (!StringUtils.isEmpty(key)){
            criteria.andLike("title","%"+key+"%");
        }
        //上下架过滤
        if (saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }

        //逻辑删除过滤
        criteria.andEqualTo("valid",true);

        //默认排序
        example.setOrderByClause("last_update_time DESC");


        //查询
        List<Spu> spus = spuMapper.selectByExample(example);
        //强壮性判断
        if (CollectionUtils.isEmpty(spus)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        long total = spuPageInfo.getTotal();
        //商品分类和品牌名称查询
        selectCategoryAndBrandName(spus);


        //封装结果并返回
        return new PageResult<>(total,spus);
    }

    private void selectCategoryAndBrandName(List<Spu> spus) {

        for (Spu spu : spus) {
            //处理品牌名称
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spu.setBname(brand.getName());

            //处理商品分类名称

            String cname = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream()
                    .map(Category::getName)
                    .collect(Collectors.joining("/"));

            spu.setCname(cname);


        }




    }


    /**
     * 商品新增
     * @param spu
     */
    public void addGoods(Spu spu) {

    }
}
