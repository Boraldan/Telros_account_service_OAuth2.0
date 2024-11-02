package boraldan.account;

import boraldan.account.controller.UserController;
import boraldan.account.controller.feign.KeycloakFeign;
import boraldan.account.service.UserService;
import boraldan.entityrepo.dto.CreatUserDto;
import boraldan.entityrepo.dto.UserKeycloakDto;
import boraldan.entityrepo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private KeycloakFeign keycloakFeign;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        when(userService.findAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void createUser() {
        CreatUserDto creatUserDto = new CreatUserDto();
        creatUserDto.setUsername("testuser");
        User user = new User();
        when(userService.findByUsernameIgnoreCase(creatUserDto.getUsername())).thenReturn(Optional.empty());
        when(keycloakFeign.createUser(any())).thenReturn(ResponseEntity.ok("User created"));
        when(userService.save(any())).thenReturn(user);

        ResponseEntity<?> response = userController.createUser(creatUserDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void updatePassword() {
        UserKeycloakDto userKeycloakDto = new UserKeycloakDto();
        when(keycloakFeign.updatePassword(any())).thenReturn(ResponseEntity.ok(""));

        ResponseEntity<String> response = userController.updatePassword(userKeycloakDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password updated successfully.", response.getBody());
    }

    @Test
    void getUserById() {
        UUID id = UUID.randomUUID();
        User user = new User();
        when(userService.findById(id)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void deleteUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setUsername("testuser");
        when(userService.findById(id)).thenReturn(Optional.of(user));
        when(keycloakFeign.deleteUser(user.getUsername())).thenReturn(ResponseEntity.ok(""));

        ResponseEntity<?> response = userController.deleteUser(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteById(id);
    }
}
