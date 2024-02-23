# Liferay 7.4 DXP

Liferay DXP 7.4 running on docker compose.

## Requirements

* Blade (version >=4.1.2)
* Java JDK 8
* Docker and Docker Compose (CE edition) - version 24.0.2

## Database DUMP

Download the Database backup from: link_to_backup

Move the to the ```docker-compose/database-dump``` folder.

## Document Library

Download the document library from: link_to_document_library

Move to the ```bundles/data/document_library/``` folder.

## Deploy Modules and Themes

```
blade gw deploy
```

## Start the environment

```
docker compose up --build -d
```

## Get logs:

```
docker compose logs -f liferay
```

## Access

Access http://localhost:8080/ and login with [USER] and [PASSWORD]