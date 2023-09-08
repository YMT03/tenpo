# Tenpo Challenge


## Índice
1. [Introducción](#introducción)
2. [Requerimientos Challenge](#requerimientos-challenge)
3. [Instrucciones](#instrucciones)
4. [Endpoints](#endpoints)
5. [Formato de Error](#formato-de-error)
6. [Aclaraciones](#aclaraciones)




## Introducción


La presente API fue realizada a partir de un Challenge.
Para ello se hizo uso de las siguientes tecnologías:
- Java 20
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Cache
- Spring Retry
- Redis
- Postgres
- Bucket4J
- Docker




## Requerimientos Challenge
Debes desarrollar una API REST en Spring Boot utilizando java 11 o superior, con las siguientes funcionalidades:
Debe contener un servicio llamado por api-rest que reciba 2 números, los sume, y le aplique una suba de un porcentaje que debe ser adquirido de un servicio externo (por ejemplo, si el servicio recibe 5 y 5 como valores, y el porcentaje devuelto por el servicio externo es 10, entonces (5 + 5) + 10% = 11).


Se deben tener en cuenta las siguientes consideraciones:
- El servicio externo puede ser un mock, tiene que devolver el % sumado. Dado que ese % varía poco, podemos considerar que el valor que devuelve ese servicio no va cambiar por 30 minutos.
- Si el servicio externo falla, se debe devolver el último valor retornado. Si no hay valor, debe retornar un error la api.
- Si el servicio falla, se puede reintentar hasta 3 veces.
- Historial de todos los llamados a todos los endpoint junto con la respuesta en caso de haber sido exitoso. Responder en Json, con data paginada. El guardado del historial de llamadas no debe sumar tiempo al servicio invocado, y en caso de falla, no debe impactar el llamado al servicio principal.
- La api soporta recibir como máximo 3 rpm (request / minuto), en caso de superar ese umbral, debe retornar un error con el código http y mensaje adecuado.
- El historial se debe almacenar en una database PostgreSQL.
- Incluir errores http. Mensajes y descripciones para la serie 4XX.
- Se deben incluir tests unitarios.
- Esta API debe ser desplegada en un docker container. Este docker puede estar en un dockerhub público. La base de datos también debe correr en un contenedor docker. Recomendación usar docker compose
- Debes agregar un Postman Collection o Swagger para que probemos tu API
- Tu código debe estar disponible en un repositorio público, junto con las instrucciones de cómo desplegar el servicio y cómo utilizarlo.
- Tener en cuenta que la aplicación funcionará de la forma de un sistema distribuido donde puede existir más de una réplica del servicio funcionando en paralelo.




## Instrucciones
Para poder ejecutar la aplicación es necesario tener instalado:
- Postman o similar (Se recomienda Postman porque se brinda collection).
- Docker y Docker Compose.
- Git (En caso de querer hacer git clone)




### Ejecutar con docker compose


Para correr la aplicación se puede hacer uso de docker compose para poder levantar tanto la aplicación como los servicios que esta requiere (Redis y Postgres).
Esto se puede hacer tanto clonando el repositorio de git, como simplemente copiando o descargando el docker-compose.yml.


1. Clonar el repository. (Opcional)


```
git clone git@github.com:YMT03/tenpo.git
```
* Otra opción es simplemente descargar el [docker-compose.yml](https://github.com/YMT03/tenpo/blob/master/docker-compose.yml).
2. Ejecutar docker compose
```
docker compose up
```
* En la base del proyecto o donde se encuentre el docker compose file.


### Realizar pruebas
Para realizar pruebas, en caso de utilizar Postman, se brinda la siguiente [collection](https://github.com/YMT03/tenpo/blob/master/src/main/resources/postman/tenpo.postman_collection).


## Endpoints


### 1. POST /api/calculator


Endpoint principal para realizar el cálculo.

Se realizan validaciones sobre el body. Se espera body con los campos numericos mostrados en el ejemplo, pueden estar en formato string.
En caso de no cumplir con la validacion se retornara el error correspondiente.
###### Ejemplo de payload request

```
{
    "number_one": 5,
    "number_two": 5
}
```


###### Ejemplo de payload response
```
{
    "result": 11.00
}
```

Ejemplo de Error.

###### Ejemplo de payload con error

```
{
    "number_one": "5",
    "number_two": "5hithere"
}
```

###### Ejemplo de payload error response
```
{
    "id": "bad_request",
    "title": "Bad Request",
    "description": "JSON parse error: Cannot deserialize value of type `java.math.BigDecimal` from String \"5hithere\": not a valid representation",
    "http_status": 400,
    "date": "2023-09-08T16:21:20.393107531Z"
}
```


### 2. GET /internal/traces


Endpoint de uso interno para obtener los llamados a la api.
Se devuelve la paginación.


Query Params
```
page: int - default: 0
size: int - default: 50
```

###### Ejemplo con query params
```
GET /internal/traces?page=5&size=1
```

###### Ejemplo de payload response


```
{
    "objects": [
        {
            "id": "1bafb5ea-c05a-4f41-bd77-fb07f9578701",
            "method": "POST",
            "uri": "/api/calculator",
            "query_string": null,
            "headers": "content-type:application/json;user-agent:PostmanRuntime/7.32.3;accept:*/*;postman-token:2e3ee80a-7502-4618-8da8-77c17461879c;host:localhost:8080;accept-encoding:gzip, deflate, br;connection:keep-alive;content-length:47;",
            "request": "{ \"number_one\": 5, \"number_two\": 5 }",
            "response": "{\"result\":11.00}",
            "http_status": 200,
            "date": "2023-09-08T15:47:48.419249Z"
        }
    ],
    "current_page": 5,
    "page_size": 1,
    "total_pages": 6,
    "total_elements": 6
}
```






## Formato de Error


Todos los errores devueltos por la API tienen el siguiente formato:
```
id : string
title : string
description : string
http_status : int
date : date
```

Ejemplos de formato

```
{
    "id": "no_mapping_found",
    "title": "No mapping found",
    "description": "No endpoint POST /api/non-existing-endpoint.",
    "http_status": 404,
    "date": "2023-09-08T15:53:12.473650757Z"
}
```






## Aclaraciones


- Para el grabado de trazas de api calls se decidió solo grabar aquellas provenientes de /api/*.
- Para la simulación de la aplicación, se utiliza el profile dev para que se utilice un bean mock en vez del percentage rest service real "productivo".
- Para simular los "errores externos" el mock utiliza una property de fail-probability, la cual está setteada en 50% para forzar algunos retries y algunos errores.
- Para que se pueda visualizar tanto el uso de la cache como los Retries se deja un log info para ver el detalle en la consola de la app.
- Se realizó un pequeño alta de uso de RestService con RestTemplate y un manejo de errores a apis externas genérico aunque no se vaya a utilizar debido al mock.
- Como espacio de mejora se podrian sumar tests de integracion y reemplazar algunas de las abstracciones del propio uso de annotations o configs de Spring para tener un poco mas de control.








