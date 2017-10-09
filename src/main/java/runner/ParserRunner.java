/*
 * kyle
 *
 */
package runner;

import ast.ASTree;
import lexer.Lexer;
import ex.ParseException;
import lexer.Token;
import parser.BasicParser;

import java.io.Reader;
import java.io.StringReader;

public class ParserRunner {
  public static void main(String[] args) throws ParseException {
    Reader reader = new StringReader(" if a>3 { \\n b=3 \\n }");
    Lexer l = new Lexer(new CodeDialog());
    //Lexer l = new Lexer(reader);
    BasicParser bp = new BasicParser();
    while (l.peek(0) != Token.EOF) {
      ASTree ast = bp.parser(l);
      System.out.println("=> " + ast.toString());
    }
  }
}
