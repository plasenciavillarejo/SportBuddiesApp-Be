<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Spring Authorization Server sample</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" 
    integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="/assets/css/signin.css" th:href="@{/assets/css/signin.css}" />
</head>
<body>
<div class="container">
    <form class="form-signin w-100 m-auto" method="post" th:action="@{/login}">
        <div th:if="${param.error}" class="alert alert-danger" role="alert">
            Credenciales erróneas.
        </div>
        <div th:if="${param.logout}" class="alert alert-success" role="alert">
            Has cerrar la sesíon con éxito.
        </div>
        <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
        <div class="form-floating">
            <input type="text" id="username" name="username" class="form-control" required autofocus>
            <label for="username">Nombre Usuario</label>
        </div>
        <div class="form-floating">
            <input type="password" id="password" name="password" class="form-control" required>
            <label for="password">Contraseña</label>
        </div>
        <div>
            
            <button class="w-100 btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            <a class="w-100 btn btn-light btn-block bg-white" href="/oauth2/authorization/google" role="link" style="margin-top: 10px">
                <img th:src="@{/img/google_icon.png}" width="20" style="margin-right: 5px;" alt="Sign in with Google">
                Sign in with Google
            </a>
            <!--  
            <a class="w-100 btn btn-light btn-block bg-white" href="/oauth2/authorization/github-idp" role="link" style="margin-top: 10px">
                <img src="/assets/img/github.png" th:src="@{/assets/img/github.png}" width="24" style="margin-right: 5px;" alt="Sign in with Github">
                Sign in with Github
            </a>-->
        </div>
    </form>
</div>
</body>
</html>