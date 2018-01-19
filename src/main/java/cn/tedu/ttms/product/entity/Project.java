package cn.tedu.ttms.product.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.tedu.ttms.common.web.JsonDateTypeConvert;


public class Project implements Serializable{
	 private static final long serialVersionUID = 5850357988911265658L;
	 /**项目ID，对应表中的主键值*/
	 private Integer id;
	 /**项目名称*/
	 private String name;
	 /**项目编号*/
	 private String code;
	 /**归属部门*/
	 private String deptId;
	 /**开始时间*/
	 @DateTimeFormat(pattern="yyyy-MM-dd")
	 private Date beginDate;//java.util.Date
	 /**结束时间*/
	 @DateTimeFormat(pattern="yyyy-MM-dd")
	 private Date endDate;//java.util.Date
	 /**项目有效*/
	 private Integer valid;
	 /**项目备注*/
	 private String note;
	 /**创建时间*/
	 private Date createdTime;
	 /**修改时间*/
	 private Date modifiedTime;
	 /**创建用户*/
	 private String createdUser;
	 /**修改用户*/
	 private String modifiedUser;
	
	 public Integer getId() {
		return id;
	 }
	 public void setId(Integer id) {
		this.id = id;
	 }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
    
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	//在将日期类型的属性转换为json串时
	//可参考JsonSerialize注解指定的类型转换器
    @JsonSerialize(using=JsonDateTypeConvert.class)//JsonSerializer<T>
	public Date getBeginDate() {  	
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	@JsonSerialize(using=JsonDateTypeConvert.class)//jackson
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getValid() {
		return valid;
	}


	public void setValid(Integer valid) {
		this.valid = valid;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Date getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	public Date getModifiedTime() {
		return modifiedTime;
	}


	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}


	public String getCreatedUser() {
		return createdUser;
	}


	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}


	public String getModifiedUser() {
		return modifiedUser;
	}


	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}


	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", code=" + code + ", deptId=" + deptId + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", valid=" + valid + ", note=" + note + ", createdTime="
				+ createdTime + ", modifiedTime=" + modifiedTime + ", createdUser=" + createdUser + ", modifiedUser="
				+ modifiedUser + "]";
	}
	 
	 
}



