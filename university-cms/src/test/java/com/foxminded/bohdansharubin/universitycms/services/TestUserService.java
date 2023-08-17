package com.foxminded.bohdansharubin.universitycms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.foxminded.bohdansharubin.universitycms.convertor.ModelConvertor;
import com.foxminded.bohdansharubin.universitycms.dto.UserDTO;
import com.foxminded.bohdansharubin.universitycms.models.Roles;
import com.foxminded.bohdansharubin.universitycms.models.User;
import com.foxminded.bohdansharubin.universitycms.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class TestUserService {
	
	private ModelConvertor convertor = new ModelConvertor();
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ModelConvertor modelConvertor;
	
	@InjectMocks
	private UserService userService;
	
	private boolean isUserAndDtoEqual(User user, UserDTO dto) {
		return user.getId() == dto.getId() &&
			user.getUsername() == dto.getUsername() &&
			user.getRole() == dto.getRole();
	}
	
	private boolean isUserListAndDtoListEqual(List<User> users, List<UserDTO> dtos) {
		return IntStream.range(0, users.size() > dtos.size() ? users.size() : dtos.size() )
			.allMatch(i -> isUserAndDtoEqual(users.get(i), dtos.get(i)));
	}
	
	private List<User> getCorrectUsersList(int listLength) {
		List<User> users = new ArrayList<>(listLength);
		IntStream.range(0, listLength)
			.forEach(i -> users.add(getCorrectUser(String.valueOf(i))));
		return users;
	}
	
	private User getCorrectUser(String dataModifier) {
		return User.builder()
			.id(1)
			.username("Username1234" + dataModifier)
			.password("Password1234" + dataModifier)
			.role(Roles.ROLE_ADMIN)
			.build();
	}
	
	private UserDTO getCorrectDtoWithoutPassword(User user) {
		return convertor.convertToUserDto(user);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {1, 3, 10})
	void findAll_returnUserList_usersExist(int listLength) {
		List<User> expectedUsers = getCorrectUsersList(listLength);

		when(userRepository.findAllByOrderByIdAsc())
			.thenReturn(expectedUsers);
		when(modelConvertor.convertToUserDtoList(expectedUsers))
			.thenReturn(convertor.convertToUserDtoList(expectedUsers));
		
		
		List<UserDTO> actualDtos = userService.findAll();
		
		assertEquals(expectedUsers.size(), actualDtos.size());
		verify(userRepository, times(1)).findAllByOrderByIdAsc();
		verify(modelConvertor, times(1)).convertToUserDtoList(any());
		assertTrue(isUserListAndDtoListEqual(expectedUsers, actualDtos));
	}
	
	@Test
	void findAll_returnEmptyList_usersNotExist() {
		when(userRepository.findAllByOrderByIdAsc())
			.thenReturn(new ArrayList<>());
		when(modelConvertor.convertToUserDtoList(any()))
			.thenReturn(new ArrayList<>());
		
		assertTrue(userService.findAll().isEmpty());
		verify(userRepository, times(1)).findAllByOrderByIdAsc();
		verify(modelConvertor, times(1)).convertToUserDtoList(any());
	}
	
	@Test
	void loadUserByUsername_returnUser_userWithUsernameExists() {
		User expectedUser = getCorrectUser("");
		when(userRepository.findByUsername(anyString()))
			.thenReturn(Optional.of(expectedUser));
		
		UserDetails actualUser = userService.loadUserByUsername(anyString());
		assertEquals(expectedUser.getPassword(), actualUser.getPassword());
		assertEquals(expectedUser.getUsername(), actualUser.getUsername());
		
		verify(userRepository, times(1)).findByUsername(anyString());
	}
	
	@Test
	void loadUserByUsername_throwUsernameNotFoundException_userWithUsernameNotExist() {
		when(userRepository.findByUsername(anyString()))
			.thenReturn(Optional.empty());
		
		assertThrows(UsernameNotFoundException.class,
			() -> userService.loadUserByUsername(anyString()));
		
		verify(userRepository, times(1)).findByUsername(anyString());
	}
	
	@Test
	void save_returnUserDto_userDtoCorrect() {
		User user = getCorrectUser("");
		UserDTO expectedDto = getCorrectDtoWithoutPassword(user);
		expectedDto.setPassword(user.getPassword());
		
		when(userRepository.save(any(User.class)))
			.thenReturn(getCorrectUser(""));
		when(modelConvertor.convertToUser(expectedDto))
			.thenReturn(user);
		when(modelConvertor.convertToUserDto(user))
			.thenReturn(expectedDto);
		
		UserDTO actualDto = userService.save(expectedDto);
		expectedDto.setPassword("");
		
		verify(userRepository, times(1)).save(any(User.class));
		verify(modelConvertor, times(1)).convertToUser(any());
		verify(modelConvertor, times(1)).convertToUserDto(any());
		assertEquals(expectedDto, actualDto);
	}
	
	@Test
	void save_returnNullUserDto_userDtoIncorrect() {
		User user = getCorrectUser("");
		user.setPassword("");
		UserDTO dto = getCorrectDtoWithoutPassword(user);
		
		when(modelConvertor.convertToUser(dto))
			.thenReturn(user);
		
		assertTrue(userService.save(dto).isNull());
		verify(modelConvertor, times(1)).convertToUser(any());
	}
	
	@Test
	void update_returnUserDto_userDtoWithPassword() {
		User user = getCorrectUser("");
		UserDTO expectedDto = getCorrectDtoWithoutPassword(user);
		expectedDto.setPassword(user.getPassword());
		
		when(userRepository.existsById(anyInt()))
			.thenReturn(true);
		when(userRepository.save(any(User.class)))
			.thenReturn(getCorrectUser(""));
		when(modelConvertor.convertToUser(expectedDto))
			.thenReturn(user);
		when(modelConvertor.convertToUserDto(user))
			.thenReturn(expectedDto);
		
		UserDTO actualDto = userService.update(expectedDto);
		expectedDto.setPassword("");
		
		verify(userRepository, times(1)).save(any(User.class));
		verify(userRepository, times(1)).existsById(anyInt());
		verify(modelConvertor, times(1)).convertToUser(any());
		verify(modelConvertor, times(1)).convertToUserDto(any());
		assertEquals(expectedDto, actualDto);
	}
	
	@Test
	void update_returnUserDto_userDtoWithoutPassword() {
		User user = getCorrectUser("");
		UserDTO expectedDto = getCorrectDtoWithoutPassword(user);
		
		when(userRepository.existsById(anyInt()))
			.thenReturn(true);
		when(userRepository.save(any(User.class)))
			.thenReturn(getCorrectUser(""));
		when(userRepository.getReferenceById(anyInt()))
			.thenReturn(user);
		when(modelConvertor.convertToUser(expectedDto))
			.thenReturn(user);
		when(modelConvertor.convertToUserDto(any()))
			.thenReturn(expectedDto);
		
		
		UserDTO actualDto = userService.update(expectedDto);
		expectedDto.setPassword("");
		
		verify(userRepository, times(1)).save(any(User.class));
		verify(userRepository, times(1)).existsById(anyInt());
		verify(userRepository, times(1)).getReferenceById(anyInt());
		verify(modelConvertor, times(1)).convertToUser(any());
		verify(modelConvertor, times(1)).convertToUserDto(any());
		assertEquals(expectedDto, actualDto);
	}
	
	@Test
	void update_returnNullUserDto_userDtoIncorrect() {
		User user = getCorrectUser("");
		user.setUsername("");
		UserDTO expectedDto = getCorrectDtoWithoutPassword(user);
		expectedDto.setUsername("");
		
		when(userRepository.existsById(anyInt()))
			.thenReturn(true);
		when(userRepository.getReferenceById(anyInt()))
			.thenReturn(user);
		when(modelConvertor.convertToUser(expectedDto))
			.thenReturn(user);
		
		
		assertTrue(userService.update(expectedDto).isNull());
		verify(modelConvertor, times(1)).convertToUser(any());
		verify(userRepository, times(1)).existsById(anyInt());
		verify(userRepository, times(1)).getReferenceById(anyInt());
	}
	
	@Test
	void update_returnNullUserDto_userNotExistWithId() {
		User user = getCorrectUser("");
		UserDTO expectedDto = getCorrectDtoWithoutPassword(user);
		
		when(userRepository.existsById(anyInt()))
			.thenReturn(false);
		
		assertTrue(userService.update(expectedDto).isNull());
		verify(userRepository, times(1)).existsById(anyInt());
	}
	
}
