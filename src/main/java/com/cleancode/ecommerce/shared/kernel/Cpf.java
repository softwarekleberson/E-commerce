package com.cleancode.ecommerce.shared.kernel;

import java.util.Objects;

import com.cleancode.ecommerce.shared.exception.IllegalCpfException;

public class Cpf {

	private final String cpf;
	
	public Cpf(String cpf) {
		if(cpf == null) {
			throw new IllegalCpfException("Cpf invalid");
		}
		
		if(!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")) {
			throw new IllegalCpfException("Cpf needs this format xxx.xxx.xxx-xx");
		}
		
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return cpf;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cpf other = (Cpf) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
	public String toString() {
		return "Cpf [cpf=" + cpf + "]";
	}
}