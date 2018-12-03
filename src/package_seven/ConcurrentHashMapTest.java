package package_seven;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-08-25
 **/

public class ConcurrentHashMapTest {
    public static void main(String[] args) throws Exception {
        ConcurrentHashMap<Integer, Object> map = new ConcurrentHashMap();

        map.put(1, "value 1");
        map.put(20, "value 20");
        map.put(21, "value 21");
        map.put(50, "value 50");
        map.put(200, "value 200");
        map.put(5, "value 5");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                }catch(InterruptedException e){

                }
                map.put(10,"value 10");
                Object a = map.remove(20);
                System.out.println(a);
            }
        }).start();

        for(Map.Entry<Integer, Object> e : map.entrySet()){
            System.out.println(e.getValue());
            Thread.sleep(1000);
        }
        System.out.println(map.get(20));
    }

}
