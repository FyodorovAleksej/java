#define M 51                                               // ����� �����
#include<stdio.h>
#include<stdlib.h>
#include<conio.h>
#include<windows.h>
void out(char**s, int n,int cool,int cl,int score,int frags);        // �����
int move(char**s, int n,int *score,int*frags);                       // ����� ���������
void move_up(char**s, int n);                              // �������� �����
void move_down(char**s, int n);                            // �������� ����
void shoot(char**s, int n);                                // �������
void gener(char**s, int n);                                // �������� �����������
void gameover();                                           // ����� ����
int main()
{
	int i,N;
	char**s;
	printf("input N\n");
	scanf("%d", &N);                                       // ���� ���-�� �����
	s = (char**)malloc(N*sizeof(char*));                   // ������
	for (i = 0; i < N; i++)                         
		s[i] = (char*)malloc((M+1) * sizeof(char));
	int j;
	for (i = 0; i < N; i++)
		for (j = 0; j < M; j++)                            // ���������� ���������
			s[i][j] = ' ';
	s[N / 2][0] = '>';                                     // ������� ������
	for (i = 0; i < N; i++)
	{
		s[i][M] = '\0';
		s[i][M - 1] = '|';
	}
	int up = 0, down = 0, sh = 0, frags = 0;               // ���������� ��� ������
	int cool = 100, cl = 1, score = 0,choose=0,reman=0;    // ���������� ��� ��������
	while (choose==0)                                      // ���� ���� �� �����������
	{
		gener(s, N);                                       // �������� ����������
		for (i = 0; i < 100000; i++)
		{
			if (GetKeyState(VK_UP) < 0 && up==0)           // ������������ �����
			{
				move_up(s, N);
				up = 1;                                    // ����������
				break;
			}
			else
				if(GetKeyState(VK_UP) > -1)up = 0;         // ����������
			if (GetKeyState(VK_DOWN) < 0 && down==0)       // ������������ ����
			{
				move_down(s, N);                           // ������������ ����
				down = 1;                                  // ����������
				break;
			}
			else 
				if (GetKeyState(VK_DOWN) > -1)down = 0;    // ����������
			if (GetKeyState(VK_RIGHT) < 0 && reman == 0 && cool!=100 && cl==1) // �����������
			{
				reman = 1;                                 // ����������
				cool = 0;                                  // ��������� �����������
				cl = 0;
				break;
			}
			else
				if (GetKeyState(VK_RIGHT) > -1)reman= 0;   // ����������
			if (GetKeyState(VK_SPACE) < 0 && sh==0 && cool!=0 &&cl==1) // �������
			{
				shoot(s, N);                               // �������
				cool--;                                    // -������
				sh = 1;
				for (int q = sh * 10; q < 50000000; q++);  // ����������
				break;
			}
			else sh = 0;                                   // ��� ����������
		}
		if (cool == 0) cl = 0;                             // �����������
		if (cl == 0) cool += 10;
		if (cool == 100 && cl == 0) cl = 1;
		choose=move(s, N,&score,&frags);                   // ������������ ��������
		for (i = 0; i < 10000; i++);                       // ������
		out(s, N, cool, cl,score,frags);                   // �����
	}
	for (i = N - 1; i >= 0; i--)
		free(s[i]);
	free(s);
	return 0;
}
void gener(char**s, int n)
{
	int j;
	srand(time(NULL));
	if (rand() % 3==0) return;                             // �������� ���������� � 2/3 �������
	j = rand() % n;                                        // ����� ����
	s[j][M-2] = '<';                                       // �������� ����������
	s[j][M] = '\0';
	return;
}
void move_up(char**s, int n)
{
	int i = 0,j=0;
	for (i = 0; i < n; i++)
		if (s[i][0] == '>') j = i;                         // ���������� ������� ������
	if (j != 0)                                            // ���������� �������
	{
		s[j - 1][0] = '>';                                 // �������� �������
		s[j][0] = ' ';
	}
	return;
}
void move_down(char**s, int n)
{
	int i = 0, j = 0;
	for (i = 0; i < n; i++)
		if (s[i][0] == '>') j = i;                         // ���������� ������� ������
	if (j != n-1)                                          // ���������� �������
	{
		s[j + 1][0] = '>';                                 // ������������ �������
		s[j][0] = ' ';
	}
	return;
}
void shoot(char**s, int n)
{
	int i = 0, j = 0;
	for (i = 0; i < n; i++)
		if (s[i][0] == '>') j = i;                         // ���������� �������
	s[j][1] = '-';                                         // �������� ��������
	return;
}
int move(char**s, int n,int *score,int *frags)
{
	int i,j;
	for (i = 0; i < n; i++)
		for (j = 1; j < M; j++)
		{
			if (s[i][j] == '<')                            // ����� ������� ����������
			{
				if (s[i][j - 1] == '-')                    // �������� �� ���������
				{
					if (s[i][j + 1] == ' ' || s[i][j + 1] == '|')
						(*frags)++;
					(*score)++;
					s[i][j - 1] = ' ';                     // ����������� ���������� � �������
					s[i][j] = ' ';
					continue;
				}
				if (j - 1 == 0)                            // ����� �� �������
				{
					gameover();
					return 1;
				}
				s[i][j - 1] = '<';                         // ������� ������������
				s[i][j] = ' ';
			}
		}
	for (i = 0; i < n; i++)
		for (j = M - 1; j >= 0; j--)
		{
			if (s[i][j] == '-')                            // ���������� ������� ��������
			{
				if (j == M - 2)                            // ����� �� �������
				{
					s[i][j] = ' ';
					continue;
				}
				if (s[i][j + 1] == '<')                    // �������� �� ���������
				{
					if (s[i][j + 2] == ' ' || s[i][j + 2] == '|')
						(*frags)++;
					(*score)++;
					s[i][j + 1] = ' ';                     // ����������� �������� � ����������
					s[i][j] = ' ';
				}
				s[i][j + 1] = '-';                         // ������� ������������
				s[i][j] = ' ';
			}
		}
	return 0;
}
void gameover()
{
	printf("\n\nGameover\n\n");
	system("pause");
	return;
}
void out(char**s,int n,int cool,int cl,int score,int frags)
{
	system("cls");             // ���� ������
	for (int q = 0; q < M; q++)
		printf("_");
	printf("\n");
	for (int i = 0; i < n-1; i++)
	{
		puts(s[i]);
		printf("\n\n");
	}
	puts(s[n - 1]);
	printf("\n");
	for (int q = 0; q < M; q++)
		printf("_");
	printf("\n");           
	if (cl == 0) printf("cooldown -- %d\n", (100 - cool) / 10);  // ���� ��� �����������
	else printf("shots -- %d\n",cool);                     // ���������� �������
	printf("\n your score is -- %d    your frags is -- %d\n",score,frags);              // ����� �����
	return;
}