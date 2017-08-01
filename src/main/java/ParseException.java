/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */

import java.io.IOException;

public class ParseException extends Exception {
  public ParseException(Throwable cause) {
    super(cause);
  }

  public ParseException(String message) {
    super(message);
  }

  public ParseException(IOException e) {
    super(e);
  }

  public ParseException(Token token) {
    this("", token);
  }

  public ParseException(String mes, Token token) {
    super("syntax error arount " + location(token) + "." + mes);
  }

  public static String location(Token t) {
    if (t == Token.EOF) {
      return "the last line";
    } else {
      return "\"" + t.getText() + "\" at line " + t.getLineNumber();
    }
  }
}
