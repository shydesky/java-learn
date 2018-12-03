package package16;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-10-25
 **/

public class DequeTest {

  public static void main(String[] args) {
    Deque<Integer> stack1 = new LinkedList<>();
    System.out.println("stack1 展示队列操作");
    for(int a=1; a<=3; a++){
      stack1.add(a);
      System.out.println("入队：" + a);
    }
/*    stack1.push(4);
    Iterator iter = stack1.iterator();
    while(iter.hasNext()){
      System.out.println(iter.next());
    }*/

    int i = stack1.peekLast();
    System.out.println("最后一个元素是队尾：" + i);
    System.out.println("当前队列大小：" + stack1.size());

    Deque<Integer> stack2 = new LinkedList<>();
    System.out.println("stack2 展示栈操作");

    for(int b=1; b<=3; b++){
      stack2.push(b);
      System.out.println("入栈：" + b);
    }

/*    stack2.add(4);
    Iterator iter = stack2.iterator();
    while(iter.hasNext()) {
      System.out.println(iter.next());
    }*/

    int j = stack2.peekLast();
    System.out.println("最后一个元素是栈底：" + j);
    System.out.println("当前栈大小：" + stack2.size());
  }
}