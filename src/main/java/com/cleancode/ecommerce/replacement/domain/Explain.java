package com.cleancode.ecommerce.replacement.domain;

import java.util.Objects;

import com.cleancode.ecommerce.replacement.domain.exception.IllegalReplacementException;

public class Explain {

	public static int EXPLAIN_MAX = 250;
	private final String explain;

	public Explain(String explain) {
		countCaracter(explain);
		this.explain = explain;
	}
	
	private void countCaracter(String explain) {
		if(explain == null || explain.isEmpty()) {
			throw new IllegalReplacementException("Could you explain the reason for this exchange?");
		}
		
		if(explain.length() > EXPLAIN_MAX) {
			throw new IllegalReplacementException("The explain exchange need less 250 characters");
		}
	}
	
	public String getExplain() {
		return explain;
	}

	@Override
	public int hashCode() {
		return Objects.hash(explain);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Explain other = (Explain) obj;
		return Objects.equals(explain, other.explain);
	}
}
