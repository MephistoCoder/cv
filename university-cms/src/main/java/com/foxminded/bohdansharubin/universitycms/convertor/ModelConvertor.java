package com.foxminded.bohdansharubin.universitycms.convertor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

import com.foxminded.bohdansharubin.universitycms.dto.CourseDTO;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;
import com.foxminded.bohdansharubin.universitycms.dto.UserDTO;
import com.foxminded.bohdansharubin.universitycms.models.Admin;
import com.foxminded.bohdansharubin.universitycms.models.Course;
import com.foxminded.bohdansharubin.universitycms.models.Person;
import com.foxminded.bohdansharubin.universitycms.models.Student;
import com.foxminded.bohdansharubin.universitycms.models.Teacher;
import com.foxminded.bohdansharubin.universitycms.models.User;

@Component
public class ModelConvertor {
	private ModelMapper modelMapper;
	private Converter<List<Teacher>, List<Integer>> teacherToTeacherId = source -> source.getSource()
		.stream()
		.map(teacher -> teacher.getId())
		.collect(Collectors.toList());
	
	public ModelConvertor() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
		    .setFieldMatchingEnabled(true)
		    .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
		
		modelMapper.addMappings(new PropertyMap<Person, PersonDTO>() {
            @Override
            protected void configure() {
                skip(destination.getUserPassword());
            }
        });
		
		modelMapper.addMappings(new PropertyMap<Student, PersonDTO>() {
            @Override
            protected void configure() {
                skip(destination.getUserPassword());
            }
		});
		modelMapper.addMappings(new PropertyMap<Teacher, PersonDTO>() {
			@Override
			protected void configure() {
				skip(destination.getUserPassword());
			}
		});
		modelMapper.addMappings(new PropertyMap<Admin, PersonDTO>() {
			@Override
			protected void configure() {
				skip(destination.getUserPassword());
			}
		});
		
//		modelMapper.addMappings(new PropertyMap<CourseDTO, Course>() {
//			@Override
//			protected void configure() {
//				skip(destination.getTeachers());
//			}
//		});
//		
//		modelMapper.addMappings(new PropertyMap<Course, CourseDTO>() {
//			@Override
//			protected void configure() {
//				using(teacherToTeacherId).map(source.getTeachers(), destination.getTeachers());
//			}
//		});
		
		modelMapper.createTypeMap(User.class, UserDTO.class)
			.addMappings(mapper -> mapper.skip(UserDTO::setPassword));
		modelMapper.createTypeMap(UserDTO.class, User.class);
		
		modelMapper.createTypeMap(Course.class, CourseDTO.class)
			.addMappings(mapper -> mapper.using(teacherToTeacherId).map(Course::getTeachers, CourseDTO::setTeachers));
		modelMapper.createTypeMap(CourseDTO.class, Course.class);
//			.addMappings(mapper -> mapper.skip(Course::setTeachers));
		
	}
	
	public PersonDTO convertToPersonDto(Person person) {
		return modelMapper.map(person, PersonDTO.class);
	}
	
	public List<PersonDTO> convertToPersonDtoList(List<? extends Person> persons) {
		return persons.stream()
			.map(this::convertToPersonDto)
			.collect(Collectors.toList());
	}
	
	public Student convertToStudent(PersonDTO personDTO) {
		return modelMapper.map(personDTO, Student.class);
	}
	
	public Admin convertToAdmin(PersonDTO personDTO) {
		return modelMapper.map(personDTO, Admin.class);
	}
	 
	public Teacher convertToTeacher(PersonDTO personDTO) {
		return modelMapper.map(personDTO, Teacher.class);
	}
		
	public UserDTO convertToUserDto(User user) {
		return modelMapper.map(user, UserDTO.class);
	}
	
	public List<UserDTO> convertToUserDtoList(List<User> users) {
		return users.stream()
			.map(this::convertToUserDto)
			.collect(Collectors.toList());
	}
	
	public User convertToUser(UserDTO dto) {
		return modelMapper.map(dto, User.class);
	}
	public Course dtoToCourse(CourseDTO dto) {
		return modelMapper.map(dto, Course.class);
	}
	
	public CourseDTO courseToDto(Course course) {
		return modelMapper.map(course, CourseDTO.class);
	}

	public List<CourseDTO> convertToCourseDtoList(List<Course> courses) {
		return courses.stream()
			.map(this::courseToDto)
			.collect(Collectors.toList());
	}
	
}
