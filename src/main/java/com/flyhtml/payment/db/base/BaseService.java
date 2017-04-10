package com.flyhtml.payment.db.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;

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

    public List<T> selectByQuery(T t) {
        if (t.getPage() != null && t.getRows() != null) {
            PageHelper.startPage(t.getPage(), t.getRows());
        }
        return m.selectByExample(t);
    }

    public T selectOne(T t) {
        return (T) m.selectOne(t);
    }

    public T selectById(T t) {
        return (T) m.selectByPrimaryKey(t);
    }

    /***
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     * 
     * @param t
     * @return
     */
    public int insertAll(T t) {
        return m.insert(t);
    }

    /***
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * 
     * @param t
     * @return
     */
    public int insertSelective(T t) {
        return m.insertSelective(t);
    }

    /***
     * 更新对象,只更新不为null的值
     * 
     * @param t
     * @return
     */
    public int update(T t) {
        return m.updateByPrimaryKeySelective(t);
    }
}
