# Entity Model

## employee-service

### Employee
Why required: source of truth for employee master details.

Important fields:
- `id` (PK)
- `employeeCode` (unique)
- `name`, `firstName`, `lastName`
- `email` (unique)
- `department`, `role`
- `phoneNumber`, `dateOfBirth`
- `dateOfJoin`
- `employmentType` (enum)
- `status` (enum)
- `managerId` (external reference)
- `workLocation`
- `createdDate`, `updatedDate`, `createdBy`, `updatedBy`
- `version` (`@Version`)

Enums:
- `EmployeeStatus`: `ACTIVE`, `INACTIVE`, `ON_LEAVE`, `RESIGNED`, `TERMINATED`
- `EmploymentType`: `PERMANENT`, `CONTRACT`, `INTERN`, `TEMPORARY`

## management-service

### ManagerDetails
Preserved for existing learning paths and current manager CRUD behavior.

### ManagementAssignment
Why required: employee-manager mapping with timeline and status.

Important fields:
- `assignmentId` (PK)
- `employeeId`, `managerId`
- `departmentCode`
- `assignmentType`, `assignmentStatus`
- `effectiveFrom`, `effectiveTo`
- `createdAt`, `updatedAt`, `createdBy`, `updatedBy`
- `version`

Enums:
- `AssignmentType`: `PRIMARY_MANAGER`, `FUNCTIONAL_MANAGER`, `PROJECT_MANAGER`
- `AssignmentStatus`: `ACTIVE`, `INACTIVE`, `ENDED`

### OnboardingRequest
Why required: onboarding workflow state tracking.

Important fields:
- `onboardingRequestId` (PK)
- `employeeId`, `managerId`
- `joiningDate`
- `onboardingStatus`
- `remarks`
- `submittedAt`, `completedAt`, `createdAt`, `updatedAt`

Enum:
- `OnboardingStatus`: `CREATED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

## leave-service

### LeaveType
Why required: configurable leave policy definition.

Fields:
- `leaveTypeId` (PK)
- `leaveCode` (unique), `leaveName`, `description`
- `defaultAnnualEntitlement`
- `carryForwardAllowed`, `maximumCarryForward`
- `active`
- `createdAt`, `updatedAt`, `version`

### LeaveBalance
Why required: yearly leave accounting per employee and leave type.

Fields:
- `leaveBalanceId` (PK)
- `employeeId`
- `leaveTypeCode`
- `leaveYear`
- `openingBalance`, `creditedDays`, `usedDays`, `pendingDays`, `adjustedDays`
- `version`, `createdAt`, `updatedAt`
- derived `availableDays = opening + credited + adjusted - used - pending`

### LeaveRequest
Why required: leave lifecycle and approval state.

Fields:
- `leaveRequestId` (PK)
- `requestNumber` (unique)
- `employeeId`, `managerId`
- `leaveTypeCode`
- `startDate`, `endDate`, `requestedDays`
- `leaveDayType`, `leaveStatus`
- `reason`, `contactDuringLeave`
- `appliedAt`, `approvedAt`, `rejectedAt`, `cancelledAt`
- `approverId`, `approverRemarks`
- `version`, `createdAt`, `updatedAt`

### LeaveBalanceTransaction
Why required: auditable ledger for all balance changes.

Fields:
- `transactionId` (PK)
- `employeeId`, `leaveBalanceId`, `leaveRequestId`
- `leaveTypeCode`, `transactionType`
- `quantity`, `balanceBefore`, `balanceAfter`
- `referenceNumber`, `remarks`
- `transactionDate`, `createdAt`, `createdBy`

Enum:
- `LeaveStatus`: `DRAFT`, `PENDING`, `APPROVED`, `REJECTED`, `CANCELLED`
- `LeaveDayType`: `FULL_DAY`, `FIRST_HALF`, `SECOND_HALF`
- `LeaveTransactionType`: `OPENING_CREDIT`, `MONTHLY_CREDIT`, `MANUAL_ADJUSTMENT`, `REQUEST_RESERVED`, `REQUEST_RELEASED`, `LEAVE_CONSUMED`, `LEAVE_REVERSED`, `CARRY_FORWARD`, `EXPIRY`

