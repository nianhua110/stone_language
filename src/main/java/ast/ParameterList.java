/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import ast.ASTList;
import ast.ASTree;

import java.util.List;

public class ParameterList extends ASTList {
  public ParameterList(List<ASTree> list) {
    super(list);
  }

  public String name(int i) {
    return ((ASTLeaf) child(i)).token().getText();
  }

  public int size() {
    return numChildren();
  }
}
