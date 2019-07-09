package com.yufan.data.pojo;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * 创建人: lirf
 * 创建时间:  2019/4/16 11:13
 * 功能介绍:
 */
@Entity
@Table(name = "tb_visit_business", catalog = "")
public class TbVisitBusiness {
    private Integer id;
    private Date visitDate;
    private String businessCode;
    private Integer visitCount;
    private Integer responseTime;
    private String sysCode;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "business_code")
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    @Basic
    @Column(name = "sys_code")
    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    @Basic
    @Column(name = "visit_date")
    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    @Basic
    @Column(name = "visit_count")
    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    @Basic
    @Column(name = "response_time")
    public Integer getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }

}
