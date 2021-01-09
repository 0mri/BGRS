#include "connectionHandler.h"
#include "IOTask.h"
#include <thread>
#include <vector>
#include <boost/algorithm/string.hpp>
#include <boost/lexical_cast.hpp>
/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/

IOTask::IOTask(std::mutex &mutex, ConnectionHandler &ch) : _mutex(mutex), _ch(ch), _shouldTerminate(false) {}

void IOTask::write()
{
    {
        while (!_shouldTerminate)
        {
            const short bufsize = 1024;
            char buf[bufsize];
            std::cin.getline(buf, bufsize);
            std::string line(buf);
            std::vector<std::string> result;
            boost::split(result, line, boost::is_any_of(" "));
            char req[2];
            short opcode = get_opcode(result[0]);
            if (opcode != 0)
            {
                shortToBytes(opcode, req);
                if (opcode == 1 || opcode == 2 || opcode == 3)
                {
                    if (result.size() < 3)
                        std::cout
                            << "ERROR " << opcode
                            << std::endl;
                    else
                    {
                        _ch.sendBytes(req, 2);
                        _ch.sendFrameAscii(result[1], '\0');
                        _ch.sendFrameAscii(result[2], '\0');
                    }
                }
                else if (opcode == 11)
                {
                    _ch.sendBytes(req, 2);
                }
                else if (opcode == 4)
                {
                    _ch.sendBytes(req, 2);
                    _mutex.lock();
                }
                else if (opcode == 8)
                {
                    if (result.size() < 2)
                        std::cout
                            << "ERROR " << opcode
                            << std::endl;
                    else
                    {
                        _ch.sendBytes(req, 2);
                        _ch.sendFrameAscii(result[1], '\0');
                    }
                }
                else if (opcode == 5 || opcode == 6 || opcode == 7 || opcode == 9 || opcode == 10)
                {
                    unsigned short course_num = 0;
                    _ch.sendBytes(req, 2);
                    try
                    {
                        course_num = boost::lexical_cast<unsigned short>(result[1]);
                    }
                    catch (const std::exception &e)
                    {
                    }

                    shortToBytes(course_num, req);
                    _ch.sendBytes(req, 2);
                }
            }
            if (opcode == 0 && result[0] != "")
                std::cout
                    << "\033[1;31mBGRS: " << result[0] << ": command not found\033[0m"
                    << std::endl;
        }
    }
}

void IOTask::read()
{
    while (!_shouldTerminate)
    {
        _mutex.try_lock();
        char res[2];
        _ch.getBytes(res, 2);
        short opcode = bytesToShort(res);

        _ch.getBytes(res, 2);
        short msg_opc = bytesToShort(res);
        std::cout << msg_by_opc(opcode) << " " << msg_opc << std::endl;
        std::string msg;

        if (opcode == 12)
        {
            _ch.getFrameAscii(msg, '\0');
            if (msg != "")
                std::cout << msg << std::endl;
        }

        if (msg_opc == 4 && opcode == 13)
        {
            _mutex.unlock();
            sleep(1);
        }
        else if (msg_opc == 4 && opcode == 12)
        {
            _shouldTerminate = true;
            _mutex.unlock();
            _ch.close();
        }
    }
}
void IOTask::shortToBytes(short num, char *bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

short IOTask::bytesToShort(char *bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}

short IOTask::get_opcode(std::string msg)
{

    if (msg == "ADMINREG")
        return 1;
    else if (msg == "STUDENTREG")
        return 2;
    else if (msg == "LOGIN")
        return 3;
    else if (msg == "LOGOUT")
        return 4;
    else if (msg == "COURSEREG")
        return 5;
    else if (msg == "KDAMCHECK")
        return 6;
    else if (msg == "COURSESTAT")
        return 7;
    else if (msg == "STUDENTSTAT")
        return 8;
    else if (msg == "ISREGISTERED")
        return 9;
    else if (msg == "UNREGISTER")
        return 10;
    else if (msg == "MYCOURSES")
        return 11;
    return 0;
}

std::string IOTask::msg_by_opc(short opcode)
{
    switch (opcode)
    {
    case 12:
        return "ACK";
    case 13:
        return "ERROR";
    default:
        return "";
    }
}