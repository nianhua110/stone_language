/*
 * kyle
 *
 */
package parser;

import ast.Arguments;
import ast.DefStmnt;
import ast.ParameterList;

public class FuncParser extends BasicParser {
  Parser param = Parser.rule().identifier(reserved);
  Parser params = Parser.rule(ParameterList.class)
      .ast(param).repeat(Parser.rule().sep(",").ast(param));
  //todo mabye 和 option 的区别
  Parser paramList = Parser.rule().sep("(").maybe(params).sep(")");
  Parser def = Parser.rule(DefStmnt.class)
      .sep("def").identifier(reserved).ast(paramList).ast(block);
  Parser args = Parser.rule(Arguments.class)
      .ast(expr).repeat(Parser.rule().sep(",").ast(expr));

  Parser postfix = Parser.rule().sep("(").maybe(args).sep(")");

  public FuncParser() {
    reserved.add(")");
    primary.repeat(postfix);
    simple.option(args);
    program.insertChoice(def);
  }
}
