package com.fh.util.terminal;

import java.io.File;

public class DeleteFilesUtil {
	
	/**
     * 递归删除目录下的所有文件及子目录下所有文件
	 * @param filePath -- 文件路径
	 * @returns
	 */
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {	
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
