package com.dakgoot.dakgoot.services;

import com.dakgoot.dakgoot.enums.UserRole;
import com.dakgoot.dakgoot.model.User;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
	public boolean canViewAllRepairRequests(User currentUser) {
		return currentUser.getRole() == UserRole.MAINTENANCE ||
				currentUser.getRole() == UserRole.ADMIN;
	}

	public boolean canUpdateRepairRequest(User currentUser) {
		return currentUser.getRole() == UserRole.MAINTENANCE ||
				currentUser.getRole() == UserRole.ADMIN;
	}

	public boolean canCreateRepairRequest(User currentUser) {
		return currentUser.getRole() == UserRole.HOMEOWNER ||
				currentUser.getRole() == UserRole.MAINTENANCE ||
				currentUser.getRole() == UserRole.ADMIN;
	}
}