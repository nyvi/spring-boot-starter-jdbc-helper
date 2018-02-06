package com.nyvi.core.base.mode;

import java.io.Serializable;
import java.util.Date;

import com.nyvi.core.annotation.Column;

/**
 * BaseDO
 * @author czk
 */
@SuppressWarnings("serial")
public class BaseDO implements Serializable {

	/**
	 * 主键
	 */
	@Column(isKey = true)
	private Long id;

	/**
	 * 创建时间
	 */
	@Column(name = "gmt_create")
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	@Column(name = "gmt_modified")
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public String toString() {
		return "BaseDO [id=" + id + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}
