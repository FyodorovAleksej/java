#include "command.h"

Command::Command(char* new_name)
{
    this->name = new char[strlen(new_name) + 1];
    strcpy(this->name,new_name);
}
bool Command::equals(char* new_name)
{
    int j = strlen(this->name);
    if (j > strlen(new_name)){
        return false;
    }
    else
    {
        for (int i = strlen(new_name);i >= strlen(new_name) - strlen(this->name);i--)
        {
            if (this->name[j--] != new_name[i])
                return false;
        }
    return true;
    }
}
Command::~Command()
{
    delete this->name;
}
Command* Command::read(FILE *file)
{
    char c;
    int len = 0;
    do
    {
      fread(&c,1,1,file);
      len++;
    } while(c!='\t');
    char* str = new char[len];
    fseek(file,-len,1);
    fread(str,1,len,file);
    fseek(file,1,1);
    return new Command(str);
}
void Command::write(FILE *file)
{
    fwrite(this->name,1,strlen(this->name),file);
    fprintf(file,"\t");
}
