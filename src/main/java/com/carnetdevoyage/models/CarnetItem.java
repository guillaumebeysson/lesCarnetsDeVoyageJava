package com.carnetdevoyage.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
public class CarnetItem {
	
	@Data
	@Embeddable
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CarnetItemId{
		@ManyToOne
		private User user;
		@ManyToOne
		private Carnet carnet;
	}
	
	@EmbeddedId
	@EqualsAndHashCode.Include
	private CarnetItemId id;
	

}
