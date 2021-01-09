#include <mutex>
class IOTask
{
private:
    std::mutex &_mutex;
    ConnectionHandler &_ch;
    bool _shouldTerminate;

public:
    IOTask(std::mutex &mutex, ConnectionHandler &ch);
    void write();
    void read();

    short get_opcode(std::string msg);
    void shortToBytes(short num, char *bytesArr);
    short bytesToShort(char *bytesArr);
    std::string msg_by_opc(short opcode);
};