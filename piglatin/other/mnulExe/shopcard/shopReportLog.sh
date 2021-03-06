#!/bin/bash
source /etc/profile
##date
#date=$(date +"%Y-%m-%d" -d "$1");
#former=$(date +"%Y-%m-%d" -d "$1-1day");

#代码路径
local_pig_script=/home/hadoop/lrfdata/pig/mnulExe/shopcard

##参数1：中间数据的时间
input_date_log=$(date +"%Y-%m-%d" -d "$1")
former=$input_date_log

#
if [ ! -n "$1" ] ;then
    echo "输入清洗日志的日期"
    exit
fi


echo""
echo "需要清洗的日志日期为============================"${former}
echo""



#第一次清洗的商家日志路径
input_clean_shop_log=/user/datum/uhuibao/clean/ttyh/${former}/shop/*
#判断输入hdfs是否存在
hadoop fs -test -e ${input_clean_shop_log}
if [ $? -eq 0 ]
	then
	echo ""
else
	echo "商家日志数据路径不存在"${input_clean_shop_log}
        exit
fi

#第二次清洗商家日志输出路径
out_clean_shop_log=/user/datum/uhuibao/middle/ttyh/clean2/${former}/shop_report_log/shop
#判断输出hdfs是否存在
hadoop fs -test -e ${out_clean_shop_log}
if [ $? -eq 0 ]
	then
	echo "商家第二次清洗结果数据已存在"${out_clean_shop_log}
	exit
fi

#start

pig -p input_clean_shop_log=${input_clean_shop_log} -p out_clean_shop_log=${out_clean_shop_log}  ${local_pig_script}/shopReportLog.pig

#for i in {11..20};do sh shopReportLog.sh 2017$i;done;








