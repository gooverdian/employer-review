# Отзыв


**URL:** /review

**POST:**
Принимает на вход тело запроса:
```json
{
	"employer_id":5,
	"rating":5.0,
	"review_type":"EMPLOYEE",
	"text":"Review for 5"
}
```
Где review_type - enumeration, который принимает 2 значения: EMPLOYEE, INTERVIEWEE


В случае успешного сохранения возвращается:
```json
{
	"review_id":5
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
400 | MISSING_FIELD | employer_id | Не передано поле employerId
400 | MISSING_FIELD | rating | Не передано поле rating
400 | BAD_FIELD_VALUE | employer_id | Значение employerId не найдено в базе
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
			"review_type":"EMPLOYEE",
			"text": "Review 01"
		},
	  	{
			"employer_id": 4,
			"rating": 2,
			"review_type":"INTERVIEWEE",
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
400 | BAD_REQUEST_PARAMETER | per_page | Некорректный параметр запроса - per_page
