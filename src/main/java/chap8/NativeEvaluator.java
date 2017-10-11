/*
 *
 */
package chap8;

import ast.ASTree;
import chap6.BasicEvaluator;
import chap6.Environment;
import chap7.FuncEvaluator;
import ex.StoneException;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import javax.print.attribute.standard.MediaSize;
import java.util.List;

/**
 * @author kyle
 */
@Require(FuncEvaluator.class)
@Reviser
public class NativeEvaluator {
  @Reviser
  public static class NativeArgEx extends FuncEvaluator.ArgumentsEx {
    public NativeArgEx(List<ASTree> list) {
      super(list);
    }

    @Override
    public Object eval(Environment callerEnv, Object value) {
      if (!(value instanceof NativeFunction))
        return super.eval(callerEnv, value);
      NativeFunction func = (NativeFunction) value;
      int nparams = func.getNumParams();
      if (size() != nparams) {
        throw new StoneException("bad number of arguments");
      }
      Object[] args = new Object[nparams];
      int num = 0;
      for (ASTree a : this) {
        BasicEvaluator.ASTreeEx ae = (BasicEvaluator.ASTreeEx) a;
        args[num++] = ae.eval(callerEnv);
      }
      return func.invoke(args, this);
    }
  }

}
