# spring3-java17-cucumber-testcontainers
sample of BBD tests using spring-3, java-17, cucumber and testcontainers (postgresql)

## run all tests from cli or your IDE
- create .env.local file in ./local directory
```bash
$ cp local/.env.local.template local/.env.local
```
- edit your .env.local file
```bash
$ mvn clean install -U
```
