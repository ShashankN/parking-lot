package com.gojek.parking.resource;

public class Vehicle {
	
	private String registrationNum;
	private String color;

	public Vehicle(String registrationNum, String color) {
		if(registrationNum==null || registrationNum.isEmpty()) {
			throw new IllegalArgumentException("Registration number cannot be empty");
		}
		if(color == null || color.isEmpty()) {
			throw new IllegalArgumentException("Color cannot be empty");
		}
		this.registrationNum = registrationNum;
		this.color = color;
	}

	public String getRegistrationNum() {
		return registrationNum;
	}

	public String getColor() {
		return color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((registrationNum == null) ? 0 : registrationNum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (registrationNum == null) {
			if (other.registrationNum != null)
				return false;
		} else if (!registrationNum.equals(other.registrationNum))
			return false;
		return true;
	}
	
	
	
}
