##Curl commands
Default logged user is user with no admin privileges.
###- Get all meals
```
curl -s http://localhost:8080/topjava/rest/meals

Produces:
[{"id":100008,"dateTime":"2020-01-31T20:00:00","description":"Ужин","calories":510,"excess":true},
{"id":100007,"dateTime":"2020-01-31T13:00:00","description":"Обед","calories":1000,"excess":true},
{"id":100006,"dateTime":"2020-01-31T10:00:00","description":"Завтрак","calories":500,"excess":true},
{"id":100005,"dateTime":"2020-01-31T00:00:00","description":"Еда на граничное значение","calories":100,"excess":true},
{"id":100004,"dateTime":"2020-01-30T20:00:00","description":"Ужин","calories":500,"excess":false},
{"id":100003,"dateTime":"2020-01-30T13:00:00","description":"Обед","calories":1000,"excess":false},
{"id":100002,"dateTime":"2020-01-30T10:00:00","description":"Завтрак","calories":500,"excess":false}]
```
###- Get filtered meals
```
curl -s "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-30&endTime=14:00:00"

Produces:
[{"id":100003,"dateTime":"2020-01-30T13:00:00","description":"Обед","calories":1000,"excess":false},
{"id":100002,"dateTime":"2020-01-30T10:00:00","description":"Завтрак","calories":500,"excess":false}]
```
###- Get meal
```
curl -s http://localhost:8080/topjava/rest/meals/100002

Produces:
{"id":100002,"dateTime":"2020-01-30T10:00:00","description":"Завтрак","calories":500,"user":null}
```
###- Delete meal
```
curl -s -X DELETE http://localhost:8080/topjava/rest/meals/100002
```
###- Insert new meal
```
curl -s -X POST -d '{"dateTime":"2020-09-03T10:00:00", "description":"curlTestMeal", "calories":500}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/meals
```
###- Update meal
```
curl -s -X PUT -d '{"id":100005,"dateTime":"2020-01-31T00:00:00","description":"updateFromCurl","calories":100},' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/meals/100005
```