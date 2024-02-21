start cmd /k java -jar target/reportingApplication-0.0.1-SNAPSHOT.jar
timeout /t 10 >nul
start "" "http://localhost:8080"
