/*
 * @author Derek Trom
 * @author Elena Corpus
 * compiler.h is the main entry point of the program and is responsible
 * for handling user input and running the other portions of the compiler,
 * such as the scanner and parser.
*/

#ifndef ICG_H
#define ICG_H

#include "ast.h"

int pcicg_start(FILE *fpo);

#endif /* ICG_H */