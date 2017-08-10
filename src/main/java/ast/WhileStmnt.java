/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package ast;

import java.util.List;

public class WhileStmnt extends  ASTList {
  public WhileStmnt(List<ASTree> list) {
    super(list);
  }

  public ASTree conditon(){
    return  child(0);
  }

  public ASTree body(){
    return child(1);
  }

  public String toString(){
    return "(while "+conditon()+ " "+ body() + ")";
  }
}
