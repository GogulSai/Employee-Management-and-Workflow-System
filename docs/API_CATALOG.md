# API Catalog

## employee-service

### Existing learning endpoints (preserved)
- `POST /v1/employees`
- `POST /v1/employees/bulk`
- `POST /v2/employees`
- `POST /v2/employees/bulk`
- `GET /v2/employees/page`
- `GET /v2/employees/page/sort/{department}`
- `PUT /v2/employees`

### Added contract endpoints
- `GET /api/v1/employees/{employeeId}`
- `GET /api/v1/employees/code/{employeeCode}`
- `GET /api/v1/employees/email/{email}`
- `GET /api/v1/employees/{employeeId}/summary`
- `PATCH /api/v1/employees/{employeeId}/status`

## management-service

### Existing learning endpoints (preserved)
- `POST /management/managers`
- `POST /management/managers/bulk`
- `PUT /management/managers/{id}`

### Added assignment/onboarding endpoints
- `POST /api/v1/management/assignments`
- `GET /api/v1/management/assignments/{assignmentId}`
- `GET /api/v1/management/employees/{employeeId}/manager`
- `GET /api/v1/management/employees/{employeeId}/assignment`
- `GET /api/v1/management/managers/{managerId}/employees`
- `PATCH /api/v1/management/assignments/{assignmentId}/status`
- `POST /api/v1/onboarding-requests`
- `GET /api/v1/onboarding-requests/{requestId}`
- `PATCH /api/v1/onboarding-requests/{requestId}/status`

## leave-service
- `GET /api/v1/leave-types`
- `POST /api/v1/leave-types`
- `PATCH /api/v1/leave-types/{leaveTypeId}`

- `GET /api/v1/employees/{employeeId}/leave-balances`
- `GET /api/v1/employees/{employeeId}/leave-balances/{leaveTypeCode}`
- `POST /api/v1/employees/{employeeId}/leave-balances`
- `POST /api/v1/employees/{employeeId}/leave-adjustments`
- `GET /api/v1/employees/{employeeId}/leave-transactions`

- `POST /api/v1/leave-requests`
- `GET /api/v1/leave-requests/{leaveRequestId}`
- `GET /api/v1/employees/{employeeId}/leave-requests`
- `GET /api/v1/managers/{managerId}/pending-leave-requests`
- `PATCH /api/v1/leave-requests/{leaveRequestId}/approve`
- `PATCH /api/v1/leave-requests/{leaveRequestId}/reject`
- `PATCH /api/v1/leave-requests/{leaveRequestId}/cancel`

## Notes
- Responses are DTO-based.
- Standardized error shapes are handled via service-level exception handlers.

