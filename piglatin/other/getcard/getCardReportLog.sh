#!/bin/bash
source /etc/profile
##date
date=$(date +"%Y-%m-%d" -d "$1");
former=$(date +"%Y-%m-%d" -d "$1-1day");
dateorder=$(date +"%Y" -d "$1");

#代码路径
local_pig_script=/home/hadoop/lrfdata/pig/getcard

#领卡日志路径
input_getcard_log=/user/datum/uhuibao/tmp/data/ttyh/cardpage/${former}/*

#第二次清洗领卡日志输出路径
out_clean_getcard_log=/user/datum/uhuibao/middle/ttyh/clean2/${former}/getcard_report_log/getcard

#########订单系统库数据2017-03-16里面数据为15号订单数据
input_ordersysdb_log=/user/datum/uhuibao/tmp/data/ttyh/orderdb/${dateorder}/order_${date}.txt

#领卡订单输出路径
out_order_path=/user/datum/uhuibao/middle/ttyh/clean2/${former}/getcard_report_log/order
#领卡已退款已取消订单输出路径
out_cancelorder_path=/user/datum/uhuibao/middle/ttyh/clean2/${former}/getcard_report_log/cancel_order

#start

pig -p input_getcard_log=${input_getcard_log} -p out_clean_getcard_log=${out_clean_getcard_log} ${local_pig_script}/getCardReportLog.pig




#for i in {11..20};do sh serviceReportLog.sh 2017$i;done;








