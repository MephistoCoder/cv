package com.foxminded.bohdansharubin.universitycms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "audiences")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Audience {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	@NotNull
	@Pattern(regexp = "[A-Z]{1,}-\\d{1,}")
	private String name;
	
}
