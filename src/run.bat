@echo off
title Generador de Documentacion - Sistema Restaurante
echo ==================================================
echo    GENERANDO DOCUMENTACION JAVADOC
echo ==================================================
echo.

echo Limpiando documentacion anterior...
if exist "docs" rmdir /s /q "docs"

echo Creando directorio de documentacion...
mkdir "docs"

echo.
echo Generando documentacion Javadoc...
echo.

javadoc -d docs ^
    -sourcepath . ^
    -subpackages modelo servicio sistemaRestaurante ^
    -windowtitle "Sistema de Restaurante - Documentacion" ^
    -doctitle "<h1>Sistema de Gestion de Restaurante</h1><p>Sistema completo para gestion de productos, pedidos y facturas</p>" ^
    -header "<strong>Sistema Restaurante</strong>" ^
    -bottom "Copyright &copy; 2025 - Todos los derechos reservados. Version 2.0" ^
    -version ^
    -author ^
    -charset UTF-8 ^
    -docencoding UTF-8 ^
    -keywords ^
    -link https://docs.oracle.com/javase/8/docs/api

if %errorlevel% equ 0 (
    echo.
    echo ==================================================
    echo    DOCUMENTACION GENERADA EXITOSAMENTE
    echo ==================================================
    echo.
    echo Archivos generados en: docs\
    echo.
    echo Estructura de paquetes documentados:
    echo   - modelo/ (6 clases)
    echo   - servicio/ (3 clases) 
    echo   - sistemaRestaurante/ (1 clase)
    echo.
    echo Abra docs\index.html en su navegador
) else (
    echo.
    echo ERROR: No se pudo generar la documentacion
    echo Revise que todos los archivos .java esten compilados
)

pause