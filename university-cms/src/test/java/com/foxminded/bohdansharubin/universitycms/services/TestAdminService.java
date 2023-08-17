package com.foxminded.bohdansharubin.universitycms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.foxminded.bohdansharubin.universitycms.TestConfig;
import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.PersonDTO;
import com.foxminded.bohdansharubin.universitycms.models.Admin;
import com.foxminded.bohdansharubin.universitycms.repositories.AdminRepository;

@SpringBootTest(classes = TestConfig.class)
@ExtendWith(MockitoExtension.class)
class TestAdminService extends TestAbstractPersonService{
	
	@Mock
	private AdminRepository adminRepository;
	
	@Mock
	private ModelConvertor modelConvertor;
	
	@InjectMocks
	private AdminService adminService;
	
	private Admin getCorrectAdmin(String uniqueModifier) {
		Admin correctAdmin = new Admin();
		correctAdmin.setFirstName("FirstName" + uniqueModifier);
		correctAdmin.setLastName("LastName" + uniqueModifier);
		correctAdmin.setUser(getCorrectUser());
		return correctAdmin;
	}
	
	private PersonDTO getCorrectDTO() {
		PersonDTO dto = convertor.convertToPersonDto(getCorrectAdmin("a"));
		dto.setUserPassword("1234");
		return dto;
	}
	
	@Test
	void findAll_returnEmptyList_adminsNotExist() {
		when(adminRepository.findAll())
			.thenReturn(new ArrayList<>());
		when(modelConvertor.convertToPersonDtoList(any()))
			.thenReturn(new ArrayList<>());
		
		assertTrue(adminService.findAll().isEmpty());
		verify(adminRepository, times(1)).findAll();
		verify(modelConvertor, times(1)).convertToPersonDtoList(any());
	}
	
	@Test
	void findAll_returnAdminsDtoList_adminsExist() {
		List<Admin> expectedAdmins = new ArrayList<>();
		expectedAdmins.add(getCorrectAdmin("a"));
		expectedAdmins.add(getCorrectAdmin("b"));
		
		when(adminRepository.findAll()).thenReturn(expectedAdmins);
		when(modelConvertor.convertToPersonDtoList(any()))
			.thenReturn(convertor.convertToPersonDtoList(expectedAdmins));
		
		List<PersonDTO> actualAdminsDto = adminService.findAll();
		
		verify(adminRepository, times(1)).findAll();
		verify(modelConvertor, times(1)).convertToPersonDtoList(any());
		assertFalse(actualAdminsDto.isEmpty());
		assertEquals(expectedAdmins.size(), actualAdminsDto.size());
		IntStream.range(0, expectedAdmins.size())
			.forEach(i -> assertTrue(isPersonAndDtoEqual(expectedAdmins.get(i),
				actualAdminsDto.get(i))));
	}
	
	@Test
	void save_returnPersonDto_personDTOHasCorrectData() {
		Admin expectedAdmin = getCorrectAdmin("a");
		
		when(adminRepository.save(any(Admin.class)))
			.thenReturn(expectedAdmin);
		when(modelConvertor.convertToPersonDto(any()))
			.thenReturn(convertor.convertToPersonDto(expectedAdmin));
		when(modelConvertor.convertToAdmin(any()))
			.thenReturn(expectedAdmin);
		
		
		PersonDTO actualAdminDto = adminService.save(getCorrectDTO());
		
		verify(adminRepository, times(1)).save(any(Admin.class));
		verify(modelConvertor, times(1)).convertToPersonDto(any());
		verify(modelConvertor, times(1)).convertToAdmin(any());
		assertFalse(actualAdminDto.isNull());
		assertTrue(isPersonAndDtoEqual(expectedAdmin, actualAdminDto));
	}
	
	@Test
	void save_returnNullPersonDTO_personDTOHasInCorrectData() {
		PersonDTO incorrectPersonDTO = getCorrectDTO();
		incorrectPersonDTO.setFirstName(" ");
		
		when(modelConvertor.convertToAdmin(any()))
			.thenReturn(convertor.convertToAdmin(incorrectPersonDTO));
		
		PersonDTO actualAdminDto = adminService.save(incorrectPersonDTO);
		assertTrue(actualAdminDto.isNull());
		verify(modelConvertor, times(1)).convertToAdmin(any());
	}
	
	@Test
	void update_returnNullPersonDTO_adminForUpdatingNotExist() {
		PersonDTO incorrectPersonDTO = getCorrectDTO();
		
		when(adminRepository.existsById(anyInt())).thenReturn(false);
		
		assertTrue(adminService.update(incorrectPersonDTO).isNull());
		verify(adminRepository, times(1)).existsById(anyInt());
	}
	
	@Test
	void update_returnNullPersonDTO_personDTOHasInCorrectData() {
		PersonDTO incorrectPersonDTO = getCorrectDTO();
		incorrectPersonDTO.setFirstName(" ");
		
		when(adminRepository.existsById(anyInt())).thenReturn(true);
		when(modelConvertor.convertToAdmin(any()))
			.thenReturn(convertor.convertToAdmin(incorrectPersonDTO));
		
		
		assertTrue(adminService.update(incorrectPersonDTO).isNull());
		verify(adminRepository, times(1)).existsById(anyInt());
		verify(modelConvertor, times(1)).convertToAdmin(any());
	}
	
	@Test
	void update_returnPersonDto_personDTOHasCorrectData() {
		PersonDTO dto = getCorrectDTO();
		Admin expectedAdmin = getCorrectAdmin("a");
		
		when(adminRepository.existsById(anyInt())).thenReturn(true);
		when(adminRepository.save(any())).thenReturn(expectedAdmin);
		when(modelConvertor.convertToAdmin(any()))
			.thenReturn(convertor.convertToAdmin(dto));
		when(modelConvertor.convertToPersonDto(expectedAdmin))
			.thenReturn(convertor.convertToPersonDto(expectedAdmin));
		
		PersonDTO actualAdminDto = adminService.update(dto);
		
		verify(adminRepository, times(1)).existsById(anyInt());
		verify(adminRepository, times(1)).save(any(Admin.class));
		verify(modelConvertor, times(1)).convertToAdmin(any());
		verify(modelConvertor, times(1)).convertToPersonDto(any());
		assertFalse(actualAdminDto.isNull());
		assertTrue(isPersonAndDtoEqual(expectedAdmin, actualAdminDto));	
	}
	
}
