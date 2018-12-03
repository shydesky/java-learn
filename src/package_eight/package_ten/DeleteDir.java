package package_eight.package_ten;

import java.io.File;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-09-07
 **/

public class DeleteDir {
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void main(String[] args) {
        System.out.println(DeleteDir.deleteDir(new File("/Users/tron/src/java-tron/storage_directory_test/database/account")));
    }
}