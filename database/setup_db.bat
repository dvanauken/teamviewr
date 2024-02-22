@echo off
:: Setup script for the teamviewer database
:: This script drops the existing teamviewer database (if it exists), creates a new one,
:: creates a dedicated user, and sets up the necessary tables.

:: INSTRUCTIONS:
:: 1. Save this script to your computer.
:: 2. Open Command Prompt (CMD) - you might need to run it as an administrator for certain operations.
:: 3. Navigate to the directory where this script is located using the cd command. For example:
::    cd C:\path\to\your\script\
:: 4. Run the script by typing its name and pressing Enter:
::    setup_db.bat
::
:: NOTE:
:: - Ensure PostgreSQL's psql utility is accessible from your system's PATH.
:: - This script includes plain text passwords, which is not recommended for production environments.
:: - Running this script will DROP the existing teamviewer database and all its data.

SET PGUSER=postgres
SET PGPASSWORD=admin
SET PGHOST=localhost
SET PGPORT=5432

echo Dropping the teamviewer database if it exists...
psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -c "DROP DATABASE IF EXISTS teamviewer;"
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to drop database: teamviewer
    exit /b %ERRORLEVEL%
)

echo Creating the teamviewer database...
psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -c "CREATE DATABASE teamviewer;"
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to create database: teamviewer
    exit /b %ERRORLEVEL%
)

echo Attempting to drop the teamviewer_user if it exists...
psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -c "DROP USER IF EXISTS teamviewer_user;"
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to drop user: teamviewer_user
    exit /b %ERRORLEVEL%
)

echo Creating the dedicated user...
psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -c "CREATE USER teamviewer_user WITH PASSWORD 'password123';"
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to create user: teamviewer_user
    exit /b %ERRORLEVEL%
)

psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -c "GRANT ALL PRIVILEGES ON DATABASE teamviewer TO teamviewer_user;"
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to grant privileges to user: teamviewer_user
    exit /b %ERRORLEVEL%
)

echo Granting additional schema-level privileges to teamviewer_user...
psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -d teamviewer -c "GRANT USAGE ON SCHEMA public TO teamviewer_user;"
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to grant USAGE ON SCHEMA public to teamviewer_user
    exit /b %ERRORLEVEL%
)

psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -d teamviewer -c "GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO teamviewer_user;"
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to grant ALL PRIVILEGES ON ALL TABLES IN SCHEMA public to teamviewer_user
    exit /b %ERRORLEVEL%
)

psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -d teamviewer -c "GRANT CREATE ON SCHEMA public TO teamviewer_user;"
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to grant CREATE ON SCHEMA public to teamviewer_user
    exit /b %ERRORLEVEL%
)

:: Switch to teamviewer_user to create tables
SET PGUSER=teamviewer_user
SET PGPASSWORD=password123

echo Creating tables...
psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -d teamviewer -a -f ddl.sql
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to create tables
    exit /b %ERRORLEVEL%
)

echo Populating tables with initial data...
psql -U %PGUSER% -h %PGHOST% -p %PGPORT% -d teamviewer -a -f dml.sql
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to populate tables with initial data
    exit /b %ERRORLEVEL%
)

echo Done.
