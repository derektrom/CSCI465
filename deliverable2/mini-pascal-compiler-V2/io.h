/*
 * @author Derek Trom
 * @author Elena Corpus
 * This is the io module that serves as the input for the scanner
 */

#ifndef IO_H
#define IO_H

#include <stdio.h>

extern unsigned pclineno;	/* current line number */

/*
Prints out an error message to the error console.
@see printf.
@return always return 0
*/
int pcerror(const char *format, ...);

/*
Gets the next character from the FILE.

@param fp the FILE pointer
@return next character in the FILE
*/
char pcgetc(FILE *fp);

/*
Puts a character back onto the FILE.

@param c the character to put back into the FILE
@param fp the FILE pointer
*/
void pcungetc(char c, FILE *fp);

#endif /* IO_H */