# Architecture

## Architecture Style
Modular microservice foundation with strict data ownership and contract-based integration.

## Services
- `employee-service`: employee master data
- `management-service`: manager assignment and onboarding workflow
- `leave-service`: leave policy, balance, request, and leave ledger

## Core Rules
1. Each service owns its own database schema.
2. Cross-service relations are identifier-based (`employeeId`, `managerId`) only.
3. APIs expose DTOs, never JPA entities.
4. No direct repository/database access across services.

## Current Communication Strategy
- Implemented now: local REST APIs per service.
- Designed for later: service-to-service sync calls for immediate validation (employee status, manager assignment).
- Designed for later: async events for notifications, reporting, audit, projections.

## Leave Request Flow (Current Business Foundation)
1. Request received in `leave-service`.
2. Validate date range and requested days.
3. Validate leave type active.
4. Reject overlaps with existing pending/approved leaves.
5. Reserve balance (`pendingDays` increment).
6. Approve/reject/cancel transitions update balance and ledger.

## Concurrency and Consistency
- `@Version` on key mutable entities (`Employee`, `ManagementAssignment`, `LeaveBalance`, `LeaveRequest`).
- Leave ledger records every balance-changing action.
- Designed to evolve to outbox + idempotent consumers later.

## Non-Goals in Current Scope
- No Feign/WebClient implementation yet.
- No Kafka/RabbitMQ setup yet.
- No service discovery, API gateway, config server, or Kubernetes wiring yet.

