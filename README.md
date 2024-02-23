# Liferay 6.2 portal

Liferay portal 6.2-EE running on docker compose.

## Database DUMP

Download the Database backup from: link_to_backup

Move the to the ```database-dump/``` folder.

# Document Library

Download the document library from: link_to_document_library

Move to the ```liferay-document-library/``` folder.

# Start up

Get a license for 6.2-EE and put into the ```liferay-files/deploy``` folder. Then run:

```
docker compose up --build -d
```

# Logs

```
docker compose logs -f liferay
```