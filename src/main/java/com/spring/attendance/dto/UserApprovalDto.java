package com.spring.attendance.dto;


import com.spring.attendance.model.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApprovalDto {
    private Long userId;
    private AccountStatus status; // APPROVED or REJECTED
}
