package cn.itcast.item.web;

import cn.itcast.common.vo.PageResult;
import cn.itcast.item.service.GoodsService;
import cn.itcast.pojo.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author dhp
 * @Date 2020/6/22 9:58
 * @Version 1.0
 */

@RestController
@RequestMapping("/spu")
public class GoodsController {

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



}
