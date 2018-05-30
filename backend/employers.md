# Поиск компании

**GET**

**URL** /employers

параметры: `text` - текст поиска, `page` (по умолчанию - 0) - страница поиска, `per_page` - (по умолчанию - 10) - кол-во компаний на 1 странице

пример ответа:
```json
{
    "pages":1132,
    "items":
        [
            {
                "id":7,
                "name":"007ex",
                "url":"https://api.hh.ru/employers/2165322",
                "stars":{"3.5":2,"4.5":2,"5.0":11},
                "rating":4.733333,
                "hh_id":2165322,
                "logo_url":"https://hh.ru/employer/logo/2165322",
                "area_name":"Россия",
                "area_id":113,
                "people_rated":15
            }
        ],
    "page":0,
    "found":1132,
    "per_page":1
}
```

# Просмотр страницы компании

**GET**

**URL** /employers/<employer_id>

параметры: `add_visit_counter` (по умолчанию false), счетчик посещения компании автоматически инкрементируется `add_visit_counter=true`

пример ответа:
```json
{
    "id":1,
    "name":"ＤΞＮＮＹ ＫＯＳＳ",
    "url":"https://api.hh.ru/employers/2617405",
    "stars":{"0.5":11,"1.0":1,"1.5":2,"2.0":1,"3.0":1},
    "rating":0.90625,
    "hh_id":2617405,
    "logo_url":"https://hh.ru/employer/logo/2617405",
    "area_name":"Россия",
    "area_id":113,
    "people_rated":16
}
```

# Добавление новой компании не из базы hh

**POST**

**URL** /employers

пример входного json
```json
{
	"name":"test",
	"url":"test.com",
	"area_id":113
}

```

пример ответа
```json
{
    "id": 4001,
    "name": "test",
    "url": "test.com",
    "stars": null,
    "rating": null,
    "hh_id": null,
    "logo_url": null,
    "area_name": "Россия",
    "area_id": 113,
    "people_rated": null
}

```

# Топ лучших компаний

**GET**

**URL** /employers/best

параметры: `size` (по умолчанию - 20) - размер топа компаний

пример ответа:
```json
[
    {
        "id":870,
        "name":"22Стиля",
        "url":"https://api.hh.ru/employers/677383",
        "stars":{"4.0":1,"4.5":2,"5.0":16},
        "rating":4.894737,
        "hh_id":677383,
        "logo_url":"https://hh.ru/employer/logo/677383",
        "area_name":"Россия","area_id":113,
        "people_rated":19
    },
    {
        "id":371,
        "name":"12 Месяцев",
        "url":"https://api.hh.ru/employers/1884825",
        "stars":{"4.5":4,"5.0":8},
        "rating":4.8333335,
        "hh_id":1884825,
        "logo_url":"https://hh.ru/employer/logo/1884825",
        "area_name":"Россия",
        "area_id":113,
        "people_rated":12
    }
]
```

# Топ худших компаний

**GET**

**URL** /employers/worst

параметры: `size` (по умолчанию - 20) - размер топа компаний

пример ответа:
```json
[
    {
        "id":870,
        "name":"22Стиля",
        "url":"https://api.hh.ru/employers/677383",
        "stars":{"4.0":19},
        "rating":4.0,
        "hh_id":677383,
        "logo_url":"https://hh.ru/employer/logo/677383",
        "area_name":"Россия",
        "area_id":113,
        "people_rated":19
    },
    {
        "id":371,
        "name":"12 Месяцев",
        "url":"https://api.hh.ru/employers/1884825",
        "stars":{"5.0":20},
        "rating":5.0,
        "hh_id":1884825,
        "logo_url":"https://hh.ru/employer/logo/1884825",
        "area_name":"Россия",
        "area_id":113,
        "people_rated":20
    }
]
```

# Посещение страницы компании
счетчик автоматически инкрементируется при get запросе "/employers/<employer_id>?add_visit_counter=true"

**GET**

**URL** /employers/visits

параметры: `size` (по умолчанию - 20) - размер топа, `interval` (по умолчанию - 30) - за сколько последних дней считать топ

пример ответа:
```json
[
    {
        "id":354,
        "name":"12 Военпроект",
        "url":"https://api.hh.ru/employers/65471",
        "people_visited":8,
        "logo_url":"https://hh.ru/employer/logo/65471"
    }
]
```

# Топ сбалансированых компаний (минимальная дисперсия оценки в озыве)

**GET**

**URL** /employers/balanced

параметры: `size` (по умолчанию - 20) - размер топа компаний

пример ответа:
```json
[
    {
        "id":870,
        "name":"22Стиля",
        "url":"https://api.hh.ru/employers/677383",
        "stars":{"4.0":19},
        "rating":4.0,
        "hh_id":677383,
        "logo_url":"https://hh.ru/employer/logo/677383",
        "area_name":"Россия",
        "area_id":113,
        "people_rated":19
    },
    {
        "id":371,
        "name":"12 Месяцев",
        "url":"https://api.hh.ru/employers/1884825",
        "stars":{"4.5":4,"5.0":8},
        "rating":4.8333335,
        "hh_id":1884825,
        "logo_url":"https://hh.ru/employer/logo/1884825",
        "area_name":"Россия",
        "area_id":113,
        "people_rated":12
    }
]
```

# Топ несбалансированых компаний (максимальная дисперсия оценки в озыве)

**GET**

**URL** /employers/disbalanced

параметры: `size` (по умолчанию - 20) - размер топа компаний

пример ответа:
```json
[
    {
        "id":371,
        "name":"12 Месяцев",
        "url":"https://api.hh.ru/employers/1884825",
        "stars":{"4.5":4,"5.0":8},
        "rating":4.8333335,
        "hh_id":1884825,
        "logo_url":"https://hh.ru/employer/logo/1884825",
        "area_name":"Россия",
        "area_id":113,
        "people_rated":12
    },
    {
        "id":870,
        "name":"22Стиля",
        "url":"https://api.hh.ru/employers/677383",
        "stars":{"4.0":19},
        "rating":4.0,
        "hh_id":677383,
        "logo_url":"https://hh.ru/employer/logo/677383",
        "area_name":"Россия",
        "area_id":113,
        "people_rated":19
    }
]
```
