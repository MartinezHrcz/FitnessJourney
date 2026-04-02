package hu.hm.fitjourneyapi.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserProfilePictureDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private hu.hm.fitjourneyapi.security.JwtUtil jwtUtil;

    @MockitoBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @MockitoBean
    private org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMappingContext;

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
    void usernameAvailable_ReturnsAvailabilityWithoutAuthentication() throws Exception {
        when(userService.isUsernameAvailable("herczegmartinez")).thenReturn(true);

        mockMvc.perform(get("/api/user/username-available").param("username", "herczegmartinez"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("herczegmartinez"))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void usernameAvailable_BlankUsername_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/user/username-available").param("username", "   "))
                .andExpect(status().isBadRequest());
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
        UserUpdateDTO updateDTO = UserUpdateDTO.builder()
                .name("Updated Name")
                .email("test@example.com")
                .weightInKg((float)80)
                .heightInCm((float)180)
                .birthday(LocalDate.now().minusYears(20))
                .build();
        UserDTO responseDTO = UserDTO.builder().name("Updated Name").build();


        when(userService.updateUser(eq(userId), any(UserUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/user/{id}", userId)
                        .with(csrf())
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

        mockMvc.perform(delete("/api/user").param("id", userId.toString()).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "11111111-1111-1111-1111-111111111111", roles = "USER")
    void getProfilePictureByUserId_Success_ReturnsImageBytes() throws Exception {
        UUID userId = UUID.randomUUID();
        byte[] imageData = "img".getBytes();
        UserProfilePictureDTO profilePictureDTO = new UserProfilePictureDTO(imageData, "image/png");

        when(userService.getProfilePicture(userId)).thenReturn(profilePictureDTO);

        mockMvc.perform(get("/api/user/profile-picture/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().bytes(imageData))
                .andExpect(content().contentType("image/png"));
    }

    @Test
    @WithMockUser(username = "11111111-1111-1111-1111-111111111111", roles = "USER")
    void getProfilePictureByUserId_MissingPicture_ReturnsNoContent() throws Exception {
        UUID userId = UUID.randomUUID();

        when(userService.getProfilePicture(userId)).thenReturn(null);

        mockMvc.perform(get("/api/user/profile-picture/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "11111111-1111-1111-1111-111111111111", roles = "USER")
    void getCurrentUserProfilePicture_Success_ReturnsImageBytes() throws Exception {
        UUID currentUserId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        byte[] imageData = "me-img".getBytes();
        UserProfilePictureDTO profilePictureDTO = new UserProfilePictureDTO(imageData, "image/jpeg");

        when(userService.getProfilePicture(currentUserId)).thenReturn(profilePictureDTO);

        mockMvc.perform(get("/api/user/profile-picture/me"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(imageData))
                .andExpect(content().contentType("image/jpeg"));
    }
}