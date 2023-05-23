package com.wipro.fhir.r4.data.e_aushdhi;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.google.gson.annotations.Expose;


@Entity
@Table(name="m_uom")
public class M_Uom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	@Column(name="UOMID")
	private Integer uOMID;
	@Expose
	@Column(name="UOMName")
	private String uOMName;
	@Expose
	@Column(name="UOMDesc")
	private String uOMDesc; 
	@Expose
	@Column(name="UOMCode")
	private String uOMCode;
	@Expose
	@Column(name="Status")
	private String status;
	@Expose
	@Column(name="ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name="Deleted",insertable = false, updatable = true)
	private Boolean deleted;
	@Expose
	@Column(name="Processed",insertable = false, updatable = true)
	private Character processed;
	@Expose
	@Column(name="CreatedBy")
	private String createdBy;
	@Expose
	@Column(name="CreatedDate",insertable = false, updatable = false)
	private Date createdDate;
	@Expose
	@Column(name="ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name="LastModDate",insertable = false, updatable = false)
	private Date lastModDate;
	public Integer getuOMID() {
		return uOMID;
	}
	public void setuOMID(Integer uOMID) {
		this.uOMID = uOMID;
	}
	public String getuOMName() {
		return uOMName;
	}
	public void setuOMName(String uOMName) {
		this.uOMName = uOMName;
	}
	public String getuOMDesc() {
		return uOMDesc;
	}
	public void setuOMDesc(String uOMDesc) {
		this.uOMDesc = uOMDesc;
	}
	public String getuOMCode() {
		return uOMCode;
	}
	public void setuOMCode(String uOMCode) {
		this.uOMCode = uOMCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}
	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Character getProcessed() {
		return processed;
	}
	public void setProcessed(Character processed) {
		this.processed = processed;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getLastModDate() {
		return lastModDate;
	}
	public void setLastModDate(Date lastModDate) {
		this.lastModDate = lastModDate;
	}
	

	       
	

	
	
	

}

