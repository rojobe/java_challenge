## El presente repositorio presenta una solución a la necesidad de registrar un usuario, autenticar un usuario creado y validar que un usuario tenga un token valido a la hora de realizar operaciones en el sistema

### La API propuesta se construyó utilizando el framework Spring Boot v2.7.10-SNAPSHOT y provee 3 servicios 

  ##### Creacion de un usuario
  
  ###### POST Request /sign-up: 
  ```
  http://localhost:8080/sign-up
  ```
  
  '''
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
'''
  ###### Response:
  ```
{
    "id": "136845db-4887-47c8-a69f-94d05886375e",
    "username": "rodrigobenito",
    "email": "rodrigobenito@mail.com",
    "password": "a2asfGf6",
    "created": "2023-03-16",
    "lastLogin": "2023-03-16",
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
  ##### Login de un usuario creado
  ###### PÖST Request /login:
  ```
  http://localhost:8080/login
  ```
  ```
  {
    "username": "rodrigobenito",
    "password": "a2asfGf6"
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
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb2RyaWdvYmVuaXRvIiwiaWF0IjoxNjc4OTg3Mzc1fQ.QSmrR2dJi6fTE04oVo76ble7wy_IpRYyB7xxiEQvSbo",
    "phones": [
        {
            "id": "cf804470-a87c-4791-8ecf-f723c187a22b",
            "number": 111222333,
            "cityCode": 261,
            "countryCode": "MDZ",
            "userId": "136845db-4887-47c8-a69f-94d05886375e"
        }
    ],
    "active": true
}
  ```
  
  ##### Obtener un usuario a través de su nombre de usuario
  ###### GET Request /users/{nombredeusuario}:
  ```
  http://localhost:8080/users/rodrigobenito
  ```
  ```
  header: token 
  value: el valor de token devuelto por el enpoint /login
   ```
  ###### Response si el token es valido:
  ```
{
    "id": "136845db-4887-47c8-a69f-94d05886375e",
    "username": "rodrigobenito",
    "email": "rodrigobenito@mail.com",
    "password": "a2asfGf6",
    "created": "2023-03-16",
    "lastLogin": "2023-03-16",
    "phones": [
        {
            "id": "cf804470-a87c-4791-8ecf-f723c187a22b",
            "number": 111222333,
            "cityCode": 261,
            "countryCode": "MDZ",
            "userId": "136845db-4887-47c8-a69f-94d05886375e"
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


### Base de Datos
Se utilizo una base de datos SQL embebida que provee Spring Boot la cual una vez iniciada la aplicación puede ser accedida a través de una navegador web 
en la siguiente URL: http://localhost:8080/h2-console.

### Ejecución de la aplicación

1. Iniciar una terminal
1. Clonar el presente repositorio
1. Navegar hasta la carpeta raiz
1. Ejecutar el siguiente código:  /.gradlew bootRun
1. Esto lanzará la tarea de correr el servicio para poder ser utilizado en la url http://localhost:8080
1. A través de un navegador ya podemos comenzar a utilizar los servicios propuestos anteriormente utilizando la URL: http://localhost:8090/

### Ejecución de unit test
1. En la carpeta raiz ejecutar el comando: ./gradlew build 
1. El proceso de construcción se encargará de ejecutar los test unitarios implementados


