#include <iostream>
#include <omp.h>
#include <string>
using namespace std;

bool estePrim(long valoare) {
	for (long i = 2; i <= valoare / 2; i++) {
		if (valoare % i == 0)
			return false;
	}
	return true;
}

// solutie secventiala
int getNrPrime(long start, long final) {
	int contor = 0;
	for (long i = start; i <= final; i++) {
		if (estePrim(i)) {
			contor += 1;
		}
	}
	return contor;
}

// solutie paralela
int getNrPrimeParalel(long start, long final) {
	int contor = 0;

#pragma omp parallel for
	for (long i = start; i <= final; i++) {
		if (estePrim(i)) {
#pragma omp critical
			contor += 1;
		}
	}
	return contor;
}

// solutie paralela
int getNrPrimeParalelDinamic(long start, long final) {
	int contor = 0;

#pragma omp parallel for schedule(dynamic)
	for (long i = start; i <= final; i++) {
		if (estePrim(i)) {
#pragma omp critical
			contor += 1;
		}
	}
	return contor;
}

// solutie paralela
int getNrPrimeParalelGuided(long start, long final) {
	int contor = 0;

#pragma omp parallel for schedule(guided, 50)
	for (long i = start; i <= final; i++) {
		if (estePrim(i)) {
#pragma omp critical
			contor += 1;
		}
	}
	return contor;
}

// solutie paralela
int getNrPrimeParalelDinamicVector(long start, long final) {
	int contor = 0;
	int nrProcesoare = omp_get_num_procs();
	int* rezultatePartiale = new int[nrProcesoare];
	memset(rezultatePartiale, 0, sizeof(int) * nrProcesoare);

#pragma omp parallel for schedule(dynamic)
	for (long i = start; i <= final; i++) {
		if (estePrim(i)) {
			rezultatePartiale[omp_get_thread_num()] += 1;
		}
	}

	for (int i = 0; i < nrProcesoare; i++)
		contor += rezultatePartiale[i];

	return contor;
}

// solutie paralela
int getNrPrimeParalelDinamicCache(long start, long final) {
	int contor = 0;
	int nrProcesoare = omp_get_num_procs();
	int* rezultatePartiale = new int[nrProcesoare*1000];
	memset(rezultatePartiale, 0, 
		sizeof(int) * nrProcesoare*1000);

#pragma omp parallel for schedule(dynamic)
	for (long i = start; i <= final; i++) {
		if (estePrim(i)) {
			rezultatePartiale[omp_get_thread_num()*1000] += 1;
		}
	}

	for (int i = 0; i < nrProcesoare; i++)
		contor += rezultatePartiale[i * 1000];

	return contor;
}

void benchmark(string msg, int(*pf)(long, long)) {
	const long N = 5e5;
	cout << endl << msg;
	double tStart = omp_get_wtime();
	int rezultat = pf(2, N);
	double tFinal = omp_get_wtime();
	printf("\n Rezultat: %d in %f secunde",
		rezultat, tFinal - tStart);
}

int main() {
	benchmark("Test secvential", getNrPrime);
	benchmark("Test paralel", getNrPrimeParalel);
	benchmark("Test paralel cu schedule dynamic", 
		getNrPrimeParalelDinamic);
	benchmark("Test paralel cu schedule guided",
		getNrPrimeParalelGuided);
	benchmark("Test paralel cu schedule dynamic fara critical",
		getNrPrimeParalelDinamicVector);
	benchmark("Test paralel cu schedule dynamic fara cache coherence",
		getNrPrimeParalelDinamicCache);
}