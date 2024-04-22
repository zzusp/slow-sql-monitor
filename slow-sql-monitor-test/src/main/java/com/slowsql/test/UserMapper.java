package com.slowsql.test;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Aaron.Sun
 * @description 用户信息数据库访问接口
 * @date Created in 9:13 2020/5/22
 * @modified By
 */
public interface UserMapper {

	@Select("SELECT * FROM schedule_personnel limit 150000")
    List<Map<String, Object>> queryUserName(String id);

}
