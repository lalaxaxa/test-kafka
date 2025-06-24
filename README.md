# POST request
http://localhost:8080/users
```
{
"name": "Alise",
"age": 30,
"email": "alise@mail.com"
}
```
Producer - позволяет добавить нового пользователя через POST запрос.
Сохраняет его в БД и отправляет UserEvent в кафку.

Consumer - слушает UserEvent из кафки, сохраняет его БД (типо логи), и через OpenFeign отправляет подтверждение в Producer, что UserEvent был сохранен.

Producer выводит сообщение о том что лог был сохранен в БД Consumer'a и его id.