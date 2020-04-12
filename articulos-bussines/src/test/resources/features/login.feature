Feature: Funcionalidad de Login

    Scenario: Login contra microservicio
        Given realizamos el ingreso con el sigiente usurio
        | usuario             | contrasenia |
        | jnsierrac@gmail.com | 1234        |
        When envio la informacion al servicio de login
        Then debo tener un token de autenticacion "Bearer "
