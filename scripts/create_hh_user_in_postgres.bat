:: Sctript to create new superuser in postgres 
:: You should include psql command in PATH of system vairibales in Windows!

(
    echo CREATE USER hh;
    echo ALTER USER hh WITH SUPERUSER;
) | psql -U postgres

echo "user hh has been created! :-)"




