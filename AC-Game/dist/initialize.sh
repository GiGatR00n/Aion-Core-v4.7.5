echo Aion-Core GS v4.7.5.x by GiGatR00n
echo Initializing Java Patch...

JAVA_HOME=/home/user_name/jdk1.7.0_XX
export JAVA_HOME
PATH=$PATH:$JAVA_HOME/bin
export PATH

echo Please wait 3 seconds...
sleep 3;

echo Gameserver is starting now...
./StartGS.sh
cd ..