@echo off
:: @author: Gabriel Lau <gabriel@poloid.com.br>
:: @version: 0.0.2
:: @since: 2014-01-26

echo.
echo # Compiling less files.
echo -------------------------
echo.

:: comandos de compilação
call lessc default.less default.css

echo - files compiled
echo.

:END
GOTO :EOF
