package cn.itcast.item.service;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import cn.itcast.common.vo.PageResult;
import cn.itcast.item.mapper.*;
import cn.itcast.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
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

    @Autowired
    private SkuMapper skuMapper;


    @Autowired
    private StockMapper stockMapper;

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
       //新增spu
        spu.setSaleable(false);//默认下架
        spu.setValid(true);//有效 未删除
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        int count = spuMapper.insertSelective(spu);
        if (count != 1){
            throw new LyException(ExceptionEnum.SKU_PARAM_ERROR);
        }
        //新增spu_detail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insertSelective(spuDetail);

        saveSkuAndStock(spu);


    }


    private void saveSkuAndStock(Spu spu) {
        //新增sku
        //新建一个存放库存的容器
        List<Stock> stocks = new ArrayList<>();
        List<Sku> skus = spu.getSkus();
        if (CollectionUtils.isEmpty(skus)){
            throw new LyException(ExceptionEnum.ADD_GOODS_ERROR);
        }
        for (Sku sku : skus) {
            if (sku != null){
                sku.setCreateTime(new Date());
                sku.setLastUpdateTime(sku.getCreateTime());
                sku.setSpuId(spu.getId());
                int num = skuMapper.insertSelective(sku);
                if (num != 1){
                    throw new LyException(ExceptionEnum.ADD_GOODS_ERROR);
                }

                // 新增库存
                Stock stock = new Stock();
                stock.setSkuId(sku.getId());
                stock.setStock(sku.getStock());

                stocks.add(stock);
            }

        }

        //新增库存
        int size = stockMapper.insertList(stocks);
        if (size != stocks.size()){
            throw new LyException(ExceptionEnum.ADD_GOODS_ERROR);
        }

    }


    public SpuDetail queryDetailById(Long id) {
        SpuDetail detail = spuDetailMapper.selectByPrimaryKey(id);
        if(detail == null){
            throw new LyException(ExceptionEnum.GOODS_DETAIL_NOT_FOND);
        }
        return detail;
    }

    public List<Sku> querySkuListBySpuId(Long id) {
        // 查询sku
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skuList = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOND);
        }

        // 查询库存
        List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stockList = stockMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(stockList)){
            throw new LyException(ExceptionEnum.GOODS_STOCK_NOT_FOND);
        }

        // 我们把stock变成一个map,其key是:sku的id,值是库存值
        Map<Long, Integer> stockMap = stockList.stream()
                .collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        // 从map中取出库存，然后保存到sku
        skuList.forEach(s -> s.setStock(stockMap.get(s.getId())));
        return skuList;
    }


    /**
     * 编辑修改商品详情
     * @param spu
     */
    public void editGoods(Spu spu) {

    }
}
