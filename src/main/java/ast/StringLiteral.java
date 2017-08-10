/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import lexer.Token;

public class StringLiteral extends ASTLeaf {
  public StringLiteral(Token token) {
    super(token);
  }

  public String value() {
    return token().getText();
  }
}
