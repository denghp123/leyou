package cn.itcast.item.service;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import cn.itcast.item.mapper.SpecParamsMapper;
import cn.itcast.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/17 16:21
 * @Version 1.0
 */

@Service
public class SpecParamsService {

    @Autowired
    private SpecParamsMapper specParamsMapper;


    public List<SpecParam> selectSpecParamsByGid(Long gid){
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);

        List<SpecParam> specParams = specParamsMapper.select(specParam);
        if (CollectionUtils.isEmpty(specParams)){
            throw new LyException(ExceptionEnum.SPEC_PARAMS_NOT_FOUND);
        }

        return specParams;


    }


    @Transactional
    public void addSpecParam(SpecParam specParam) {
        if (StringUtils.isEmpty(specParam.getName())){
            throw new LyException(ExceptionEnum.ADD_SPEC_PARAMS);
        }
        int count = specParamsMapper.insertSelective(specParam);
        if (count !=1){
            throw new LyException(ExceptionEnum.ADD_SPEC_PARAMS);
        }

    }




    public void  updateSpecParam(SpecParam specParam){
        if (StringUtils.isEmpty(specParam.getName())){
            throw new LyException(ExceptionEnum.UPDATE_SPEC_PARAMS);
        }

        int count = specParamsMapper.updateByPrimaryKeySelective(specParam);

        if (count !=1){
            throw new LyException(ExceptionEnum.UPDATE_SPEC_PARAMS);
        }
    }

    public void deleteSpecParam(Long id) {
        int count = specParamsMapper.deleteByPrimaryKey(id);
        if (count !=1){
            throw new LyException(ExceptionEnum.DELETE_SPEC_PARAMS);
        }
    }
}
