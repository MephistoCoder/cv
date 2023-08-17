package com.foxminded.bohdansharubin.universitycms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.NullPersonDTO;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;
import com.foxminded.bohdansharubin.universitycms.models.Student;
import com.foxminded.bohdansharubin.universitycms.repositories.StudentRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Slf4j
public class StudentService extends AbstractPersonService {
	
	private StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository,
		ModelConvertor modelConvertor) {
		super(modelConvertor);
		this.studentRepository = studentRepository;
	}
	
	@Transactional
	public PersonDTO save(PersonDTO studentDTO) {
		Student student = modelConvertor.convertToStudent(studentDTO);
		if(!student.isValid()) {
			log.warn("Can't create student with incorrect data", studentDTO);
			return new NullPersonDTO();
		}
		log.info("Created student = {}", student);
		return modelConvertor.convertToPersonDto(studentRepository.save(student));
	}
	
	public PersonDTO findById(int id) {
		log.debug("Id = {} for finding student", id);
		return modelConvertor.convertToPersonDto(studentRepository.getReferenceById(id));
	}
	
	public List<PersonDTO> findAll() {
		log.debug("Finding all students");
		return modelConvertor.convertToPersonDtoList(studentRepository.findAll());
	}
	
	@Transactional
	public void deleteById(int id) {
		log.debug("Id = {} for deleting student", id);
		studentRepository.deleteById(id);
	}
	
	protected boolean existsById(int id) {
		log.debug("Id for checking existence of student = {}", id);
		return studentRepository.existsById(id);
	}

}
