/*
 * kyle
 *
 */
package runner;

import ast.ASTree;
import ast.NullStmnt;
import chap6.BasicEnv;
import chap6.BasicEvaluator;
import chap6.Environment;
import ex.ParseException;
import lexer.Lexer;
import lexer.Token;
import parser.BasicParser;

public class BasicInterpreter {
  public static void run(BasicParser bp, Environment env) throws ParseException {
    Lexer lexer = new Lexer(new CodeDialog());
    while (lexer.peek(0) != Token.EOF) {
      ASTree t = bp.parser(lexer);
      if (!(t instanceof NullStmnt)) {
        Object r = ((BasicEvaluator.ASTreeEx) t).eval(env);
        System.out.println("= >" + r);
      }
    }
  }

  public static void main(String[] args) throws ParseException {
    run(new BasicParser(), new BasicEnv());
  }
}
