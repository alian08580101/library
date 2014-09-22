package com.jd.a6i.db.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class ErrorType extends Result {
	@JsonDeserialize
	@JsonIgnore(value=true)
	private static final long serialVersionUID = -4382779583698096673L;
	@JsonIgnore(value=true)
	private int id;
	private String typeName;
	private int typeCode;
	private String memo;
	private int parentId;
	private int nodeLevel;
	private int typeGroup;
	private String dataUpdate;
	public int getId() {
		return id;
	}
	public String getTypeName() {
		return typeName;
	}
	public int getTypeCode() {
		return typeCode;
	}
	public String getMemo() {
		return memo;
	}
	public int getParentId() {
		return parentId;
	}
	public int getNodeLevel() {
		return nodeLevel;
	}
	public int getTypeGroup() {
		return typeGroup;
	}
	public String getDataUpdate() {
		return dataUpdate;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public void setNodeLevel(int nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public void setTypeGroup(int typeGroup) {
		this.typeGroup = typeGroup;
	}
	public void setDataUpdate(String dataUpdate) {
		this.dataUpdate = dataUpdate;
	}
	@Override
	public String toString() {
		return "ErrorType [id=" + id + ", typeName=" + typeName + ", typeCode="
				+ typeCode + ", memo=" + memo + ", parentId=" + parentId
				+ ", nodeLevel=" + nodeLevel + ", typeGroup=" + typeGroup
				+ ", dataUpdate=" + dataUpdate + "]";
	}
}
