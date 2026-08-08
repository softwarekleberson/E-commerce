package com.cleancode.ecommerce.customer.domain.customer;

import java.util.Objects;

public class SystemClientStatus {

	private boolean systemClientStatus;
	
	public SystemClientStatus(boolean systemClientStatus) {
		this.systemClientStatus = systemClientStatus;
	}
	
	public boolean isSystemClientStatus() {
		return systemClientStatus;
	}

	public static SystemClientStatus changeStatus(boolean newStatus) {
		newStatus = !newStatus;
		return new SystemClientStatus(newStatus);
	}

	@Override
	public int hashCode() {
		return Objects.hash(systemClientStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemClientStatus other = (SystemClientStatus) obj;
		return systemClientStatus == other.systemClientStatus;
	}
}