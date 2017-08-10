/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import java.util.List;

public class BinaryExpr extends ASTList {
  public BinaryExpr(List<ASTree> list) {
    super(list);
  }

  public ASTree left() {
    return child(0);
  }

  public String operator() {
    return ((ASTLeaf) child(1)).token().getText();
  }

  public ASTree right() {
    return child(2);
  }
}
