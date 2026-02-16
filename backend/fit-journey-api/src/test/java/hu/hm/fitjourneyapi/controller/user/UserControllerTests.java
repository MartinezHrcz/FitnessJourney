package hu.hm.fitjourneyapi.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "USER")
    void getUser_ById_ReturnsUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = UserDTO.builder().id(userId).name("John Doe").build();

        when(userService.getUserById(userId)).thenReturn(userDTO);

        mockMvc.perform(get("/api/user/search").param("id", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getUser_ByEmail_ReturnsUser() throws Exception {
        String email = "test@fitjourney.hu";
        UserDTO userDTO = UserDTO.builder().email(email).build();

        when(userService.getUserByEmail(email)).thenReturn(userDTO);

        mockMvc.perform(get("/api/user/search").param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getUser_NoParams_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/user/search"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateUser_Success_ReturnsUpdatedUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserUpdateDTO updateDTO = UserUpdateDTO.builder().name("Updated Name").build();

        UserDTO responseDTO = UserDTO.builder().name("Updated Name").build();


        when(userService.updateUser(eq(userId), any(UserUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteUser_UserNotFound_Returns404() throws Exception {
        UUID userId = UUID.randomUUID();

        doThrow(new UserNotFound("...")).when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/user").param("id", userId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUsers_Anonymous_ReturnsForbidden() throws Exception {
        // Testing that security is actually working without @WithMockUser
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isForbidden());
    }
}