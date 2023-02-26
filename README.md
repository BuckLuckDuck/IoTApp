# IoT Application

## Инструкция по запуску
1. Поднять Docker container c PostgreSQL
`docker compose up -d`.
2. Запустить `IoTAppApplication.java` приложения.

## Реализованные API-методы
### 1. Добавление нового устройства
***
**Следуя требованиям задания:** *В ответ возвращается секретный ключ*

**Endpoint:** `http://localhost:8080/api/device` 

**Method:** `POST`

<ins>**Request:**<ins>

**body/json**
```
{
    "serialNumber" : "907310503-44529630000",
    "name" : "Увлажнитель воздуха в зале",
    "type" : "Комнатный девайс"
}
```
<ins>**Response:**<ins>

`Status: 200 OK`

**body/json** 
```
{
    "key": "lpKVHwqnP3QF"
}
```
<ins>**Ошибки:**<ins>

`Status: 400 Bad Request`
```
{
    "statusCode": 400,
    "message": "Device with serial number 907310503-44529630000 already exists"
}
```
### 2. Сохранение события от IoT устройства
***
Реализовано с помощью передачи **серийного номера** и **ключа**. Можно было реализовать только с помощью **ключа**, но в таком случае это было бы более ресурсозатратно.

**Endpoint:** `http://localhost:8080/api/events?serialNumber=907310503-44529630000&key=lpKVHwqnP3QF`

**Method:** `POST`

<ins>**Request:**<ins>

**body/json**
```
{
    "type" : "Увлажнение воздуха",
    "payload" : {
        "Minutes" : 30,
        "Power" : 0.7
    }
}
```
<ins>**Response:** <ins>

`Status: 201 Created`.

<ins>**Ошибки:**<ins>

<ins>**Response:**<ins>

`Status 404 Not Found`

**body/json**
```
{
    "statusCode": 404,
    "message": "Device with serial number 907310503-44529630000 not found"
}
```

Или

<ins>**Response:**<ins> 

`Status: 400 Bad Request`, если был отправлен невалидный **ключ**.
### 3. Получить информацию об активных устройствах
***
Неактивные более 30 минут устройства удаляются. Каждые 10 минут сервер отправляет запрос на поиск таковых устройств, можно поставить промежуток и меньше (например 1 минуту), но это увеличит нагрузку на сервер.

**Endpoint:** `http://localhost:8080/api/active`

**Method:** `GET`

<ins>**Response:**<ins>

`Status 200 OK`

**body/json**
```
[
    {
        "id": 1,
        "device": {
            "id": 1,
            "serialNumber": "907310503 - 44529630000",
            "name": "Увлажнитель воздуха в зале",
            "type": "Комнатный девайс",
            "key": "$2a$10$gcIOSkvhxDf3v4qGV5csie.OXamUMhUihzBRVCvhuEiRBC7F/D6nO",
            "dateOfAdd": "2023-02-26T21:17:03.856"
        },
        "firstActionDate": "2023-02-26T21:18:48.755",
        "lastActionDate": "2023-02-26T21:18:48.755"
    },
    {
        "id": 2,
        "device": {
            "id": 2,
            "serialNumber": "471034520 - 44529630000",
            "name": "Микроволновка на кухне",
            "type": "Кухонный девайс",
            "key": "$2a$10$KA7BmktvXuuNAAqj7rc2OOrkrYJCZgEmzsAeOVqZfaVW7Yj8U6nQ2",
            "dateOfAdd": "2023-02-26T21:17:39.808"
        },
        "firstActionDate": "2023-02-26T21:19:39.797",
        "lastActionDate": "2023-02-26T21:19:39.797"
    },
    {
        "id": 3,
        "device": {
            "id": 3,
            "serialNumber": "907310502 - 44529630000",
            "name": "Чайник на кухне",
            "type": "Кухонный девайс",
            "key": "$2a$10$GoC/XuNPtMEtaB1KsEQJROfJwf8/1486s7CIkv.650Jjz6aqDvWt2",
            "dateOfAdd": "2023-02-26T21:18:07.011"
        },
        "firstActionDate": "2023-02-26T21:20:11.465",
        "lastActionDate": "2023-02-26T21:20:11.465"
    }
]
```
### 4. Получить информацию о конкретном устройстве по серийному номеру
***
**Серийный номер** отправляется параметром в строке запроса.

**Endpoint:** `http://localhost:8080/api/device?serialNumber=907310502-44529630000`

**Method:** `GET`

<ins>**Response:**<ins>

`Status 200 OK`

**body/json**
```
{
    "id": 3,
    "serialNumber": "907310502-44529630000",
    "name": "Чайник на кухне",
    "type": "Кухонный девайс",
    "key": "$2a$10$GoC/XuNPtMEtaB1KsEQJROfJwf8/1486s7CIkv.650Jjz6aqDvWt2",
    "dateOfAdd": "2023-02-26T21:18:07.011"
}
```

<ins>**Ошибки:**<ins>

**Response:**

`Status 404 Not Found`

**body/json**
```
{
    "statusCode": 404,
    "message": "Device with serial number 907310502 - 445296300001 not found"
}
```
### 5. Получить информацию о всех добавленных в систему устройств
***
**Параметры в строке запроса:**
1. `limit`, количество вывода устройств на странице за раз. `Default value = 20` 
2. `offset`, номер страницы. `Default value = 0`
3. `date`, фильтр по дате добавления. Принимается в формате 'yyyy-MM-dd'. **Необязательный параметр.**
4. `type`, фильтр по типу устройства. **Необязательный параметр.**

