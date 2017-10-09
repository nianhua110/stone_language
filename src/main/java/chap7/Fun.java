/*
 * kyle
 *
 */
package chap7;

import ast.ASTList;
import ast.ASTree;
import ast.BlockStmnt;
import ast.ParameterList;

import java.util.List;

/**
 * @author kyle
 */
public class Fun extends ASTList {
  public Fun(List<ASTree> list) {
    super(list);
  }

  public ParameterList parametes() {
    return (ParameterList) child(0);
  }

  public BlockStmnt body() {
    return (BlockStmnt) child(1);
  }

  @Override
  public String toString() {
    return "(fun " + parametes() + " " + body() + ")";
  }
}
