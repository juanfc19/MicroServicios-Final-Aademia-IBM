# MicroServicios-Final-Aademia-IBM
Este repositorio contiene los microservicios para buscar una sucursal y obtener un perfil de tarjetas de credito de un cliente

## API'S

### Sucursales
El servicio de sucursales, tiene como objetivo proporcionar al cliente las sucursales más cercanas a su ubicación o a una busqueda en especifico, se debe consumir el servcio desde una api externa (https://www.banamex.com/localizador/jsonP/json5.json) la cual devolvera en un archivo json, todas las sucursales del pais.

El proyecto esta divido en seis paquetes:

- Modelo

Este paquete contiene los modelos de Sucursal, el cual se utilizara para mapear los objetos que se vayan obteniendo del json, SucursalRequest sera para obtener los datos del cuerpo de la petición, es por seguridad para no exponer, como esta formada mi clase principal que es Sucursal. La clase GPS es para obtener los datos del gps del usuario.

- Feign

Este paquete contiene una interfaz llamada ISucursalFeign, la cual esta anotada con el tipo @FeignClient, la cual sera la responsable de hacer la petición a la api y obtener la respuesta de la misma.

- Service

Este paquete contiene dos clases, la primera es una interfaz llamada ISucursalService, la cual sera el contrato que contiene todas las funciones que deberan utilizarce para la busqueda de sucursales. La clase SucursalServiceImpl esta anotada con @Service y es la encargada de llamar a nuestro feignCliente para recibir los datos de la api, ademas contiene toda la logica de negocio para realizar la busqueda de sucursales de acuerdo a la preferencia de los usuarios.

- Mapping

Este paquete contiene una clase llamada Mapping la cual sera la encargada de definir la constante para realizar el llamado a todos nuestros endpoints.

- Exception

Este paquete guarda todas nuestras excepciones personalizadas que podrán ejecutarse en un momento en especifico, ademas contiene la clase GlobalExceptionHandler, la cual esta anotada con @ControllerAdvice y sera la encargada de guardar y ejecutar las excepciones al momento de ser lanzadas. Existen 3 tipos de excepciones: SucursalNotFound, FormatInvalidCpException y EmptyException. Ademas se incluye una clase Response la cual sera nuestro estandar para realizar las respuestas de la api.

- Controller

Este paquete contiene una clase llamada SucursalRestController de tipo @RestController, la cual contiene todos nuestros endpoints de nuestro microservicio y en caso de existir algun error lanzara las excepciones correspondientes.

#### Inputs
El servicio recibira como entradas:
  - Un Estado y/o una colonia.
  - Código Postal
  - Coordenadas GPS (Latitud y Longitud)

#### Output
Cada endpoint tendra como salida la misma respuesta en formato JSON, contiene un **Estatus**, el cual informará al usuario acerca del estatus de la petición, ya sea si tuvo un error o la petición se cumplio con exíto. El parametro **Message** es el mensaje de respuesta sobre la petición. El parametro **DateTime** es la fecha y hora en la que se realiza la petición y el parametro **Data** contiene los datos de respuesta, en este caso regresa una lista de sucursales.

```
{
  "status": HttpStatus
  "message": String
  "dateTime": LocalDate
  "data": List<Sucursal>
}
```

#### EndPoints
Mi propuesta fue crear 3 endpoints diferentes con entradas diferentes:

- **Buscar por Estado y/o Colonia**

Este endpoint es de tipo POST, ya que una petición GET en producción no puede tener un cuerpo y es necesario hacer el endpoint de tipo POST, tiene como entrada unicamente el estado y la colonia, en caso de ser vacia la colonia se buscara por estado, si el estado es vacio, se lanzara una excepción indicado que existen campos vacios. Si el servicio no encontro sucursales, se lanzara un mensaje indicando al usuario que no fueron encontradas las sucursales, caso contrario se mostrara una respuesta con la lista de sucursales:

- POST

http://localhost:8888/api/sucursales/buscar-estado-colonia

- BODY
```
{
    "estado":"Puebla",
    "colonia":"Col. La Paz"
}
```

- **Buscar por Código Postal**

Este endpoint es de tipo GET, tiene como entrada un parametro llamado "cp", si el parametro es vacio se lanzara una excepción indicando que existen campos vacios. Si el cp es menor a 5 digitos o mayor a 5, el sistema lanzara una excepción indicando que el formato del Código Postal es invalido.Si el servicio no encontro sucursales, se lanzara un mensaje indicando al usuario que no fueron encontradas las sucursales. Si el servicio encuentra sucursales, se retornara la lista de sucursales que coincidan con los primeros 3 digitos del código postal, es decir si mi Código Postal es 72309 regresará los códigos postales que coincidan con 723.

- GET

http://localhost:8888/api/sucursales/buscar-cp?cp=72309

- PARAMS

cp: String
 
- **Buscar por GPS**

Este endpoint es de tipo POST, ya que recibira un cuerpo, tiene como entradas un json con lat y lng, si alguno de los campos es vacio, se lanzara una excepción indicando que existen campos vaciós, Si el servicio no encontro sucursales, se lanzara un mensaje indicando al usuario que no fueron encontradas las sucursales. Si el servicio encuentra sucursales retornara las sucursales más cercanas en un radio de 2 kilometros. Para este servicio se utilizo una función que toma los puntos del gps y calcula la distancia que existe entre el punto del gps del usuario y las coordenadas de la sucursal, si la distancia entre esos puntos es menor a 2 kilometros, se agrega a la lista de sucursales.

- POST

http://localhost:8888/api/sucursales/buscar-gps

- BODY
```
{
    "lat": "19.051712617126928", 
    "lng":"-98.18798501394821"
}
```

#### PRUEBA POSTMAN

### Perfiles de Tarjetas

El servicio Perfiles de Tarjetas tiene como objetivo obtener los tipos de tarjetas de credito de acuerdo al perfil proporcionado por el cliente, dicho perfil debe tener lo siguiente:

- **Pasiones:** Corresponde a lo que mas le gusta hacer al cliente.
- **Salario:** Corresponde al salario mensual del cliente y que no puede ser menor a 7000 pesos.
- **Edad:** Corresponde a la edad de cliente, la cual debe ser mayor o igual a 18 años.

El proyecto esta divido en seis paquetes:

- Modelo

Este paquete incluye el modelo Perfil, el cual se utilizara como respuesta de nuestro servicio, ademas se incluyen un modelo llamado PerfilRequest, el cual sera el encargado de recibir el body de nuestra consulta.

- DAO

Este paquete incluye nuestra intefaz llamada IPerfilDao y la clase PerfilDaoImpl la cual sera de tipo @Repository y contendra nuestro JDBCTemplate para realizar la consulta a nuestra base de datos.

- Mapping

Este paquete contiene una clase llamada Mapping la cual sera la encargada de definir la constante para realizar el llamado a todos nuestros endpoints.

- Exception

Este paquete guarda todas nuestras excepciones personalizadas que podrán ejecutarse en un momento en especifico, ademas contiene la clase GlobalExceptionHandler, la cual esta anotada con @ControllerAdvice y sera la encargada de guardar y ejecutar las excepciones al momento de ser lanzadas. Existen 4 tipos de excepciones: PerfilNotFoundException, EdadInvalidaException, EmptyException y SalarioInvalidoException. Ademas se incluye una clase Response la cual sera nuestro estandar para realizar las respuestas de la api.

- Service

Este paquete contiene dos clases, la primera es una interfaz llamada IPerfilService, la cual sera el contrato que contiene todas las funciones que deberan utilizarce para la obtener el peril del cliente. La clase PerfilServiceImpl esta anotada con @Service y es la encargada de comunicarse con nuestro dao para recibir la respuesta de nuestra base de datos.

- Controller

Este paquete contiene una clase llamada PerfilRestController de tipo @RestController, la cual contiene los endpoints de nuestro microservicio y en caso de existir algun error lanzara las excepciones correspondientes.

#### Inputs
El servicio recibira como entrada:

```
{
    "pasion":"Shopping",
    "salario":50000,
    "edad":23
}
```

#### Output
Cada endpoint tendra como salida la misma respuesta en formato JSON, contiene un **Estatus**, el cual informará al usuario acerca del estatus de la petición, ya sea si tuvo un error o la petición se cumplio con exíto. El parametro **Message** es el mensaje de respuesta sobre la petición. El parametro **DateTime** es la fecha y hora en la que se realiza la petición y el parametro **Data** contiene los datos de respuesta, en este caso regresa una lista de sucursales.

```
{
  "status": HttpStatus
  "message": String
  "dateTime": LocalDate
  "data": Perfil
}
```

#### EndPoints

Mi propuesta fue crear 1 endpoint, el cual recibira la pasion, el salario y la edad, en caso de que la pasion sea vacia, se lanzara una excepción, si el salario es menor a $7000 se lanzara una excepción indicando que para obtener una tarjeta de credito el salario debe ser mayor o igual a $7000, si la edad es menor a 18 años se lanzara una excepción indicando que el cliente debe ser mayor para obtener una tarjeta de credito. 

**Solución**

Para plantear la solución, se desarrollo una base de datos que previamente fue normalizada, la base fue cargada en H2 y se crearon los archivos schema.sql y data.sql.

Se utilizo una base de datos para guardar los perfiles, porque gracias a utilizarlo de esta forma lo podemos hacer escalable en caso de que en algun futuro se deseen agregar mas perfiles se pueden agregar sin ningun problema. Otro punto importante es la rapidez con la que podemos obtener un perfil de tarjetas de credito, esto es gracias a que eixiste un query que es el encargado de obtener las tarjetas de credito de acuerdo a las entradas establecidas, con ello nos ahorramos en escribir demasiado código en nuestro microservicio, haciendo que se vea mas limpio y mejorando considerablemente el rendimiento gracias a la rapidez del query.

- POST

http://localhost:8090/api/perfiles/obtener-perfil

- BODY

```
{
    "pasion":"Shopping",
    "salario":50000,
    "edad":27
}
```

#### PRUEBA POSTMAN


### Eureka Server 

Nos permite crear un servidor en Eureka donde podremos registrar nuestros microservicios y hacer que se conozcan entre ellos, se registraron los dos microservicios creados en el proyecto; SUCURSALES-SERVICE y PERFILES-SERVICE

![Eureka](https://user-images.githubusercontent.com/49379405/137525303-854add52-ae45-44fc-809e-49f24fc2c070.PNG)


