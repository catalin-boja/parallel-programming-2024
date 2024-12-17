#include <iostream>
#include <string>
#include <omp.h>

using namespace std;

int main() {

	//exemplu for cu reduction
	//calcul numar divizori pentru o valoare data
	int valoare = 23478645;
	int nrDivizori = 0;
#pragma omp parallel for schedule(dynamic) reduction(+:nrDivizori)
	for (int i = 1; i < valoare / 2; i++) {
		if (valoare % i == 0) {
			nrDivizori += 1;
		}
	}

	printf("\n Nr divizori pentru %d  = %d",
		valoare, nrDivizori);

	//exemplu for cu nowait

#pragma omp parallel
	{
#pragma omp for
		for (int i = 0; i < omp_get_num_threads(); i++) {
			printf("\n Iteratie %d executata de thread %d"
				, i, omp_get_thread_num());
		}

		printf("\n Instructiune dupa for #1");

#pragma omp for nowait
		for (int i = 0; i < omp_get_num_threads(); i++) {
			printf("\n Iteratie %d executata de thread %d"
				, i, omp_get_thread_num());
		}

		printf("\n Instructiune dupa bucla for #2");

	}

	//exemplu secventa master
#pragma omp parallel
	{
		printf("\n Hello");
#pragma omp master
		printf("\n Hello master thread (0) - %d", omp_get_thread_num());
	}

	//exemplu secventa single
#pragma omp parallel
	{
		printf("\n Hello #2");
#pragma omp single
		printf("\n Hello thread %d", omp_get_thread_num());
	}


	//exemplu sections
#pragma omp parallel num_threads(4)
	{
		printf("\n Hello #3");
#pragma omp sections
		{
#pragma omp section
			printf("\n Sectiune #1 - thread %d", omp_get_thread_num());

#pragma omp section
			printf("\n Sectiune #2 - thread %d", omp_get_thread_num());
		}
	}


}