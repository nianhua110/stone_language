/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import lexer.Token;

public class Name extends ASTLeaf {
  public Name(Token token) {
    super(token);

  }

  public String name() {
    return token().getText();
  }
}
