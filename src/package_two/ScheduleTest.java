package package_two;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: java-test
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-08-17
 **/

public class ScheduleTest {
    public static void main(String args[]){
        ScheduleTest2 cli = new ScheduleTest2();
        for(int i=0;i<2;i++){
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){

            }
            System.out.println(i);
            cli.main(i);
        }
    }
}


class ScheduleTest2 {

    public void main(int i) {
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                func(i);
            }
        }, 5, 2, TimeUnit.SECONDS);
    }

    public void func(int i){
        synchronized (this) {
            try {
                //System.out.println(new Date(System.currentTimeMillis()));
                Thread.sleep(1000);
                //System.out.println(this.hashCode());
                System.out.println(i);
                System.out.println(new Date(System.currentTimeMillis()));
            } catch (Exception e) {

            }
        }
    }
}