package package13;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-11-02
 **/

class BackupNo{

  Properties prop = new Properties();

  public int getNumber() {
    InputStream in = null;
    try {
      in = new BufferedInputStream(new FileInputStream("/Users/tron/src/java-test-1/src/package13/a.properties"));
      prop.load(in);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Integer.valueOf((String) prop.get("number"));
  }

  public void setNumber(int number){

    FileOutputStream oFile = null;
    try {
      oFile = new FileOutputStream("/Users/tron/src/java-test-1/src/package13/a.properties");
      prop.setProperty("number", String.valueOf(number));
      prop.store(oFile, "The New properties file");
      oFile.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isSuccess(){
    InputStream in = null;
    try {
      in = new BufferedInputStream(new FileInputStream("/Users/tron/src/java-test-1/src/package13/a.properties"));
      prop.load(in);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return (Boolean)prop.get("success");
  }

  public void setSuccess(boolean sucess){
    FileOutputStream oFile = null;
    try {
      oFile = new FileOutputStream("/Users/tron/src/java-test-1/src/package13/a.properties");
      prop.setProperty("success", String.valueOf(sucess));
      oFile.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
