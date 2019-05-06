package com.yensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 更新对方粉丝数和自己关注数
     * @param userid
     * @param friendid
     * @param x
     */
    @PostMapping(value = "/user/{userid}/{friendid}/{x}")
    public void updateFanscountAndFollowcount(@PathVariable("userid") String userid,
                                              @PathVariable("friendid") String friendid,
                                              @PathVariable("x") int x);
}
