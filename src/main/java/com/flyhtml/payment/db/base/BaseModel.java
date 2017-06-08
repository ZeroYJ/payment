package com.flyhtml.payment.db.base;

import javax.persistence.Transient;

/**
 * @author xiaowei
 * @time 17-4-7 下午3:00
 * @describe 通用Model
 */
public class BaseModel {

  @Transient private transient Integer page = 1;

  @Transient private transient Integer rows = 10;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getRows() {
    return rows;
  }

  public void setRows(Integer rows) {
    this.rows = rows;
  }
}
