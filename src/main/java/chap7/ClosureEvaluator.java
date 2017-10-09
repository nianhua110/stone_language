/*
 *
 *
 */
package chap7;

import ast.ASTree;
import chap6.Environment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import java.util.List;

/**
 * @author kyle
 */
@Require(FuncEvaluator.class)
@Reviser
public class ClosureEvaluator {
  @Reviser
  public static class FunEx extends Fun {
    public FunEx(List<ASTree> list) {
      super(list);
    }

    public Object eval(Environment env) {
      return new Function(parametes(), body(), env);
    }
  }

}
