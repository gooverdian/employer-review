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
400 | BAD_FIELD_VALUE | rating | Значение поля rating не удовлетворяет ограничениям


**GET:**
Принимает параметры:

- employer_id
- page	Нумерация страниц начинается  с 0
- per_page


При успешной выборке возвращает список отзывов:
```json
{
	"reviews": [
	  	{
			"employer_id": 4,
			"rating": 2,
			"text": "Review 01"
		},
	  	{
			"employer_id": 4,
			"rating": 2,
			"text": "Review 00"
		}
	],
	"page": 2,
	"pages": 3,
	"per_page": 5
}
```

Может возвращать ошибки в формате указанном выше.
Возможные значения:  

HTTP code | type | value | описание
----------|------|-------|-----------
200 | NO_DATA | review | Отклики для запрошенного работодателя не найдены
400 | BAD_REQUEST_PARAMETER | page | Некорректный параметр запроса - page
400 | BAD_REQUEST_PARAMETER | perPage | Некорректный параметр запроса - perPage
