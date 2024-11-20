#include <thread>
#include <iostream>
#include <string>
#include <vector>
#include <omp.h>
using namespace std;

void benchmark(string mesaj, 
	int (*pf)(vector<int>), vector<int> valori) {

	cout << endl << "Test: " << mesaj;
	double tStart = omp_get_wtime();
	int rezultat = pf(valori);
	double tFinal = omp_get_wtime();
	cout << endl << "Rezultat: " << rezultat << " durata " <<
		tFinal - tStart;
}

int solutieSecventiala(vector<int> valori) {
	int rezultat = 0;
	for (int i = 0; i < valori.size(); i++) {
		int temp = valori[i] / 5;
		if (valori[i] % 2 == 0) {
			rezultat += 1;
		}
	}
	return rezultat;
}

void procesareVector(vector<int>& valori, int indexStart,
	int indexFinal, int& contor) {
	contor = 0;
	for (int i = indexStart; i < indexFinal; i++) {
		int temp = valori[i] / 5;
		if (valori[i] % 2 == 0)
			contor += 1;
	}
}

int solutieParalela(vector<int> valori) {
	int rezultat[4];
	vector<thread> fire;

	int delta = valori.size() / 4;

	for (int i = 0; i < 4; i++) {
		int indexStart = i * delta;
		int indexFinal = (i != 3) ? (i + 1) * delta : valori.size();

		fire.push_back(
			thread(procesareVector,
				ref(valori),
				indexStart,
				indexFinal,
				ref(rezultat[i])));
	}
	int rezultatFinal = 0;
	for (int i = 0; i < 4; i++) {
		fire[i].join();
		rezultatFinal += rezultat[i];
	}
	return rezultatFinal;

}

int solutieParalelaFaraCacheSync(vector<int> valori) {
	int rezultat[4*1000];
	vector<thread> fire;

	int delta = valori.size() / 4;

	for (int i = 0; i < 4; i++) {
		int indexStart = i * delta;
		int indexFinal = (i != 3) ? (i + 1) * delta : valori.size();

		fire.push_back(
			thread(procesareVector,
				ref(valori),
				indexStart,
				indexFinal,
				ref(rezultat[i*1000])));
	}
	int rezultatFinal = 0;
	for (int i = 0; i < 4; i++) {
		fire[i].join();
		rezultatFinal += rezultat[i*1000];
	}
	return rezultatFinal;

}

int main() {

	const int N = 7e8;
	vector<int> valori(N);
	for (int i = 0; i < N; i++) {
		valori[i] = i + 1;
	}

	alignas(1024) int vb;

	benchmark("Test secvential", solutieSecventiala, valori);
	benchmark("Test paralel fara race condition",
		solutieParalela, valori);
	benchmark("Test paralel fara cache coherence",
		solutieParalelaFaraCacheSync, valori);
}
