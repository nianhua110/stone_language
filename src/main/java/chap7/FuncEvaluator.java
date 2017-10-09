/*
 * kyle
 *
 */
package chap7;

import ast.*;
import chap6.BasicEvaluator;
import chap6.Environment;
import ex.StoneException;
import javassist.bytecode.analysis.ControlFlow;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import java.util.List;

@Require(BasicEvaluator.class)
@Reviser
public class FuncEvaluator {
  @Reviser
  public static interface EnvEx extends Environment {
    void putNew(String name, Object value);

    Environment where(String name);

    void setOuter(Environment e);
  }


  @Reviser
  public static class DefStmntEx extends DefStmnt {
    public DefStmntEx(List<ASTree> c) {
      super(c);
    }

    public Object eval(Environment env) {
      ((EnvEx) env).putNew(name(), new Function(parameters(), body(), env));
      return name();
    }
  }

  @Reviser
  public static class PrimaryEx extends PrimaryExpr {
    public PrimaryEx(List<ASTree> list) {
      super(list);
    }

    public ASTree operand() {
      return child(0);
    }

    public Postifix postifix(int nest) {
      return (Postifix) child(numChildren() - nest - 1);
    }
  }

  @Reviser
  public static abstract class PostfixEx extends Postifix {
    public PostfixEx(List<ASTree> list) {
      super(list);
    }

    public abstract Object eval(Environment env, Object value);
  }

  @Reviser
  public static class ArgumentsEx extends Arguments {
    public ArgumentsEx(List<ASTree> list) {
      super(list);
    }

    public Object eval(Environment callerEnv, Object value) {
      if (!(value instanceof Function))
        throw new StoneException("bad function", this);
      Function func = (Function) value;
      ParameterList params = func.parameters();
      if (size() != params.size())
        throw new StoneException("bad number of arguments", this);
      Environment newEnv = func.makeEnv();
      int num = 0;
      for (ASTree a : this) {
        ((ParamsEx) params).eval(newEnv, num++, ((BasicEvaluator.ASTreeEx) a).eval(callerEnv));
      }
      return ((BasicEvaluator.BlockEx) func.body()).eval(newEnv);

    }


    @Reviser
    public static class ParamsEx extends ParameterList {
      public ParamsEx(List<ASTree> list) {

        super(list);
      }

      public void eval(Environment env, int index, Object value) {
        ((EnvEx) env).putNew(name(index), value);
      }
    }
  }


}
