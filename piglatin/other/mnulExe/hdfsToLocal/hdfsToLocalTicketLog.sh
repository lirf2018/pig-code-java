#!/bin/bash
echo ""

echo "只循环100天"

if [ ! -n "$1" ] ;then
    echo "请输入获取hdfs日志的开始时间(包含输入的时间),格式如:2016-11-11"
    exit
fi
if [ ! -n "$2" ] ;then
    echo "请输入获取hdfs日志的结束时间(包含输入的时间),格式如:2016-11-11"
    exit
fi

#比较日期
t1=`date -d "$1" +%s`
t2=`date -d "$2" +%s`

if [ $t1 -gt $t2 ]; then
   echo "结束日期不能小于开始日期"
   exit
fi

#参数1：包括的起始时间
#参数2：包括的截止时间

echo ""
echo ""
echo "下载的dfs文件从: $1(包括)至$2(包括)日志"
echo ""

#本地或者线上本地
localRoot="/home/hadoop/lrfdata/data/backup/ttyh"


echo "开始获取卡券日报----卡券日志"
i=0
#循环
while(($i<100))
do
    getDate=$(date +"%Y-%m-%d" -d "$1+"$i"day")
    t3_=`date -d "$getDate" +%s`
    if [ $t3_ -gt $t2 ]; then
        echo "循环结束ticket"
	i=10000
        #exit
    else
        echo "循环获取日期=$getDate"
        hdfsRootPath=/user/datum/uhuibao/middle/ttyh/clean2/${getDate}/ticket_report_log/ticket
        localRootPathticket=${localRoot}/ticket_log/ticket/${getDate}

        #判断hdfs路径是否存在
        hadoop fs -test -e ${hdfsRootPath}
        if [ $? -eq 0 ]
            then
            echo ""
        else
            echo "hdfs数据不存在"${hdfsRootPath}
            exit
        fi
        if [ ! -d "${localRootPathticket}" ]; then
            mkdir ${localRootPathticket}
            echo "文件夹创建成功${localRootPathticket}"
        else
            echo "本地文件夹已存在${localRootPathticket}程序停止"
            exit
            #rm -rf ${localRootPathticket}
            #mkdir ${localRootPathticket}
        fi
        #执行hadoop
        hadoop fs -get ${hdfsRootPath}/* ${localRootPathticket}/
        echo "hdfs文件夹下载成功"
        fi    
    i=$(($i+1))
done
echo "结束获取卡券日报----卡券日志"
echo ""


