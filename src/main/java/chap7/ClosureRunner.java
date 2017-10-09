/*
 *
 *
 */
package chap7;

import chap7.ClosureEvaluator;
import chap7.ClosureInterpreter;
import javassist.gluonj.util.Loader;

/**
 * @author kyle
 */
public class ClosureRunner {

  public static void main(String[] args) throws Throwable {
    Loader.run(ClosureInterpreter.class, args, ClosureEvaluator.class);
  }
}
