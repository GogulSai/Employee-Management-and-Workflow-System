# Service Boundary

## Employee Service Ownership
Owns employee master profile data and employee lifecycle status.

### Owned Data
- Employee identity and profile (`employeeCode`, names, email)
- Organization mapping (`department`, `role`, `managerId` reference)
- Employment attributes (`employmentType`, `status`, `dateOfJoin`)

### Must Not Own
- Leave balances/requests
- Management assignment workflows

## Management Service Ownership
Owns reporting assignments and onboarding workflow data.

### Owned Data
- Manager records (`ManagerDetails` for learning continuity)
- Assignment history and current assignment (`ManagementAssignment`)
- Onboarding lifecycle (`OnboardingRequest`)

### Must Not Own
- Full employee master entity copy
- Leave balances and leave request state

## Leave Service Ownership
Owns leave policy and leave accounting.

### Owned Data
- Leave policy catalog (`LeaveType`)
- Employee yearly leave balances (`LeaveBalance`)
- Leave lifecycle (`LeaveRequest`)
- Leave accounting ledger (`LeaveBalanceTransaction`)

### Must Not Own
- Employee master profile
- Manager master profile

## Cross-Service References
- Allowed: `employeeId`, `managerId`, `leaveTypeCode`, `requestNumber`
- Not allowed: cross-service `@ManyToOne`/`@OneToMany`

## Snapshot Strategy
- Current decision: do not persist employee/manager snapshots in leave requests yet.
- Rationale: avoid premature duplication; fetch fresh owner data when needed.
- Future option: add snapshots only where historical reconstruction is required.

