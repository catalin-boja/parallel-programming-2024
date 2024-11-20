#include <iostream>
#include <thread>
#include <mutex>
#include <vector>

using namespace std;

//definire mutex global
mutex semafor;

void hello(int id) {
	printf("\n Hello de pe thread %d", id);
}

void increment(int valoare, int& contor) {
	printf("\n Incrementare contor cu valoare %d", valoare);
	semafor.lock();
	contor += valoare;
	semafor.unlock();
}

void incrementCuSemaforLocal(int valoare, int& contor, mutex& semafor) {
	printf("\n Incrementare contor cu valoare %d", valoare);
	semafor.lock();
	contor += valoare;
	semafor.unlock();
}

int main() {

	hello(0);

	thread t1(hello, 1);
	thread t2(hello, 2);
	thread t3(hello, 3);

	//bariera
	t1.join();
	t2.join();
	t3.join();

	int contor = 0;
	thread t4(increment, 4, ref(contor));
	thread t5(increment, 5, ref(contor));
	thread t6(increment, 6, ref(contor));

	//bariera
	t4.join();
	t5.join();
	t6.join();

	increment(20, contor);

	printf("\nValoare finala contor: %d", contor);

	mutex mutexLocal;
	thread t7(incrementCuSemaforLocal, 7, ref(contor), ref(mutexLocal));
	thread t8(incrementCuSemaforLocal, 8, ref(contor), ref(mutexLocal));
	thread t9(incrementCuSemaforLocal, 9, ref(contor), ref(mutexLocal));

	//bariera
	t7.join();
	t8.join();
	t9.join();

	vector<thread> fireExecutie;
	fireExecutie.push_back(thread(hello, 1));
	fireExecutie.push_back(thread(hello, 2));
	fireExecutie.push_back(thread(hello, 3));
	for (int i = 0; i < fireExecutie.size(); i++)
	{
		fireExecutie[i].join();
	}

	

	printf("\nValoare finala contor: %d", contor);

}