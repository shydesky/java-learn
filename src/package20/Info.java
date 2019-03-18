package package20;

/**
 * @program: java-learn
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2019-03-18
 **/

public class Info<T> {

  private T var;     // 定义泛型变量

  public void setVar(T var) {
    this.var = var;
  }

  public T getVar() {
    return this.var;
  }

  public String toString() {   // 直接打印
    return this.var.toString();
  }

};