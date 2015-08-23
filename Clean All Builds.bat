@ECHO off
@COLOR 0C
TITLE Aion-Core v4.7.5.x by GiGatR00n
@ECHO.
@ECHO.
@ECHO =======================ChatServer Builds Cleaning=======================
@ECHO ========================================================================
@cd AC-Chat
@call ..\AC-Tools\Ant\bin\ant clean
@cd ..
@ECHO.
@ECHO.
@ECHO ======================LoginServer Builds Cleaning=======================
@ECHO ========================================================================
@cd AC-Login
@call ..\AC-Tools\Ant\bin\ant clean
@cd ..
@ECHO.
@ECHO.
@ECHO =======================GameServer Builds Cleaning=======================
@ECHO ========================================================================
@cd AC-Game
@call ..\AC-Tools\Ant\bin\ant clean
del jar-yguard.xml
del yshrinklog.xml

@cd ..

@ECHO.
@ECHO.
@ECHO --------------------------  CLEANING FINISHED  -------------------------

@PAUSE