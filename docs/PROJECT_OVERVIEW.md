# Project Overview

## Project
Employee Management & Workflow System

## Objective
Build an interview-friendly but production-evolvable microservice foundation with strong domain boundaries first, then communication, resilience, security, and platform capabilities.

## Current Modules
- `employee-service`
- `management-service`
- `leave-service`

## Technology Baseline
- Java 17
- Spring Boot 3.5.3
- Spring Web, Spring Data JPA, Validation
- H2 (per-service, isolated in-memory DB for development)
- Lombok

## What This Repository Prioritizes Now
- Domain-first entities
- DTO contracts for API boundaries
- Validation and exception handling
- Optimistic locking where concurrent updates matter
- No cross-service JPA relationships

## Documentation Map
- `docs/ARCHITECTURE.md`
- `docs/SERVICE_BOUNDARY.md`
- `docs/ENTITY_MODEL.md`
- `docs/API_CATALOG.md`
- `docs/EVENT_CATALOG.md`
- `docs/DATABASE_DESIGN.md`
- `docs/SAMPLE_PAYLOADS.md`
- `docs/ROADMAP.md`
- `docs/INTERVIEW_STORY.md`
- `docs/FUTURE_MICROSERVICE_PLAN.md`

## Documentation Update Policy (Mandatory)
Update docs in the same change whenever any of the following changes:
- Entity fields, constraints, indexes, lifecycle states
- Enums
- DTO request/response contracts
- Endpoints, paths, methods, response codes
- Workflow rules (apply/approve/reject/cancel etc.)
- Published event schemas

## Minimum Update Matrix
- Entity/constraint change -> `ENTITY_MODEL.md`, `DATABASE_DESIGN.md`, `SAMPLE_PAYLOADS.md`
- Endpoint/DTO change -> `API_CATALOG.md`, `SAMPLE_PAYLOADS.md`, `SERVICE_BOUNDARY.md`
- Workflow/status transition change -> `ARCHITECTURE.md`, `EVENT_CATALOG.md`, `INTERVIEW_STORY.md`
- Roadmap/phase change -> `ROADMAP.md`, `FUTURE_MICROSERVICE_PLAN.md`

