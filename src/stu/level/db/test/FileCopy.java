package stu.level.db.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.iq80.leveldb.util.FileUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-10-11
 **/

@EnableAspectJAutoProxy
public class FileCopy {

  public static void main(String[] args) {
    try {
      long t0 = System.currentTimeMillis();
      FileCopy fc = new FileCopy();
      fc.backup();
      System.out.println("backup use " + String.valueOf(System.currentTimeMillis() - t0) + "ms");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void backup() throws IOException {
    String from = "/Users/tron/src/java-tron/output-directory";
    String to = "test-copy";
    File toDirectory = new File(to);
    if(toDirectory.exists()){
      FileUtils.deleteDirectoryContents(toDirectory);
    }
    backup_directory(from, to, null);
  }

  public void backup_one_file(String src, String dest, String fileName) throws IOException{
    Path fromPath = Paths.get(src, fileName);
    Path toPath = Paths.get(dest, fileName);
    if(!toPath.toFile().exists()){
      System.out.println("create file:  " + dest + fileName);
      Files.createLink(toPath, fromPath);
    }else{
      //String dest_md5 = CheckMD5.getMD5(toPath.toFile());
      //String src_md5 = CheckMD5.getMD5(fromPath.toFile());
      if(true){
        System.out.println("replace file:  " + dest + "/" +  fileName);
        Files.createLink(toPath, fromPath);
      }
    }
  }

  public void backup_directory(String from, String to, String fileName) throws IOException{
    File[] files = new File(from).listFiles();
    File toDirectory = new File(to);
    if (!toDirectory.exists()) {
      toDirectory.mkdirs();
    }else{
      for (File tofile: toDirectory.listFiles()){
        File oldfile = new File(from + "/" + tofile.getName());
        if(!oldfile.exists()){
          System.out.println("delete file:  " + to + "/" + tofile.getName());
          tofile.delete();
        }
      }
    }

    //backup modified file or directory where in src
    for (File file : files) {
      if (file.isDirectory()) {
        //System.out.println("backup dir " + from + "  " + to +"  " + file.getName());
        backup_directory(from + "/" + file.getName(), to + "/" + file.getName(), file.getName());
      }else if(file.isFile()){
        //System.out.println("backup file " + from + "  " + to +"  " +file.getName());
        backup_one_file(from, to, file.getName());
      }
    }
  }
}