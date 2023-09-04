# Cognixus To-Do app

Simple to-do list API developed using Java with Spring Boot.

# Building the app
Prerequisite:
- Java JDK  17
- Maven 3.8 ++
- MariaDB (if not build with Docker)
- Docker

We can build the app using following methods:
1. Maven + Java
2. Docker

## Maven + Java

*This method requires you to have MariaDB running locally. Update the application.yml if you want to connect to remote database.*

To build the JAR file, we can run following command:

    mvn package install
Locate the JAR file in folder target, then run using this command:

    java -jar <jar-file-name>.jar

## Docker

Build the multi-image container that contains both Spring boot app and MariaDB. Run the following commands:

    docker compose -f docker-compose.yml up

# Testing the API
Successful build of this app will run locally on port 8080. We can test the following APIs:
- Create To-Do
- List all To-Dos
- Update To-Do
- Delete To-Do

These APIs require user authentication, see Authentication below.

## Authentication
You must perform authentication before sending request to certain APIs. Successful authentication will generated a JWT token to be use in header request later. This time, we will use Google as our authentication provider. 

1. Open any browser and go to http://localhost:8080/login
2. You will see a OAuth2 Login with Google as login option.
3. Click Google and provide your Google login credentials.
4. Successful login will redirect you to http://localhost:8080/auth/token?token=...

    {	"token": "eyJhbGciOiJIUzI1NiJ9...."	}

6. Copy the token response and include it on the header request.
## Create To-Do

You can create a To-Do detail by sending POST request using curl as below:

> `
curl -d '{"title":"value"}' -X POST http://localhost:8080/todo/detail
`

API will return To-Do detail with generated id and completed status as false (represents wether the to-do is done or not)

    {
	    "id": 1,
	    "title": "value",
	    "completed": false
    }

## List all To-Dos

You can list all saved To-Do details by sending GET request using curl as below:

> `
curl -H "Authorization: Bearer <jwt-token>" http://localhost:8080/todo/list
`

API will return list of all To-Do detail as below:

    [
	    {
	        "id": 1,
	        "title": "test",
	        "completed": false
	    },
	    {
	        "id": 2,
	        "title": "test",
	        "completed": false
	    },
	    {
	        "id": 3,
	        "title": "test",
	        "completed": false
	    },...
    ]


## Update To-Do

Updating To-Do simply means mark as completed and vice versa. You can send PUT request using curl as below:

> `
curl -d '{"id":1, "completed":true}' -H "Authorization: Bearer <jwt-token>" -X PUT http://localhost:8080/todo/detail
`

API will return updated To-Do detail.

    {
	    "id": 1,
	    "title": "value",
	    "completed": true
    }


## Delete To-Do

You can send DELETE request using curl as below:

> `
curl -H "Authorization: Bearer <jwt-token>" -X DELETE http://localhost:8080/todo/detail/{insert To-Do id}
`

API will return deleted To-Do detail.

    {
	    "id": 1,
	    "title": "value",
	    "completed": true
    }

# Environment Variables
Please set environment variables below before running the app:
- DB_URL
- DB_PASSWORD
- DB_USERNAME
- GOOGLE_CLIENT_ID
- GOOGLE_CLIENT_SECRET
- JWT_ISSUER
- JWT_SIGNKEY

These variables can be found at **application.yml** and **docker-compose.yml**. You can either hardcode the value in the mentioned files or set the environment locally.

# OpenAPI

You can import JSON/YML OpenAPI definition at http://localhost:8080/swagger-ui/index.html

# Docker Image
You can find the published Docker image [here](https://hub.docker.com/r/h4mizan/cognixus)

<br><br>
@author: hamizannordin