@ECHO off
SET MODE=clean package
SET TITLE=Build
CLS
TITLE Aion-Core Commons v4.7.5.x by GiGatR00n - %TITLE%ing Commons
CD ./AC-Commons
call mvn %MODE%
pause
)

