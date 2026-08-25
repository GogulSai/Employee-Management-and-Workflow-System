# Roadmap

## Phase 1 (Current)
- Entity-first foundations
- DTO contracts and validation
- Service-local exception handling
- Isolated H2 databases

## Phase 2
- Synchronous service calls (`leave-service` -> `employee-service` and `management-service`)
- Feign or WebClient
- Resilience4j timeout/retry/circuit-breaker

## Phase 3
- Service discovery
- API Gateway
- Centralized configuration
- Security (JWT/OAuth2)

## Phase 4
- Event bus (Kafka/RabbitMQ)
- Outbox pattern
- Idempotent consumers and DLQ

## Phase 5
- PostgreSQL/MySQL per service
- Flyway/Liquibase
- Targeted caching only for real use-cases
- Centralized logging, metrics, tracing

## Phase 6
- Dockerfile per service
- Docker Compose
- Health checks and environment config profiles

## Phase 7
- Kubernetes manifests (Deployment, Service, Ingress)
- ConfigMap, Secret
- Readiness/liveness probes
- HPA and resources

## Phase 8
- CI/CD (build, test, scan, deploy)
- Dependency/security scanning
- Container image scanning
- Kubernetes deployment automation

