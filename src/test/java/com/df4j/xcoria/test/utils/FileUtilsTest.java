package com.df4j.xcoria.test.utils;

import com.df4j.xcoria.exception.XcoriaException;
import org.testng.annotations.Test;

import java.io.File;

import static com.df4j.xcoria.utils.FileUtils.*;
import static org.testng.Assert.*;

public class FileUtilsTest {

    @Test
    public void testCreateNewFile() {
        // 准备
        String path = "testCreateNewFile.txt";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        // 测试
        createNewFile(file);
        assertTrue(file.exists());
        // 清理
        deleteFile(file);
        assertFalse(file.exists());
    }

    @Test
    public void testDeleteFile() {
        // 准备
        String path = "testCreateNewFile.txt";
        File file = new File(path);
        if (!file.exists()) {
            createNewFile(file);
        }
        assertTrue(file.exists());

        // 测试
        deleteFile(file);
        assertFalse(file.exists());
    }

    @Test
    public void testCheckFile() {
        // 准备
        File file = new File("test.txt");
        boolean flag = false;
        if (file.exists()) {
            deleteFile(file);
        }
        assertFalse(file.exists());
        // 测试
        flag = checkFile(file, false, false, false);
        assertFalse(flag);
        flag = checkFile(file, true, false, false);
        assertFalse(flag);
        flag = checkFile(file, true, true, false);
        assertFalse(flag);
        flag = checkFile(file, true, true, true);
        assertFalse(flag);

        createNewFile(file);
        flag = checkFile(file, false, false, false);
        assertTrue(flag);
        flag = checkFile(file, true, false, false);
        assertTrue(flag);
        flag = checkFile(file, true, true, false);
        assertTrue(flag);
        flag = checkFile(file, true, true, true);
        assertTrue(flag);
        // 清理
        deleteFile(file);

    }

    @Test
    public void testCheckDirectory() {
        String path = ".";
        boolean flag = false;
        flag = checkDirectory(new File(path), false, false, false);
        assertTrue(flag);
        flag = checkDirectory(new File(path), true, false, false);
        assertTrue(flag);
        flag = checkDirectory(new File(path), true, true, false);
        assertTrue(flag);
        flag = checkDirectory(new File(path), true, true, true);
        assertTrue(flag);
    }

    @Test
    public void testCheckAndPrepareDestFile() {
        // 准备
        File file = new File("abc/test.txt");
        if (file.exists()) {
            deleteFile(file);
        }
        File parent = file.getParentFile();
        if (parent.exists()) {
            deleteFile(parent);
        }

        // 测试
        assertFalse(file.exists());
        assertFalse(file.getParentFile().exists());

        checkAndPrepareDestFile(file, true);
        assertFalse(file.exists());
        assertTrue(file.getParentFile().exists());

        createNewFile(file);

        assertThrows(XcoriaException.class, () -> checkAndPrepareDestFile(file, false));

        checkAndPrepareDestFile(file, true);
        assertFalse(file.exists());

        // 清理
        deleteFile(file);
        deleteFile(parent);
    }

    @Test
    public void testMove() {
        // 准备
        String srcPath = "src.txt";
        String destPath = "dest.txt";
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (srcFile.exists()) {
            deleteFile(srcFile);
        }
        if (destFile.exists()) {
            deleteFile(destFile);
        }
        createNewFile(srcFile);
        assertTrue(srcFile.exists());
        assertFalse(destFile.exists());

        // 测试
        move(srcFile, destFile, false);
        assertFalse(srcFile.exists());
        assertTrue(destFile.exists());

        // 清理
        deleteFile(srcFile);
        deleteFile(destFile);
    }

    @Test
    public void testTestMove() {
        // 准备
        String srcPath = "src.txt";
        String destPath = "dest.txt";
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (srcFile.exists()) {
            deleteFile(srcFile);
        }
        if (destFile.exists()) {
            deleteFile(destFile);
        }
        createNewFile(srcFile);
        assertTrue(srcFile.exists());
        assertFalse(destFile.exists());

        // 测试
        move(srcPath, destPath, false);
        assertFalse(srcFile.exists());
        assertTrue(destFile.exists());

        // 清理
        deleteFile(srcFile);
        deleteFile(destFile);
    }

    @Test
    public void testCopy() {
        // 准备
        String srcPath = "src.txt";
        String destPath = "dest.txt";
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (srcFile.exists()) {
            deleteFile(srcFile);
        }
        if (destFile.exists()) {
            deleteFile(destFile);
        }
        createNewFile(srcFile);
        assertTrue(srcFile.exists());
        assertFalse(destFile.exists());

        // 测试
        copy(srcFile, destFile, false);
        assertTrue(srcFile.exists());
        assertTrue(destFile.exists());

        // 清理
        deleteFile(srcFile);
        deleteFile(destFile);
    }

    @Test
    public void testTestCopy() {
        // 准备
        String srcPath = "src.txt";
        String destPath = "dest.txt";
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        if (srcFile.exists()) {
            deleteFile(srcFile);
        }
        if (destFile.exists()) {
            deleteFile(destFile);
        }
        createNewFile(srcFile);
        assertTrue(srcFile.exists());
        assertFalse(destFile.exists());

        // 测试
        copy(srcPath, destPath, false);
        assertTrue(srcFile.exists());
        assertTrue(destFile.exists());

        // 清理
        deleteFile(srcFile);
        deleteFile(destFile);
    }
}