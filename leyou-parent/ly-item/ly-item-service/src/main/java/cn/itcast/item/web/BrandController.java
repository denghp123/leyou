package cn.itcast.item.web;

import cn.itcast.common.vo.PageResult;
import cn.itcast.item.service.BrandService;
import cn.itcast.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 品牌分页查询接口
     * @param desc  升序/降序
     * @param page 当前页
     * @param rows  每页大小
     * @param sortBy  需要排序的字段
     * @param key  搜索关键字
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>>  queryBrandsForPage(
            @RequestParam(value = "desc",defaultValue = "false") boolean desc,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5")  Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "key" ,required = false) String key

    ){



    return ResponseEntity.ok(brandService.queryBrandForPage(desc,page,rows,sortBy,key));
    }

    /**
     * 品牌新增
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping("addBrand")
    public ResponseEntity<Void> addBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        brandService.addBrand(brand,cids);
       return ResponseEntity.status(HttpStatus.CREATED).build();

    }


    /**
     * 品牌编辑修改
     * @param brand
     * @param cids
     * @return
     */
    @PutMapping("addBrand")
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids")List<Long> cids){
        brandService.updateBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 根据id删除品牌
     * @param bid
     * @return
     */
     @DeleteMapping("/delete/{bid}")
    public ResponseEntity<Void> deleteBrandById(@PathVariable("bid")Long bid){
         brandService.deleteBrandById(bid);
         return ResponseEntity.status(HttpStatus.CREATED).build();
     }


    /**
     * 根据分类查询分类下的品牌信息
     * @param cid
     * @return
     */
     @GetMapping("/cid/{cid}")
     public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid")Long cid){
         return ResponseEntity.ok(brandService.queryBrandByCid(cid));
     }

    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
     @GetMapping("{id}")
     public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
         return ResponseEntity.ok(brandService.queryBrandById(id));
     }




    /**
     * 批量查询品牌信息
     * @param ids
     * @return
     */
    @GetMapping("/ids")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids){

        return ResponseEntity.ok(brandService.queryBrandByIds(ids));

    }
}
