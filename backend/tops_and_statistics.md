
# Топ лучших / худших

**URL:** "employers/worst" и "employers/best" 

**GET:**
принемает параметр size - размер топа, по дефолту size = 20

результат выдачи с size = 1
```json
[
    {
        "name":"12Групп",
        "url":"https://api.hh.ru/employers/198089",
        "id":360,
        "stars":{"0.5":14,"1.0":1},
        "rating":0.53333336,
        "hh_id":198089,
        "logo_url":"https://hh.ru/employer/logo/198089",
        "area_name":"Россия",
        "area_id":113,
        "people_rated":15
    }
]
```
# Статистика для главной

**URL:** /statistic 

**GET:**
пример ответа по урлу http://localhost:8081/statistic
```json
{
    "REVIEW_COUNT":14419,
    "EMPLOYER_WITH_REVIEW_COUNT":1000
}
```
