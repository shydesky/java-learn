package package_11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-09-18
 **/

public class ListSort {

  public static void main(String[] args) {
    ArrayList<String> alist = new ArrayList<>();
    alist.add("0000123");
    alist.add("053100");
    alist.add("2312");
    alist.add("2312");
    alist.add("022");
    alist.add("412");
    alist.add("71");
    //alist.sort(null);
    Collections.sort(alist, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return Integer.valueOf(o1) > Integer.valueOf(o2)?1:Integer.valueOf(o1)==Integer.valueOf(o2)?0:-1;
      }
    });
    System.out.println(alist);
  }
}