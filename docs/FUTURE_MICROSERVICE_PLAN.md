# Future Microservice Plan

## Immediate Next Increments
1. Add integration tests for leave workflows:
   - insufficient balance
   - overlapping requests
   - approve/reject/cancel transitions
   - optimistic lock conflict
2. Introduce remote client interfaces in `leave-service` for employee summary and manager assignment lookup.
3. Add standardized response envelope for management and leave services if desired.

## Sync Communication Plan
- `leave-service` calls `employee-service` for employee status/summary.
- `leave-service` calls `management-service` for manager assignment.
- Add timeout, retry, and circuit breaker around remote calls.

## Async Communication Plan
- Publish leave and employee events.
- Add outbox table and relay job.
- Consumer-side idempotency keys and DLQ policies.

## Data/Platform Plan
- Replace H2 with PostgreSQL/MySQL per service.
- Add schema migrations and rollback strategy.
- Introduce centralized observability (logs, metrics, traces).

## Security Plan
- JWT-based auth at gateway.
- Service identities for internal communication.
- Role-based controls for approval operations.

## Operational Plan
- Dockerize each service.
- Compose for local multi-service runs.
- Kubernetes rollout with health probes and autoscaling.
- CI/CD pipelines with quality and security gates.

## Documentation Governance
For every PR touching `controller`, `dto`, `entity/model`, `enums`, `event`, or workflow `service` methods:
- update affected docs in `/docs`
- include doc updates in PR checklist
- reject PR if contracts changed without docs

