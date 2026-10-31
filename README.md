# turbophotoshop

Spring Boot REST API (Java 9) for simple image processing. Uploaded images are kept in memory and identified by a generated ID. Results are returned as PNG or JSON.

## Endpoints
- `POST /image/add`: upload an image (request body), returns its ID
- `GET /image/{id}/size`: width and height
- `GET /image/{id}/histogram`: 256-bin grayscale histogram
- `GET /image/{id}/crop?start=&stop=&width=&height=`: cropped PNG
- `DELETE /image/{id}`: remove the image

## Run
```bash
./mvnw spring-boot:run   # port 80 (server.port in application.properties)
```

## Structure
```
src/main/java/com/tps/turbophotoshop/web/Controller.java              REST endpoints
src/main/java/com/tps/turbophotoshop/web/ImageProcessorController.java image store and processing
src/main/java/com/tps/turbophotoshop/web/CustomErrorController.java    error responses
```
