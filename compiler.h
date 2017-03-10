#include "command.h"
#ifndef RESTORAN_H
#define RESTORAN_H


class Compiler
{
private:
    int count_of_commands;
    Command **commands;
public:
    Compiler(int);
    ~Compiler();
    bool isContain(char*);
    bool getCommand(char*);
    void write();
    static Compiler* read();
};

#endif // COMPILER_H
