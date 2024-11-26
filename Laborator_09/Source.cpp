#include <iostream>
#include <string>
#include <omp.h>

using namespace std;

int main() {
	int nrProcesoare = omp_get_num_procs();
	printf("\n Numar procesoare: %d", nrProcesoare);
	int nrMaxThreaduri = omp_get_max_threads();
	printf("\n Numar max fire executie: %d", nrMaxThreaduri);
	int idThreadCurent = omp_get_thread_num();
	printf("\n ID thread curent: %d", idThreadCurent);
	int nrThreaduriActive = omp_get_num_threads();
	printf("\n Nr thread-uri active: %d", nrThreaduriActive);

	if (omp_in_parallel()) {
		printf("\n Executie paralela");
	}
	else {
		printf("\n Executie secventiala");
	}

	int nrThreaduri = 4;
	omp_set_num_threads(nrThreaduri);

#pragma omp parallel
	{
		printf("\n Hello de pe thread %d", omp_get_thread_num());

		if (omp_get_thread_num() == 0) {

			printf("\n Nr thread-uri active: %d", omp_get_num_threads());

			if (omp_in_parallel()) {
				printf("\n Executie paralela");
			}
			else {
				printf("\n Executie secventiala");
			}
		}
	}

	printf("\n Test numar maxim thread-uri");
	//omp_set_num_threads(40000);
#pragma omp parallel
	{
		if (omp_get_thread_num() == 0) {
			printf("\n Numar obtinut de thread-uri: %d",
				omp_get_num_threads());
		}
	}

	// test clauze omp parallel
	int nrT = 10;

	//daca conditia din if -> false atunci nu se paralelizeaza secventa
#pragma omp parallel num_threads(nrT) if(nrProcesoare > 4)
	{
		if (omp_get_thread_num() == 0) {
			printf("\n Numar obtinut de thread-uri: %d",
				omp_get_num_threads());
		}
	}

	int contor = 10;
	int variabila = 10;
	int variabilaPartajata = 0;

#pragma omp parallel num_threads(4) private(contor) \
firstprivate(variabila) shared(variabilaPartajata)
	{
		contor = 0;

		printf("\n contor initial = %d", contor);
		printf("\n variabila initiala = %d", variabila);
		for (int i = 0; i < 10; i++) {
			contor += 1;
			variabila += 1;
			variabilaPartajata += 1; //atentie la race condition
		}
		printf("\n contor final = %d", contor);
		printf("\n variabila finala = %d", variabila);
	}

	printf("\n variabila partajata = %d", variabilaPartajata);

	omp_set_num_threads(8);

	//control thread-uri in secventa paralela
#pragma omp parallel
	{
		printf("\n Hello de la %d", omp_get_thread_num());
#pragma omp barrier
		printf("\n Bye de la %d", omp_get_thread_num());
	}

	//test critical - mutex
	int suma = 0;
#pragma omp parallel num_threads(4) shared(suma)
	{
		for (int i = 0; i < 100000; i++) {
			if(i % 101 == 0)
#pragma omp critical
				suma += i; //evitam race condition cu critical
		}
	}

	printf("\n Suma este %d", suma);


	//suma elemente vector
	//varianta secventiala
	const int N = 10000;
	int valori[N];
	for (int i = 0; i < N; i++) {
		valori[i] = i + 1;
	}

	int rezultat = 0;
	for (int i = 0; i < N; i++) {
		rezultat += valori[i];
	}

	printf("\n Suma este: %d", rezultat);

	//varianta paralela
	rezultat = 0;
	int indexThread = 0;
#pragma omp parallel num_threads(8) private(indexThread)
	{
		indexThread = omp_get_thread_num();
		for (int i = 0; i < N; i++) {
#pragma omp critical
			if(indexThread == i)
			{
				rezultat += valori[i];
				indexThread += 8;
			}
		}
	}

	printf("\n Suma este: %d", rezultat);


	printf("\n Final exemplu");
}