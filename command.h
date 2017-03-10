#include <string.h>
#include <stdio.h>
#ifndef MEAL_H
#define MEAL_H


class Command
{
private:
    char* name;
public:
    Command(char*);
    ~Command();
    bool equals(char*);
    static Command* read(FILE*);
    void write(FILE*);
};

#endif // MEAL_H
