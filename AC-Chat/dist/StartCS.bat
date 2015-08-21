@ECHO off
@COLOR 0C
TITLE Aion-Core CS v4.7.5.x by GiGatR00n
:START
CLS
IF "%MODE%" == "" (
CALL PanelCS.bat
)
ECHO Starting Aion-Core Chat in %MODE% mode.
JAVA -version:"1.7" %JAVA_OPTS% -cp ./libs/*;ac-chat.jar com.aionemu.chatserver.ChatServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Chat Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Chat Server is terminated!
ECHO.
PAUSE
EXIT