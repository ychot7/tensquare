package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    /**
     * 根据手机号查询用户
     * @return
     */
    public User findByMobile(String mobile);

    /**
     * 更新粉丝数
     * @param userid 用户ID
     * @param x 粉丝数
     */
    @Modifying
    @Query("update User u set u.fanscount=u.fanscount+?1 where u.id=?2")
    public void updateFanscount(int x, String userid);

    /**
     * 更新关注数
     * @param userid 用户ID
     * @param x 粉丝数
     */
    @Modifying
    @Query("update User u set u.followcount=u.followcount+?1 where u.id=?2")
    public void uodateFollowcount(int x, String userid);
}
