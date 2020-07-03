package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User, Integer>{
	User findByUserName(String userName);
	User findByUserEmail(String userEmail);
	User findByUserEmailOrUserPhoneNumber(String userEmail,String userPhoneNumber);
    User findByUserPhoneNumber(String userPhoneNumber);
    //User findByUserEmail(String userEmail);
    Integer countByUserName(String accountnum);
    Integer countByUserEmail(String accountnum);
    Integer countByUserPhoneNumber(String accountnum);
    //public User findByUserName(String accountnum);

    /**
     * 查询是否存在该用户
     * 
     * @return
     */
    //@Query(value = "select count(*) from user_info ui where ui.user_name = ?1", nativeQuery = true)
    //public Integer selectUserCount(String accountnum);

    /**
     * 修改密码
     * 
     * @param password
     * @param id
     * @return
     */
    // @Transactional
    // @Modifying
    
    // @Query(value = "update user_auths  set user_password = :userPassword  where user_id = :userId",nativeQuery = true)
    // public Integer update(@Param("userPassword")String userPassword,@Param("userId") int userId);
    //@Query(value = "update user_info  set user_password = :userPassword  where user_email = :userEmail",nativeQuery = true)
    //public Integer update(@Param("userPassword")String userPassword,@Param("userEmail") String userEmail);

}