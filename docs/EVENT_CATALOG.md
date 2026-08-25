# Event Catalog

> Event classes are prepared as contracts only. Broker publishing and consumption are not implemented yet.

## employee-service

### EmployeeCreatedEvent
Fields:
- `eventId`, `eventType`, `occurredAt`, `employeeId`, `employeeCode`, `departmentCode`, `managerId`, `employeeStatus`, `correlationId`

### EmployeeStatusChangedEvent
Fields:
- `eventId`, `occurredAt`, `employeeId`, `previousStatus`, `newStatus`, `correlationId`

## management-service

### ManagerAssignedEvent
Fields:
- `eventId`, `occurredAt`, `assignmentId`, `employeeId`, `managerId`, `effectiveFrom`, `correlationId`

## leave-service

### LeaveRequestedEvent
Fields:
- `eventId`, `occurredAt`, `leaveRequestId`, `employeeId`, `managerId`, `leaveTypeCode`, `startDate`, `endDate`, `requestedDays`, `correlationId`

### LeaveApprovedEvent
Fields:
- `eventId`, `occurredAt`, `leaveRequestId`, `employeeId`, `approvedBy`, `approvedAt`, `correlationId`

### LeaveRejectedEvent
Fields:
- `eventId`, `occurredAt`, `leaveRequestId`, `employeeId`, `rejectedBy`, `reason`, `rejectedAt`, `correlationId`

### LeaveCancelledEvent
Fields:
- `eventId`, `occurredAt`, `leaveRequestId`, `employeeId`, `cancelledAt`, `correlationId`

## Future Delivery Guarantees (Planned)
- Outbox publishing
- At-least-once with idempotent consumers
- Correlation ID propagation
- Event schema versioning
- Retry with dead-letter queue

