/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import lexer.Token;

public class NumberLiteral extends ASTLeaf {
  public NumberLiteral(Token t){
    super(t);
  }

  public int val(){
    return token().getLineNumber();
  }
}
