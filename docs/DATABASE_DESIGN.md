# Database Design

## Principles
- Database per service.
- No foreign keys across service databases.
- Enforce local integrity with unique constraints, indexes, and optimistic locking.

## employee-service

### Table: `employee_main`
Key constraints and indexes:
- Unique: `email`
- Unique: `employee_code`
- Index: `email`
- Index: `department`
- Index: `manager_id`

Concurrency:
- `version` (`@Version`) for optimistic locking.

## management-service

### Table: `manager_details`
- Unique: `manager_email`
- Index: `manager_email`

### Table: `management_assignment`
- Index: `employee_id`
- Index: `manager_id`
- Index: `department_code`
- `version` for optimistic locking

Business integrity handled in service layer:
- Prevent multiple active `PRIMARY_MANAGER` assignments for the same employee.
- Validate `effectiveFrom <= effectiveTo` when `effectiveTo` exists.

### Table: `onboarding_request`
- Index: `employee_id`
- Index: `manager_id`
- Index: `onboarding_status`

## leave-service

### Table: `leave_type`
- Unique: `leave_code`

### Table: `leave_balance`
- Unique composite: `employee_id + leave_type_code + leave_year`
- Index: `employee_id`
- Index: `leave_type_code`
- `version` for optimistic locking

Design decision:
- `availableDays` is derived and not persisted.

### Table: `leave_request`
- Unique: `request_number`
- Index: `employee_id`
- Index: `manager_id`
- Index: `leave_status`
- Index: `start_date,end_date`
- `version` for optimistic locking

### Table: `leave_balance_transaction`
- Index: `leave_balance_id`
- Index: `leave_request_id`
- Index: `employee_id`

## Data Evolution Plan
- Move from H2 to PostgreSQL/MySQL per service.
- Introduce Flyway/Liquibase.
- Add production-grade index tuning after query profiling.

