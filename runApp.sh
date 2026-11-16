#!/bin/bash
set -e

cd src
javac main/*.java
java main.MainMenu
