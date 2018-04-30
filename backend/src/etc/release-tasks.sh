#!/bin/bash

java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
ru.hh.school.employerreview.FlywayMigrator
#java -DsettingsDir=backend/src/etc -cp backend/target/employer-review-backend.jar \
# ru.hh.school.employerreview.downloader.SpecializationsDownloader
java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
ru.hh.school.employerreview.downloader.AreaDownloader area=1
java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
ru.hh.school.employerreview.downloader.EmployersDownloader area=1 type=company