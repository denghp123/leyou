package cn.itcast.common.mapper;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author dhp
 * @Date 2020/6/22 11:35
 * @Version 1.0
 */

@RegisterMapper
public interface BaseMapper<T> extends Mapper<T>, IdListMapper<T,Long> , InsertListMapper<T> {
}
