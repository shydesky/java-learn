package package_12;

/**
 * @program: java-test-1
 * @description:本例用来说明java GC的一些原理
 * @author: shydesky@gmail.com
 * @create: 2018-09-19
 **/

/**
 -Xms60m
 -Xmx60m
 -Xmn20m
 -XX:NewRatio=2 ( 若 Xms = Xmx, 并且设定了 Xmn, 那么该项配置就不需要配置了 )
 -XX:SurvivorRatio=8
 -XX:MaxPermSize=30m
 -XX:+PrintGCDetails
 */
public class TestVm{
  public void doTest(){
    Integer M = new Integer(1024 * 1024 * 1);  //单位, 兆(M)
    byte[] bytes = new byte[100 * M]; //申请 1M 大小的内存空间
    //bytes = null;  //断开引用链
    //System.gc();   //通知 GC 收集垃圾
    System.out.println();       //
    bytes = new byte[1 * M];  //重新申请 1M 大小的内存空间
    bytes = new byte[1 * M];  //再次申请 1M 大小的内存空间
    System.gc();
    System.out.println();
  }
  public static void main(String[] args) {
    new TestVm().doTest();
  }
}

/* ********************************************************************控制台输出结果***************************************************************************************************************************************
[GC (System.gc()) [PSYoungGen: 3372K->688K(18432K)] 3372K->696K(59392K), 0.0050092 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
    [Full GC (System.gc()) [PSYoungGen: 688K->0K(18432K)] [ParOldGen: 8K->504K(40960K)] 696K->504K(59392K), [Metaspace: 3159K->3159K(1056768K)], 0.0155083 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]

    [GC (System.gc()) [PSYoungGen: 2703K->1152K(18432K)] 3207K->1656K(59392K), 0.0030915 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
    [Full GC (System.gc()) [PSYoungGen: 1152K->0K(18432K)] [ParOldGen: 504K->1493K(40960K)] 1656K->1493K(59392K), [Metaspace: 3167K->3167K(1056768K)], 0.0093903 secs] [Times: user=0.01 sys=0.01, real=0.01 secs]

    Heap
    PSYoungGen      total 18432K, used 819K [0x00000007bec00000, 0x00000007c0000000, 0x00000007c0000000)
    eden space 16384K, 5% used [0x00000007bec00000,0x00000007becccef8,0x00000007bfc00000)
    from space 2048K, 0% used [0x00000007bfe00000,0x00000007bfe00000,0x00000007c0000000)
    to   space 2048K, 0% used [0x00000007bfc00000,0x00000007bfc00000,0x00000007bfe00000)
    ParOldGen       total 40960K, used 1493K [0x00000007bc400000, 0x00000007bec00000, 0x00000007bec00000)
    object space 40960K, 3% used [0x00000007bc400000,0x00000007bc575558,0x00000007bec00000)
    Metaspace       used 3173K, capacity 4496K, committed 4864K, reserved 1056768K
class space    used 347K, capacity 388K, committed 512K, reserved 1048576K
*************************************************************************************************************************************************************************************************************** */

/****结果分析
 1、新生代分配的内存为20m，分三个区域Eden、Survior1（from）、Survior2（to） 设置的SurvivorRatio=8，故大小比例为 Eden：Survior1（from）：Survior2（to）= 8：1：1，from和to两个区域始终有一个区域是空的，用于新生代的gc，故新生代实际可以
    用的内存是18m，即90%。
 2、GC分为两类：minorGC和fullGC(或称为majorGC)，在hotspot的GC实现中，System.gc()方法默认会先触发minorGC，然后再触发fullGC。
    注意：当 Full GC 进行的时候，默认的方式是尽量清空新生代(YoungGen)，因此在调System.gc()时，新生代(YoungGen)中存活的对象会提前进入老年代。从第两次fullGC执行的结果就可以看出来新生代(YoungGen)中存活的对象提前进入老年代了。
    -XX:-ScavengeBeforeFullGC 这个参数可以阻止fullGC之前先执行minorGC
 ****/