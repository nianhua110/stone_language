/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package chap16;

import ast.ASTLeaf;
import ast.ASTree;
import ast.BinaryExpr;
import ast.NumberLiteral;
import lexer.Lexer;
import lexer.ParseException;
import lexer.Token;
import parser.Parser;
import runner.CodeDialog;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;

public class OpPrecedenceParser {
  private Lexer lexer;

  protected HashMap<String, Precedence> operators;

  public static class Precedence {
    int value;
    boolean leftAssoc;

    public Precedence(int value, boolean leftAssoc) {
      this.value = value;
      this.leftAssoc = leftAssoc;
    }
  }

  public OpPrecedenceParser(Lexer lexer) {
    this.lexer = lexer;
    operators = new HashMap<>();
    operators.put("<", new Precedence(1, true));
    operators.put(">", new Precedence(1, true));
    operators.put("+", new Precedence(2, true));
    operators.put("-", new Precedence(2, true));
    operators.put("*", new Precedence(3, true));
    operators.put("/", new Precedence(3, true));
    operators.put("^", new Precedence(4, true));

  }

  public ASTree expression() throws ParseException {
    ASTree right = factor();
    Precedence next;
    while ((next = nextOperator()) != null) {
      right = doShift(right, next.value);
    }
    return right;
  }

  private ASTree doShift(ASTree left, int prec) throws ParseException {
    ASTLeaf op = new ASTLeaf(lexer.read());
    ASTree right = factor();
    Precedence next;
    while ((next = nextOperator()) != null && rightIsExpr(prec, next)) {
      right = doShift(right, next.value);
    }

    return new BinaryExpr(Arrays.asList(left, op, right));
  }

  private Precedence nextOperator() throws ParseException {
    Token t = lexer.peek(0);
    if (t.isIndentifier()) {
      return operators.get(t.getText());
    } else {
      return null;
    }
  }

  private static boolean rightIsExpr(int prec, Precedence nextPrec) {
    if (nextPrec.leftAssoc) {
      return prec < nextPrec.value;
    } else {
      return prec <= nextPrec.value;
    }
  }

  public ASTree factor() throws ParseException {
    if (isToken("(")) {
      token("(");
      ASTree e = expression();
      token(")");
      return e;
    } else {
      Token t = lexer.read();
      if (t.isNumber()) {
        NumberLiteral n = new NumberLiteral(t);
        return n;
      } else {
        throw new ParseException(t);
      }
    }
  }

  void token(String name) throws ParseException {
    Token t = lexer.read();
    if (!t.isIndentifier() && name.equals(t.getText()))
      throw new ParseException(t);
  }

  boolean isToken(String name) throws ParseException {
    Token t = lexer.peek(0);
    return t.isIndentifier() && name.equals(t.getText());
  }

  public static void main(String[] args) throws  ParseException{
    Reader r = new StringReader(" 3+4*5-6+8*9+2/1");
    Lexer lexer = new Lexer(r);
    OpPrecedenceParser p = new OpPrecedenceParser(lexer);
    ASTree t = p.expression();
    System.out.println("=> "+t);

  }
}
