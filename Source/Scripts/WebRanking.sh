#!/bin/bash

# Opens the browser with the local path to the webpage

sh ./Ranking.sh "$1" "$2"
python -mwebbrowser http://localhost:8080/Outputs/Web/rankingWeb.html
cd ../..
python -m SimpleHTTPServer 8080
