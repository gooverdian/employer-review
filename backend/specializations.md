# Отзыв


**URL:** /specializations

**GET:**
Принимает параметр text.

Если параметр не задан возвращается полный список специализаций.

Например: http://localhost:8080/specializations?text=инфо

Результат возвращается в виде:
```json
[
	{
		"name": "Информационные технологии, интернет, телеком",
		"specializations": [
			{
				"name": "CRM системы",
				"specialization_id": 6
			},
		],
		"professional_field_id": 1
	},
	{
		"name": "Безопасность",
		"specializations": [
			{
				"name": "Экономическая и информационная безопасность",
				"specialization_id": 195
			}
		],
		"professional_field_id": 8
	},
	{
		"name": "Высший менеджмент",
		"specializations": [
			{
				"name": "Информационные технологии, Интернет, Мультимедиа",
				"specialization_id": 204
			}
		],
		"professional_field_id": 9
	}
]
```
