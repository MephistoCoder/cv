package com.foxminded.bohdansharubin.schoolspringbootapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private int id;
	
	@Column(name = "group_name")
	private String name;
	
	@OneToMany(mappedBy = "group",
			   fetch = FetchType.EAGER,
			   cascade = CascadeType.ALL)
	private List<Student> students = new ArrayList<>();
	
	public Group(String name) {
		this.name = name;
	}
	
	public Group() {
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject) {
			return true;
		}
		if (!(otherObject instanceof Group)) {
			return false;
		}
		Group other = (Group) otherObject;
		return id == other.id && Objects.equals(name, other.name);
	}
	
}
