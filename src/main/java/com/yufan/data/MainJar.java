package com.yufan.data;

import com.yufan.data.log.info.service.IInfoService;
import com.yufan.data.log.info.service.impl.InfoServiceImpl;
import com.yufan.data.util.DatetimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 创建人: lirf
 * 创建时间:  2019/4/12 9:10
 * 功能介绍:
 */
public class MainJar {

    private static Logger LOG = Logger.getLogger(MainJar.class);

    public static void main(String[] args) {
        String date = "";
        String all = "";
        if (args.length == 0) {
            //获取上一天数据
            date = DatetimeUtil.getDateLastOrNextByNowDate("yyyy-MM-dd", -1);
        } else if (args.length == 1) {
            date = args[0];
        } else if (args.length == 2) {
            //指定处理时间段(包括边界日期>=和<=)
            date = args[0];
            all = args[1];
        } else {
            LOG.info("----->处理异常");
            return;
        }
        //info日志
        infoData(date, all);
    }

    /**
     * info日志
     *
     * @param date
     * @param all
     */
    private static void infoData(String date, String all) {

        IInfoService infoService = new InfoServiceImpl();

        if (StringUtils.isNotEmpty(all)) {
            LOG.info("----->处理日期>" + date);
            infoService.saveBusinessDataAll(date);
        } else {
            LOG.info("----->处理日期：" + date);
            infoService.saveBusinessData(date);
        }
    }


}
