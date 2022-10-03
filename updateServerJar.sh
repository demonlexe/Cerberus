#!/bin/sh

cd ./target

# shellcheck disable=SC2144
until [ -f cerberus*.jar ]
do
  sleep 2
done

mv cerberus*.jar C:/Users/lexi/Desktop/SpigotServer/plugins

cd C:/Users/lexi/Desktop/SpigotServer
cmd.exe /c start.bat