/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package parser;

import ast.ASTLeaf;
import ast.ASTList;
import ast.ASTree;
import ast.BinaryExpr;
import lexer.Lexer;
import ex.ParseException;
import lexer.Token;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class Parser {
  protected static abstract class Element {
    protected abstract void parse(Lexer lexer, List<ASTree> res) throws ParseException;

    protected abstract boolean match(Lexer lexer) throws ParseException;
  }

  protected static class Tree extends Element {
    protected Parser parser;

    protected Tree(Parser p) {
      parser = p;
    }

    @Override
    protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
      res.add(parser.parse(lexer));
    }

    @Override
    protected boolean match(Lexer lexer) throws ParseException {
      return parser.match(lexer);
    }
  }

  public static class OrTree extends Element {
    protected Parser[] parsers;

    public OrTree(Parser[] parsers) {
      this.parsers = parsers;
    }

    @Override
    protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
      Parser p = choose(lexer);
      if (p == null) {
        throw new ParseException(lexer.peek(0));
      } else {
        res.add(p.parse(lexer));
      }
    }

    @Override
    protected boolean match(Lexer lexer) throws ParseException {
      return choose(lexer) != null;
    }

    protected Parser choose(Lexer lexer) throws ParseException {
      for (Parser p : parsers) {
        if (p.match(lexer)) {
          return p;
        }
      }
      return null;
    }

    protected void insert(Parser p) {
      Parser[] newParsers = new Parser[parsers.length + 1];
      newParsers[0] = p;
      System.arraycopy(parsers, 0, newParsers, 1, parsers.length);
      parsers = newParsers;
    }
  }

  protected static class Repeat extends Element {
    protected Parser parser;
    protected boolean onlyOnce;

    public Repeat(Parser parser, boolean once) {
      this.parser = parser;
      this.onlyOnce = once;
    }

    @Override
    protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
      while (parser.match(lexer)) {
        ASTree t = parser.parse(lexer);
        if (t.getClass() != ASTList.class || t.numChildren() > 0) {
          res.add(t);
        }

        if (onlyOnce) {
          break;
        }
      }

    }

    @Override
    protected boolean match(Lexer lexer) throws ParseException {
      return parser.match(lexer);
    }
  }

  public static abstract class AToken extends Element {
    protected Factory factory;

    protected AToken(Class<? extends ASTLeaf> type) {
      if (type == null) {
        type = ASTLeaf.class;
      }
      factory = Factory.get(type, Token.class);
    }

    @Override
    protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
      Token t = lexer.read();
      if (test(t)) {
        ASTree leaf = factory.make(t);
        res.add(leaf);
      } else {
        throw new ParseException(t);
      }
    }

    @Override
    protected boolean match(Lexer lexer) throws ParseException {
      return test(lexer.peek(0));
    }

    protected abstract boolean test(Token t);
  }

  protected static class IdToken extends AToken {
    HashSet<String> reserved;

    public IdToken(Class<? extends ASTLeaf> type, HashSet<String> r) {
      super(type);
      reserved = r != null ? r : new HashSet<>();
    }

    @Override
    protected boolean test(Token t) {
      return t.isIndentifier() && !reserved.contains(t.getText());
    }
  }

  protected static class NumToken extends AToken {
    public NumToken(Class<? extends ASTLeaf> type) {
      super(type);
    }

    @Override
    protected boolean test(Token t) {
      return t.isNumber();
    }
  }

  protected static class StrToken extends AToken {
    public StrToken(Class<? extends ASTLeaf> type) {
      super(type);
    }

    @Override
    protected boolean test(Token t) {
      return t.isString();
    }
  }

  protected static class Leaf extends Element {
    protected String[] tokens;

    public Leaf(String[] tokens) {
      this.tokens = tokens;
    }

    @Override
    protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
      Token t = lexer.read();
      if (t.isIndentifier()) {
        for (String token : tokens) {
          if (token.equals(t.getText())) {
            find(res, t);
            return;
          }
        }
        if (tokens.length > 0) {
          throw new ParseException(tokens[0] + " expected.", t);
        } else {
          throw new ParseException(t);
        }
      }
    }

    protected void find(List<ASTree> res, Token t) {
      res.add(new ASTLeaf(t));
    }

    @Override
    protected boolean match(Lexer lexer) throws ParseException {

      Token t = lexer.peek(0);
      if (t.isIndentifier()) {
        for (String token : tokens) {
          if (token.equals(t.getText())) {
            return true;
          }
        }
      }
      return false;
    }
  }

  protected static class Skip extends Leaf {
    public Skip(String[] tokens) {
      super(tokens);
    }

    @Override
    protected void find(List<ASTree> res, Token t) {

    }
  }


  public static class Precedence {
    int value;
    boolean leftAssoc;

    public Precedence(int value, boolean leftAssoc) {
      this.value = value;
      this.leftAssoc = leftAssoc;
    }
  }

  public static class Operators extends HashMap<String, Precedence> {
    public static boolean LEFT = true;
    public static boolean RIGHT = false;

    public void add(String name, int prec, boolean leftAssoc) {
      put(name, new Precedence(prec, leftAssoc));
    }
  }

  protected static class Expr extends Element {
    protected Factory factory;
    protected Operators operators;
    protected Parser factor;

    public Expr(Class<? extends ASTree> clazz, Parser parser, Operators operators) {
      if(clazz.equals(BinaryExpr.class)){
        System.out.println("pause");
      }
      this.factory = Factory.getForASTList(clazz);
      this.operators = operators;
      this.factor = parser;
      if(factory ==null){
        System.out.println("factory is null");
      }
    }

    private ASTree doShift(Lexer lexer, ASTree left, int prec) throws ParseException {
      ArrayList<ASTree> list = new ArrayList<>();
      list.add(left);
      list.add(new ASTLeaf(lexer.read()));
      ASTree right = factor.parse(lexer);
      Precedence next;
      while ((next = nextOperator(lexer)) != null && rightIsExpr(prec, next)) {
        right = doShift(lexer, right, next.value);
      }
      list.add(right);
      return factory.make(list);

    }

    protected Precedence nextOperator(Lexer lexer) throws ParseException {
      Token t = lexer.peek(0);
      if (t.isIndentifier())
        return operators.get(t.getText());
      else
        return null;
    }

    protected static boolean rightIsExpr(int prec, Precedence nextPrec) {
      if (nextPrec.leftAssoc) {
        return prec < nextPrec.value;
      } else {
        return prec <= nextPrec.value;
      }
    }

    @Override
    protected void parse(Lexer lexer, List<ASTree> res) throws ParseException {
      ASTree right = factor.parse(lexer);
      Precedence prec;
      while ((prec = nextOperator(lexer)) != null) {
        right = doShift(lexer, right, prec.value);

      }
      res.add(right);
    }

    @Override
    protected boolean match(Lexer lexer) throws ParseException {
      return factor.match(lexer);
    }
  }

  protected static final String factoryName = "create";

  protected static abstract class Factory {
    protected abstract ASTree make0(Object arg) throws Exception;

    protected ASTree make(Object arg) {
      try {

        return make0(arg);
      } catch (IllegalArgumentException e1) {
        throw e1;
      } catch (Exception e2) {
        throw new RuntimeException(e2);
      }
    }

    protected static Factory getForASTList(Class<? extends ASTree> clazz) {
      Factory f = get(clazz, List.class);
      if (f == null) {
        f = new Factory() {
          @Override
          protected ASTree make0(Object arg) throws Exception {
            List<ASTree> results = (List<ASTree>) arg;
            if (results.size() == 1) {
              return results.get(0);
            } else {
              return new ASTList(results);
            }
          }
        };
      }
      return f;
    }

    protected static Factory get(Class<? extends ASTree> clazz, Class<?> argType) {
      if (clazz == null) {
        return null;
      }
      try {
        final Method m = clazz.getMethod(factoryName, new Class<?>[]{argType});
        return new Factory() {
          @Override
          protected ASTree make0(Object arg) throws Exception {
            return (ASTree) m.invoke(null, arg);
          }
        };
      } catch (NoSuchMethodException e) {
      }
      try {
        final Constructor<? extends ASTree> c = clazz.getConstructor(argType);
        return new Factory() {
          @Override
          protected ASTree make0(Object arg) throws Exception {
            return c.newInstance(arg);
          }
        };
      } catch (NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    }
  }

  protected List<Element> elements;
  protected Factory factory;

  public Parser(Class<? extends ASTree> clazz) {
    reset(clazz);
  }

  public Parser(Parser p) {
    this.elements = p.elements;
    this.factory = p.factory;
  }

  public ASTree parse(Lexer lexer) throws ParseException {
    ArrayList<ASTree> result = new ArrayList<>();
    for (Element e : elements) {
      e.parse(lexer, result);

    }
    return factory.make(result);
  }

  protected boolean match(Lexer lexer) throws ParseException {
    if (elements.size() == 0) {
      return true;
    } else {
      Element e = elements.get(0);
      return e.match(lexer);
    }
  }

  public static Parser rule() {
    return rule(null);
  }

  public static Parser rule(Class<? extends ASTree> clazz) {
    return new Parser(clazz);
  }

  public Parser reset() {
    elements = new ArrayList<Element>();
    return this;
  }

  public Parser reset(Class<? extends ASTree> clazz) {
    elements = new ArrayList<Element>();
    factory = Factory.getForASTList(clazz);
    return this;
  }

  public Parser number() {
    return number(null);
  }

  public Parser number(Class<? extends ASTLeaf> clazz) {
    elements.add(new NumToken(clazz));
    return this;
  }

  public Parser identifier(HashSet<String> revserved) {
    return identifier(null, revserved);
  }

  public Parser identifier(Class<? extends ASTLeaf> clazz, HashSet<String> reserved) {
    elements.add(new IdToken(clazz, reserved));
    return this;
  }

  public Parser string() {
    return string(null);
  }

  public Parser string(Class<? extends ASTLeaf> clazz) {
    elements.add(new StrToken(clazz));
    return this;
  }

  public Parser token(String... pat) {
    elements.add(new Leaf(pat));
    return this;
  }

  public Parser sep(String... pat) {
    elements.add(new Skip(pat));
    return this;
  }

  public Parser ast(Parser p) {
    elements.add(new Tree(p));
    return this;
  }

  public Parser or(Parser... p) {
    elements.add(new OrTree(p));
    return this;
  }

  public Parser maybe(Parser p) {
    Parser p2 = new Parser(p);
    p2.reset();
    elements.add(new OrTree(new Parser[]{p, p2}));
    return this;
  }


  public Parser option(Parser p) {
    elements.add(new Repeat(p, true));
    return this;
  }


  public Parser repeat(Parser p) {
    elements.add(new Repeat(p, false));
    return this;
  }

  public Parser expression(Parser subexp, Operators operators) {
    elements.add(new Expr(null, subexp, operators));
    return this;
  }

  public Parser expression(Class<? extends ASTree> clazz, Parser subexp,
                           Operators operators) {
    elements.add(new Expr(clazz, subexp, operators));
    return this;
  }

  public Parser insertChoice(Parser p) {
    Element e = elements.get(0);
    if (e instanceof OrTree) {
      ((OrTree) e).insert(p);
    } else {
      Parser otherwise = new Parser(this);
      reset(null);
      or(p, otherwise);
    }
    return this;
  }
}
