/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import java.util.List;

public class NegativeExpr extends ASTList {
  public NegativeExpr(List<ASTree> list) {
    super(list);
  }

  public ASTree operand() {
    return child(0);
  }

  @Override
  public String toString() {
    return "-" + operand();
  }
}
