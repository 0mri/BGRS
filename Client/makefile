CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude 
LDFLAGS:=-lboost_system -pthread

all: BGRSclient
	g++ -o bin/BGRSclient bin/connectionHandler.o bin/IOTask.o  bin/BGRSclient.o $(LDFLAGS) 

BGRSclient: bin/connectionHandler.o bin/IOTask.o bin/BGRSclient.o
	
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/IOTask.o:
	g++ $(CFLAGS) -o bin/IOTask.o src/IOTask.cpp

bin/BGRSclient.o:
	g++ $(CFLAGS) -o bin/BGRSclient.o src/BGRSclient.cpp
	
clean:
	rm -f bin/*
	
