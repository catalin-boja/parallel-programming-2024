#include <iostream>
#include <omp.h>
#include <string.h>

int main() {

	int contor = 10;
	int contorIntern = 100;

#pragma omp parallel num_threads(4) firstprivate(contor) \
shared(contorIntern)
	{
		printf("\n Hello %d", omp_get_thread_num());

		contor += 10;

#pragma omp for lastprivate(contorIntern)
		for (int i = 0; i < 10; i++) {
			printf("\n Iteratie %d executata de %d", 
				i , omp_get_thread_num());
			contorIntern += 1;
		}

		printf("\n Contor intern: %d", contorIntern);

#pragma omp barrier
		printf("\n ======================");

#pragma omp for schedule(guided,3)
		for (int i = 0; i < 100; i++) {
			printf("\n Iteratie %d executata de %d",
				i, omp_get_thread_num());
		}

	}

	printf("\n Valoare contor: %d", contor);

	omp_set_num_threads(4);

#pragma omp parallel for
	for (int i = 0; i < 4; i++) {
		printf("\n Bye !");
	}

}