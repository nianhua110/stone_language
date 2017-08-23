/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package chap7;

import ast.BlockStmnt;
import ast.ParameterList;
import chap6.Environment;

public class Function {
  protected ParameterList parameters;
  protected BlockStmnt body;
  protected Environment env;

  public Function(ParameterList parameters, BlockStmnt body, Environment env) {
    this.parameters = parameters;
    this.body = body;
    this.env = env;
  }

  public ParameterList parameters() {
    return parameters;
  }

  public BlockStmnt body() {
    return body;
  }

  public Environment makeEnv() {
    return new NestedEnv(env);
  }

  @Override
  public String toString() {
    return "<fun:" + hashCode() + ">";
  }
}
