package cn.itcast.item.web;

import cn.itcast.item.service.SpecParamsService;
import cn.itcast.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/17 16:34
 * @Version 1.0
 */

@RestController
@RequestMapping("spec")
public class SpecParamsController {

    @Autowired
    private SpecParamsService specParamsService;

    /**
     * 根据规格查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> selectSpecParamByGid(@RequestParam("gid")Long gid){
        return ResponseEntity.ok(specParamsService.selectSpecParamsByGid(gid));
    }


    /**
     * 新增规格参数
     * @param specParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> addSpecParam(@RequestBody SpecParam specParam){

        specParamsService.addSpecParam(specParam);

        return ResponseEntity.status(HttpStatus.CREATED).build();


    }


    /**
     * 修改编辑规格参数
     * @param specParam
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateSpecParam(@RequestBody SpecParam specParam){
        specParamsService.updateSpecParam(specParam);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 删除规格参数
     * @param id
     * @return
     */
    @DeleteMapping("/param/{id}")
    public ResponseEntity<Void> deleteSpecParam(@PathVariable("id") Long id){
        specParamsService.deleteSpecParam(id);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }





}
