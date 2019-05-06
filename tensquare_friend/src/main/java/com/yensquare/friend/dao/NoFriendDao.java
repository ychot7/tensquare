package com.yensquare.friend.dao;

import com.yensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoFriendDao extends JpaRepository<NoFriend, String> {
    NoFriend findByUseridAndFriendid(String userId, String friendId);
}
