# Sample Payloads

## Employee Create
`POST /v1/employees`

```json
{
  "name": "Ananya Rao",
  "email": "ananya.rao@company.com",
  "department": "ENG",
  "role": "Senior Developer",
  "salary": 85000.00,
  "status": "ACTIVE",
  "doj": "20260110"
}
```

## Management Assignment Create
`POST /api/v1/management/assignments`

```json
{
  "employeeId": 101,
  "managerId": 9001,
  "departmentCode": "ENG",
  "assignmentType": "PRIMARY_MANAGER",
  "effectiveFrom": "2026-09-01",
  "effectiveTo": null,
  "createdBy": "hr-admin"
}
```

## Leave Type Create
`POST /api/v1/leave-types`

```json
{
  "leaveCode": "SICK",
  "leaveName": "Sick Leave",
  "description": "Medical leave",
  "defaultAnnualEntitlement": 10.0,
  "carryForwardAllowed": false,
  "maximumCarryForward": 0.0,
  "active": true
}
```

## Leave Balance Create
`POST /api/v1/employees/{employeeId}/leave-balances`

```json
{
  "leaveTypeCode": "SICK",
  "leaveYear": 2026,
  "openingBalance": 2.0,
  "creditedDays": 10.0,
  "adjustedDays": 0.0,
  "remarks": "Annual opening"
}
```

## Leave Apply
`POST /api/v1/leave-requests`

```json
{
  "employeeId": 101,
  "managerId": 9001,
  "leaveTypeCode": "SICK",
  "startDate": "2026-10-12",
  "endDate": "2026-10-13",
  "requestedDays": 2.0,
  "leaveDayType": "FULL_DAY",
  "reason": "Medical rest",
  "contactDuringLeave": "+91-9000000000"
}
```

## Leave Approve
`PATCH /api/v1/leave-requests/{leaveRequestId}/approve`

```json
{
  "approverId": 9001,
  "remarks": "Approved"
}
```

## Leave Reject
`PATCH /api/v1/leave-requests/{leaveRequestId}/reject`

```json
{
  "approverId": 9001,
  "remarks": "Insufficient reason"
}
```

## Leave Cancel
`PATCH /api/v1/leave-requests/{leaveRequestId}/cancel`

```json
{
  "approverId": 101,
  "remarks": "Plan changed"
}
```

