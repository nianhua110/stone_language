/*
 * kyle
 *
 */

import java.util.Arrays;
import java.util.List;

public class ClinitTest {
  static int  a= 6;

  static {
    a=5;
  }

  public static class Children extends ClinitTest{
    static int b =8;
    static {
      a=67;
    }
  }

  public static void main(String[] args){
   // System.out.println(ClinitTest.a);
    System.out.println(Children.a);
    System.out.println(Children.b);
    System.out.println(Children.a);
    System.out.println(ClinitTest.a);

    Integer a = 1;
    Integer b = 2;
    Integer c = 3;
    Integer d = 3;
    Integer e = 321;
    Integer f = 321;
    Integer s = a+b;
    Long g = 3L;
    System.out.println(c == d);
    //todo  为什么e==f 是false
    //原因 这是源码中的 Integer 中 valueOf，也就是说cache中已有-128到127，不在这范围的会新new ，这时可以理解比较是内存地址
    System.out.println(e == f);
    System.out.println(c == (a + b));
    System.out.println(c.equals(a + b));
    System.out.println(g == (a + b));
    System.out.println(g.equals(a + b));

    //泛型 自动装箱,自动拆箱,便利循环,变长参数;
    List<Integer> list= Arrays.asList(1,2,3,4);
    int sum=0;
    for (int integer : list) {
      sum+=integer;
    }
    System.out.println(sum);
  }
}
