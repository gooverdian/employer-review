# Отзыв


**URL:** /review

**POST:**
Принимает на вход тело запроса:
```json
{
	"employer_id":0,
	"rating":2.5,
	"review_type":"INTERVIEWEE",
	"text":"ReviewText 01",
	"salary":100000,
	"specializations":[{"name":"Specialization 01","specialization_id":0},{"name":"Specialization 02","specialization_id":1}],
	"review_id":null,
	"position_id":0,
	"employment_duration":0,
	"employment_terminated":false
}
```
Где review_type - enumeration, который принимает 2 значения: EMPLOYEE, INTERVIEWEE

Обязательными являются поля employer_id, rating, review_type, text


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
- page	Нумерация страниц начинается  с 0 (необязательный)
- per_page (необязательный)
- review_type (необязательный)


При успешной выборке возвращает список отзывов:
```json
{
	"reviews": [
	  	{
			"rating":	2,
			"text":	"gdfghdfgh\n",
			"salary":	null,
			"specializations":	[],
			"employer_id":	5,
			"review_id": 2,
			"review_type":	"EMPLOYEE",
			"position_id":	null,
			"employment_duration":	null,
			"employment_terminated":	null,
			"created_on":	"2018-05-28T13:27:47MSK"
		},
	  	{
			"rating":	4,
			"text":	"dfghdfghdfh",
			"salary":	null,
			"specializations":	[],
			"employer_id":	5,
			"review_id":	1,
			"review_type":	"EMPLOYEE",
			"position_id":	null,
			"employment_duration":	null,
			"employment_terminated":	null,
			"created_on":	"2018-05-28T13:27:10MSK"
		}
	],
	"page": 2,
	"pages": 3,
	"per_page": 5,
	"found": 15
}
```

Может возвращать ошибки в формате указанном выше.
Возможные значения:

HTTP code | type | value | описание
----------|------|-------|-----------
200 | NO_DATA | review | Отклики для запрошенного работодателя не найдены
400 | BAD_REQUEST_PARAMETER | page | Некорректный параметр запроса - page
400 | BAD_REQUEST_PARAMETER | per_page | Некорректный параметр запроса - per_page


# Генерация отзывов

**POST**

**URL:** /review/generate

Принимает на вход параметры запроса:
    mat_expected_review_count   - Мат. ожидание количества отзывов по каждой компании (по-умолчанию 10)
    deviation_review_count      - Дисперсия количества отзывов по каждой компании (по-умолчанию 10)
    max_employer_size           - Максимальное кол-во компаний по которым будут сгененрированы отзывы (по-умолчанию 1000)

Если генерация отзывов прошла успешна, вернется статус 200

# Подсчет последних отзывов о компании

**GET**

**URL:** /review/count/{employer_id}

Параметр: `interval` (по умолчанию 1) - за сколько последних дней считать отзывы

Пример ответа:
```json
{
    "employer_id":1,
    "counter":2
}
```

