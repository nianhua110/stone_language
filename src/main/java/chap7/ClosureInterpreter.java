/*
 *
 *
 */
package chap7;

import ex.ParseException;
import parser.ClosureParser;
import runner.BasicInterpreter;

import static java.lang.System.in;

/**
 * @author kyle
 */
public class ClosureInterpreter extends BasicInterpreter {

  public static void main(String[] args) throws ParseException {
    run(new ClosureParser(), new NestedEnv());
  }
}
