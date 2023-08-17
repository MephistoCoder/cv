package com.foxminded.bohdansharubin.universitycms.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name ="groups")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {
	
	private static final String DEFAULT_NAME_PATTERN = "[A-Z]{2}-\\d{2}/?\\d?";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	@NotNull
	@Pattern(regexp = "[A-Z]{2}-\\d{2}/?\\d?")
	private String name;
	
	@Builder.Default
	@ManyToMany(mappedBy = "groups")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Student> students = new ArrayList<>();
	
	@Builder.Default
	@ManyToMany(mappedBy = "groups")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Teacher> teachers = new ArrayList<>();
	
	public boolean isValid() {
		return id >= 0 &&
			name.matches(DEFAULT_NAME_PATTERN);
	}
	
}
