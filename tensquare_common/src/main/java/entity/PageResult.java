package entity;

import java.util.List;

/**
 * 返回分页结果
 *
 * @since 1.0   2019/4/1
 * @author yc
 */
public class PageResult <T>{
    private Long tatal;
    private List<T> rows;

    public PageResult(Long tatal, List<T> rows) {
        this.tatal = tatal;
        this.rows = rows;
    }

    public PageResult() {
    }

    public Long getTatal() {
        return tatal;
    }

    public void setTatal(Long tatal) {
        this.tatal = tatal;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
