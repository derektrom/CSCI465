/**
 * <h1>Lexer</h1>
 * This is the Lexer for the package and provides the getsym
 * module for analyzing the string of tokens and acts
 * as the state machine of the program
 *
 * @author  Derek Trom
 * @version 1.0
 * @since   2020-09-26
 */
package LexicalAnalyzer;

public class Lexer {
    private String currentLexeme = "";
    private String currentChar = "";
    private String detectedLexeme = "";
    private String detectedToken = "";
    private String programText = "";
    private int programCounter = 0;
    private int lookAheadCounter = 0;
    private int programSize = 0;
    private int lineCounter = 1;
    private int startLine = 1;
    private int state = 0;
    private boolean hasSymbols = true;
    private boolean stopSearching = false;

    /**
     * Constructor for Lexer
     * @param programText the string of the input program
     */
    public Lexer (String programText) {
        this.programText = programText;
        this.programSize = programText.length()-1;
    }

    /**
     * State machine that generates lexemes based off
     * of the language in Language.java
     * @return detectedLexeme this will be added to the Match list
     * @throws Exception Thrown if there is an unrecognized token
     */
    public String getsym() throws Exception {

        // reset the stopSearching flag, currentLexeme, and
        // detectedLexeme to properly search for the next symbol.
        stopSearching = false;
        currentChar = "";
        currentLexeme = "";
        detectedLexeme = "";

        // While we shouldn't stop searching for a match...
        while (!stopSearching) {

            // If programCounter has reached programSize, we should stop
            // searching because we have run out of symbols in the programText.
            if (programCounter >= programSize) {
                stopSearching();
                hasSymbols = false;
                break;
            }

            // Example of how an exception may be thrown to be handled by the Driver
            // if (programCounter == 150) {
            //     throw new Exception("There was an erroneous token discovered on line "+lineCounter);
            // }

            // If we found a newline, increment the lineCounter
            if (currentChar.matches(LexicalAnalyzer.Language.REGEX_NEWLINE)) {
                // System.out.println("Next Line Started");
                lineCounter++;
                startLine = lineCounter;
            }

            // System.out.println("CURRENT STATE: "+state);
            switch (state) {
                // --------------------\/-------------------- ST_START --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_START :
                    // Set the lookAheadCounter = to the programCounter so we
                    // know where to start looking ahead from if necessary.
                    lookAheadCounter = programCounter;
                    // Set the currentChar to the char in the programText
                    // currently pointed to by the programCounter
                    currentChar = Character.toString(programText.charAt(programCounter));
                    // then, we increment the program counter to look at
                    // the next character in the programText next time around
                    programCounter++;

                    // if (currentChar.matches(Language.REGEX_NEWLINE)) {
                    //     lineCounter++;
                    // }

                    if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_COLON)) {
                        state = LexicalAnalyzer.Language.ST_COLON;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_LCRLYBRACK)) {
                        state = LexicalAnalyzer.Language.ST_LCRLYBRK;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_LPAREN)) {
                        state = LexicalAnalyzer.Language.ST_LPAREN;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_RPAREN)) {
                        state = LexicalAnalyzer.Language.ST_RPAREN;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_LETTER)) {
                        state = LexicalAnalyzer.Language.ST_LETTER;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_COMMA)) {
                        state = LexicalAnalyzer.Language.ST_COMMA;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_SEMICOLON)) {
                        state = LexicalAnalyzer.Language.ST_SEMICOLON;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_EQU)) {
                        state = LexicalAnalyzer.Language.ST_EQU;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_LT)) {
                        state = LexicalAnalyzer.Language.ST_LT;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_GT)) {
                        state = LexicalAnalyzer.Language.ST_GT;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_PERIOD)) {
                        state = LexicalAnalyzer.Language.ST_PERIOD;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_DIGIT)) {
                        state = LexicalAnalyzer.Language.ST_DIGIT;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_PLUS)) {
                        state = LexicalAnalyzer.Language.ST_PLUS;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_MINUS)) {
                        state = LexicalAnalyzer.Language.ST_MINUS;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_MULT)) {
                        state = LexicalAnalyzer.Language.ST_MULT;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_DIVIDE)) {
                        state = LexicalAnalyzer.Language.ST_DIVIDE;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_LSQBRACKET)) {
                        state = LexicalAnalyzer.Language.ST_LSQBRACKET;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_RS_RSQBRACKET)) {
                        state = LexicalAnalyzer.Language.ST_RSQBRACKET;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_SINGLEQT)) {
                        state = LexicalAnalyzer.Language.ST_SINGLEQT;
                    } else if (currentChar.matches(LexicalAnalyzer.Language.REGEX_WHITESPACE)) {
                        //ignore
                    } else {
                        throw new Exception("Could not compile.\n"+
                                "Unrecognized token: "+currentChar+
                                "\nFound on line: "+lineCounter);
                    }
                    break;
                // --------------------/\-------------------- ST_START --------------------/\-------------------- //
                // --------------------\/-------------------- ST_COLON --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_COLON :
                    // System.out.println("Entering ST_COLON");

                    detectedToken = LexicalAnalyzer.Language.TOK_RS_COLON;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    state = LexicalAnalyzer.Language.ST_COLON_EQUALS;
                    break;

                // --------------------/\-------------------- ST_COLON --------------------/\-------------------- //
                // --------------------\/-------------------- ST_COLON_EQUALS --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_COLON_EQUALS :
                    // System.out.println("Entering ST_COLON_EQUALS");
                    lookAheadCounter++;
                    // System.out.println("Lookahead counter incremented");
                    currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                    // System.out.println(currentLexeme);
                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_RS_ASSIGN)) {
                        detectedToken = LexicalAnalyzer.Language.TOK_RS_ASSIGN;
                        detectedLexeme = currentLexeme;
                        lookAheadCounter++;
                        programCounter = lookAheadCounter;

                        // System.out.println("State Reset by ST_COLON_EQUALS - found an ASSIGN");
                        resetStateAndStopSearching();
                        break;
                    } else {
                        // System.out.println("State Reset by ST_COLON_EQUALS - did not find ASSIGN");
                        resetStateAndStopSearching();
                        break;
                    }
                    // --------------------/\-------------------- ST_COLON_EQUALS --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_LCRLYBRK --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_LCRLYBRK :
                    // System.out.println("Entering ST_LCRLYBRK");
                    detectedToken = LexicalAnalyzer.Language.TOK_LP_COMMENT;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    lookAheadCounter++;
                    if (Character.toString(programText.charAt(lookAheadCounter)).equals(LexicalAnalyzer.Language.REGEX_NEWLINE)) {
                        // System.out.println("Next Line Started");
                        lineCounter++;
                        currentLexeme += Character.toString(programText.charAt(lookAheadCounter)).replace("\n","\\n");
                    } else {
                        currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                    }
                    // System.out.println("Leaving state ST_LCRLYBRK - found a LCRLYBRACK");
                    state = LexicalAnalyzer.Language.ST_LCRLYBRK_IGNOREALL;
                    break;
                // --------------------/\-------------------- ST_LCRLYBRK --------------------/\-------------------- //
                // --------------------\/-------------------- ST_LCRLYBRK_IGNOREALL --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_LCRLYBRK_IGNOREALL :
                    // System.out.println("Entering ST_LCRLYBRK_IGNOREALL");
                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_PT_CRLYCOMMENT)) {
                        detectedLexeme = currentLexeme;
                        lookAheadCounter++;
                        programCounter = lookAheadCounter;

                        // System.out.println("State Reset by ST_LCRLYBRK_IGNOREALL - found a COMMENT");
                        resetStateAndStopSearching();
                        break;
                    } else {
                        lookAheadCounter++;
                        // System.out.println("Lookahead counter incremented");
                        // System.out.println("No state change - have not yet detected RCRLYBRACK");
                        if (Character.toString(programText.charAt(lookAheadCounter)).equals(LexicalAnalyzer.Language.REGEX_NEWLINE)) {
                            // System.out.println("Next Line Started");
                            lineCounter++;
                            currentLexeme += Character.toString(programText.charAt(lookAheadCounter)).replace("\n","\\n");
                        } else {
                            currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                        }
                        break;
                    }
                    // --------------------/\-------------------- ST_LCRLYBRK_IGNOREALL --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_LPAREN --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_LPAREN :
                    // System.out.println("Entering ST_LPAREN");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_LPAREN;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    state = LexicalAnalyzer.Language.ST_LBIGRAM;
                    break;
                // --------------------/\-------------------- ST_LPAREN --------------------/\-------------------- //
                // --------------------\/-------------------- ST_LBIGRAM --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_LBIGRAM :
                    // System.out.println("Entering ST_LBIGRAM");
                    // System.out.println(currentLexeme);
                    lookAheadCounter++;
                    // System.out.println("Lookahead counter incremented");
                    currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_RS_LBIGRAM)) {
                        detectedToken = LexicalAnalyzer.Language.TOK_LP_COMMENT;
                        detectedLexeme = currentLexeme;
                        lookAheadCounter++;
                        currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                        // System.out.println("Leaving state ST_BIGRAM - found a LBIGRAM");
                        state = LexicalAnalyzer.Language.ST_LBIGRAM_IGNOREALL;
                        break;
                    } else {
                        // System.out.println("State Reset by ST_LBIGRAM - did not find LBIGRAM");
                        resetStateAndStopSearching();
                        break;
                    }
                    // --------------------/\-------------------- ST_LBIGRAM --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_LBIGRAM_IGNOREALL --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_LBIGRAM_IGNOREALL :
                    // System.out.println("Entering ST_LBIGRAM_IGNOREALL");
                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_PT_BGRMCOMMENT)) {
                        detectedLexeme = currentLexeme;
                        lookAheadCounter++;
                        programCounter = lookAheadCounter;

                        // System.out.println("State Reset by ST_LBIGRAM_IGNOREALL - found a COMMENT");
                        resetStateAndStopSearching();
                        break;
                    } else {
                        lookAheadCounter++;
                        // System.out.println("Lookahead counter incremented");
                        // System.out.println("No state change - have not yet detected RBIGRAM");
                        if (Character.toString(programText.charAt(lookAheadCounter)).equals(LexicalAnalyzer.Language.REGEX_NEWLINE)) {
                            // System.out.println("Next Line Started");
                            lineCounter++;
                            currentLexeme += Character.toString(programText.charAt(lookAheadCounter)).replace("\n","\\n");
                        } else {
                            currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                        }
                        break;
                    }
                    // --------------------/\-------------------- ST_LBIGRAM_IGNOREALL --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_RPAREN --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_RPAREN :
                    // System.out.println("Entering ST_RPAREN");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_RPAREN;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_RPAREN - found a RPAREN");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_RPAREN --------------------/\-------------------- //
                // --------------------\/-------------------- ST_LETTER --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_LETTER :
                    // System.out.println("Entering ST_LETTER");

                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    state = LexicalAnalyzer.Language.ST_ID;
                    break;

                // --------------------/\-------------------- ST_LETTER --------------------/\-------------------- //
                // --------------------\/-------------------- ST_ID --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_ID :
                    detectedToken = LexicalAnalyzer.Language.TOK_LP_ID;
                    // System.out.println("Entering ST_ID");
                    lookAheadCounter++;
                    // System.out.println("Lookahead counter incremented");
                    currentLexeme += Character.toString(programText.charAt(lookAheadCounter));

                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_PT_ID)) {
                        detectedLexeme = currentLexeme;

                        // Set up a flag to determine whether we found a reserved word.
                        // For each case, if the detectedLexeme matches any regex of a
                        // reserved word, we will set the detectedToken to that of the
                        // reserved word and waive the flag. We will also reset the
                        // state and stop searching because we have successfully
                        // determined the symbol, which should be returned to the
                        // Driver for I/O management by the IOModule.
                        boolean reservedWordFound = false;
                        switch (detectedLexeme) {
                            case LexicalAnalyzer.Language.REGEX_RW_AND :
                                // System.out.println("State Reset by ST_ID - found an RW (and)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_AND;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_ARRAY :
                                // System.out.println("State Reset by ST_ID - found an RW (array)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_ARRAY;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_BEGIN :
                                // System.out.println("State Reset by ST_ID - found an RW (begin)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_BEGIN;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_BOOL :
                                // System.out.println("State Reset by ST_ID - found a TYPE (bool)");
                                detectedToken = LexicalAnalyzer.Language.TOK_TYPE_BOOL;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_CHAR :
                                // System.out.println("State Reset by ST_ID - found a TYPE (char)");
                                detectedToken = LexicalAnalyzer.Language.TOK_TYPE_CHAR;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_DIV :
                                // System.out.println("State Reset by ST_ID - found an RW (div)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_DIV;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_DO :
                                // System.out.println("State Reset by ST_ID - found an RW (do)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_DO;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_DOWNTO :
                                // System.out.println("State Reset by ST_ID - found an RW (downto)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_DOWNTO;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_ELSE :
                                // System.out.println("State Reset by ST_ID - found an RW (else)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_ELSE;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_END :
                                // System.out.println("State Reset by ST_ID - found an RW (end)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_END;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_FALSE :
                                // System.out.println("State Reset by ST_ID - found an BOOLLIT (false)");
                                detectedToken = LexicalAnalyzer.Language.TOK_LIT_BOOL;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_FOR :
                                // System.out.println("State Reset by ST_ID - found an RW (for)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_FOR;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_FUNCTION :
                                // System.out.println("State Reset by ST_ID - found an RW (function)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_FUNCTION;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_IF :
                                // System.out.println("State Reset by ST_ID - found an RW (if)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_IF;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_INT :
                                // System.out.println("State Reset by ST_ID - found a TYPE (int)");
                                detectedToken = LexicalAnalyzer.Language.TOK_TYPE_INT;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_MOD :
                                // System.out.println("State Reset by ST_ID - found an RW (mod)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_MOD;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_NOT :
                                // System.out.println("State Reset by ST_ID - found an RW (not)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_NOT;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_OF :
                                // System.out.println("State Reset by ST_ID - found an RW (of)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_OF;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_OR :
                                // System.out.println("State Reset by ST_ID - found an RW (or)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_OR;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_PROCEDURE :
                                // System.out.println("State Reset by ST_ID - found an RW (procedure)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_PROCEDURE;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_PROGRAM :
                                // System.out.println("State Reset by ST_ID - found an RW (program)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_PROGRAM;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_REAL :
                                // System.out.println("State Reset by ST_ID - found a TYPE (real)");
                                detectedToken = LexicalAnalyzer.Language.TOK_TYPE_REAL;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_STR :
                                // System.out.println("State Reset by ST_ID - found a TYPE (string)");
                                detectedToken = LexicalAnalyzer.Language.TOK_TYPE_STR;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_THEN :
                                // System.out.println("State Reset by ST_ID - found an RW (then)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_THEN;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_TO :
                                // System.out.println("State Reset by ST_ID - found an RW (to)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_TO;
                                reservedWordFound = true;break;
                            case LexicalAnalyzer.Language.REGEX_TRUE :
                                // System.out.println("State Reset by ST_ID - found an BOOLLIT (true)");
                                detectedToken = LexicalAnalyzer.Language.TOK_LIT_BOOL;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_VAR :
                                // System.out.println("State Reset by ST_ID - found an RW (var)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_VAR;
                                reservedWordFound = true; break;
                            case LexicalAnalyzer.Language.REGEX_RW_WHILE :
                                // System.out.println("State Reset by ST_ID - found an RW (while)");
                                detectedToken = LexicalAnalyzer.Language.TOK_RW_WHILE;
                                reservedWordFound = true; break;
                            default:
                                break;
                        }

                        // If we found a reserved word, we need to increment the
                        // lookAheadCounter to point to the next char immediately
                        // after it and set the programCounter to the lookAheadCounter.
                        // Else, we don't change the state so we can continue searching
                        // for the entire ID lexeme.
                        String nextChar = Character.toString(programText.charAt(lookAheadCounter+1));
                        String tempID = currentLexeme + nextChar;

                        if (reservedWordFound && !tempID.matches(LexicalAnalyzer.Language.REGEX_PT_ID)) {
                            lookAheadCounter++;
                            programCounter = lookAheadCounter;
                            resetStateAndStopSearching();
                            break;
                        } else {
                            // System.out.println("No state change - have not reached end of ID");
                        }
                        break;
                    } else {

                        // If we didn't match the ID regex, but the next char is a newline,
                        // we should increment the lineCounter to maintain proper line numbering.
                        if (Character.toString(programText.charAt(lookAheadCounter)).equals(LexicalAnalyzer.Language.REGEX_NEWLINE)) {
                            // System.out.println("Next Line Started");
                            lineCounter++;
                        }

                        // We found an ID, so we should move the programCounter to
                        // however far lookAheadCounter got successfully, then
                        // reset the State and stop searching to allow this symbol
                        // to be returned to the Driver and handled by IOModule.
                        // System.out.println("State Reset by ST_ID - found an ID");
                        programCounter = lookAheadCounter;
                        resetStateAndStopSearching();
                        break;
                    }

                    // --------------------/\-------------------- ST_ID --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_COMMA --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_COMMA :
                    // System.out.println("Entering ST_COMMA");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_COMMA;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_COMMA - found a COMMA");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_COMMA --------------------/\-------------------- //
                // --------------------\/-------------------- ST_SEMICOLON --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_SEMICOLON :
                    // System.out.println("Entering ST_SEMICOLON");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_SEMICOLON;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_SEMICOLON - found a SEMICOLON");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_SEMICOLON --------------------/\-------------------- //
                // --------------------\/-------------------- ST_EQU --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_EQU :
                    // System.out.println("Entering ST_EQU");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_EQU;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_EQU - found a EQU");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_EQU --------------------/\-------------------- //
                // --------------------\/-------------------- ST_LT --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_LT :
                    // System.out.println("Entering ST_LT");

                    detectedToken = LexicalAnalyzer.Language.TOK_RS_LT;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    state = LexicalAnalyzer.Language.ST_LT_EQU;
                    break;
                // --------------------/\-------------------- ST_LT --------------------/\-------------------- //
                // --------------------\/-------------------- ST_LT_EQU --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_LT_EQU :
                    // System.out.println("Entering ST_LT_EQU");
                    lookAheadCounter++;
                    // System.out.println("Lookahead counter incremented");
                    currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                    // System.out.println(currentLexeme);
                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_RS_LTE)) {
                        detectedToken = LexicalAnalyzer.Language.TOK_RS_LTE;
                        detectedLexeme = currentLexeme;
                        lookAheadCounter++;
                        programCounter = lookAheadCounter;

                        // System.out.println("State Reset by ST_LT_EQU - found an LTE");
                        resetStateAndStopSearching();
                        break;
                    } else {
                        // System.out.println("Leaving ST_LT_EQU - did not find LTE");
                        state = LexicalAnalyzer.Language.ST_NE;
                        break;
                    }
                    // --------------------/\-------------------- ST_LT_EQU --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_NE --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_NE :
                    // System.out.println("Entering ST_NE");

                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_RS_NE)) {
                        detectedToken = LexicalAnalyzer.Language.TOK_RS_NE;
                        detectedLexeme = currentLexeme;
                        lookAheadCounter++;
                        programCounter = lookAheadCounter;

                        // System.out.println("State Reset by ST_NE - found an NE");
                        resetStateAndStopSearching();
                    } else {

                        // System.out.println("State Reset by ST_NE - did not detect an NE");
                        resetStateAndStopSearching();
                    }
                    break;
                // --------------------/\-------------------- ST_NE --------------------/\-------------------- //
                // --------------------\/-------------------- ST_GT --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_GT :
                    // System.out.println("Entering ST_GT");

                    detectedToken = LexicalAnalyzer.Language.TOK_RS_GT;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    state = LexicalAnalyzer.Language.ST_GT_EQU;
                    break;
                // --------------------/\-------------------- ST_GT --------------------/\-------------------- //
                // --------------------\/-------------------- ST_GT_EQU --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_GT_EQU :
                    // System.out.println("Entering ST_GT_EQU");
                    lookAheadCounter++;
                    // System.out.println("Lookahead counter incremented");
                    currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                    // System.out.println(currentLexeme);
                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_RS_GTE)) {
                        detectedToken = LexicalAnalyzer.Language.TOK_RS_GTE;
                        detectedLexeme = currentLexeme;
                        lookAheadCounter++;
                        programCounter = lookAheadCounter;

                        // System.out.println("State Reset by ST_GT_EQU - found an GTE");
                        resetStateAndStopSearching();
                        break;
                    } else {
                        // System.out.println("State Reset by ST_GT_EQU - did not find GTE");
                        resetStateAndStopSearching();
                        break;
                    }
                    // --------------------/\-------------------- ST_GT_EQU --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_DIGIT --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_DIGIT :
                    // System.out.println("Entering ST_DIGIT");

                    detectedToken = LexicalAnalyzer.Language.TOK_LIT_INT;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    state = LexicalAnalyzer.Language.ST_INTEGER;
                    break;

                // --------------------/\-------------------- ST_DIGIT --------------------/\-------------------- //
                // --------------------\/-------------------- ST_INTEGER --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_INTEGER :
                    // System.out.println("Entering ST_INTEGER");
                    lookAheadCounter++;
                    // System.out.println("Lookahead counter incremented");
                    currentLexeme += Character.toString(programText.charAt(lookAheadCounter));

                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_LIT_INT)) {
                        detectedLexeme = currentLexeme;

                        break;
                    } else {

                        String checkForDecimal = Character.toString(programText.charAt(lookAheadCounter));
                        // System.out.println(checkForDecimal);
                        boolean decimalFound = false;
                        switch (checkForDecimal) {
                            case LexicalAnalyzer.Language.REGEX_RS_DECIMAL :
                                // System.out.println("Leaving ST_INTEGER - found a decimal (.)");
                                // detectedToken = Language.TOK_LIT_REAL;
                                // detectedLexeme = currentLexeme;
                                decimalFound = true;
                                state = LexicalAnalyzer.Language.ST_REAL;
                                break;
                            default:
                                break;
                        }
                        if (decimalFound) {
                            programCounter = lookAheadCounter;
                            break;
                        }

                        // System.out.println("State Reset by ST_INTEGER - found an INTEGER");
                        programCounter = lookAheadCounter;
                        resetStateAndStopSearching();
                        break;
                    }
                    // --------------------/\-------------------- ST_INTEGER --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_REAL --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_REAL :
                    // System.out.println("Entering ST_REAL");
                    lookAheadCounter++;
                    // System.out.println("Lookahead counter incremented");
                    currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                    // System.out.println("FROM REST_REAL: "+currentLexeme);
                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_LIT_REAL)) {
                        detectedToken = LexicalAnalyzer.Language.TOK_LIT_REAL;
                        detectedLexeme = currentLexeme;
                        // System.out.println(currentLexeme);
                        break;
                    } else {
                        if (Character.toString(programText.charAt(lookAheadCounter)).equals(LexicalAnalyzer.Language.REGEX_NEWLINE)) {
                            // System.out.println("Next Line Started");
                            lineCounter++;
                        }
                        if (currentLexeme.contains(LexicalAnalyzer.Language.REGEX_RS_RANGE)) {
                            resetStateAndStopSearching();
                            break;
                        }

                        // System.out.println("State Reset by ST_REAL - found a REAL");
                        programCounter = lookAheadCounter;
                        resetStateAndStopSearching();
                        break;
                    }
                    // --------------------/\-------------------- ST_REAL --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_PERIOD --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_PERIOD :
                    // System.out.println("Entering ST_PERIOD");

                    detectedToken = LexicalAnalyzer.Language.TOK_RS_PERIOD;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    lookAheadCounter++;
                    // System.out.println("Lookahead counter incremented");
                    currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                    // System.out.println(currentLexeme);
                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_RS_RANGE)) {
                        state = LexicalAnalyzer.Language.ST_RANGE;
                        break;
                    } else {
                        // System.out.println("State Reset by ST_RANGE - did not find RANGE");
                        resetStateAndStopSearching();
                        hasSymbols = false;
                        break;
                    }
                    // System.out.println(currentLexeme);

                    // --------------------/\-------------------- ST_PERIOD --------------------/\-------------------- //
                    // --------------------\/-------------------- ST_RANGE --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_RANGE :
                    // System.out.println("Entering ST_RANGE");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_RANGE;
                    detectedLexeme = currentLexeme;
                    lookAheadCounter++;
                    programCounter = lookAheadCounter;

                    // System.out.println("State Reset by ST_RANGE - found a RANGE");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_RANGE --------------------/\-------------------- //
                // --------------------\/-------------------- ST_PLUS --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_PLUS :
                    // System.out.println("Entering ST_PLUS");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_PLUS;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_PLUS - found a PLUS");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_PLUS --------------------/\-------------------- //
                // --------------------\/-------------------- ST_MINUS --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_MINUS :
                    // System.out.println("Entering ST_MINUS");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_MINUS;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_MINUS - found a MINUS");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_MINUS --------------------/\-------------------- //
                // --------------------\/-------------------- ST_MULT --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_MULT :
                    // System.out.println("Entering ST_MULT");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_MULT;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_MULT - found a MULT");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_MULT --------------------/\-------------------- //
                // --------------------\/-------------------- ST_DIVIDE --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_DIVIDE :
                    // System.out.println("Entering ST_DIVIDE");
                    detectedToken = LexicalAnalyzer.Language.TOK_RW_DIV;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_DIVIDE - found a DIVIDE");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_DIVIDE --------------------/\-------------------- //
                // --------------------\/-------------------- ST_LSQBRACKET --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_LSQBRACKET :
                    // System.out.println("Entering ST_LSQBRACKET");
                    detectedToken = LexicalAnalyzer.Language.TOK_RS_LSQBRACKET;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_LSQBRACKET - found a LSQBRACKET");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_LSQBRACKET --------------------/\-------------------- //
                // --------------------\/-------------------- ST_RSQBRACKET --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_RSQBRACKET :
                    // System.out.println("Entering ST_RSQBRACKET");
                    detectedToken = Language.TOK_RS_RSQBRACKET;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    // System.out.println("State Reset by ST_RSQBRACKET - found a RSQBRACKET");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- ST_RSQBRACKET --------------------/\-------------------- //
                // --------------------\/-------------------- ST_SINGLEQT --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_SINGLEQT :
                    // System.out.println("Entering ST_SINGLEQT");
                    detectedToken = LexicalAnalyzer.Language.TOK_LIT_STR;
                    currentLexeme += currentChar;
                    detectedLexeme = currentLexeme;
                    lookAheadCounter++;
                    if (Character.toString(programText.charAt(lookAheadCounter)).equals(Language.REGEX_NEWLINE)) {
                        // System.out.println("Next Line Started");
                        lineCounter++;
                        currentLexeme += Character.toString(programText.charAt(lookAheadCounter)).replace("\n","\\n");
                    } else {
                        currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                    }
                    // System.out.println("Leaving state ST_SINGLEQT - found a SINGLEQT");
                    state = LexicalAnalyzer.Language.ST_SINGLEQT_ACCEPTALL;
                    break;
                // --------------------/\-------------------- ST_SINGLEQT --------------------/\-------------------- //
                // --------------------\/-------------------- ST_SINGLEQT_ACCEPTALL --------------------\/-------------------- //
                case LexicalAnalyzer.Language.ST_SINGLEQT_ACCEPTALL :
                    // System.out.println("Entering ST_SINGLEQT_ACCEPTALL");
                    if (currentLexeme.matches(LexicalAnalyzer.Language.REGEX_LIT_STRING)) {
                        detectedLexeme = currentLexeme;
                        if (detectedLexeme.replace("'","").length()==1) {
                            detectedToken = LexicalAnalyzer.Language.TOK_LIT_CHAR;
                        }
                        lookAheadCounter++;
                        programCounter = lookAheadCounter;

                        // System.out.println("State Reset by ST_SINGLEQT_ACCEPTALL - found a STRING");
                        resetStateAndStopSearching();
                        break;
                    } else {
                        lookAheadCounter++;
                        // System.out.println("Lookahead counter incremented");
                        // System.out.println("No state change - have not yet detected SINGLEQT");
                        if (Character.toString(programText.charAt(lookAheadCounter)).equals(LexicalAnalyzer.Language.REGEX_NEWLINE)) {
                            // System.out.println("Next Line Started");
                            lineCounter++;
                            currentLexeme += Character.toString(programText.charAt(lookAheadCounter)).replace("\n","\\n");
                        } else {
                            currentLexeme += Character.toString(programText.charAt(lookAheadCounter));
                        }
                        break;
                    }
                    // --------------------/\-------------------- ST_SINGLEQT_ACCEPTALL --------------------/\-------------------- //
                    // --------------------\/-------------------- DEFAULT --------------------\/-------------------- //
                default:
                    // System.out.println("State Reset by default case");
                    resetStateAndStopSearching();
                    break;
                // --------------------/\-------------------- DEFAULT --------------------/\-------------------- //
            }
        }

        return detectedLexeme;
    }

    // Returns whether the Lexer is ready to read symbols
    public boolean isReady() {
        return hasSymbols;
    }

    // Returns the detectedToken
    public String getDetectedToken() {
        return detectedToken;
    }

    // Returns the detectedLexeme
    public String getDetectedLexeme() {
        return detectedLexeme;
    }

    // Used to reset the state and stopSearching
    public void resetStateAndStopSearching() {
        state = LexicalAnalyzer.Language.ST_START;
        stopSearching();
    }

    // Used to reset the state and restart searching from the first state
    public void resetState() {
        System.out.println("STATE RESET from resetState()");
        state = LexicalAnalyzer.Language.ST_START;
    }

    // Used to stop searching for more symbols to add to the detectedLexeme
    public void stopSearching() {
        stopSearching = true;
    }
}
