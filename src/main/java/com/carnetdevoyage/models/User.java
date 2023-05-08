package com.carnetdevoyage.models;


import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "role", visible = true)
@JsonSubTypes({
		@JsonSubTypes.Type(value = User.class, name = "ADMIN"),
		@JsonSubTypes.Type(value = Client.class, name = "CLIENT")
})
public class User {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue
	private long id;
	
	@Email
	private String email;
	
	@NotBlank
	private String username;
	
	@NotBlank
	@Length(min = 8)
	String password;
	
	private UserRole role;

}
