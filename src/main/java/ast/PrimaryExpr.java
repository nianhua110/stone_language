/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import java.util.List;

public class PrimaryExpr extends ASTList {
  public PrimaryExpr(List<ASTree> list) {
    super(list);
  }

  public static ASTree create(List<ASTree> c){
    return  c.size() == 1 ? c.get(0):new PrimaryExpr(c);
  }
}
