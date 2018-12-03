package package_nine;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-09-03
 **/

public class CacheTest {
    public static void main(String[] args) {
        Cache<Integer, Long> mapCache = CacheBuilder.newBuilder()
                 .maximumSize(4).expireAfterWrite(10, TimeUnit.SECONDS).initialCapacity(4)
                 .recordStats().build();
        mapCache.put(6, 66L);

        mapCache.put(1, 11L);
        mapCache.put(2, 22L);
        mapCache.put(3, 33L);
        mapCache.put(4, 44L);
        mapCache.put(5, 55L);


/*        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        Iterator<Integer> iterator = mapCache.asMap().keySet().iterator();

        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println(mapCache.size());
        mapCache.cleanUp();
        mapCache.put(4, 4L);

        System.out.println(mapCache.size());

        mapCache.put(4, 4L);

        //mapCache.invalidateAll();
        /*mapCache.cleanUp();
        mapCache.cleanUp();*/
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*for(Map.Entry<Integer, Long> entry: mapCache.asMap().entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }*/
        System.out.println(mapCache.size());

    }
}