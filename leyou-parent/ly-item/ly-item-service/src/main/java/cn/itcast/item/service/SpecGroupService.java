package cn.itcast.item.service;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import cn.itcast.item.mapper.SpecGroupMapper;
import cn.itcast.pojo.SpecGroup;
import cn.itcast.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author dhp
 * @Date 2020/6/17 15:46
 * @Version 1.0
 */
@Service
public class SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;


    @Autowired
    private SpecParamsService specParamsService;

    public List<SpecGroup> selectSpecGroupById(Long id){
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(id);

        List<SpecGroup> specGroups = specGroupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(specGroups)){
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }

        return specGroups;

    }




    @Transactional
    public void addSpecGroup(Map<String, Object> parms) {
        Long cid = Long.parseLong(parms.get("cid").toString());
        String name = (String) parms.get("name");
        if (cid == null || StringUtils.isEmpty(name)){
            throw new LyException(ExceptionEnum.ADD_SPEC_GROUP);
        }
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        specGroup.setName(name);
        int count = specGroupMapper.insertSelective(specGroup);

        if (count != 1){
            throw new LyException(ExceptionEnum.ADD_SPEC_GROUP);
        }

    }


    @Transactional
    public void updateSpecGroup(SpecGroup specGroup) {
        String name = specGroup.getName();
        if (StringUtils.isEmpty(name)){
            throw new LyException(ExceptionEnum.UPDATE_SPEC_GROUP);
        }
        int count = specGroupMapper.updateByPrimaryKeySelective(specGroup);

        if (count != 1){
            throw new LyException(ExceptionEnum.UPDATE_SPEC_GROUP);
        }
    }



    @Transactional
    public void deleteSpecGroupById(Long id){
        int count = specGroupMapper.deleteByPrimaryKey(id);
        if (count != 1){
            throw new LyException(ExceptionEnum.DELETE_SPEC_GROUP);
        }

        //根据组id删除对应规格参数
        Integer num = specParamsService.removeSpecParamByGroupId(id);
        if (num ==0){
            throw new LyException(ExceptionEnum.DELETE_SPEC_GROUP);
        }

    }

    public List<SpecGroup> querySpecGroupByCid(Long cid) {

        //查询规格组
        List<SpecGroup> specGroups = selectSpecGroupById(cid);


        //查询当前分类下的所有参数
        List<SpecParam> specParams = specParamsService.selectSpecParams(null, cid, null);


        Map<Long, List<SpecParam>> map = specParams.stream().collect(Collectors.groupingBy(SpecParam::getGroupId));
        //把规格参数分组保存到specGroup中
        for (SpecGroup specGroup : specGroups) {
         specGroup.setParams(map.get(specGroup.getId()));
        }

       return specGroups;
    }
}
