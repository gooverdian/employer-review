#!/bin/bash

java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
ru.hh.school.employerreview.FlywayMigrator

#java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
#ru.hh.school.employerreview.downloader.SpecializationsDownloader

#java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
#ru.hh.school.employerreview.downloader.AreaDownloader area=1 area=2 area=4 area=3 area=66 area=88 area=104 area=68 area=78 area=76 area=72

#java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
#ru.hh.school.employerreview.downloader.EmployersDownloader area=1 area=2 area=4 area=3 area=66 area=88 area=104 area=68 area=78 area=76 area=72

#java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
#ru.hh.school.employerreview.webextractor.ReviewExtractor threads=4 limit=10000 length_threshold=100

#java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
#ru.hh.school.employerreview.review.generator.ReviewsGeneratorMain limit=50 reviews=5 deviation=5 per_page=50
