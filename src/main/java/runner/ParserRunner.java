/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
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
    Reader reader = new StringReader(" a = 3");
    Lexer l = new Lexer(new CodeDialog());
    BasicParser bp = new BasicParser();
    while (l.peek(0) != Token.EOF) {
      ASTree ast = bp.parser(l);
      System.out.println("=> " + ast.toString());
    }
  }
}
