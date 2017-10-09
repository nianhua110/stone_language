/*
 * kyle
 *
 */
package runner;

import chap7.FuncEvaluator;
import javassist.gluonj.util.Loader;

public class FuncRunner {
  public static void main(String[] args) throws Throwable {
    Loader.run(FuncInterpretre.class, args, FuncEvaluator.class);
  }
}
