package com.tensquare.spit.controllen;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitControllen {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest request;

    /**
     * 查询全部
     * @return
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /**
     * 增加
     * @param spit
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Spit spit) {
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null || "".equals(claims)) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /**
     * 根据主键查询
     * @param spitId
     * @return
     */
    @GetMapping(value = "/{spitId}")
    public Result findById(@PathVariable String spitId) {
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    /**
     * 修改
     * @param spit
     * @param spitId
     * @return
     */
    @PutMapping(value = "/{spitId}")
    public Result update(@RequestBody Spit spit, @PathVariable String spitId) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 根据主键删除
     * @param spitId
     * @return
     */
    @DeleteMapping(value = "/{spitId}")
    public Result delete(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return  new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据上级Id分页查询吐槽列表
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/comment/{parentId}/{page}/{size}")
    public Result findByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size) {
        Page<Spit> pagelist = spitService.findByParentId(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pagelist.getTotalElements(), pagelist.getContent()));
    }

    /**
     * 点赞
     * @param spitId
     * @return
     */
    @PutMapping(value = "/thumbup/{spitId}")
    public Result updateThumbup(@PathVariable String spitId){
        //判断用户是否点过赞
        String userId = "2023";     //TODO
        if (redisTemplate.opsForValue().get("thumbup_" + userId + "_" + spitId) != null) {
            return new Result(false, StatusCode.ERROR, "您已经点过赞了！");
        }
        spitService.updateThumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_" + userId + "_" + spitId, "1");
        return new Result(true, StatusCode.OK, "点赞成功");
    }

}
