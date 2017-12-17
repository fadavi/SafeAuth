-- SafeAuth database skeleton
-- Copyright (c) 2017 - Mohamad Fadavi <fadavi@fadavi.net>

PRAGMA foreign_keys = ON;
PRAGMA encoding = "UTF-8";
PRAGMA auto_vacuum = FULL;
PRAGMA cache_size = -16000;

CREATE TABLE IF NOT EXISTS account (
  id INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT,
  username TEXT UNIQUE NOT NULL,
  password_hash TEXT UNIQUE NOT NULL,
  password_salt TEXT UNIQUE NOT NULL
);
