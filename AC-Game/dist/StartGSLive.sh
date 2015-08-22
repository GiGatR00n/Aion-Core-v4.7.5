case $1 in
noloop)
  [ -d log/ ] || mkdir log/
  [ -f log/console.log ] && mv log/console.log "log/backup/`date +%Y-%m-%d_%H-%M-%S`_console.log"
  java -Xms15600m -Xmx15600m -ea -XX:-UseSplitVerifier -javaagent:./libs/ac-commons-1.3.jar -cp ./libs/*:AC-Game.jar com.aionemu.gameserver.GameServer > log/console.log 2>&1
  echo $! > gameserver.pid
  echo "Server started!"
  ;;
*)
  ./StartGSLive_loop.sh &
  ;;
esac