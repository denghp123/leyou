package cn.itcast.item.web;

import cn.itcast.common.vo.PageResult;
import cn.itcast.item.service.GoodsService;
import cn.itcast.pojo.Sku;
import cn.itcast.pojo.Spu;
import cn.itcast.pojo.SpuDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/22 9:58
 * @Version 1.0
 */

@RestController
@RequestMapping("/spu")
public class  GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品SPU列表分页查询
     * @param page 页码数
     * @param rows  每页条数
     * @param saleable  是否上下架
     * @param key  搜索字段
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<Spu>>  querySpuForPage(

            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5")  Integer rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key" ,required = false) String key

    ){



        return ResponseEntity.ok(goodsService.querySpuForPage(page,rows,saleable,key));
    }


    /**
     * 新增商品
     * @param spu
     * @return
     */
    @PostMapping("/goods")
    public ResponseEntity<Void> addGoods(@RequestBody Spu spu){
    goodsService.addGoods(spu);
    return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 编辑 商品spu详情  回显
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(goodsService.queryDetailById(id));
    }

    /**
     * 编辑 商品sku信息 回显
     * @param id
     * @return
     */
    @GetMapping("/skuList")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(this.goodsService.querySkuListBySpuId(id));
    }


    /**
     * 编辑修改商品信息
     * @param spu
     * @return
     */
    @PutMapping("/goods")
    public ResponseEntity<Void> editGoods(@RequestBody Spu spu){
        goodsService.editGoods(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除我的商品
     * @param spuId
     * @return
     */
    @DeleteMapping("/delete/{spuId}")
    public ResponseEntity<Void>  deleteGoodsBySpuId(@PathVariable("spuId")Long spuId){
        goodsService.deleteGoodsBySpuId(spuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();


    }

    /**
     * 下架
     * @param spuId
     * @return
     */
    @PostMapping("/soldout/{spuId}")
    public ResponseEntity<Void>  updateGoodsBySpuId(@PathVariable("spuId")Long spuId){
        goodsService.updateGoodsBySpuId(spuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    /**
     * 上架
     * @param spuId
     * @return
     */
    @PostMapping("/putaway/{spuId}")
    public ResponseEntity<Void>  updateAddGoodsBySpuId(@PathVariable("spuId")Long spuId){
        goodsService.updateAddGoodsBySpuId(spuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }



    @GetMapping("/{spuId}")
    public ResponseEntity<Spu> querySpuBySpuId(@PathVariable("spuId") Long spuId){
        return ResponseEntity.ok(goodsService.querySpuBySpuId(spuId));
    }



}
