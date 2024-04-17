package com.slowsql.test;

import org.apache.ibatis.annotations.Select;

/**
 * @author Aaron.Sun
 * @description 用户信息数据库访问接口
 * @date Created in 9:13 2020/5/22
 * @modified By
 */
public interface UserMapper {

	@Select("SELECT user_name FROM sys_user WHERE id = #{id}")
    String queryUserName(String id);

}
