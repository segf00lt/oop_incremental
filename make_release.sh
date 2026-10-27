#!/bin/sh

rm -f release.zip
mkdir -p release
cp *.java *.ctxt README.TXT *.pdf package.bluej release/
zip -r release.zip release
