## Веб-сервис для работы с учетными записями клиентов
### Технологии
* Java 11
* Spring Boot
* Lombok
* liquibase
* Postgresql 13
* JPA (Hibernate)
* Maven

### Требования
Необходимо разработать веб-сервис для работы с учетными записями клиентов со следующей функциональностью:
- Создание учетной записи
- Чтение учетной записи по id
- Поиск учетной записи по полям [фамилия, имя, отчество, телефон, емейл]. Поиск осуществляется только при условии
  указания хотябы 1 поля

### Запуск приложения
1) ```mvn clean install```
2) Поднять базу, запустить ```docker-compose up``` в корне приложения
3) Запустить приложение, как стандартный Spring boot проект и добавить env
```$xslt
PORT=8080;DATABASE_URL=localhost;DATABASE_PORT=5435;DATABASE_NAME=UserDB;DATABASE_USERNAME=postgres;DATABASE_PASSWORD=postgres
```
