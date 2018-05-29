## Генерация отзывов

### Для успешной генерации отзывов следует осуществить следующие шаги:

* Сбилдить бэкэнд чтоб получить `employer-review-backend.jar` ( например `mvn:package` )
* Загрузить тексты отзывов в бд
```
java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
ru.hh.school.employerreview.webextractor.ReviewExtractor threads=4 limit=10000
```
* Запустить генератор отзывов:
```
java -DsettingsDir=src/etc -cp target/employer-review-backend.jar \
ru.hh.school.employerreview.review.generator.ReviewsGeneratorMain limit=50 reviews=5 deviation=5 per_page=50
```
