package com.carnetdevoyage.models;


import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
	
	@OneToMany(mappedBy="id.user", cascade=CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<CarnetItem> carnetItems;
	
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
	
	@NonNull
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    List<Role> roles;

}
