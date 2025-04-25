package com.df4j.xcoria.utils;

import com.df4j.xcoria.exception.XcoriaException;

import java.io.*;

public class FileUtils {

    /**
     * 创建新文件
     *
     * @param file
     */
    public static void createNewFile(File file) {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            file.createNewFile();
        } catch (Exception e) {
            throw new XcoriaException("创建新文件失败, path: " + file.getAbsolutePath(), e);
        }
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                throw new XcoriaException("删除文件失败, path: " + file.getAbsolutePath(), e);
            }
        }
    }

    /**
     * 检测文件，默认仅仅金策是否存在，根据参数检测是否可读，可写，可执行。所有的条件需要都满足
     *
     * @param file            目标文件
     * @param checkCanRead    是否检测可读
     * @param checkCanWrite   是否检测可写
     * @param checkCanExecute 是否检测可执行
     * @return 是否
     */
    public static boolean checkFile(File file, boolean checkCanRead, boolean checkCanWrite, boolean checkCanExecute) {
        return file.exists()
                && file.isFile()
                && (!checkCanRead || file.canRead())
                && (!checkCanWrite || file.canWrite())
                && (!checkCanExecute || file.canExecute());
    }

    /**
     * 检测目录
     *
     * @param file            目标文件
     * @param checkCanRead    是否检测可读
     * @param checkCanWrite   是否检测可写
     * @param checkCanExecute 是否检测可执行
     * @return 是否
     */
    public static boolean checkDirectory(File file, boolean checkCanRead, boolean checkCanWrite, boolean checkCanExecute) {
        return file.exists()
                && file.isDirectory()
                && (!checkCanRead || file.canRead())
                && (!checkCanWrite || file.canWrite())
                && (!checkCanExecute || file.canExecute());
    }

    /**
     * 检测目标地址是否存在文件，并做相关清理或者创建处理，以便后续在目标地址写入文件
     *
     * @param file      目标文件
     * @param overwrite 如果目标地址存在文件,是否覆盖
     */
    public static void checkAndPrepareDestFile(File file, boolean overwrite) {
        try {
            if (file.exists()) {
                if (file.isFile()) {
                    if (overwrite) {
                        file.delete();
                    } else {
                        throw new XcoriaException("目标地址文件存在，dest:" + file.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    throw new XcoriaException("目标文件为目录，请先处理目录, dest:" + file.getAbsolutePath());
                }

            } else {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
            }
        } catch (Exception e) {
            ThrowUtils.rethrow("检测文件是否存在抛出异常", e);
        }
    }

    /**
     * 移动文件（重命名）
     *
     * @param src       文件路径
     * @param dest      目标路径
     * @param overwrite 是否覆盖存在的文件
     */
    public static void move(String src, String dest, boolean overwrite) {
        File srcFile = new File(src);
        File destFile = new File(dest);
        move(srcFile, destFile, overwrite);
    }

    /**
     * 移动文件
     *
     * @param src       源文件
     * @param dest      目标文件
     * @param overwrite 是否覆盖
     */
    public static void move(File src, File dest, boolean overwrite) {
        checkAndPrepareDestFile(dest, overwrite);
        boolean flag;
        try {
            flag = src.renameTo(dest);
        } catch (Exception e) {
            throw new XcoriaException("移动文件失败", e);
        }
        if (flag) {
            return;
        }
        throw new XcoriaException("移动文件失败, dest:" + dest.getAbsolutePath());
    }

    /**
     * 复制文件
     *
     * @param src       文件路径
     * @param dest      目标路径
     * @param overwrite 是否覆盖存在的文件
     */
    public static void copy(String src, String dest, boolean overwrite) {
        File srcFile = new File(src);
        File destFile = new File(dest);
        copy(srcFile, destFile, overwrite);
    }


    /**
     * 复制文件
     *
     * @param src       源文件地址
     * @param dest      目标文件地址
     * @param overwrite 是否覆盖
     */
    public static void copy(File src, File dest, boolean overwrite) {
        checkAndPrepareDestFile(dest, overwrite);
        createNewFile(dest);
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            ThrowUtils.rethrow("复制文件失败", e);
        } finally {
            CloseUtils.closeAll(out, in);
        }
    }
}
