package com.foxminded.bohdansharubin.universitycms.models;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lessons_times")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column(name = "start_time")
	@NotNull
	private LocalTime startTime;
	
	@Column(name = "end_time")
	@NotNull
	private LocalTime endTime;
	
	public boolean isValid() {
		return startTime.isAfter(LocalTime.of(7, 0)) &&
			startTime.isBefore(endTime) &&
			endTime.isBefore(LocalTime.of(21, 00));
	}
	
}
