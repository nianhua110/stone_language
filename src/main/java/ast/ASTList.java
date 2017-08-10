/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {
  protected List<ASTree> children;

  public ASTList(List<ASTree> list) {
    children = list;
  }

  public ASTree child(int i) {
    return children.get(i);
  }

  public int numChildren() {
    return children.size();
  }

  public Iterator<ASTree> children() {
    return children.iterator();
  }

  @Override
  public String toString() {
    String str = children.stream()
        .map(in -> in.toString())
        .reduce((a, b) -> a + " " + b)
        .orElse("");
    return "( " + str + " )";
  }

  public String location() {
    return children.stream()
        .map(in -> in.location())
        .filter(in -> in != null)
        .findFirst()
        .orElse(null);
  }
}
