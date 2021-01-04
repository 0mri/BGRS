
if [ "$#" -gt 0 ]
then
    mvn exec:java -Dexec.mainClass="bgu.spl.net.impl.BGRSServer.TPCMain" -Dexec.args=$1
fi
