:#=======================================================================
:# Author: GiGatR00n v4.7.5.x
:#=======================================================================

@ECHO off
@COLOR 0B
SET MODE=clean package
SET TITLE=Build
TITLE Aion-Core v4.7.5.x by GiGatR00n
:MENU
CLS
ECHO                     Aion-Core v4.7.5.x - %TITLE% Panel 
ECHO                               by GiGatR00n
ECHO.
ECHO    -------------------------------------------------------------
ECHO    .             1 - Build Game Server                         .
ECHO    .             3 - Build Chat Server                         .
ECHO    .             2 - Build Login Server                        .
ECHO    .             4 - Build Commons                             .
ECHO    .             5 - Build All Servers                         . 
ECHO    .             6 - Exit                                      .
ECHO    -------------------------------------------------------------
ECHO.
:ENTER
SET /P Ares= Type your option and press ENTER:
IF %Ares%==1 GOTO GameServer
IF %Ares%==2 GOTO LoginServer
IF %Ares%==3 GOTO ChatServer
IF %Ares%==4 GOTO Commons
IF %Ares%==5 GOTO FULL
IF %Ares%==6 GOTO QUIT
:FULL
cd ..\AC-Commons 
start /WAIT /B ..\tools\Ant\bin\ant clean dist
cd ..\AC-Game
start /WAIT /B ..\tools\Ant\bin\ant clean dist
cd ..\AC-Login
start /WAIT /B ..\tools\Ant\bin\ant clean dist
cd ..\AC-Chat
start /WAIT /B ..\tools\Ant\bin\ant clean dist
GOTO :QUIT

:Commons
GOTO :QUIT

:GameServer
cd ..\AC-Game
start /WAIT /B ..\tools\Ant\bin\ant clean dist
GOTO :QUIT

:LoginServer
cd ..\AC-Login
start /WAIT /B ..\tools\Ant\bin\ant clean dist
GOTO :QUIT

:ChatServer
cd ..\AC-Chat
start /WAIT /B ..\tools\Ant\bin\ant clean dist
GOTO :QUIT

:QUIT
exit
