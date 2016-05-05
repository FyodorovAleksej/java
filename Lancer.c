#define M 51                                               // Длина строк
#include<stdio.h>
#include<stdlib.h>
#include<conio.h>
#include<windows.h>
void out(char**s, int n,int cool,int cl,int score,int frags);        // Вывод
int move(char**s, int n,int *score,int*frags);                       // Сдвиг элементов
void move_up(char**s, int n);                              // Движение вверх
void move_down(char**s, int n);                            // Движение вниз
void shoot(char**s, int n);                                // Выстрел
void gener(char**s, int n);                                // Создание противников
void gameover();                                           // Конец игры
int main()
{
	int i,N;
	char**s;
	printf("input N\n");
	scanf("%d", &N);                                       // Ввод кол-ва строк
	s = (char**)malloc(N*sizeof(char*));                   // Память
	for (i = 0; i < N; i++)                         
		s[i] = (char*)malloc((M+1) * sizeof(char));
	int j;
	for (i = 0; i < N; i++)
		for (j = 0; j < M; j++)                            // Заполнение пробелами
			s[i][j] = ' ';
	s[N / 2][0] = '>';                                     // Корабль игрока
	for (i = 0; i < N; i++)
	{
		s[i][M] = '\0';
		s[i][M - 1] = '|';
	}
	int up = 0, down = 0, sh = 0, frags = 0;               // Переменные для клавиш
	int cool = 100, cl = 1, score = 0,choose=0,reman=0;    // Переменные для стрельбы
	while (choose==0)                                      // Пока игра не закончилась
	{
		gener(s, N);                                       // Создание противника
		for (i = 0; i < 100000; i++)
		{
			if (GetKeyState(VK_UP) < 0 && up==0)           // Передвижение вверх
			{
				move_up(s, N);
				up = 1;                                    // Блокировка
				break;
			}
			else
				if(GetKeyState(VK_UP) > -1)up = 0;         // Блокировка
			if (GetKeyState(VK_DOWN) < 0 && down==0)       // Передвижение вниз
			{
				move_down(s, N);                           // Передвижение вниз
				down = 1;                                  // Блокировка
				break;
			}
			else 
				if (GetKeyState(VK_DOWN) > -1)down = 0;    // Блокировка
			if (GetKeyState(VK_RIGHT) < 0 && reman == 0 && cool!=100 && cl==1) // Перезарядка
			{
				reman = 1;                                 // Блокировка
				cool = 0;                                  // Состояние перезарядки
				cl = 0;
				break;
			}
			else
				if (GetKeyState(VK_RIGHT) > -1)reman= 0;   // Блокировка
			if (GetKeyState(VK_SPACE) < 0 && sh==0 && cool!=0 &&cl==1) // Выстрел
			{
				shoot(s, N);                               // Выстрел
				cool--;                                    // -Патрон
				sh = 1;
				for (int q = sh * 10; q < 50000000; q++);  // Калибровка
				break;
			}
			else sh = 0;                                   // Нет блокировки
		}
		if (cool == 0) cl = 0;                             // Перезарядка
		if (cl == 0) cool += 10;
		if (cool == 100 && cl == 0) cl = 1;
		choose=move(s, N,&score,&frags);                   // Передвижение объектов
		for (i = 0; i < 10000; i++);                       // Таймер
		out(s, N, cool, cl,score,frags);                   // Вывод
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
	if (rand() % 3==0) return;                             // Создание противника в 2/3 случаев
	j = rand() % n;                                        // Выбор ряда
	s[j][M-2] = '<';                                       // Создание противника
	s[j][M] = '\0';
	return;
}
void move_up(char**s, int n)
{
	int i = 0,j=0;
	for (i = 0; i < n; i++)
		if (s[i][0] == '>') j = i;                         // Нахождение корабля игрока
	if (j != 0)                                            // Блокировка границы
	{
		s[j - 1][0] = '>';                                 // Смещение корабля
		s[j][0] = ' ';
	}
	return;
}
void move_down(char**s, int n)
{
	int i = 0, j = 0;
	for (i = 0; i < n; i++)
		if (s[i][0] == '>') j = i;                         // Нахождение корабля игрока
	if (j != n-1)                                          // Блокировка границы
	{
		s[j + 1][0] = '>';                                 // Передвижение корабля
		s[j][0] = ' ';
	}
	return;
}
void shoot(char**s, int n)
{
	int i = 0, j = 0;
	for (i = 0; i < n; i++)
		if (s[i][0] == '>') j = i;                         // Нахождение корабля
	s[j][1] = '-';                                         // Создание выстрела
	return;
}
int move(char**s, int n,int *score,int *frags)
{
	int i,j;
	for (i = 0; i < n; i++)
		for (j = 1; j < M; j++)
		{
			if (s[i][j] == '<')                            // Поиск каждого противника
			{
				if (s[i][j - 1] == '-')                    // Проверка на попадание
				{
					if (s[i][j + 1] == ' ' || s[i][j + 1] == '|')
						(*frags)++;
					(*score)++;
					s[i][j - 1] = ' ';                     // Уничтожение противника и патрона
					s[i][j] = ' ';
					continue;
				}
				if (j - 1 == 0)                            // Выход за границы
				{
					gameover();
					return 1;
				}
				s[i][j - 1] = '<';                         // Обычное передвижение
				s[i][j] = ' ';
			}
		}
	for (i = 0; i < n; i++)
		for (j = M - 1; j >= 0; j--)
		{
			if (s[i][j] == '-')                            // Нахождение каждого выстрела
			{
				if (j == M - 2)                            // Выход за границу
				{
					s[i][j] = ' ';
					continue;
				}
				if (s[i][j + 1] == '<')                    // Проверка на попадание
				{
					if (s[i][j + 2] == ' ' || s[i][j + 2] == '|')
						(*frags)++;
					(*score)++;
					s[i][j + 1] = ' ';                     // Уничтожение выстрела и противника
					s[i][j] = ' ';
				}
				s[i][j + 1] = '-';                         // Обычное передвижение
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
	system("cls");             // Окно вывода
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
	if (cl == 0) printf("cooldown -- %d\n", (100 - cool) / 10);  // Если идёт перезарядка
	else printf("shots -- %d\n",cool);                     // Оставшиеся патроны
	printf("\n your score is -- %d    your frags is -- %d\n",score,frags);              // Вывод счёта
	return;
}