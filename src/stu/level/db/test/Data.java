package stu.level.db.test;

import java.util.Random;

/**
 * @program: java-learn
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2019-01-11
 **/

public class Data {
  public byte[] key;
  public byte[] value;

  public Data(byte[] key, byte[] value){
    this.key = key;
    this.value = value;
  }

  public static byte[] generateBytes(int length) {
    Random random = new Random();
    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }
    return sb.toString().getBytes();
  }

  public static void main(String[] args) {
    System.out.println(new String(new Data(null, null).generateBytes(10)));
  }

}