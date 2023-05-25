//package com.carnetdevoyage.models;
//
//import jakarta.persistence.Embeddable;
//import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.Entity;
//import jakarta.persistence.ManyToOne;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//
//@Data
//@SuperBuilder
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@NoArgsConstructor
//@Entity
//public class CommentItem {
//
//	@Data
//	@Embeddable
//	@AllArgsConstructor
//	@NoArgsConstructor
//	private static class CommentItemId{
//		
//		@ManyToOne
//		private Comment comment;
//
//		@ManyToOne
//		private Carnet carnet;
//	}
//	
//	@EmbeddedId
//	private CommentItemId id;
//}
