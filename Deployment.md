# Team12 Backend — Class Method Deployment

**Stack:** Spring Boot 3.2 (Java 21), MySQL 8 (AWS RDS), Docker, Docker Hub, EC2 (Amazon Linux 2023)
**Region:** us-east-2 (Ohio)

## Local (verified)
- `docker compose -f compose.app.yml up --build`
- App on http://localhost:8080 using local MySQL (host.docker.internal:3307)

## Image Build + Push
- Build: `docker build -f dockerfile -t team12/airport-backend:dev .`
- Tag: `docker tag team12/airport-backend:dev chrismorrison101/airport-backend:latest`
- Push: `docker push chrismorrison101/airport-backend:latest`

## RDS
- Engine: MySQL 8, Free tier
- DB name: `flight_management`
- User: `airport_admin`
- Password: supplied via env var at runtime (not committed)
- SG inbound: MySQL 3306 from EC2 private IP (172.31.32.52/32)

## EC2
- Amazon Linux 2023, t3.micro
- SG inbound: HTTP 80 from 0.0.0.0/0
- Install Docker:
  `sudo dnf install -y docker && sudo systemctl enable --now docker && sudo usermod -aG docker ec2-user`
- Run:

docker run -d --name airport-backend
--restart unless-stopped
-p 80:8080
-e SPRING_PROFILES_ACTIVE=local
-e DB_HOST=team12-flight-mgmt.cfaiwoua8oyz.us-east-2.rds.amazonaws.com
-e DB_PORT=3306
-e DB_NAME=flight_management
-e DB_USER=airport_admin
-e DB_PASSWORD='***'
-e ALLOWED_ORIGINS='http://localhost:5137'
chrismorrison101/airport-backend:latest


## Verify
- EC2 shell: `curl -i http://localhost/manage/airports` → 200 + JSON
- Laptop: `http://<EC2_PUBLIC_IP>/manage/airports` → 200 + JSON

## Notes
- Tests use H2; seeding disabled under `test` profile
- All secrets via env vars; no hard-coded creds
- Hibernate auto-selects MySQL dialect