/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import java.util.List;

public abstract class Postifix extends ASTList {
  public Postifix(List<ASTree> list) {
    super(list);
  }
}
