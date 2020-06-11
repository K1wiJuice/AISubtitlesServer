package com.AISubtitles.Server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.AISubtitles.Server.entity.User;

/**
 * 
 * @author Gavin
 *
 */
@Mapper
public interface FindPasswordDao {

    /**
     * 
     * @param qqnum
     * @return
     */
	/*
	 *这里的查询采用Mybatis,但在spring环境下jpa的事务可能会覆盖mybatis的事务——有什么方法能够使两者结合起来用？用jpa来维护类之间的关系，用mybatis来写复杂sql
	 *需要根据数据库进行更改并调试 （这里以sql脚本所生成数据库u_user为参考）
	*/
    @Select("select id,real_name,sex,age,nickname,qq_num,phone_num,birthday,role_id,email from u_user where qq_num = #{qqnum}")
    @Results({@Result(column = "id", property = "id"), @Result(column = "real_name", property = "realName"),
        @Result(column = "sex", property = "sex"), @Result(column = "age", property = "age"),
        @Result(column = "nickname", property = "nickname"), @Result(column = "qq_num", property = "qqNum"),
        @Result(column = "phone_num", property = "phoneNum"),
        @Result(column = "birthday", property = "birthday"), @Result(column = "role_id", property = "roleId"),
        @Result(column = "email", property = "email")}) //,class_num @Result(column = "class_num", property = "classNum"),
    public User select(String qqnum);

    /**
     * 查询是否存在该用户
     * 
     * @return
     */
    @Select("select count(*) from u_user where qq_num = #{qqNum}")
    public Integer selectUserCount(String qqNum);

    /**
     * 修改密码
     * 
     * @param password
     * @param id
     * @return
     */
    @Update("update u_login set password = #{password}  where user_id = #{id}")
    public Integer update(@Param("password") String password, @Param("id") String id);

}
