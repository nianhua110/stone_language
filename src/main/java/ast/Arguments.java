/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import java.util.List;

public class Arguments extends Postifix {
  public Arguments(List<ASTree> list) {
    super(list);
  }

  protected int size() {
    return numChildren();
  }
}
