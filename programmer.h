#include "compiler.h"
#ifndef CLIENT_H
#define CLIENT_H


class Programmer
{
public:
    Programmer();
    bool getCommand(Compiler*,char*);
};

#endif // CLIENT_H
