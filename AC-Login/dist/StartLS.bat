@ECHO off
@COLOR 0C
TITLE Aion-Core LS v4.7.5.x by GiGatR00n
:START
CLS
IF "%MODE%" == "" (
CALL PanelLS.bat
)
ECHO Starting Aion-Core Login in %MODE% mode.
JAVA -version:"1.7" %JAVA_OPTS% -cp ./libs/*;ac-login.jar com.aionemu.loginserver.LoginServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Login Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Login Server is terminated!
ECHO.
PAUSE
EXIT