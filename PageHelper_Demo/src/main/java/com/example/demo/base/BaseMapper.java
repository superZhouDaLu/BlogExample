package com.example.demo.base;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.condition.SelectByConditionMapper;
import tk.mybatis.mapper.common.condition.UpdateByConditionMapper;
import tk.mybatis.mapper.common.ids.DeleteByIdsMapper;

/**
 * 公用Mapper接口
 *
 * @author SuperZhouDaLu
 */
@Repository
public interface BaseMapper<T> extends Mapper<T>, SelectByConditionMapper<T>, UpdateByConditionMapper<T>, DeleteByIdsMapper<T>, MySqlMapper<T> {
    // FIXME 特别注意，该接口不能被扫描到，否则会出错
}
