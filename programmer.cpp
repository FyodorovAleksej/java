#include "programmer.h"

Programmer::Programmer()
{

}
bool Programmer::getCommand(Compiler *res, char* name)
{
    return (res->getCommand(name));
}
