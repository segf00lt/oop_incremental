#!/bin/sh

mkdir release
cp *.java *.ctxt README.TXT *.pdf package.bluej release/
zip -r release.zip release
