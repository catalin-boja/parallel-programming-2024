#include <iostream>
#include <string>
#include <omp.h>
using namespace std;

//cautare secventiala
void cautareSecventiala(int* valori, long N, int cheie) {
	for (long i = 0; i < N; i++) {
		{
			float temp = valori[i] / 11;
			if (valori[i] == cheie)
				return;
		}
	}
}

//cautare paralela
void cautareParalela(int* valori, long N, int cheie) {
	bool esteGasitaValoarea = false;

#pragma omp parallel for shared(esteGasitaValoarea)
	for (long i = 0; i < N; i++) {
		{
			if (esteGasitaValoarea)
				continue;

			float temp = valori[i] / 11;
			if (valori[i] == cheie)
				esteGasitaValoarea = true;
		}
	}
}

void interschimb(int& a, int& b) {
	int temp = a;
	a = b;
	b = temp;
}

void bubblesort(int* valori, long N) {
	while (true) {
		bool esteSortat = true;
		for (long i = 0; i < N-1; i++) {
			if (valori[i] > valori[i + 1]) {
				interschimb(valori[i],valori[i + 1]);
				esteSortat = false;
			}
		}
		if (esteSortat)
			return;
	}
}

void oddevensort(int* valori, long N) {
	for (long it = 0; it < N; it++) {
		if (it % 2 == 1) {
			for (long i = 2; i < N; i += 2) {
				if (valori[i - 1] > valori[i])
					interschimb(valori[i - 1], valori[i]);
			}
		}
		else {
			for (long i = 1; i < N; i += 2) {
				if (valori[i - 1] > valori[i])
					interschimb(valori[i - 1], valori[i]);
			}
		}

		//for (long i = (it%2 + 1); i < N; i += 2) {
		//	if (valori[i - 1] > valori[i])
		//		interschimb(valori[i - 1], valori[i]);
		//}
	}
}

void oddevensort2(int* valori, long N) {
	for (long it = 0; it < N; it++) {
		for (long i = (it%2 + 1); i < N; i += 2) {
			if (valori[i - 1] > valori[i])
				interschimb(valori[i - 1], valori[i]);
		}
	}
}

void oddevensortParalel(int* valori, long N) {
	for (long it = 0; it < N; it++) {
		if (it % 2 == 1) {
#pragma omp parallel for
			for (long i = 2; i < N; i += 2) {
				if (valori[i - 1] > valori[i])
					interschimb(valori[i - 1], valori[i]);
			}
		}
		else {
#pragma omp parallel for
			for (long i = 1; i < N; i += 2) {
				if (valori[i - 1] > valori[i])
					interschimb(valori[i - 1], valori[i]);
			}
		}
	}
}

void oddevensort2Paralel(int* valori, long N) {
	for (long it = 0; it < N; it++) {
#pragma omp parallel for
		for (long i = (it % 2 + 1); i < N; i += 2) {
			if (valori[i - 1] > valori[i])
				interschimb(valori[i - 1], valori[i]);
		}
	}
}

void benchmark(string mesaj, 
	int* valori, long N, int cheie,
	void (*pf)(int*, long, int))
{
	printf("\n Test pentru %s", mesaj.c_str());
	double tStart = omp_get_wtime();
	pf(valori, N, cheie);
	double tFinal = omp_get_wtime();
	printf("\n Durata %f", tFinal - tStart);
}

void benchmarkSortare(string mesaj,
	int* valori, long N,
	void (*pf)(int*, long))
{
	printf("\n Test pentru %s", mesaj.c_str());
	double tStart = omp_get_wtime();
	pf(valori, N);
	double tFinal = omp_get_wtime();
	printf("\n Durata %f", tFinal - tStart);
}


int main() {
	const long N = 6e4;
	int* valori = new int[N];
	for (long i = 0; i < N; i++) {
		valori[i] = N - i;
	}

	benchmark("Cautare secventiala", 
		valori, N, 1, cautareSecventiala);
	benchmark("Cautare paralela",
		valori, N, 1, cautareParalela);

	benchmarkSortare("Bubble Sort", 
		valori, N, bubblesort);

	//reinitializare valori pentru testul urmator
	for (long i = 0; i < N; i++) {
		valori[i] = N - i;
	}

	//benchmarkSortare("Odd Even Sort",
	//	valori, N, oddevensort);

	////reinitializare valori pentru testul urmator
	//for (long i = 0; i < N; i++) {
	//	valori[i] = N - i;
	//}

	//benchmarkSortare("Odd Even Sort",
	//	valori, N, oddevensort2);

	////reinitializare valori pentru testul urmator
	//for (long i = 0; i < N; i++) {
	//	valori[i] = N - i;
	//}

	benchmarkSortare("Odd Even Sort Paralel",
		valori, N, oddevensortParalel);

	//reinitializare valori pentru testul urmator
	for (long i = 0; i < N; i++) {
		valori[i] = N - i;
	}

	benchmarkSortare("Odd Even Sort Paralel",
		valori, N, oddevensort2Paralel);
}
