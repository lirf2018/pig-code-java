package com.yufan.data.log.info.dao;

import com.yufan.data.pojo.TbVisitBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 创建人: lirf
 * 创建时间:  2019/4/12 18:01
 * 功能介绍:
 */
public interface IInfoJpaDao extends JpaRepository<TbVisitBusiness, Integer>, JpaSpecificationExecutor<TbVisitBusiness> {

    @Query(value = "SELECT COUNT(*) from tb_visit_business where DATE_FORMAT(visit_date,'%Y-%m-%d')=?1", nativeQuery = true)
    int findHasSaveInfoDataByDate(String date);

    @Query(value = "SELECT visit_date as visit_date from tb_visit_business where visit_date>=?1 GROUP BY visit_date", nativeQuery = true)
    List findHasSaveInfoDataByStartDateListMap(String startDate);

}
