# SafeAuth
Safe JavaEE authentication implementation using **client-side** asymmetric encryption

* Asymmetric client-side encryption ([RSA](https://en.wikipedia.org/wiki/RSA_(cryptosystem))) of user credentials. (Thanks to [jsencrypt](https://github.com/travist/jsencrypt))
* Server-side (Database) salted password hashing. ([PBKDF2](https://en.wikipedia.org/wiki/PBKDF2))
* Least possible JVM memory leakage of raw password.
* Cross RDBMS compatibility. (Thanks [jOOQ](https://www.jooq.org/)!)
* As a SNAPSHOP version, SQLite used as backend database; use [`modelgen/generate.sh`](https://github.com/fadavi/SafeAuth/blob/master/modelgen/generate.sh) to generate database schema (`$HOME/safeauth.db`) and jOOQ entity classes. Feel free to edit database schema script at [`modelgen/skel.sql`](https://github.com/fadavi/SafeAuth/blob/master/modelgen/skel.sql) and then update changes using `generate.sh`.
* I'd used OracleJDK 1.8.0_144, [Netbeans](https://netbeans.org) 8.2 as IDE, [Glassfish](https://javaee.github.io/glassfish/) 4.1 as web server. It seems there's acceptable stability on GNU/Linux operating systems. (Windows&reg; not tested)

## License
[GNU Genaral Public License (GPL) v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)