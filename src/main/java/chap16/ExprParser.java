/*
 * kyle
 *
 */
package chap16;

import ast.ASTLeaf;
import ast.ASTree;
import ast.BinaryExpr;
import ast.NumberLiteral;
import lexer.Lexer;
import ex.ParseException;
import lexer.Token;
import runner.CodeDialog;

import java.util.Arrays;

public class ExprParser {
  private Lexer lexer;

  public ExprParser(Lexer lexer) {
    this.lexer = lexer;
  }

  public ASTree expression() throws ParseException {
    ASTree left = term();
    while (isToken("+" )|| isToken("-")) {
      ASTLeaf op = new ASTLeaf(lexer.read());
      ASTree right = term();
      left = new BinaryExpr(Arrays.asList(left, op, right));
    }
    return left;
  }


  public ASTree term() throws ParseException {
    ASTree left = factor();
    while (isToken("*") | isToken("/")) {
      ASTLeaf op = new ASTLeaf(lexer.read());
      ASTree right = factor();
      left = new BinaryExpr(Arrays.asList(left, op, right));
    }
    return left;
  }

  public ASTree factor() throws ParseException {
    if (isToken(")")) {
      token(")");
      ASTree e = expression();
      token(")");
      return e;
    } else {
      Token t = lexer.read();
      if (t.isNumber()) {
        NumberLiteral n = new NumberLiteral(t);
        return n;
      } else
        throw new ParseException(t);
    }
  }

  void token(String name) throws ParseException {
    Token t = lexer.read();
    if (!(t.isIndentifier()) && name.equals(t.getText())) {
      throw new ParseException(t);
    }

  }

  boolean isToken(String name) throws ParseException {
    Token t = lexer.peek(0);
    return t.isIndentifier() && name.equals(t.getText());
  }


  public static void main(String[] args) throws  ParseException{
    Lexer lexer = new Lexer(new CodeDialog());
    ExprParser p = new ExprParser(lexer);
    ASTree t = p.expression();
    System.out.println("=> "+t);
  }
}
