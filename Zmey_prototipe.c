#include <stdio.h>
#include <stdlib.h>
#include <conio.h>
#include <windows.h>                                       // Библиотека для клавиш
int N = 3;
int gener(char**s, int m, int n);
struct snake                                               // Массив ячеек для змейки
{
	int x;
	int y;
}h[100] = { 0 };
struct choise                                              // Выбор направления
{
	int UP;
	int DOWN;
	int LEFT;
	int RIGHT;
};
int gameover();
void block(int *flag,int*oldflag);
int check(struct choise *last);                            // Ф-ция для проврки направления движения
void out(char**s, int n, int m);                           // Ф-ция вывода картинки
int move(char**s,int n,int m,int**mat);                    // Общая ф-ция движения
void fuell(char**s, int n, int m);                         // Ф-ция заполнения карты
int main()
{
	int **mat;                                             // Цифровая матрица (нужна для запоминания поворотов)
	char**s;                                               // Карта со змейкой
	int n,m, i,j;
	printf("\ninput n\n");                                 // Размерность по вертикали
	scanf("%d", &n);
	rewind(stdin);
	printf("\ninput m\n");                                 // Размерность по горизонтали
	scanf("%d", &m);
	rewind(stdin);
	mat = (int**)malloc(n*sizeof(int*));                   // Выделение памяти...
	for (i = 0; i < n; i++)
	{
		mat[i] = (int*)malloc( m*sizeof(int));
		for (j = 0; j < m; j++) mat[i][j] = 0;             // Обнуление цифровой матрицы
	}
	s = (char**)malloc((n+1)*sizeof(char*));
	for (i = 0; i < n+1; i++)
		s[i] = (char*)malloc( m*2 + 2);  
	fuell(s, n, m);                                        // Заполнение карты символами | |
	move(s,n,m,mat);                                       // Вызов основной ф-ции
	return 0;
}
void fuell(char**s, int n, int m)
{
	int i, j;
	for (i = 0; i < n; i++)
		for (j = 0; j <= m * 2; j++)                       // Заполнение карты символами | |
			if (j % 2 == 0) s[i][j] = '|';                 // Если чётная координата
			else s[i][j] = ' ';                            // Если нечётная координата
			for (i = 0; i < n; i++)                        // Обрезка муссора в строке
				s[i][m * 2 + 1] = '\0';
	return;
}
int move(char**s, int n, int m,int**mat)
{
	struct choise last;                                    // Переменная ласт определяет последнее состояние клавиш
	last.DOWN = 0;
	last.UP = 0;
	last.RIGHT = 0;
	last.LEFT = 0;
	h[2].y = n / 2;                                        // Первое заполнение карты змейкой
	h[2].x = 0;                                            // Сохранение координат каждой ячейки змейки
	h[1].y = n / 2;
	h[1].x = 1;
	h[0].y = n / 2;
	h[0].x = 2;
	s[n / 2][1] = -37;
	s[n / 2][3] = -37;
	s[n / 2][5] = -37;                                     // Конец заполнения
	out(s, n, m);                                          // Вывод самого первого кадра
	int flag = 0, i = 5, j = n / 2, f, k, kal = 0, oldflag=0;
	gener(s, m, n);
	while (gameover()==0)
	{
		for ( k = 0; k < 100000; k++)                      // Считывание клавиши с клавиатуры
		{
			if (GetKeyState(VK_UP) < 0 || GetKeyState(VK_DOWN) < 0 || GetKeyState(VK_RIGHT) < 0 || GetKeyState(VK_LEFT) < 0)   // Если нажата хоть одна клавиша
			{
				flag = check(&last);                       // Проверка на изменение состояния клавиш
				kal = 1;                                   // Флажок для калибровки времени
				break;                                     // Выход если какая-то клавиша была нажата
			}
		}
		block(&flag, &oldflag);
		if (kal == 1)                                      // Калибровка времени
		{
			for (int b = (k+1)*3; b < 25000000; b++);
			kal = 0;
		}
		mat[h[0].y][h[0].x] = flag;                        // Запоминание считанного поворота в цифровой матрице
		for (int y = 0; y < (n/4)*(m/4)*3000000; y++);                // Задержка (таймер)
		int p = 0, old_x = 0, old_y = 0;
		for (int q = 0; q < N;q++)                         // Просчёт каждой ячейки змейки
		{
			i = h[q].x * 2 + 1;                            // Выставление координат на позицию просчитываемой ячейки змейки
			j = h[q].y;
			switch (mat[h[q].y][h[q].x])                   // Просмотр запомненного поворота в точке нахождения q-ой ячейки змейки
			{
			case 1:                                        // Передвижение вниз
			{
				f = j;                                     // Запоминание старой координаты
				if (j >= n - 1) j = j - n + 1;             // Переход за края карты
				else j++; 
				if (s[j][i] == 1)
				{
					old_x = h[N-1].x * 2 + 1;
					old_y = h[N - 1].y;
					p = 1;
					gener(s, m, n);
				}
				s[j][i] = -37;                             // Рисование новой ячейки
				s[f][i] = ' ';                             // Стирание старой ячейки
				h[q].y = j;                                // Запоминание старой координаты
				//out(s, n, m);                              // Вывод нового кадра
				break;
			}
			case 2:                                        // Передвижение вверх
			{
				f = j;                                     // Запоминание старой координаты
				if (j - 1 < 0) j = j + n - 1;              // Переход за края карты
				else j--;
				if (s[j][i] == 1)
				{
					old_x = h[N - 1].x * 2 + 1;
					old_y = h[N - 1].y;
					p = 1;
					gener(s, m, n);
				}
				s[j][i] = -37;                             // Рисование новой ячейки
				s[f][i] = ' ';                             // Стирание старой ячейки
				h[q].y = j;                                // Запоминание старой координаты
				//out(s, n, m);                              // Вывод нового кадра
				break;
			}
			case 3:                                        // Передвижение влево
			{
				f = i;                                     // Запоминание старой ячейки
				if (i <= 1) i = i + m * 2-2 ;              // Переход за края карты
				else i = i - 2;   
				if (s[j][i] == 1)
				{
					old_x = h[N - 1].x * 2 + 1;
					old_y = h[N - 1].y;
					p = 1;
					gener(s, m, n);
				}
				s[j][i] = -37;                             // Рисование новой ячейки
 				s[j][f] = ' ';                             // Стирание старой ячейки
				h[q].x = i/2;                              // Запоминание новой координаты
				//out(s, n, m);                              // Вывод нового кадра
				break;
			}
			case 4:                                        // Передвижение вправо
			{
				f = i;                                     // Запоминание старой ячейки
				if (i >= m*2 - 1) i = i - m * 2 + 2;       // Переход за края карты
				else i = i + 2;
				if (s[j][i] == 1)
				{
					old_x = h[N - 1].x * 2 + 1;
					old_y = h[N - 1].y;
					p = 1;
					gener(s, m, n);
				}
				s[j][i] = -37;                             // Рисование новой ячейки
				s[j][f] = ' ';                             // Стирание старой ячейки
				h[q].x = i / 2;                            // Запоминание новой координаты
				//out(s, n, m);                              // Вывод нового кадра
				break;
			}
			default :                                      // Передвижение до первого поворота
			{
				f = i;                                     // То же самое, что и движение вправо
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
void out(char**s, int n, int m)                            // Ф-ция вывода кадра
{
	int i;
	system("cls");
	printf("\n\n\n");                                      // Пропуск вниз
	for (i = 0; i < n-1; i++) 
	{
		puts(s[i]);                                        // Вывод каждой строчки
		printf("\n"); 
	}
	puts(s[n - 1]);
	printf("\n\n     your score is - %d", N * 10 - 30);
	return ;
}
int check(struct choise *last)                             // Проверка изменения в состоянии клавиш
{
	struct choise n;                                       // Новое состояние
	n.DOWN = GetKeyState(VK_DOWN);                         // Состояние клавиши ВНИЗ
	n.UP = GetKeyState(VK_UP);                             // Состояние клавиши ВВЕРХ
	n.LEFT = GetKeyState(VK_LEFT);                         // Состояние клавиши ВЛЕВО
	n.RIGHT = GetKeyState(VK_RIGHT);                       // Состояние клавиши ВПРАВО
	if ((n.DOWN !=last->DOWN) || (n.UP != last->UP) || (n.LEFT != last->LEFT) || (n.RIGHT != last->RIGHT))   // Сравнение с предыдущим состоянием
	{
		if (n.DOWN < 0)                                    // Какая из клавиш была нажата?
		{
			if (n.DOWN ==-127) last->DOWN=1 ;              // Сохранение изменений в состоянии ВНИЗ
			else last->DOWN = 0;                           // Сохранение изменений в состоянии ВНИЗ
			return 1;                                      // Возврат 1
		}
		if (n.UP < 0)
		{
			if (n.UP == -127) last->UP = 1;                // Сохранение изменений в состоянии ВВЕРХ
			else last->UP = 0;                             // Сохранение изменений в состоянии ВВЕРХ
			return 2;                                      // Возврат 2
		}
		if (n.LEFT <0) 
		{
			if (n.LEFT == -127) last->LEFT = 1;            // Сохранение изменений в состоянии ВЛЕВО
			else last->LEFT = 0;                           // Сохранение изменений в состоянии ВЛЕВО
			return 3;                                      // Возврат 3
		}
		if (n.RIGHT <0)
		{
			if (n.RIGHT == -127) last->RIGHT = 1;          // Сохранение изменений в состоянии ВПРАВО
			else last->RIGHT = 0;                          // Сохранение изменений в состоянии ВПРАВО
			return 4;                                      // Возврат 4
		}
	}
	return 0;                                              // Если ничего так и не изменилось (на всякий случай)
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