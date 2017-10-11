/*
 *
 */
package chap8;

import chap7.ClosureEvaluator;
import javassist.gluonj.util.Loader;

import static java.lang.System.in;

/**
 * @author kyle
 */
public class NativeRunner {

  public static void main(String[] args) throws Throwable {
    Loader.run(NativeIntercerpeter.class, args, NativeEvaluator.class, ClosureEvaluator.class);
  }
}
/*
def fib(n){
   if n < 2 {
      n
   } else {
     fib(n-1) + fib(n-2)
   }

}
t = currentTime()
fib 15
print currentTime() - t + "mesc"

 */