package org.example.lexer;

import java.io.*;

%%

%public
%class EtaLexer
%type Token
%line
%column

%unicode

/* MACRO DEFINITIONS */

WHITESPACE = [ \t\f\r\n]
LETTER = [a-zA-Z]
DIGIT = [0-9]
IDENTIFIER = {LETTER}({LETTER}|{DIGIT}|_|')*
INTEGER = "0"|([1-9]{DIGIT}*)
STRING = \"([^\"\\\n] | \\[nt\"\'\\] | \\x\{[0-9a-fA-F]{1,6}\})*\"
CHARACTER = \'([^\'\\\n]|\\[nt\"\'\\]|\\x\{[0-9a-fA-F]{1,6}\})\'

%%

/* RULES */
// Whitespace & Comments
{WHITESPACE} { /* ignore */ }
"//".* { /* ignore */ }

// Keywords
"use" | "if" | "while" | "else" | "return" | "length" | "int" | "bool" |
"true" | "false"
{ return new Token(TokenType.KEYWORD, yytext(), yyline + 1, yycolumn + 1); }

// Symbols
"*>>" |
"==" | "!=" | "<=" | ">=" |
"+" | "-" | "*" | "/" | "%" |
"=" | "<" | ">" |
"&" | "|" | "!" |
":" | ";" | "," | "_" |
"(" | ")" | "{" | "}" | "[" | "]"
{ return new Token(TokenType.SYMBOL, yytext(), yyline + 1, yycolumn + 1); }

// Literals
{INTEGER} { return new Token(TokenType.INTEGER, yytext(), yyline + 1, yycolumn + 1); }
{IDENTIFIER} { return new Token(TokenType.ID, yytext(), yyline + 1, yycolumn + 1); }

// Strings & Chars
{STRING} { return new Token(TokenType.STRING, yytext().substring(1, yytext().length() - 1), yyline + 1, yycolumn + 1); }
"''" { return new Token(TokenType.ERROR, "Invalid character constant", yyline + 1, yycolumn + 1); }
{CHARACTER} { return new Token(TokenType.CHARACTER, yytext().substring(1, yytext().length() - 1), yyline + 1, yycolumn + 1); }

// Error
. { return new Token(TokenType.ERROR, "Invalid character", yyline + 1, yycolumn + 1); }