**Endpoints examples:**
- `http://localhost:8080/api/device/all`
- `http://localhost:8080/api/device/all?limit=10&offset=0`
- `http://localhost:8080/api/device/all?limit=10&offset=0&date=2023-02-26`
- `http://localhost:8080/api/device/all?limit=5&offset=3&type=Кухонный+девайс`
- `http://localhost:8080/api/device/all?limit=3&offset=0&type=Кухонный+девайс&date=2023-02-26`

**Method:** `GET` 

<ins>**Response:**<ins>

`Status 200 OK`

**body/json**
```
{
    "content": [
        {
            "id": 1,
            "serialNumber": "907310503 - 44529630000",
            "name": "Увлажнитель воздуха в зале",
            "type": "Комнатный девайс",
            "key": "$2a$10$gcIOSkvhxDf3v4qGV5csie.OXamUMhUihzBRVCvhuEiRBC7F/D6nO",
            "dateOfAdd": "2023-02-26T21:17:03.856"
        },
        {
            "id": 2,
            "serialNumber": "471034520 - 44529630000",
            "name": "Микроволновка на кухне",
            "type": "Кухонный девайс",
            "key": "$2a$10$KA7BmktvXuuNAAqj7rc2OOrkrYJCZgEmzsAeOVqZfaVW7Yj8U6nQ2",
            "dateOfAdd": "2023-02-26T21:17:39.808"
        },
        {
            "id": 3,
            "serialNumber": "907310502 - 44529630000",
            "name": "Чайник на кухне",
            "type": "Кухонный девайс",
            "key": "$2a$10$GoC/XuNPtMEtaB1KsEQJROfJwf8/1486s7CIkv.650Jjz6aqDvWt2",
            "dateOfAdd": "2023-02-26T21:18:07.011"
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageSize": 3,
        "pageNumber": 0,
        "unpaged": false,
        "paged": true
    },
    "totalElements": 3,
    "last": true,
    "totalPages": 1,
    "size": 3,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 3,
    "first": true,
    "empty": false
}
```
### 6. Получить информацию о событиях конкретного устройства по серийному номеру
***
**Параметры в строке запроса:**
1. `serialNumber`, серийный номер устройства. **Обязательный.**
2. `date`, фильтр по дате добавления событий. **Необязательный.**
3. `limit`, количество вывода устройств на странице за раз. `Default value = 20` 
4. `offset`, номер страницы. `Default value = 0`

**Endpoints examples:** 
- `http://localhost:8080/api/events?serialNumber=907310502-44529630000`
- `http://localhost:8080/api/events?serialNumber=907310502+-+44529630000&limit=3&offset=0&date=2023-02-26`

**Method:** `GET` 

<ins>**Response:**<ins>

`Status 200 OK`

**body/json**
```
{
    "content": [
        {
            "id": 3,
            "device": {
                "id": 3,
                "serialNumber": "907310502 - 44529630000",
                "name": "Чайник на кухне",
                "type": "Кухонный девайс",
                "key": "$2a$10$GoC/XuNPtMEtaB1KsEQJROfJwf8/1486s7CIkv.650Jjz6aqDvWt2",
                "dateOfAdd": "2023-02-26T21:18:07.011"
            },
            "type": "Вскипятить воду",
            "payload": {},
            "time_of_add": "2023-02-26T21:20:11.465"
        },
        {
            "id": 4,
            "device": {
                "id": 3,
                "serialNumber": "907310502 - 44529630000",
                "name": "Чайник на кухне",
                "type": "Кухонный девайс",
                "key": "$2a$10$GoC/XuNPtMEtaB1KsEQJROfJwf8/1486s7CIkv.650Jjz6aqDvWt2",
                "dateOfAdd": "2023-02-26T21:18:07.011"
            },
            "type": "Подогреть воду",
            "payload": {},
            "time_of_add": "2023-02-26T21:25:27.643"
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageSize": 3,
        "pageNumber": 0,
        "unpaged": false,
        "paged": true
    },
    "totalElements": 2,
    "last": true,
    "totalPages": 1,
    "size": 3,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
}
```
### 7. Получить статистику по указанному периоду времени о количестве полученных событий, сгруппированном по типам устройств
***
**Параметры в строке запроса:**
1. `dateAfter`, дата "после какой" или "начало промежутка периода времени". **Необязательный**
2. `dateBefore`, дата "до какой" или "конец промежутка периода времени". **Необязательный**

**Endpoints examples:** 
- `http://localhost:8080/api/events/statistics`, получение полной статистики, вне зависимости от времени.
- `http://localhost:8080/api/events/statistics?dateAfter=2023-01-15`, получение статистики событий после 15-Января-2023
- `http://localhost:8080/api/events/statistics?dateBeforer=2023-06-01`, получение статистики событий до 01-Июня-2023
- `http://localhost:8080/api/events/statistics?dateAfter=2023-01-15&dateBeforer=2023-06-01`, получение статистики событий после 15-Января-2023 и до 01-Июня-2023

**Method:** `GET` 

<ins>**Response:**<ins>

`Status 200 OK`

**body/json**
```
[
    {
        "deviceType": "Комнатный девайс",
        "eventsCount": 1
    },
    {
        "deviceType": "Кухонный девайс",
        "eventsCount": 3
    }
]
```
