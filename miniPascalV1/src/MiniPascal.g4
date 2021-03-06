//Grammar for the MiniPascal language
grammar MiniPascal;

myStartingRule : PROGRAM ;

/*
 A multi-line Java-like comment
 */

program : PROGRAM STR io';' block #progrm;
block : vardeclpart? procdeclpart? statementpart? #blockProgrm ;

// VARIABLE DECLARATION
vardeclpart : (VAR (vardecl SMCOLON)+)? #blockVar;
vardecl : variable (',' variable)* ':' type #varDeclaration;
type : simpletype #simpleVar| arraytype #arrayVarType;
arraytype : ARRAY '['indexrange']' OF simpletype #arrayVar;
indexrange : DIGIT'..'DIGIT #rangeIndexVar;
simpletype : (INTEGER|STRING|FLOAT|BOOLEAN) #idVar;

// PROCEDURE DECLARATION
procdeclpart : (procdecl';')*;
procdecl : PROCEDURE ID ';' block;

// STATEMENT DECLARATION
io : (ID COMMA ID) ;
statementpart : (cmpstatement)* #stmt;
cmpstatement : BEGIN '{'statement'}' (statement';')* END{} #mainStmt;
statement : smpstatement #smpStmt| stcstatement #stcStmt;
smpstatement: assstatement #attribStmt| procstatement #stmtID| readstatement #resultReadStmt| writestatement #resultWriteStmt;
assstatement : variable ':=' expression #stmtAttrib;
procstatement : ID #idStmt;
readstatement : READ '('variable (',' variable)*')' #readStmt;
writestatement : WRITE '('expression (',' expression)*')' #writeStmt;

// EXPRESSION
expression : simpleexpression #resultSimpleExp | simpleexpression relationop=('=' | '<' | '>' | '<>' | '<=' | '>=') simpleexpression #resultExp;
simpleexpression : ('+' | '-')? term (addop term)* #simpleExpression;
term : factor (multop factor)* #termExpression;
factor: variable #varExpr | constant #constantExpr| '('expression')' #multExpr | NOT factor #notExpression;
constant : DIGIT ('.' DIGIT)* | STR | ID | TRUE | FALSE;
addop : PLUS | MINUS | OR;
multop : DIV | MULT | AND;
variable : ID #varID | ID '['expression']' #indexedVar;

// STRUCTURED STATEMENT
stcstatement : cmpstatement #cmpStatement | ifstatement #ifStatement| whilestatement #whileStatement | forstatement #forStatement;
ifstatement : IF expression THEN statement #ifStmt | IF expression THEN b1=statement ELSE b2=statement #ifElseStmt;
whilestatement : WHILE expression DO b1=cmpstatement #whileStmt;
forstatement : FOR '(' (simpleexpression)? ';' expression ';' counter=assstatement? ')' b1=cmpstatement #forStmt;

// LEXER RULES
letdig  : LETTER | DIGIT;
specsym :  	'+' | '-' | '*' | '=' | '<>' | '<' | '>' | '<=' | '>=' |
                '(' | ')' | '[' | ']' | ':=' | '.' | ',' | ';' | ':' | '..' | DIV | OR |
                AND | NOT | IF | THEN | ELSE | OF | WHILE | DO |
                BEGIN | END | READ | WRITE | VAR | ARRAY |
                PROCEDURE | PROGRAM | FUNCTION;

predid : INTEGER | BOOLEAN | TRUE | FALSE;
fragment A:('a'|'A');
fragment B:('b'|'B');
fragment C:('c'|'C');
fragment D:('d'|'D');
fragment E:('e'|'E');
fragment F:('f'|'F');
fragment G:('g'|'G');
fragment H:('h'|'H');
fragment I:('i'|'I');
fragment J:('j'|'J');
fragment K:('k'|'K');
fragment L:('l'|'L');
fragment M:('m'|'M');
fragment N:('n'|'N');
fragment O:('o'|'O');
fragment P:('p'|'P');
fragment Q:('q'|'Q');
fragment R:('r'|'R');
fragment S:('s'|'S');
fragment T:('t'|'T');
fragment U:('u'|'U');
fragment V:('v'|'V');
fragment W:('w'|'W');
fragment X:('x'|'X');
fragment Y:('y'|'Y');
fragment Z:('z'|'Z');

//Tokens
FOR : F O R;
DIV : D I V ;
OR  : O R ;
AND : A N D ;
NOT : N O T ;
IF  : I F ;
THEN : T H E N ;
ELSE : E L S E ;
OF : O F ;
WHILE : W H I L E;
DO : D O ;
BEGIN : B E G I N ;
END : E N D ;
READ : R E A D ;
WRITE : W R I T E ;
VAR : V A R ;
ARRAY : A R R A Y;
PROCEDURE : P R O C E D U R E;
PROGRAM : P R O G R A M;
LETTER : [a-zA-Z]+;
DIGIT  : [0-9]+;
PLUS : '+';
MINUS : '-';
MULT : '*';
EQUALS : '=';
RT : '>';
LT : '<';
LE : '<=';
RE : '>=';
LR : '<>';
OP : '(';
CP : ')';
OC : '[';
CC : ']';
ATTRIB : ':=';
DOT : '.';
COMMA : ',';
SMCOLON : ';';
COLON : ':';
DBDOT : '..';
INTEGER : I N T E G E R;
BOOLEAN : B O O L E A N ;
FLOAT : F L O A T ;
STRING : S T R I N G ;
TRUE : T R U E ;
FALSE : F A L S E;



STR     : '"'[a-zA-Z0-9\t ]*'"';

ID     : [a-zA-Z][a-zA-Z0-9_]*;
WS      : [\n\r \t]+ -> skip;
