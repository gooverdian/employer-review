#!/bin/bash

sudo -u postgres createdb employer_review
sudo -u postgres psql -d employer_review -f create-tables-specializations.sql
sudo -u postgres psql -d employer_review -f create-table-review.sql
