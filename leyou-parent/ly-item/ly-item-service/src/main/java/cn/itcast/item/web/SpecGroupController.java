package cn.itcast.item.web;

import cn.itcast.item.service.SpecGroupService;
import cn.itcast.pojo.SpecGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author dhp
 * @Date 2020/6/17 15:59
 * @Version 1.0
 */
@RestController
@RequestMapping("spec")
public class SpecGroupController {

    @Autowired
    private SpecGroupService specGroupService;

    /**
     * 根据商品分类查询商品规格信息
     * @param cid
     * @return
     */
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> selectSpecGroupsByCid(@PathVariable("cid")Long cid){
       return ResponseEntity.ok(specGroupService.selectSpecGroupById(cid));
    }

    /**
     * 新增商品规格分组
     * @param
     * @param
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> addSpecGroup(@RequestBody Map<String, Object> params
                                             ){

        specGroupService.addSpecGroup(params);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 编辑修改规格组信息
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroup(@RequestBody SpecGroup specGroup){
        specGroupService.updateSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 根据id删除商品组信息
     * @param id
     * @return
     */
    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteSpecGroupById(@PathVariable ("id")Long id){
        specGroupService.deleteSpecGroupById(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
