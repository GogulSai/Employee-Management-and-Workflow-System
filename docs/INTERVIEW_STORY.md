# Interview Story

## Problem Statement
We had a monolithic learning codebase and wanted to evolve it into a microservices-ready system without breaking existing practice features.

## Approach
- Kept existing CRUD and learning controllers intact.
- Improved domain model quality first.
- Introduced strict service boundaries and per-service ownership.
- Added DTO-first API contracts and foundational workflow logic.

## Key Design Decisions
- No cross-service JPA relationships.
- External references stored as IDs.
- Leave accounting uses a transaction ledger for auditability.
- Optimistic locking added where concurrency risk exists.
- Calculated fields like `availableDays` are derived, not persisted.

## Why This Is Practical
- Easy to explain and implement incrementally.
- Preserves current learning investment.
- Creates a direct path to production practices (outbox, resilience, gateway, security).

## Trade-offs
- Some backward-compatible legacy fields (`name`, `department`, `role`) are retained in employee model while introducing stronger fields.
- Snapshot denormalization across services is deferred until historical reporting requirements are explicit.

## Next Talking Points
- How to implement service-to-service validation safely.
- How to publish leave events with outbox.
- How to enforce idempotency and replay safety.

