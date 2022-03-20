package com.spbstu.edu.advertisement.service.impl;

import com.spbstu.edu.advertisement.dto.UserDto;
import com.spbstu.edu.advertisement.entity.User;
import com.spbstu.edu.advertisement.exception.CustomException;
import com.spbstu.edu.advertisement.exception.ExceptionId;
import com.spbstu.edu.advertisement.mapper.UserMapper;
import com.spbstu.edu.advertisement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Spy
    private PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @InjectMocks
    private UserServiceImpl userService;

    private final String phoneNumber = "+78005553535";
    private final String password = "123";
    private final String encodedPassword = passwordEncoder.encode(password);

    private final String newPhoneNumber = "+78005555555";
    private final String newPassword = "345";
    private final String encodedNewPassword = passwordEncoder.encode(newPassword);

    private User user;
    private UserDto userDto;
    private Long userId;


    @BeforeEach
    public void setUp() {
        userId = 222L;
        user = new User();
        user.setId(userId);
        user.setName("Nikita");
        user.setSurname("Krasnoperov");
        user.setPassword(encodedPassword);
        user.setPhoneNumber(phoneNumber);

        userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setPassword(encodedPassword);
        userDto.setPhoneNumber(user.getPhoneNumber());
    }

    @Test
    public void testGetUserDtoById()  {
        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doReturn(userDto).when(userMapper).toUserDto(user);

        UserDto returnedUserDto = userService.getUser(userId);

        verify(userRepository).findById(userId);
        verify(userMapper).toUserDto(user);
        verifyNoMoreInteractions(userRepository, userMapper);

        assertNotNull(returnedUserDto);
        assertEquals(userDto, returnedUserDto);
    }

    @Test
    public void testGetUserEntityById()  {
        doReturn(Optional.of(user)).when(userRepository).findById(userId);

        User returnedUser = userService.getUserEntity(userId);

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository, userMapper);

        assertNotNull(returnedUser);
        assertEquals(user, returnedUser);
    }

    @Test
    public void testGetUserByIdFailed()  {
        doReturn(Optional.empty()).when(userRepository).findById(userId);

        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> userService.getUserEntity(userId)
        );

        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository, userMapper);

        assertNotNull(thrownException);
        assertEquals(ExceptionId.USER_NOT_FOUND, thrownException.getId());
    }

    @Test
    public void testDeleteUser()  {
        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    public void testDeleteUserFailed()  {
        doThrow(new EmptyResultDataAccessException(1)).when(userRepository).deleteById(userId);

        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> userService.deleteUser(userId)
        );

        verify(userRepository).deleteById(userId);
        verifyNoMoreInteractions(userRepository, userMapper);

        assertNotNull(thrownException);
        assertEquals(ExceptionId.USER_NOT_FOUND, thrownException.getId());
    }

    @Test
    public void testAddUser()  {
        userDto.setPassword(password);

        User userWithoutId = new User();
        userWithoutId.setName(user.getName());
        userWithoutId.setSurname(user.getSurname());
        userWithoutId.setPassword(user.getPassword());
        userWithoutId.setPhoneNumber(user.getPhoneNumber());

        UserDto userDtoWithoutId = new UserDto();
        userDtoWithoutId.setName(userDto.getName());
        userDtoWithoutId.setSurname(userDto.getSurname());
        userDtoWithoutId.setPassword(userDto.getPassword());
        userDtoWithoutId.setPhoneNumber(userDto.getPhoneNumber());

        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<UserDto> userDtoCaptor = ArgumentCaptor.forClass(UserDto.class);

        doReturn(null).when(userRepository).findByPhoneNumber(phoneNumber);
        doAnswer(
                (Answer<UserDto>) invocationOnMock -> {
                    User user = invocationOnMock.getArgument(0);

                    return user == null ? null : userDto;
                }
        ).when(userMapper).toUserDto(userCaptor.capture());
        doCallRealMethod().when(passwordEncoder).encode(passwordCaptor.capture());
        doReturn(userWithoutId).when(userMapper).toUser(userDtoCaptor.capture());
        doReturn(user).when(userRepository).save(userCaptor.capture());

        UserDto returnedUserDto = userService.addUser(userDtoWithoutId);

        String capturedPassword = passwordCaptor.getValue();
        List<User> capturedUsers = userCaptor.getAllValues();
        User capturedNullUser = capturedUsers.get(0);
        User capturedUserWithoutId = capturedUsers.get(1);
        User capturedUserWithId = capturedUsers.get(2);
        UserDto capturedUserDto = userDtoCaptor.getValue();

        verify(userRepository).findByPhoneNumber(phoneNumber);
        verify(userMapper).toUserDto(capturedNullUser);
        verify(passwordEncoder).encode(capturedPassword);
        verify(userMapper).toUser(capturedUserDto);
        verify(userRepository).save(userWithoutId);
        verify(userMapper).toUserDto(capturedUserWithId);

        verifyNoMoreInteractions(userRepository, userMapper, passwordEncoder);

        assertNull(capturedNullUser);
        assertEquals(password, capturedPassword);

        assertNull(capturedUserDto.getId());
        assertNull(capturedUserWithoutId.getId());
        assertEquals(userId, capturedUserWithId.getId());
        assertEquals(userId, returnedUserDto.getId());
    }

    @Test
    public void testAddUserFailed()  {
        UserDto userDtoWithoutId = new UserDto();
        userDtoWithoutId.setName(userDto.getName());
        userDtoWithoutId.setSurname(userDto.getSurname());
        userDtoWithoutId.setPassword(userDto.getPassword());
        userDtoWithoutId.setPhoneNumber(userDto.getPhoneNumber());

        doReturn(user).when(userRepository).findByPhoneNumber(phoneNumber);
        doReturn(userDto).when(userMapper).toUserDto(user);

        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> userService.addUser(userDtoWithoutId)
        );

        verify(userRepository).findByPhoneNumber(phoneNumber);
        verify(userMapper).toUserDto(user);

        verifyNoMoreInteractions(userRepository, userMapper, passwordEncoder);

        assertNotNull(thrownException);
        assertEquals(ExceptionId.USERNAME_RESERVED, thrownException.getId());
    }

    @Test
    public void testUpdateUser() {
        UserDto newUserDto = new UserDto();
        newUserDto.setId(userDto.getId());
        newUserDto.setName(userDto.getName());
        newUserDto.setSurname(userDto.getSurname());
        newUserDto.setPassword(password);
        newUserDto.setPhoneNumber(userDto.getPhoneNumber());
        newUserDto.setNewPassword(newPassword);

        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setPassword(encodedNewPassword);
        newUser.setPhoneNumber(user.getPhoneNumber());

        UserDto newUserDtoWithNewPassword = new UserDto();
        newUserDtoWithNewPassword.setId(userDto.getId());
        newUserDtoWithNewPassword.setName(userDto.getName());
        newUserDtoWithNewPassword.setSurname(userDto.getSurname());
        newUserDtoWithNewPassword.setPassword(encodedNewPassword);
        newUserDtoWithNewPassword.setPhoneNumber(userDto.getPhoneNumber());

        ArgumentCaptor<String> rawPasswordCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> encodedPasswordCaptor = ArgumentCaptor.forClass(String.class);

        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doReturn(userDto).when(userMapper).toUserDto(user);
        doCallRealMethod().when(passwordEncoder).matches(rawPasswordCaptor.capture(), encodedPasswordCaptor.capture());
        doCallRealMethod().when(passwordEncoder).encode(rawPasswordCaptor.capture());
        doReturn(user).when(userRepository).findByPhoneNumber(phoneNumber);
        doReturn(user).when(userMapper).toUser(userDto);
        doReturn(newUser).when(userMapper).updateWithNullAsNoChange(newUserDto, user);
        doReturn(newUser).when(userRepository).save(newUser);
        doReturn(newUserDtoWithNewPassword).when(userMapper).toUserDto(newUser);

        UserDto updatedUserDto = userService.updateUser(newUserDto);

        List<String> capturedPasswords = rawPasswordCaptor.getAllValues();
        String capturedRawOldPassword = capturedPasswords.get(0);
        String capturedRawNewPassword = capturedPasswords.get(1);
        String capturedEncodedOldPassword = encodedPasswordCaptor.getValue();

        verify(userRepository).findById(userId);
        verify(userMapper, times(2)).toUserDto(user);
        verify(passwordEncoder).matches(capturedRawOldPassword, capturedEncodedOldPassword);
        verify(passwordEncoder).encode(capturedRawNewPassword);
        verify(userRepository).findByPhoneNumber(phoneNumber);
        verify(userMapper).toUser(userDto);
        verify(userMapper).updateWithNullAsNoChange(newUserDto, user);
        verify(userRepository).save(newUser);
        verify(userMapper).toUserDto(newUser);

        verifyNoMoreInteractions(userRepository, userMapper, passwordEncoder);

        assertEquals(password, capturedRawOldPassword);
        assertEquals(encodedPassword, capturedEncodedOldPassword);
        assertEquals(newPassword, capturedRawNewPassword);

        assertNotNull(updatedUserDto);
        assertEquals(newUserDtoWithNewPassword, updatedUserDto);
    }

    @Test
    public void testUpdateUserFailedOnAuth() {
        UserDto newUserDto = new UserDto();
        newUserDto.setId(userDto.getId());
        newUserDto.setName(userDto.getName());
        newUserDto.setSurname(userDto.getSurname());
        newUserDto.setPassword(newPassword);
        newUserDto.setPhoneNumber(userDto.getPhoneNumber());
        newUserDto.setNewPassword(newPassword);

        ArgumentCaptor<String> rawPasswordCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> encodedPasswordCaptor = ArgumentCaptor.forClass(String.class);

        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doReturn(userDto).when(userMapper).toUserDto(user);
        doCallRealMethod().when(passwordEncoder).matches(rawPasswordCaptor.capture(), encodedPasswordCaptor.capture());

        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> userService.updateUser(newUserDto)
        );

        String capturedRawOldPassword = rawPasswordCaptor.getValue();
        String capturedEncodedOldPassword = encodedPasswordCaptor.getValue();

        verify(userRepository).findById(userId);
        verify(userMapper).toUserDto(user);
        verify(passwordEncoder).matches(capturedRawOldPassword, capturedEncodedOldPassword);

        verifyNoMoreInteractions(userRepository, userMapper, passwordEncoder);

        assertEquals(newPassword, capturedRawOldPassword);
        assertEquals(encodedPassword, capturedEncodedOldPassword);

        assertNotNull(thrownException);
        assertEquals(ExceptionId.INVALID_AUTHENTICATION, thrownException.getId());
    }

    @Test
    public void testUpdateUserFailedOnExistingUsername() {
        UserDto newUserDto = new UserDto();
        newUserDto.setId(userDto.getId());
        newUserDto.setName(userDto.getName());
        newUserDto.setSurname(userDto.getSurname());
        newUserDto.setPassword(password);
        newUserDto.setPhoneNumber(newPhoneNumber);

        User userWithNewPhoneNumber = new User();
        userWithNewPhoneNumber.setId(111L);
        userWithNewPhoneNumber.setName("Joseph");
        userWithNewPhoneNumber.setSurname("Biden");
        userWithNewPhoneNumber.setPassword(encodedNewPassword);
        userWithNewPhoneNumber.setPhoneNumber(newPhoneNumber);

        UserDto userDtoWithNewPhoneNumber = new UserDto();
        userDtoWithNewPhoneNumber.setId(userWithNewPhoneNumber.getId());
        userDtoWithNewPhoneNumber.setName(userWithNewPhoneNumber.getName());
        userDtoWithNewPhoneNumber.setSurname(userWithNewPhoneNumber.getSurname());
        userDtoWithNewPhoneNumber.setPassword(userWithNewPhoneNumber.getPassword());
        userDtoWithNewPhoneNumber.setPhoneNumber(userWithNewPhoneNumber.getPhoneNumber());

        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doReturn(userDto).when(userMapper).toUserDto(user);
        doReturn(userWithNewPhoneNumber).when(userRepository).findByPhoneNumber(newPhoneNumber);
        doReturn(userDtoWithNewPhoneNumber).when(userMapper).toUserDto(userWithNewPhoneNumber);

        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> userService.updateUser(newUserDto)
        );

        verify(userRepository).findById(userId);
        verify(userMapper).toUserDto(user);
        verify(userRepository).findByPhoneNumber(newPhoneNumber);
        verify(userMapper).toUserDto(userWithNewPhoneNumber);

        verifyNoMoreInteractions(userRepository, userMapper, passwordEncoder);

        assertNotNull(thrownException);
        assertEquals(ExceptionId.USERNAME_RESERVED, thrownException.getId());
    }

    @Test
    public void testUpdateUserWithEntity() {
        doReturn(user).when(userRepository).save(user);

        User updatedUser = userService.updateUser(user);

        verify(userRepository).save(user);

        verifyNoMoreInteractions(userRepository, userMapper, passwordEncoder);

        assertEquals(user, updatedUser);
    }
}
