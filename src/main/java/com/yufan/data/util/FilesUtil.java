package com.yufan.data.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 创建人: lirf
 * 创建时间:  2019/4/15 13:48
 * 功能介绍:
 */
public class FilesUtil {
    private List<String> absolutePaths = new LinkedList<>();

    /*
     * 通过递归得到某一路径下所有的目录及其文件(记得必须得传目錄，不能传文件)
     */
    public List<String> getFiles(String filePath) {

        File root = new File(filePath);
        File[] files = root.listFiles();
        if (files == null) {//如果传进来的是文件，而不是目录
            absolutePaths.add(filePath);
            return absolutePaths;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file.getAbsolutePath());
                // System.out.println("显示 " + filePath + " 下所有子目录及其文件:" +
                // file.getAbsolutePath());
            } else {// 默认为没有目录，只有文件
                if (!file.getAbsolutePath().toString().contains("_SUCCESS"))
                    absolutePaths.add(file.getAbsolutePath().toString());
            }
        }
        return absolutePaths;
    }
}
