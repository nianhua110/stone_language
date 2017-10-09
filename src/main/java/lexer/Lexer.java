package lexer;/*
 *
 *
 */


import ex.ParseException;

import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
  private LineNumberReader reader;
  private boolean hasMore;
  private ArrayList<Token> queue = new ArrayList<Token>();
  private static String regexPat =
      "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\n|[^\"])*\")|([A-Z_a-z][A-Z_a-z0-9]*)|==|<=|>=|&&|\\|\\||\\p{Punct})";
  ;
  private Pattern pattern = Pattern.compile(regexPat);

  public Lexer(Reader reader) {
    this.reader = new LineNumberReader(reader);
    this.hasMore = true;
  }


  public Token read() throws ParseException {
    if (fillQueue(0)) {
      return queue.remove(0);
    } else {
      return Token.EOF;
    }
  }

  public Token peek(int i) throws ParseException {
    if (fillQueue(i)) {
      return queue.get(i);
    } else {
      return Token.EOF;
    }
  }

  private boolean fillQueue(int i) throws ParseException {
    while (i >= queue.size()) {
      if (hasMore) {
        readLine();
      } else {
        return false;
      }
    }
    return true;
  }

  protected void readLine() throws ParseException {
    String line;
    try {
      line = reader.readLine();
    } catch (Exception ex) {
      throw new ParseException(ex);
    }
    if (line == null) {
      hasMore = false;
      return;
    }

    int lineNo = reader.getLineNumber();
    Matcher matcher = pattern.matcher(line);
    matcher.useTransparentBounds(true).useAnchoringBounds(false);
    int pos = 0;
    int endPos = line.length();
    while (pos < endPos) {
      matcher.region(pos, endPos);
      if (matcher.lookingAt()) {
        addToken(lineNo, matcher);
        pos = matcher.end();
      } else {
        throw new ParseException("bad token at line " + lineNo);
      }
    }
    queue.add(new IdToken(lineNo, Token.EOL));
  }


  protected void addToken(int lineNo, Matcher matcher) {
    String m = matcher.group(1);
    if (m != null) {
      if (matcher.group(2) == null) {
        Token token;
        if (matcher.group(3) != null) {
          token = new NumToken(lineNo, Integer.parseInt(m));
        } else if (matcher.group(4) != null) {
          token = new StrToken(lineNo, m);
        } else {
          token = new IdToken(lineNo, m);
        }
        queue.add(token);
      }
    }
  }

  protected String toStringLiteral(String s) {
    StringBuilder sb = new StringBuilder();
    int len = s.length() - 1;
    for (int i = 1; i < len; i++) {
      char c = s.charAt(i);
      if (c == '\\' && i + 1 < len) {
        int c2 = s.charAt(i + 1);
        if (c2 == '"' || c2 == '\\')
          c = s.charAt(++i);
        else if (c2 == 'n') {
          ++i;
          c = '\n';
        }
      }
      sb.append(c);
    }
    return sb.toString();
  }

  protected static class IdToken extends Token {
    private String text;

    public IdToken(int line, String id) {
      super(line);
      text = id;
    }

    @Override
    public String getText() {
      return text;
    }

    @Override
    public boolean isIndentifier() {
      return true;
    }
  }


  protected static class StrToken extends Token {
    private String literal;

    public StrToken(int lineNumber, String literal) {
      super(lineNumber);
      this.literal = literal;
    }

    @Override
    public boolean isString() {
      return true;
    }

    @Override
    public String getText() {
      return literal;
    }
  }

  protected static class NumToken extends Token {
    private int v;

    public NumToken(int lineNumber, int v) {
      super(lineNumber);
      this.v = v;
    }

    @Override
    public boolean isNumber() {
      return true;
    }

    @Override
    public String getText() {
      return String.valueOf(v);
    }

    @Override
    public int getNumber() {
      return v;
    }
  }
}
