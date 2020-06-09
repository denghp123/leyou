package cn.itcast.item.web;

import cn.itcast.common.vo.PageResult;
import cn.itcast.item.service.BrandService;
import cn.itcast.pojo.Brands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author dhp
 * @Date 2020/6/9 10:15
 * @Version 1.0
 */
@RestController
@RequestMapping("brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 品牌分类查询接口
     * @param desc  升序/降序
     * @param page 当前页
     * @param rows  每页大小
     * @param sortBy  需要排序的字段
     * @param key  搜索关键字
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brands>>  queryBrandsForPage(
            @RequestParam(value = "desc",defaultValue = "false") boolean desc,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5")  Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "key" ,required = false) String key
    ){



    return ResponseEntity.ok(brandService.queryBrandForPage(desc,page,rows,sortBy,key));
    }
}