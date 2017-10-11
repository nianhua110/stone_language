/*
 *
 */
package chap8;

import chap7.NestedEnv;
import ex.ParseException;
import parser.ClosureParser;
import runner.BasicInterpreter;

/**
 * @author kyle
 */
public class NativeIntercerpeter extends BasicInterpreter {

  public static void main(String[] args) throws ParseException {
    run(new ClosureParser(), new Natives().environment(new NestedEnv()));
  }
}
