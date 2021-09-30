# SpringRestDemo

Just a little demo project to show how to secure a spring boot rest api via basic authentication via jws.

## How to access via jws?

Get request on localhost:8080/protected/api/employees will result in a forbidden error, because spring security is configurate to protect everything under /protected/api/*.

To get access the rest services the user has to signup and signin.

So first call auth endpoint to signup. To signup do a post request on 
localhost:8080/auth/signup
with payload
```
{
    "username": "dtonal",
    "password": "dtonalPass"
}
```
The service returns something like 
```
{
    "message": "User registered successfully!"
}
```

Then the user has to signin with his credentials. To do so do a post request on
localhost:8080/auth/signin
with the same payload 
```
{
    "username": "dtonal",
    "password": "dtonalPass"
}
```

On success the response contains the accessToken.

```
{
    "username": "dtonallll",
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkdG9uYWxsbGwiLCJpYXQiOjE2MzMwMjI4MzQsImV4cCI6MTYzMzEwOTIzNH0.D4AG3QBQV5DfNXCNfJHq30lNHzFPaOINyYQvUXYz0Snc4RJ0vUfKU8AWcIEi2pE_X5P0vXtj53kyfKUVTiZHiw"
}
```

The accessToken must than be set on the api calls for ever service under protected/api/ like localhost:8080/protected/api/employees.