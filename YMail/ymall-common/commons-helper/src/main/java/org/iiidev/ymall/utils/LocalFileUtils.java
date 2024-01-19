/*
 * Copyright (c) 2024. 版权归III_Delay所有
 */

package org.iiidev.ymall.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * LocalFileUtils
 *
 * @Author IIIDelay
 * @Date 2024/1/19 19:38
 **/
public class LocalFileUtils {
    private static final List<String> toRemoveList = Lists.newArrayList(toRemoveStr0, toRemoveStr1, toRemoveStr2, toRemoveStr3, toRemoveStr4);
    private static int count = 0;
    private static int preCount = 0;

    public static void main(String[] args) throws IOException {
        delFileAndDirectory("E:...");
    }

    public static void delFileAndDirectory(String path) {
        File file = new File(path);
        if (ArrayUtils.isNotEmpty(file.listFiles())) {
            childDel(file.listFiles(), Lists.newArrayList("__MACOSX", ""));
        }
    }

    private static void childDel(File[] files, List<String> toDelFileNames) {
        if (preCount != count) {
            System.out.println("count = " + count);
        }
        preCount = count;
        if (count > 50000) {
            throw new RuntimeException("文件在不停的重复超过50000次危险退出!!!");
        }

        for (File file : files) {
            if (ArrayUtils.isNotEmpty(file.listFiles())) {
                count++;
                childDel(file.listFiles(), toDelFileNames);
            }
            if (file.isFile() && toDelFileNames.contains(file.getName())) {
                count++;
                boolean del = FileUtil.del(file);
                System.out.println(del ? (file.getName() + " 删除成功 " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS")) : (file.getName() + " 删除失败" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS")));
            } else if (file.isFile() && FileUtil.isEmpty(file)) {
                count++;
                boolean del = FileUtil.del(file);
                System.out.println(del ? (file.getName() + " 删除成功" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS")) : (file.getName() + " 删除失败" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS")));
            } else if (file.isDirectory() && FileUtil.isDirEmpty(file)) {
                count++;
                boolean del = FileUtil.del(file);
                System.out.println(del ? (file.getName() + " 删除成功" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS")) : (file.getName() + " 删除失败" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS")));
            } else if (file.isDirectory()) {
                String currentPathName = file.toPath().getFileName().toString();
                if (toDelFileNames.contains(currentPathName)) {
                    count++;
                    boolean del = FileUtil.del(file);
                    System.out.println(del ? (file.getName() + " 删除成功" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS")) : (file.getName() + " 删除失败" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS")));
                }
            }
        }
    }

    /**
     * rename 替换名称
     *
     * @param filePath filePath
     * @throws IOException
     */
    public static void rename(String filePath, List<String> toRemoveStr) throws IOException {
        File file = new File(filePath);
        File[] files = file.listFiles();
        if (ArrayUtils.isNotEmpty(files)) {
            childRename(files, toRemoveStr);
        }
    }

    private static void childRename(File[] files, List<String> toRemoveStr) throws IOException {

        if (preCount != count) {
            System.out.println("count = " + count);
        }
        preCount = count;
        if (count > 50000) {
            throw new RuntimeException("文件在不停的重复超过50000次危险退出!!!");
        }

        for (File file : files) {
            if (ArrayUtils.isNotEmpty(file.listFiles())) {
                childRename(file.listFiles(), toRemoveStr);
            }
            if (file.isFile() || file.isDirectory()) {
                String fileName = file.getName();
                for (String toRemove : toRemoveStr) {
                    if (fileName.contains(toRemove)) {
                        String newName = StrUtil.removeAll(fileName, toRemove);
                        if (file.isFile()) {
                            count++;
                            File tempFile = file;
                            file = FileUtil.rename(file, newName, true);
                            System.out.println(tempFile.getName() + " 变更为文件=> " + file.getName() + " " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS"));
                        } else if (file.isDirectory()) {
                            count++;
                            File tempFile = file;
                            String string = file.toPath().getFileName().toString();
                            String replace = StringUtils.replace(string, toRemove, "");
                            File fileNew = PathUtil.rename(file.toPath(), StringUtils.isBlank(replace) ? IdUtil.getSnowflakeNextIdStr() : replace, true).toFile();
                            System.out.println(tempFile.getPath() + " 变更为路径=> " + fileNew.getName() + " " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss:SS"));
                        }
                    }
                }
            }
        }
    }
}