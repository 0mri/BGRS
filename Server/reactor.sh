
if [ "$#" -gt 1 ]
then
    mvn exec:java -Dexec.mainClass="bgu.spl.net.impl.BGRSServer.ReactorMain" -Dexec.args="$1 $2"
fi



