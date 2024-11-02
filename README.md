# Тестовое задание Telros на создание Account сервиса OAuth2.0

## Описание проекта

В данном проекте реализован REST API на языке Java с использованием фреймворка Spring Boot. Основная цель проекта — управление пользователями и их контактной информацией, а также возможность загрузки фотографий пользователей. Проект использует PostgreSQL в качестве СУБД и реализует авторизацию с помощью JWT токенов OAuth2.0 и Keycloak.

В проекте используется **Spring Cloud** для реализации микросервисной архитектуры, включая следующие компоненты:
- **spring-cloud-starter-openfeign**: Для упрощения работы с REST клиентами. Позволяет легко вызывать другие микросервисы с помощью аннотаций.
- **Eureka**: Для регистрации и обнаружения микросервисов. Позволяет сервисам находить друг друга без необходимости жесткого указания адресов.
- **Spring Cloud Gateway**: Для маршрутизации и фильтрации запросов к микросервисам. Обеспечивает единый входной пункт для клиентов и управление маршрутизацией запросов.
- **CircuitBreaker**: Для повышения надежности микросервисов. Позволяет обрабатывать ошибки и предотвращать дальнейшие вызовы к неработающим сервисам, улучшая устойчивость системы.

## Используемые технологии

- **Spring Boot**: Основной фреймворк для создания REST приложений.
- **Spring Data JPA**: Для взаимодействия с базой данных и реализации CRUD операций.
- **Spring Security**: Для обеспечения безопасности приложения и авторизации пользователей.
- **Keycloak**: Решение для управления идентификацией и доступом, использованное для авторизации по логину и паролю.
- **JWT OAuth2.0**: Авторизацию с помощью JWT токенов OAuth2.0
- **PostgreSQL**: СУБД, используемая для хранения данных о пользователях.
- **Docker**: Для контейнеризации приложения и его зависимостей.
- **Lombok**: Для упрощения кода сущностей и уменьшения количества шаблонного кода.
- **ModelMapper**: Для упрощения маппинга между сущностями и DTO.
- **Mockito и Spring Security Test**: Для написания автотестов и тестирования безопасности.

## Авторизация
Для доступа к API необходимо выполнить авторизацию через Keycloak. 
Используйте логин и пароль `admin:admin` для получения токена доступа. 
Для доступа к сервису Keycloak используйте следующие учетные данные `keycloak:123`.

## CRUD операции для пользователей

- **Создать пользователя**: `POST /api/users`
- **Получить всех пользователей**: `GET /api/users`
- **Получить пользователя по ID**: `GET /api/users/{id}`
- **Обновить пользователя**: `PUT /api/users/{id}`
- **Удалить пользователя**: `DELETE /api/users/{id}`

## CRUD операции для фотографий

- **Загрузить фотографию**: `POST /api/users/{id}/photo`
- **Получить фотографию**: `GET /api/users/{id}/photo`

## Запуск контейнеров

Файл `docker-compose.yml` для запуска необходимых сервисов:

```yaml
version: '3.8'

services:
  keycloak:
    image: quay.io/keycloak/keycloak:latest
    container_name: keycloak
    environment:
      KEYCLOAK_ADMIN: keycloak
      KEYCLOAK_ADMIN_PASSWORD: 123
    command: ["start-dev"]
    ports:
      - "8079:8080"

  postgres:
    image: postgres:latest
    container_name: postgres
    environment:
      POSTGRES_DB: postgres_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 123
    ports:
      - "15432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/telros_db

volumes:
  postgres_data:
