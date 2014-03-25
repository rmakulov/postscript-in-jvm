package scanner;

%%

%{
  private int string_depth = 0;
  private int proc_depth = 0;
  private String curString = "";


public int getProcDepth(){
 return proc_depth;
}
%}

%line
%char
%state COMMENT
%state YYSTRING
%full

%debug

ALPHA=[A-Za-z]
DIGIT=[0-9]
HEX_NUMBER = [0-9A-Fa-f]*
REAL_NUMBER = [+-]?({FLit1}|{FLit2}|{FLit3}) {Exponent}?
FLit1    = [0-9]+ \. [0-9]*
FLit2    = \. [0-9]+
FLit3    = [0-9]+
Exponent = [eE] [+-]? [0-9]+

INT_CHAR = \\{DIGIT}{DIGIT}{DIGIT}
SPECIAL_CHAR_SEQUENCE = {INT_CHAR} | \\[()tfbrn\\]
SPECIAL_CHARS = [()\\\[\]\>\<\%]
NOTNEWLINE_SEQUENCE = \\([\n\r]|\r\n)
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012]
NEWLINE=\r|\n|\r\n
WHITE_SPACE_CHAR=[\n\r\ \t\b\f]
STRING_TEXT=([^()] | NONNEWLINE_SEQUENCE| WHITE_SPACE_CHAR)*
COMMENT_TEXT= ([^\n\r])*
Ident = {ALPHA}({ALPHA}|{DIGIT}|_)*
NAME = [^\{\}()\\\[\]\>\<\ \%\t\n\r\b\f\/]+
COMMENT_END = [\n\r]*


%%

<YYINITIAL> {
  "[" { return (new Yytoken(Tokens.OPEN_SQUARE_BRACKET,yytext(),yyline,yychar,yychar+1)); }
  "]" { return (new Yytoken(Tokens.CLOSE_SQUARE_BRACKET,yytext(),yyline,yychar,yychar+1)); }

  "{" {   proc_depth++;
            return (new Yytoken(Tokens.OPEN_CURLY_BRACE,yytext(),yyline,yychar,yychar+1));
            }

  "}"  { if (--proc_depth < 0) {
            System.out.println("Error");
            System.exit(-1);
         }
         return new Yytoken(Tokens.CLOSE_CURLY_BRACE,yytext(),yyline,yychar,yychar+1);
       }

  "<<" { return (new Yytoken(Tokens.OPEN_CHEVRON_BRACKET,yytext(),yyline,yychar,yychar+1)); }
  ">>" { return (new Yytoken(Tokens.CLOSE_CHEVRON_BRACKET,yytext(),yyline,yychar,yychar+1)); }

  [+-]?{DIGIT}+ { return (new Yytoken(Tokens.INTEGER,yytext(),yyline,yychar,yychar+yylength())); }

  "<" {HEX_NUMBER} ">" {String text = yytext().substring(1,yytext().length()-2);
return (new Yytoken(Tokens.HEX,text,yyline,yychar,yychar+yylength())); }

  //85ASCII Base-85 Strings
  "<~" {STRING_TEXT} "~>" { return (new Yytoken(Tokens.STRING_TEXT,yytext(),yyline,yychar+2,yychar+yylength()-2)); }

  //radix numbers
  {DIGIT}+ "#" {DIGIT}+ { return (new Yytoken(Tokens.RADIX,yytext(),yyline,yychar,yychar+yylength()));}

  {REAL_NUMBER} { return(new Yytoken(Tokens.REAL,yytext(),yyline,yychar,yychar+yylength()));}

  {NONNEWLINE_WHITE_SPACE_CHAR}+ { }

  "%" { yybegin(COMMENT); }

  "(" {
    curString =  "";
    string_depth = 1;
    yybegin(YYSTRING);
  }
  



  {NAME} { return (new Yytoken(Tokens.EXEC_NAME,yytext(),yyline,yychar,yychar+yylength())); }

  \/{NAME} {
           String text =yytext().substring(1);
          return (new Yytoken(Tokens.LIT_NAME,text,yyline,yychar+1,yychar+yylength()));
    }
}


<COMMENT> {
  "%" {  }
  {COMMENT_END} { yybegin(YYINITIAL); }
  {COMMENT_TEXT} {return (new Yytoken(Tokens.COMMENTS,yytext(),yyline,yychar,yychar+yylength())); }
}


<YYSTRING> {
  "(" {  string_depth++;
       curString = curString + "(";
    }
  ")" { if (--string_depth == 0) {
            yybegin(YYINITIAL);
            return (new Yytoken(Tokens.STRINGS,curString,yyline,0,curString.length()));
            } else {
            curString = curString+ ")";
         }
       }
  {STRING_TEXT} {String text = yytext();
                //todo
                //curString = curString + text.substring(yychar,yychar+yylength());
                curString = curString + text.substring(0,text.length());
                }
}

{NEWLINE} { }

. {
  System.out.println("Illegal character: <" + yytext() + ">");
	Utility.error(Utility.E_UNMATCHED);
}