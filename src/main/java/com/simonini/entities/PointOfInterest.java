package com.simonini.entities;

import org.springframework.data.annotation.Id;

public class PointOfInterest {
	
	@Id
	public String id;
	  
	private String 	name;
	private Integer	xCoordinate;
	private Integer	yCoordinate;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getxCoordinate() {
		return xCoordinate;
	}
	
	public void setxCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	public Integer getyCoordinate() {
		return yCoordinate;
	}
	
	public void setyCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PointOfInterest other = (PointOfInterest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PointOfInterest [id=" + id + ", name=" + name + ", xCoordinate=" + xCoordinate + ", yCoordinate="
				+ yCoordinate + "]";
	}
}
