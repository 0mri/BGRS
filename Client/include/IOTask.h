#include <thread>
#include <iostream>
#include <mutex>
#include "connectionHandler.h"
class IOTask
{
private:
    std::mutex &_mutex;
    ConnectionHandler &_ch;

public:
    IOTask(std::mutex &mutex, ConnectionHandler &ch) : _mutex(mutex), _ch(ch) {}
    void write()
    {
        while (true)
        {
            std::cout << "Send to Server: " << std::endl;
            const short bufsize = 1024;
            char buf[bufsize];
            std::cin.getline(buf, bufsize);
            std::string line(buf);
            int len = line.length();
            if (_ch.sendLine(line))

            {
                std::cout << "Disconnected. Exiting...\n"
                          << std::endl;
                break;
            }
            // connectionHandler.sendLine(line) appends '\n' to the message. Therefor we send len+1 bytes.
            std::cout << "Sent " << len + 1 << " bytes to server" << std::endl;
        }
    }
    void read()
    {
        while (true)
        {
            std::cout << "Start Read Task!" << std::endl;
            const short bufsize = 1024;
            char buf[bufsize];
            std::cin.getline(buf, bufsize);
            std::string line(buf);
            int len = line.length();
            if (_ch.sendLine(line))

            {
                std::cout << "Disconnected. Exiting...\n"
                          << std::endl;
                break;
            }
            // connectionHandler.sendLine(line) appends '\n' to the message. Therefor we send len+1 bytes.
            std::cout << "Sent " << len + 1 << " bytes to server" << std::endl;
        }
    }
};