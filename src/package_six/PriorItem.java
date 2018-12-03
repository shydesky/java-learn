package package_six;

import javax.print.event.PrintEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-08-22
 **/

public class PriorItem implements java.lang.Comparable<PriorItem> {

    private int count;

    private Item item;

    @Override
    public int compareTo(PriorItem o) {
        return Integer.compare(this.count , o.getCount());
    }

    public PriorItem(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public Item getItem(){
        return item;
    }

    public int getCount(){
        return - count;
    }

    public static void main(String[] args) {
        String a1 = "6b74ca933mfm7ff977t";


        String a2 = "9cbedc42mff39f6d35a5t";
        String a3 = "mct";
        String a4 = "cmdaemf7d54b4et";
        String a5 = "et";

        String s1 = a1+a2+a3+a4+a5;
        String s2 = s1.replace("m", "0");
        String s3 = s2.replace("t", "8");

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);


        /*ConcurrentHashMap<Integer, PriorItem> advObjToFetch = new ConcurrentHashMap<Integer, PriorItem>();
        List<Item> outcome = new ArrayList<>();
        *//*for(int i=10; i>0; i--){*//*
        advObjToFetch.put(1, new PriorItem(new Item(1), 1));
        advObjToFetch.put(3, new PriorItem(new Item(3), 3));
        advObjToFetch.put(7, new PriorItem(new Item(7), 7));
        advObjToFetch.put(2, new PriorItem(new Item(2), 2));
        advObjToFetch.put(5, new PriorItem(new Item(5), 5));
        advObjToFetch.put(6, new PriorItem(new Item(6), 6));
*/
        /*}*/
        /*List<PriorItem> alist = new ArrayList<PriorItem>(advObjToFetch.values());
        alist.stream().sorted(Comparator.comparing(PriorItem::getCount)).forEach(idToFetch ->{
            outcome.add(idToFetch.getItem());
        });

        for(int j=0; j<outcome.size(); j++){
            System.out.println(outcome.get(j).getA());
        }*/
    }
}
 class Item{

    private int a;
    public Item(int a){
        this.a = a;
    }

    public int getA(){
        return a;
    }
 }
