package com.simonini.entities;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

public class PointOfInterest {

	@Id
	public String id;

	@NotEmpty(message = "Nome não pode ser vazio")
	private String name;

	@NotNull(message = "coordenada x não pode ser vazia")
	@Min(value = 0, message = "coordenada x deve ser um valor positivo")
	private Integer xCoordinate;

	@NotNull(message = "coordenada y não pode ser vazia")
	@Min(value = 0, message = "coordenada y deve ser um valor positivo")
	private Integer yCoordinate;
	
	@Transient
	private List<String> errorsList = new ArrayList<String>();
	
	public List<String> getErrorsList() {
		return errorsList;
	}

	public void setErrorsList(List<String> errorsList) {
		this.errorsList = errorsList;
	}

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
	
	public void addErrorMessage(String message) {
		this.errorsList.add(message);
	}
	
	public boolean hasErrors() {
		return !this.errorsList.isEmpty();
	}

	public Double distanceFrom(PointOfInterest other) {
		return getDistance(other.getxCoordinate(), other.getyCoordinate());
	}

	public Double getDistance(Integer xReference, Integer yReference) {
		return Math.sqrt(Math.pow((this.xCoordinate - xReference), 2) + Math.pow((this.yCoordinate - yReference), 2));
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





