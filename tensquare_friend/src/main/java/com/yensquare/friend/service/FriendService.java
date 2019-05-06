package com.yensquare.friend.service;

import com.yensquare.friend.client.UserClient;
import com.yensquare.friend.dao.FriendDao;
import com.yensquare.friend.dao.NoFriendDao;
import com.yensquare.friend.pojo.Friend;
import com.yensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    /**
     * 删除好友
     * @param userid
     * @param friendid
     */
    public void deleteFriend(String userid, String friendid) {
        friendDao.deleteFriend(userid, friendid);
        friendDao.updateLike(friendid, userid,"0");
        userClient.updateFanscountAndFollowcount(userid, friendid, -1);
        addNoFriend(userid, friendid);
    }

    /**
     * 添加不喜欢的好友
     * @param userId
     * @param friendId
     */
    public int addNoFriend(String userId, String friendId) {
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if (noFriend != null) {
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setFriendid(friendId);
        noFriend.setUserid(userId);
        noFriendDao.save(noFriend);
        return 1;
    }

    /**
     * 添加好友
     * @param userId
     * @param friendId
     * @return
     */
    public int addFriend(String userId, String friendId) {

        /** 判断如果用户已经添加了这个好友，则不进行任何操作,返回0 */
        if (friendDao.selectCount(userId, friendId) > 0) {
            return 0;
        }

        /** 向喜欢表中添加记录*/
        Friend friend = new Friend();
        friend.setFriendid(friendId);
        friend.setUserid(userId);
        friend.setIslike("0");
        friendDao.save(friend);
        userClient.updateFanscountAndFollowcount(userId, friendId, 1);

        /** 判断对方是否喜欢你，如果喜欢，将islike设置为1 */
        if (friendDao.selectCount(friendId, userId) > 0) {
            friendDao.updateLike(userId, friendId, "1");
            friendDao.updateLike(friendId, userId, "1");
        }

        return 1;
    }
}
