#include <iostream>
#include <string>
#include <fstream>
#include <omp.h>
#include "sha1.h"

using namespace std;

void solutieParalela() {
	string hashCautat = "00c3cc7c9d684decd98721deaa0729d73faa9d9b";
	string hash = "";

	//citire din fisier
	ifstream fisier("10-million-password-list-top-1000000.txt");
	if (!fisier.is_open()) {
		printf("\n ************ Probleme fisier **********");
	}
	else {

		printf("\n Prelucrare fisier...");

		double tStart = omp_get_wtime();
		while (!fisier.eof()) {
			string linie;
			fisier >> linie;
			//printf("\n Linie: %s", linie.c_str());

			hash = sha1(sha1("parallel" + linie));
			if (hash == hashCautat) {
				printf("\n *** Parola gasita: %s", linie.c_str());
				break;
			}
		}
		double tFinal = omp_get_wtime();
		printf("\n Durata: %f sec", tFinal - tStart);
		fisier.close();
	}
}

void solutieSecventiala() {
	string hashCautat = "00c3cc7c9d684decd98721deaa0729d73faa9d9b";
	string hash = "";

	//citire din fisier
	ifstream fisier("10-million-password-list-top-1000000.txt");
	if (!fisier.is_open()) {
		printf("\n ************ Probleme fisier **********");
	}
	else {

		printf("\n Prelucrare fisier...");

		double tStart = omp_get_wtime();
		while (!fisier.eof()) {
			string linie;
			fisier >> linie;
			//printf("\n Linie: %s", linie.c_str());

			hash = sha1(sha1("parallel" + linie));
			if (hash == hashCautat) {
				printf("\n *** Parola gasita: %s", linie.c_str());
				break;
			}
		}
		double tFinal = omp_get_wtime();
		printf("\n Durata: %f sec", tFinal - tStart);
		fisier.close();
	}
}

int main()
{

	string hashCautat = "00c3cc7c9d684decd98721deaa0729d73faa9d9b";
	
	solutieSecventiala();
	solutieParalela();
}