# Employee Management & Workflow System

This repository contains a microservice-oriented learning foundation with incremental architecture evolution.

## Modules

- `employee-service` - employee master data ownership
- `management-service` - manager assignments and onboarding workflow ownership
- `leave-service` - leave types, balances, requests, and leave ledger ownership

## Domain Ownership Rules

- Each service owns its own entities and database schema.
- Cross-service references are stored as IDs (`employeeId`, `managerId`) and never as JPA relationships.
- DTOs are exposed from controllers; entities remain internal.
- Duplicate and concurrent update protections are handled with unique constraints and optimistic locking.

## Current Entity Foundation

### Employee Service

- `Employee` with unique `employeeCode` and unique `email`
- Status and type controlled by enums (`EmployeeStatus`, `EmploymentType`)
- Audit fields and `@Version` retained for concurrency

### Management Service

- Existing `ManagerDetails` kept for practice use-cases
- `ManagementAssignment` for reporting/manager ownership configuration
- `OnboardingRequest` for onboarding workflow state

### Leave Service

- `LeaveType` for policy metadata
- `LeaveBalance` for per employee + leave type + year tracking
- `LeaveRequest` for leave lifecycle
- `LeaveBalanceTransaction` as an immutable-style ledger record

## Planned API Direction

The project now includes foundational endpoints aligned with `/api/v1` contracts for future inter-service communication. HTTP clients (Feign/WebClient), service discovery, API gateway, and messaging are intentionally deferred.

## Roadmap

1. **Phase 1**: Strong entities, DTOs, validations, CRUD, isolated H2 DB per service
2. **Phase 2**: Synchronous service calls, resilience policies
3. **Phase 3**: Discovery, gateway, centralized config, security
4. **Phase 4**: Events, outbox, idempotent consumers, DLQ
5. **Phase 5**: Production DBs, migrations, caching where needed
6. **Phase 6**: Docker and Compose
7. **Phase 7**: Kubernetes manifests and runtime probes
8. **Phase 8**: CI/CD, scanning, and deployment automation

