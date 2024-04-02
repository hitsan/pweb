FROM mariadb:10.5 AS db
COPY my.cnf /etc/mysql/conf.d/my.cnf
COPY sql.sql /docker-entrypoint-initdb.d
RUN chmod 644 /etc/mysql/my.cnf

FROM phpmyadmin:apache AS phpmyadmin
COPY config.inc.php /var/www/html/config.inc.php

FROM dbeaver/cloudbeaver:24.0.1 AS dbeaver