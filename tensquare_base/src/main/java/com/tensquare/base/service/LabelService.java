package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签业务逻辑类
 *
 * @since 1.0 2019/4/1
 * @author yc
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }
    /**
     * 根据ID查询标签
     * @return
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }
    /**
     * 增加标签
     * @param label
     */
    public void add(Label label){
        label.setId( idWorker.nextId()+"" );//设置ID
        labelDao.save(label);
    }
    /**
     * 修改标签
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }
    /**
     * 删除标签
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 条件查询
     * @param label
     * @return
     */
    public List<Label> findSearch(Label label) {
        return labelDao.findAll(creatSpecifition(label));
    }

    /**
     * 条件 + 分页查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page findSearch(Label label, int page, int size) {
        Specification specification = creatSpecifition(label);
        Pageable pageable = PageRequest.of(page - 1, size);

        return labelDao.findAll(specification, pageable);
    }

    private Specification<Label> creatSpecifition(Label label) {
        return new Specification<Label>() {

            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    list.add(cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%"));
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    list.add(cb.equal(root.get("state").as(String.class), label.getState()));
                }
                if (label.getRecommand() != null && !"".equals(label.getRecommand())) {
                    list.add(cb.equal(root.get("recommand").as(String.class), label.getRecommand()));
                }

                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
    }
}
