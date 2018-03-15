# Отзыв


**URL:** /review

**POST:**
Принимает на вход тело запроса:
```json
{
	"employerId":5,
	"rating":5.0,
	"text":"Review for 5"
}
```

В случае успешного сохранения возвращает:
```json
{
	"reviewId":5
}
```

Может возвращать ошибки в формате:
```json
{
	"errors": [
		{
		    "type": "...",
		    "value": "..."
		}
	]
}
```
HTTP code | type | value | описание
----------|------|-------|-----------
400 | MISSING_FIELD | employerId | Не передано поле employerId
400 | MISSING_FIELD | rating | Не передано поле rating
400 | BAD_FIELD_VALUE | employerId | Значение employerId не найдено в базе
500 | SAVE_ERROR | review | Не удалось сохранить отзыв



**GET:**
Принимает параметры:

- employerId
- page	Нумерация страниц начинается  с 0
- per_page


При успешной выборке возвращает список отзывов:
```json
{
	"reviews": [
	  	{
			"employerId": 4,
			"rating": 2,
			"text": "Review 01"
		},
	  	{
			"employerId": 4,
			"rating": 2,
			"text": "Review 00"
		}
	],
	"page": 2,
	"pages": 3,
	"perPage": 5
}
```

Может возвращать ошибки в формате указанном выше.
Возможные значения:  

HTTP code | type | value | описание
----------|------|-------|-----------
400 | MISSING_PARAMETER | employerId | Отсутствует параметр запроса - employerId
500 | DB_ERROR | review | Произошла ошибка при обращении в БД
