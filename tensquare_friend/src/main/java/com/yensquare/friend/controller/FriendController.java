package com.yensquare.friend.controller;

import com.yensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @DeleteMapping(value = "/{friendid}")
    public Result remove(@PathVariable String friendid) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null || "".equals(claims)) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        friendService.deleteFriend(claims.getId(), friendid);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 添加
     * @param friendid
     * @param type
     * @return
     */
    @PutMapping(value = "/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid, @PathVariable String type) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null || "".equals(claims)) {
            return new Result(false, StatusCode.ACCESSERROR, "无权访问");
        }
        if ("1".equals(type)) {
            /** 喜欢 */
            if (friendService.addFriend(claims.getId(), friendid) == 0) {
                return new Result(false, StatusCode.ERROR, "已经添加此好友");
            }
        } else if ("2".equals(type)) {
            /** 不喜欢 */
            if (friendService.addNoFriend(claims.getId(), friendid) == 0) {
                return new Result(false, StatusCode.ERROR, "不能重复添加非好友");
            }
        } else {
            return new Result(false, StatusCode.ERROR, "参数异常");
        }

        return new Result(true, StatusCode.OK, "操作成功");
    }
}
