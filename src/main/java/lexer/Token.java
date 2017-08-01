package lexer;/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */

public class Token {
  public static final Token EOF = new Token(-1) {
  };
  public static final String EOL = "\\n";

  private int lineNumber;

  public Token(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public Token setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
    return this;
  }

  public boolean isIndentifier() {
    return false;
  }

  public boolean isNumber() {
    return false;
  }

  public boolean isString() {
    return false;
  }

  public String getText() {
    return "";
  }
}
