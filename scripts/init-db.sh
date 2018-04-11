#!/bin/bash

sudo -u postgres createdb employer_review
sudo -u postgres psql -c "CREATE USER hh;"
sudo -u postgres psql -c "ALTER USER hh WITH SUPERUSER;"
sudo -u postgres psql -c "ALTER USER hh WITH PASSWORD '123';"
