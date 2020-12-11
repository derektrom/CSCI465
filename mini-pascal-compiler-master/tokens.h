/*
 * @author Derek Trom
 * @author Elena Corpus
 * tokens.h 
 */

#ifndef TOKENS_H
#define TOKENS_H

/*
All of our possible sym value types.
*/
typedef union symval {
    int ival;
    double rval;
    char cval;
    char *id;
    char *str;
} symval;

/*
All of our possible tokens.
KEEP UP TO DATE WITH pcsymstr.
*/
typedef enum {
    /* End-of-Tokens */
    eofsym = 0,

    /* Operators */
    idivsym,
    modsym,
    addsym,
    minussym,
    multsym,
    divsym,

    /* Scopes */
    programsym,
    proceduresym,
    functionsym,
    beginsym,
    endsym,

    /* Boolean operators */
    andsym,
    orsym,
    notsym,
    ltsym,
    ltesym,
    neqsym,
    gtsym,
    gtesym,
    eqsym,

    /* Punctuation */
    assignsym,
    colonsym,
    semicolonsym,
    commasym,
    dotsym,
    dotdotsym,
    lparensym,
    rparensym,
    lbracksym,
    rbracksym,

    /* Control flow */
    ifsym,
    elsesym,
    thensym,
    dosym,
    whilesym,

    /* Variables */
    idsym,
    arraysym,
    ofsym,
    charsym,
    stringsym,
    integersym,
    realsym,
    varsym,

    /* Constants */
    integernosym,
    realnosym,
    stringvalsym,
    charvalsym,
    constsym,

    /* Built-in functions */
    chrsym,
    ordsym,
    readsym,
    readlnsym,
    writesym,
    writelnsym,

    /* Number of syms */
    numsyms
} pcsym;

/*
Array of string representation for each sym.
KEEP UP TO DATE WITH pcsym enum.
*/
extern const char *pcsymstr[numsyms];

/* Structure for each token generated by our scanner. */
typedef struct pctoken {
    pcsym sym;
    symval val;
    unsigned lineno;
} pctoken;

/*
Creates a new token with the given values.

@param sym the sym type
@param val the value
@param lineno the line number
@return a malloc'd token or NULL on error
*/
pctoken *pcnewtoken(pcsym sym, symval val, unsigned lineno);

#endif /* TOKENS_H */