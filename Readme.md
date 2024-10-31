# `RestServiceInfoStudents`
В приложении реализован RESTсервис, в котором реализованы CRUD операции с объектами в базе данных MongoDB
### Инструкция по развертыванию проекта
Приложение необходимо запускать с помощью docker:
1. Упаковать код с помощью maven: `mvn package`;
3. Собрать и запустить docker-compose: `docker-compose up`.
### В приложении реализована авторизация по протоколу Aouth2.0 через GitHub
1. Перед выполнением запросов необходимо выполнить авторизацию:
```
curl -X GET http://localhost:8080/login 
```
2. В открывшемся окне необходимо авторизоватья через GitHub;
3. После авторизации будет выполнен переход на страницу, представляющую сообщение о результате авторизации и данные авторизованного пользователя;
### Реализованные CRUD операции:
* POST /students - создание объекта
```
curl -X POST http://localhost:8080/students -H "Content-Type: application/json" --data-binary @- <<DATA
{
    "lastname": "Dostoevskiy",
    "firstname": "Fedor",
    "patronymic": "Michailovich", 
    "group": "1",
    "averageGrade": 4.77
}
DATA
```
* GET /students - получение списка всех объектов
```
curl -X GET http://localhost:8080/students 
```
* GET /students/{studentId} - получение информации об объекте по его id (в качестве {studentId} необходимо использвать Id в формате UUID существующего объекта)
```
curl -X GET http://localhost:8080/students/19083e58-00b1-4e7d-9b79-7bcf744dc635
```
* PUT /students/{studentId} - редактирование объекта (в качестве {studentId} необходимо использвать Id в формате UUID существующего объекта)
```
curl -X PUT http://localhost:8080/students/97429604-37b4-4694-ae81-60f1464db398 -H "Content-Type: application/json" --data-binary @- <<DATA
{
    "lastname": "Update",
    "firstname": "Update",
    "patronymic": "Update",
    "group": "Update",
    "averageGrade": 4.99
}
DATA
```
* DELETE /students/{studentId} - удаление объекта (в качестве {studentId} необходимо использвать Id в формате UUID существующего объекта)
```
curl -X DELETE http://localhost:8080/students/19083e58-00b1-4e7d-9b79-7bcf744dc635 
```