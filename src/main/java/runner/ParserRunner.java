/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package runner;

import ast.ASTree;
import lexer.Lexer;
import lexer.ParseException;
import lexer.Token;
import parser.BasicParser;

public class ParserRunner {
  public static void main(String[] args) throws ParseException {
    Lexer l = new Lexer(new CodeDialog());
    BasicParser bp = new BasicParser();
    while (l.peek(0) != Token.EOF) {
      ASTree ast = bp.parser(l);
      System.out.println("=> " + ast.toString());
    }
  }
}
