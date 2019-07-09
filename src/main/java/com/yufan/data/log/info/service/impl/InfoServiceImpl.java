package com.yufan.data.log.info.service.impl;

import com.yufan.data.log.info.dao.IInfoJpaDao;
import com.yufan.data.pojo.TbVisitBusiness;
import com.yufan.data.util.*;
import com.yufan.data.log.info.service.IInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 创建人: lirf
 * 创建时间:  2019/4/12 9:21
 * 功能介绍:
 */
public class InfoServiceImpl implements IInfoService {

    private Logger LOG = Logger.getLogger(InfoServiceImpl.class);

    @Override
    public void saveBusinessData(String date) {
        String filePath = new ConfigProperty().getValue("info.log.path");
        // 读取文件
        FilesUtil futil = new FilesUtil();
        List<String> listFile = futil.getFiles(filePath);
        Collections.sort(listFile);
        //查询当前数据日期是否已存在=date
        IInfoJpaDao infoJpaDao = (IInfoJpaDao) ApplicationInit.getTargetClassBan(IInfoJpaDao.class);
        int hasSaveData = infoJpaDao.findHasSaveInfoDataByDate(date);
        if (hasSaveData <= 0) {
            for (int i = 0; i < listFile.size(); i++) {
                String fileName = listFile.get(i);
//                LOG.info("扫描filePath--->" + fileName);
                fileName = fileName.replace("\\", "/");
                // 对比时间表里的最后一条记录的时间和查询出来的文件列表时间
                String[] arr = fileName.split("/");
                int len = arr.length;
                String fileDate = arr[len - 2];
//                LOG.info("扫描数据日期fileDate--->" + fileDate);
                if (StringUtils.isNotEmpty(fileDate) && fileDate.equals(date)) {
//                    LOG.info("保存本地数据日期fileDate--->" + fileDate);
                    //增加列数据(colValues要与实体类中属性顺序一致)
                    String[] colValues = {"info"};
                    Map<String, String> colValuesMap = new HashMap<>();
                    colValuesMap.put("sysCode", "info");
                    List<TbVisitBusiness> visitBusinessList = CommonMathod.parseLogText2Object(fileName, colValues, colValuesMap, TbVisitBusiness.class);
                    if (null == visitBusinessList || visitBusinessList.size() == 0) {
                        continue;
                    }
                    LOG.info("----保存数据成功size=" + visitBusinessList.size());
                    infoJpaDao.save(visitBusinessList);
                }
            }
        }
    }

    @Override
    public void saveBusinessDataAll(String startDate) {
        IInfoJpaDao infoJpaDao = (IInfoJpaDao) ApplicationInit.getTargetClassBan(IInfoJpaDao.class);
        String filePath = new ConfigProperty().getValue("info.log.path");
        // 读取文件
        FilesUtil futil = new FilesUtil();
        List<String> listFile = futil.getFiles(filePath);
        Collections.sort(listFile);
        //查询已保存的日期>=startDate
        List list = infoJpaDao.findHasSaveInfoDataByStartDateListMap(startDate);
        Map<String, String> hasSaveData = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String d = (String) list.get(i);
            hasSaveData.put(d, d);
        }


        for (int i = 0; i < listFile.size(); i++) {
            String fileName = listFile.get(i);
//                LOG.info("扫描filePath--->" + fileName);
            fileName = fileName.replace("\\", "/");
            // 对比时间表里的最后一条记录的时间和查询出来的文件列表时间
            String[] arr = fileName.split("/");
            int len = arr.length;
            String fileDate = arr[len - 2];
//            LOG.info("扫描数据日期fileDate--->" + fileDate);
            if (StringUtils.isNotEmpty(fileDate) && null == hasSaveData.get(fileDate) && DatetimeUtil.compareDate(fileDate, startDate) > -1) {
//                    LOG.info("保存本地数据日期fileDate--->" + fileDate);
                //增加列数据(colValues要与实体类中属性顺序一致)
                String[] colValues = {"info"};
                Map<String, String> colValuesMap = new HashMap<>();
                colValuesMap.put("sysCode", "info");
                List<TbVisitBusiness> visitBusinessList = CommonMathod.parseLogText2Object(fileName, colValues, colValuesMap, TbVisitBusiness.class);
                if (null == visitBusinessList || visitBusinessList.size() == 0) {
                    continue;
                }
                LOG.info("----保存数据成功size=" + visitBusinessList.size());
                infoJpaDao.save(visitBusinessList);
            }
        }
    }

}
