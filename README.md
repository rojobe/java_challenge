## El presente repositorio presenta una solución a la necesidad de registrar un usuario, autenticar un usuario creado y validar que un usuario tenga un token valido a la hora de realizar operaciones en el sistema

### La API propuesta se construyó utilizando el framework Spring Boot v2.7.10 y provee 3 servicios 

  ##### Creacion de un usuario
  
  ###### POST Request /v1/user/sign-up: 
  ```
  http://localhost:8080/v1/user/sign-up
  ```
  
  ```
  {
    "username": "rodrigobenito",
    "email": "rodrigobenito@mail.com",
    "password": "a2asfGf6",
    "phones": [
        {
        "number": 111222333,
        "cityCode": 261,
        "countryCode": "MDZ"
        }
    ]
}
```
  ###### Response:
  ```
{
    "id": "136845db-4887-47c8-a69f-94d05886375e",
    "username": "rodrigobenito",
    "email": "rodrigobenito@mail.com",
    "password": "a2asfGf6",
    "created": "2023-03-16",
    "lastLogin": "2023-03-16",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InJvZHJpZ29iZW5pdG8zM0BtYWlsLmNvbSIsInN1YiI6InJvZHJpZ29iZW5pdG8zM0BtYWlsLmNvbSIsImlhdCI6MTY3OTU4MjI5N30.y8V36DDSDk6Vrdyb-Dv0JAhUYI0T6WJy5YGe1200GY4",
    "phones": [
        {
            "id": "cf804470-a87c-4791-8ecf-f723c187a22b",
            "number": 111222333,
            "cityCode": 261,
            "countryCode": "MDZ",
            "userId": null
        }
    ],
    "active": true
}
  ```
  ##### Login de un usuario creado utilizando token
  ###### PÖST Request /login-by-token:
  ```
  http://localhost:8080/v1/user/login-by-token
  ```
  ```
  Para poder utilizar los recursos del sistema se debe enviar el valor del token en el header de la request  sign-up
  KEY: token 
  VALUE: el valor de token devuelto por el endpoint /login
   ```
  ###### Response si el token es valido:
  ```
{
    "id": "136845db-4887-47c8-a69f-94d05886375e",
    "username": "rodrigobenito",
    "email": "rodrigobenito@mail.com",
    "created": "2023-03-16",
    "lastLogin": "2023-03-16",
    "phones": [
        {
            "id": "cf804470-a87c-4791-8ecf-f723c187a22b",
            "number": 111222333,
            "cityCode": 261,
            "countryCode": "MDZ",
        }
    ],
    "active": true
}  
```
###### Response si el token es invalido:
```
{
    "timeStamp": "2023-03-16",
    "codigo": 401,
    "detail": "You are not authorized"
}
```

  
  ##### Obtener un usuario a través de su email
  ###### GET Request /v1/user/login:
  ```
  http://localhost:8080/v1/user/login
  ```
 ```
{
  "email": "rodrigobenito@mail.com"
  "password": "a2asfGf6"
}
```

  ###### Response si el email coindide con un usuario del sistema y el password matchea:
  ```
{
    "id": "136845db-4887-47c8-a69f-94d05886375e",
    "username": "rodrigobenito",
    "email": "rodrigobenito@mail.com",
    "created": "2023-03-16",
    "lastLogin": "2023-03-16",
    "phones": [
        {
            "id": "cf804470-a87c-4791-8ecf-f723c187a22b",
            "number": 111222333,
            "cityCode": 261,
            "countryCode": "MDZ",
        }
    ],
    "active": true
}  
```


### Base de Datos
Se utilizo una base de datos SQL embebida que provee Spring Boot la cual una vez iniciada la aplicación puede ser accedida a través de una navegador web 
en la siguiente URL: http://localhost:8080/h2-console.

### Ejecución de la aplicación

1. Iniciar una terminal
1. Clonar el presente repositorio
1. Navegar hasta la carpeta raiz
1. Ejecutar el siguiente código:  gradle bootRun
1. Esto lanzará la tarea de correr el servicio para poder ser utilizado en la url http://localhost:8080
1. A través de alguna herramienta como Postman ya podemos comenzar a utilizar los servicios propuestos anteriormente utilizando la URL: http://localhost:8090/ como punto de entrada a las request

### Ejecución de unit test
1. En la carpeta raiz ejecutar el comando: gradle build 
1. El proceso de construcción se encargará de ejecutar los test unitarios implementados


