/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package runner;


import lexer.Lexer;
import ex.ParseException;
import lexer.Token;


public class LeterRunner {
  public static void main(String[] args) throws ParseException {
    Lexer l = new Lexer(new CodeDialog());
    for (Token t; (t = l.read()) != Token.EOF; ) {
      System.out.println(t.getText());
    }
  }
}
