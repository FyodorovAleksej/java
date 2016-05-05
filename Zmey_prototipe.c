#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <windows.h>                                       // ���������� ��� ������
int N = 3;
int gener(char**s, int m, int n);
struct snake                                               // ������ ����� ��� ������
{
	int x;
	int y;
}h[100] = { 0 };
struct choise                                              // ����� �����������
{
	int UP;
	int DOWN;
	int LEFT;
	int RIGHT;
};
int gameover();
void block(int *flag,int*oldflag);
int check(struct choise *last);                            // �-��� ��� ������� ����������� ��������
void out(char**s, int n, int m);                           // �-��� ������ ��������
int move(char**s,int n,int m,int**mat);                    // ����� �-��� ��������
void fuell(char**s, int n, int m);                         // �-��� ���������� �����
int main()
{
	int **mat;                                             // �������� ������� (����� ��� ����������� ���������)
	char**s;                                               // ����� �� �������
	int n,m, i,j;
	printf("\ninput n\n");                                 // ����������� �� ���������
	scanf("%d", &n);
	rewind(stdin);
	printf("\ninput m\n");                                 // ����������� �� �����������
	scanf("%d", &m);
	rewind(stdin);
	mat = (int**)malloc(n*sizeof(int*));                   // ��������� ������...
	for (i = 0; i < n; i++)
	{
		mat[i] = (int*)malloc( m*sizeof(int));
		for (j = 0; j < m; j++) mat[i][j] = 0;             // ��������� �������� �������
	}
	s = (char**)malloc((n+1)*sizeof(char*));
	for (i = 0; i < n+1; i++)
		s[i] = (char*)malloc( m*2 + 2);  
	fuell(s, n, m);                                        // ���������� ����� ��������� | |
	move(s,n,m,mat);                                       // ����� �������� �-���
	return 0;
}
void fuell(char**s, int n, int m)
{
	int i, j;
	for (i = 0; i < n; i++)
		for (j = 0; j <= m * 2; j++)                       // ���������� ����� ��������� | |
			if (j % 2 == 0) s[i][j] = '|';                 // ���� ������ ����������
			else s[i][j] = ' ';                            // ���� �������� ����������
			for (i = 0; i < n; i++)                        // ������� ������� � ������
				s[i][m * 2 + 1] = '\0';
	return;
}
int move(char**s, int n, int m,int**mat)
{
	struct choise last;                                    // ���������� ���� ���������� ��������� ��������� ������
	last.DOWN = 0;
	last.UP = 0;
	last.RIGHT = 0;
	last.LEFT = 0;
	h[2].y = n / 2;                                        // ������ ���������� ����� �������
	h[2].x = 0;                                            // ���������� ��������� ������ ������ ������
	h[1].y = n / 2;
	h[1].x = 1;
	h[0].y = n / 2;
	h[0].x = 2;
	s[n / 2][1] = -37;
	s[n / 2][3] = -37;
	s[n / 2][5] = -37;                                     // ����� ����������
	out(s, n, m);                                          // ����� ������ ������� �����
	int flag = 0, i = 5, j = n / 2, f, k, kal = 0, oldflag=0;
	gener(s, m, n);
	while (gameover()==0)
	{
		for ( k = 0; k < 100000; k++)                      // ���������� ������� � ����������
		{
			if (GetKeyState(VK_UP) < 0 || GetKeyState(VK_DOWN) < 0 || GetKeyState(VK_RIGHT) < 0 || GetKeyState(VK_LEFT) < 0)   // ���� ������ ���� ���� �������
			{
				flag = check(&last);                       // �������� �� ��������� ��������� ������
				kal = 1;                                   // ������ ��� ���������� �������
				break;                                     // ����� ���� �����-�� ������� ���� ������
			}
		}
		block(&flag, &oldflag);
		if (kal == 1)                                      // ���������� �������
		{
			for (int b = (k+1)*3; b < 25000000; b++);
			kal = 0;
		}
		mat[h[0].y][h[0].x] = flag;                        // ����������� ���������� �������� � �������� �������
		for (int y = 0; y < (n/4)*(m/4)*3000000; y++);                // �������� (������)
		int p = 0, old_x = 0, old_y = 0;
		for (int q = 0; q < N;q++)                         // ������� ������ ������ ������
		{
			i = h[q].x * 2 + 1;                            // ����������� ��������� �� ������� �������������� ������ ������
			j = h[q].y;
			switch (mat[h[q].y][h[q].x])                   // �������� ������������ �������� � ����� ���������� q-�� ������ ������
			{
			case 1:                                        // ������������ ����
			{
				f = j;                                     // ����������� ������ ����������
				if (j >= n - 1) j = j - n + 1;             // ������� �� ���� �����
				else j++; 
				if (s[j][i] == 1)
				{
					old_x = h[N-1].x * 2 + 1;
					old_y = h[N - 1].y;
					p = 1;
					gener(s, m, n);
				}
				s[j][i] = -37;                             // ��������� ����� ������
				s[f][i] = ' ';                             // �������� ������ ������
				h[q].y = j;                                // ����������� ������ ����������
				//out(s, n, m);                              // ����� ������ �����
				break;
			}
			case 2:                                        // ������������ �����
			{
				f = j;                                     // ����������� ������ ����������
				if (j - 1 < 0) j = j + n - 1;              // ������� �� ���� �����
				else j--;
				if (s[j][i] == 1)
				{
					old_x = h[N - 1].x * 2 + 1;
					old_y = h[N - 1].y;
					p = 1;
					gener(s, m, n);
				}
				s[j][i] = -37;                             // ��������� ����� ������
				s[f][i] = ' ';                             // �������� ������ ������
				h[q].y = j;                                // ����������� ������ ����������
				//out(s, n, m);                              // ����� ������ �����
				break;
			}
			case 3:                                        // ������������ �����
			{
				f = i;                                     // ����������� ������ ������
				if (i <= 1) i = i + m * 2-2 ;              // ������� �� ���� �����
				else i = i - 2;   
				if (s[j][i] == 1)
				{
					old_x = h[N - 1].x * 2 + 1;
					old_y = h[N - 1].y;
					p = 1;
					gener(s, m, n);
				}
				s[j][i] = -37;                             // ��������� ����� ������
 				s[j][f] = ' ';                             // �������� ������ ������
				h[q].x = i/2;                              // ����������� ����� ����������
				//out(s, n, m);                              // ����� ������ �����
				break;
			}
			case 4:                                        // ������������ ������
			{
				f = i;                                     // ����������� ������ ������
				if (i >= m*2 - 1) i = i - m * 2 + 2;       // ������� �� ���� �����
				else i = i + 2;
				if (s[j][i] == 1)
				{
					old_x = h[N - 1].x * 2 + 1;
					old_y = h[N - 1].y;
					p = 1;
					gener(s, m, n);
				}
				s[j][i] = -37;                             // ��������� ����� ������
				s[j][f] = ' ';                             // �������� ������ ������
				h[q].x = i / 2;                            // ����������� ����� ����������
				//out(s, n, m);                              // ����� ������ �����
				break;
			}
			default :                                      // ������������ �� ������� ��������
			{
				f = i;                                     // �� �� �����, ��� � �������� ������
				if (i >= m * 2 - 1) i = i - m * 2 + 2;
				else i = i + 2;
				if (s[j][i] == 1)
				{
					old_x = h[N - 1].x*2+1;
					old_y = h[N - 1].y;
					p = 1;
					gener(s, m, n);
				}
				s[j][i] = -37;
				s[j][f] = ' ';
				h[q].x = i / 2;
				//out(s, n, m);
				break;
			}
		}	
			if (q == N - 1 && p == 1)
			{
				s[old_y][old_x] = -37;
				h[N].x = old_x/2;
				h[N].y = old_y;
				N++;
				p = 0;
				break;
			}
	}
	out(s, n, m);
  }
  system("pause");
}
void out(char**s, int n, int m)                            // �-��� ������ �����
{
	int i;
	system("cls");
	printf("\n\n\n");                                      // ������� ����
	for (i = 0; i < n-1; i++) 
	{
		puts(s[i]);                                        // ����� ������ �������
		printf("\n"); 
	}
	puts(s[n - 1]);
	printf("\n\n     your score is - %d", N * 10 - 30);
	return ;
}
int check(struct choise *last)                             // �������� ��������� � ��������� ������
{
	struct choise n;                                       // ����� ���������
	n.DOWN = GetKeyState(VK_DOWN);                         // ��������� ������� ����
	n.UP = GetKeyState(VK_UP);                             // ��������� ������� �����
	n.LEFT = GetKeyState(VK_LEFT);                         // ��������� ������� �����
	n.RIGHT = GetKeyState(VK_RIGHT);                       // ��������� ������� ������
	if ((n.DOWN !=last->DOWN) || (n.UP != last->UP) || (n.LEFT != last->LEFT) || (n.RIGHT != last->RIGHT))   // ��������� � ���������� ����������
	{
		if (n.DOWN < 0)                                    // ����� �� ������ ���� ������?
		{
			if (n.DOWN ==-127) last->DOWN=1 ;              // ���������� ��������� � ��������� ����
			else last->DOWN = 0;                           // ���������� ��������� � ��������� ����
			return 1;                                      // ������� 1
		}
		if (n.UP < 0)
		{
			if (n.UP == -127) last->UP = 1;                // ���������� ��������� � ��������� �����
			else last->UP = 0;                             // ���������� ��������� � ��������� �����
			return 2;                                      // ������� 2
		}
		if (n.LEFT <0) 
		{
			if (n.LEFT == -127) last->LEFT = 1;            // ���������� ��������� � ��������� �����
			else last->LEFT = 0;                           // ���������� ��������� � ��������� �����
			return 3;                                      // ������� 3
		}
		if (n.RIGHT <0)
		{
			if (n.RIGHT == -127) last->RIGHT = 1;          // ���������� ��������� � ��������� ������
			else last->RIGHT = 0;                          // ���������� ��������� � ��������� ������
			return 4;                                      // ������� 4
		}
	}
	return 0;                                              // ���� ������ ��� � �� ���������� (�� ������ ������)
}
int gener(char**s,int m,int n)
{
	int i = 0,f=0,j=0;
	srand(time(NULL));
	while (1)
	{
		f = rand() % m;
		j = rand() % n;
		for (i = 0; i < N; i++)
			if (h[i].x != f && h[i].y != j)
			{
				s[j][f * 2 + 1] = 1;
				return 1;
			}
	}
	s[rand() % n][(rand() % m) * 2 + 1] = 1;
	return 1;
}
void block(int*flag, int*oldflag)
{
	switch (*flag)
	{
	case 1:
		{
			if (*oldflag == 2)
			{
				(*flag = 2);
				return;
			}
			break;
		}
	case 2:
     	{
			if (*oldflag == 1)
			{
				(*flag = 1);
				return;
			}
			break;
	    }
	case 3:
	    {
			if (*oldflag == 4 || *oldflag==0)
			{
				(*flag = 4);
				return;
			}
			break;
    	}
	case 4:
		{
			if (*oldflag == 3)
			{
				(*flag = 3);
				return;
			}
			break;
		}
	}
	*oldflag = *flag;
	return;
}
int gameover()
{
	int i,j;
	for (i = 0; i < N-1; i++)
		for (j = i + 1; j < N;j++)
			if ((h[i].x == h[j].x) && (h[i].y == h[j].y))
			{
				printf("\n\n\n\n You lose. Your score is - %d\n", N * 10 - 30);
				return 1;
			}
	return 0;
}