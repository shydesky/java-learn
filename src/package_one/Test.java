package package_one;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-07-16
 **/

public class Test {
    public static void main(String[] args) {

        /*Trans t = new Trans();
        Thread a = new Thread(new MyThread(t, 1));
        Thread b = new Thread(new MyThread(t, 2));
*/
        //a.start();
        //b.start();
        //System.out.println("5ed1b9a8bd6adf5e13a563be48603ead7e3fd687b3dc664a51dd75100583ee44bdca56a6b22863306f4fc7938b02beed7a677f4c178d4165f170fd057d8164ad".length());

      /*  ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Thread a = new Thread(new MyThread2(map, 100));
        Thread b = new Thread(new MyThread2(map, 200));
        a.start();
        b.start();
        map.put("a", 1);
        System.out.println(map.size());
        boolean flag=true;
        while(flag) {
            map.forEach((key, value) -> System.out.println(key));
            flag=false;
        }*/

        Date date=new Date(System.currentTimeMillis());
        System.out.println(date);
    }
}