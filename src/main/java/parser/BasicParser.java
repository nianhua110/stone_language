/*
 *
 *
 */
package parser;


import ast.*;
import lexer.Lexer;
import ex.ParseException;
import lexer.Token;

import java.util.HashSet;

public class BasicParser {
  HashSet<String> reserved = new HashSet<String>();
  Parser.Operators operators = new Parser.Operators();
  Parser expr0 = Parser.rule();
  Parser primary = Parser.rule(PrimaryExpr.class)
      .or(Parser.rule().sep("(").ast(expr0).sep(")"),
          Parser.rule().number(NumberLiteral.class),
          Parser.rule().identifier(Name.class, reserved),
          Parser.rule().string(StringLiteral.class));
  Parser factor = Parser.rule().or(Parser.rule(NegativeExpr.class)
      .sep("-").ast(primary), primary);
  Parser expr = expr0.expression(BinaryExpr.class, factor, operators);

  Parser statement0 = Parser.rule();
  Parser block = Parser.rule(BlockStmnt.class)
      .sep("{").option(statement0)
      .repeat(Parser.rule().sep(";", Token.EOL).option(statement0)
          .repeat(Parser.rule().sep(";", Token.EOL).option(statement0))
          .sep("}")).option(Parser.rule().sep(Token.EOL));
  Parser simple = Parser.rule(PrimaryExpr.class).ast(expr);
  Parser statment = statement0.or(Parser.rule(IfStmnt.class).sep("if")
          .ast(expr).ast(block).option(Parser.rule().sep("else").ast(block)),
      Parser.rule(WhileStmnt.class).sep("while").ast(expr).ast(block),
      simple);

  Parser program = Parser.rule().or(statment, Parser.rule(NullStmnt.class))
      .sep(";", Token.EOL);

  public BasicParser() {
    reserved.add(";");
    reserved.add("}");
    reserved.add(Token.EOL);

    operators.add("=", 1, Parser.Operators.RIGHT);
    operators.add("==", 2, Parser.Operators.LEFT);
    operators.add(">", 2, Parser.Operators.LEFT);
    operators.add("<", 2, Parser.Operators.LEFT);
    operators.add("+", 3, Parser.Operators.LEFT);
    operators.add("-", 3, Parser.Operators.LEFT);
    operators.add("*", 4, Parser.Operators.LEFT);
    operators.add("/", 4, Parser.Operators.LEFT);
    operators.add("%", 4, Parser.Operators.LEFT);
  }

  public ASTree parser(Lexer lexer) throws ParseException {
    return program.parse(lexer);
  }
}
