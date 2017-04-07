package com.flyhtml.payment.db.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author xiaowei
 * @time 17-4-7 下午5:43
 * @describe
 */
public class BaseService<T extends BaseModel, M extends BaseMapper> {

    @Autowired
    protected M m;

    public List<T> selectAll(T t) {
        if (t.getPage() != null && t.getRows() != null) {
            PageHelper.startPage(t.getPage(), t.getRows());
        }
        return m.selectAll();
    }

    public int insert(T t) {
        return m.insert(t);
    }

    public int update(T t) {
        return m.updateByPrimaryKey(t);
    }
}
