# APIRest Spring Boot - Administración de Clientes

Este proyecto es una API REST desarrollada con Spring Boot que proporciona servicios para la aplicación de administración de clientes. La API permite gestionar información de clientes, incluyendo la creación, edición, eliminación y búsqueda de clientes.

## Descripción General

La API REST ofrece los siguientes servicios:

- Listado de clientes
- Búsqueda de clientes
- Creación de clientes
- Edición de clientes
- Eliminación de clientes

## Requisitos Previos

Antes de empezar, asegúrate de tener instalados los siguientes requisitos en tu máquina:

- Java Development Kit (JDK) 17
- Maven
- Git

## Paso a Paso para Clonar e Instalar

Sigue estos pasos para clonar e instalar el proyecto en tu máquina local:

1. **Clonar el Repositorio**

   Abre una terminal y ejecuta el siguiente comando para clonar el repositorio:

   ```bash
   git clone https://github.com/jrgeorge89/CustomerAdminSpring.git
   ```

   Luego, navega al directorio del proyecto:

   ```bash
   cd nombre-del-repositorio-backend
   ```


2. **Configurar la Base de Datos**

   La BD que requiere se configurara como base de datos para la API será la Dependencia H2 DataBase que permitirá guardar en memoria sin necesidad de instalar y conectar BD:

   ```bash
      http://localhost:8080/h2-console/
   ```

   Actualiza el archivo application.properties en el directorio src/main/resources/ con los detalles de la conexión a la base de datos:

   ```bash
    # swagger-ui custom path
    springdoc.swagger-ui.path=/swagger-ui.html
    # Disabling the swagger-ui
    springdoc.swagger-ui.enabled=true
    #server.port=8083

    spring.application.name=customer

    # H2 Database Configuration
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    spring.h2.console.enabled=true
    spring.jpa.show-sql=true
    spring.jpa.hibernate.ddl-auto=update
   ```

3. **Instalar Dependencias**

   Instala las dependencias necesarias ejecutando el siguiente comando:

   ```bash
   mvn clean install
   ```

4. **Ejecutar la Aplicación**

   Una vez instaladas las dependencias y configurada la base de datos, puedes ejecutar la aplicación con el siguiente comando:

   ```bash
   mvn spring-boot:run
   ```
    La API estará disponible en http://localhost:8080.


## Funcionalidades de la Aplicación

1. **Listado de Clientes**

   - **Endpoint:**: GET /api/customers
   - **Descripción:**: Recupera la lista de todos los clientes.
   
2. **Búsqueda de Clientes**

   - **Búsqueda por ID:**: Permite buscar clientes por "Id".
     - **Endpoint**: GET /api/customers/{id}
   - **Búsqueda Básica:**: Permite buscar clientes por "Shared Key".
     - **Endpoint**: GET /api/customers/search?userName=jrincon
   - **Búsqueda Avanzada:**: Permite buscar clientes utilizando múltiples criterios.
     - **Endpoint**: POST /api/customers/advanced-search?name=Jorge Rincon&phone=6666666
     - **Cuerpo de la Solicitud:**:
     
        ```bash
        {
            "userName": "sharedKey",
            "name": "businessID",
            "phone": "phone",
            "email": "email",
            "startDate": "yyyy-MM-dd",
            "endDate": "yyyy-MM-dd"
        }
        ```

3. **Creación de Clientes**

   - **Endpoint:**: POST /api/customers
   - **Descripción:**: Crea un nuevo cliente.
   - **Cuerpo de la Solicitud::**:
     
        ```bash
        {
            "name": "John Doe",
            "phone": "123456789",
            "email": "johndoe@example.com",
            "startDate": "2023-01-01",
            "endDate": "2023-12-31"
        }
        ```

4. **Edición de Clientes**

   - **Endpoint:**: PUT /api/customers/{id}
   - **Descripción:**: Actualiza la información de un cliente existente.
   - **Cuerpo de la Solicitud::**:
     
        ```bash
        {
            "name": "John Doe",
            "phone": "987654321",
            "email": "johndoe_new@example.com",
            "startDate": "2023-01-01",
            "endDate": "2023-12-31"
        }
        ```

5. **Eliminación de Clientes**

   - **Endpoint:**: DELETE /api/customers/{id}
   - **Descripción:**: Elimina un cliente.


## Instalación de Dependencias Adicionales

   Las dependencias necesarias están especificadas en el archivo pom.xml. Si necesitas agregar dependencias adicionales, asegúrate de incluirlas en este archivo y luego ejecutar mvn clean install.


## Funcionalidades Implementadas y Plus+

   - Este proyecto incluye las pruebas Unitarias con JUnit (Pruebas TDD).
   - Plus: Editor para diseñar su documentación de APIs (Swagger-UI):
   
   ```bash
   http://localhost:8080/swagger-ui/
   ```


¡Gracias por utilizar nuestra API REST para la administración de clientes! 😊🚀
