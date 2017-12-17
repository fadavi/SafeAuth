#!/bin/bash
# Copyright 2017 - Mohamad Fadavi <fadavi@fadavi.net>
# This script, generates ~/safeauth.db and jOOQ classes, automatically.
# WARNING: existing database file will be deleted

# delete previous database
rm -f ~/safeauth.db

# create new database using new(?) schema
sqlite3 ~/safeauth.db < skel.sql

# copy database file to current directory. it will be used by jOOQ generator
cp ~/safeauth.db ./safeauth.db

# run jOOQ generator
java -cp "../lib/jooq-3.10.2.jar:../lib/jooq-codegen-3.10.2.jar:../lib/jooq-meta-3.10.2.jar:../lib/sqlite-jdbc-3.21.0.jar:." org.jooq.util.GenerationTool config.xml

# delete temporary database file
rm -f ./safeauth.db