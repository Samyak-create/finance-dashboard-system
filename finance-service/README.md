# Finance Service

This project is fully dockerized and ready to be run in a container environment.

## Prerequisites

Before getting started, make sure you have the following installed on your machine:
- **Java 21**
- **Docker** (and Docker Desktop if you are on Windows/Mac)
- **MySQL** running locally on port `3306` with a database named `finance_db` (using username `root` and password `54321`)

---

## How to Run

### 1. Build the Docker Image
The compiled Java `.jar` file is already included in the `target/` folder in this repository. You do not need to compile the code yourself! 

Simply open your terminal in the directory and run:
```bash
docker build -t finance-service:latest .
```

### 2. Run the Docker Container
Finally, start the container. The application is pre-configured to look for your host machine's MySQL database (using `host.docker.internal`). 

Run the following command to start the app in the background on port `8080`:

```bash
docker run -d -p 8080:8080 --name finance-service finance-service:latest
```

Your service will now be running and accessible at `http://localhost:8080/`.

---

### Useful Commands

If you ever need to stop or restart the application, use these simple Docker commands:

- **View application logs:** `docker logs -f finance-service`
- **Stop the application:** `docker stop finance-service`
- **Start it back up:** `docker start finance-service`
- **Delete the container (to re-run fresh):** `docker rm -f finance-service`
