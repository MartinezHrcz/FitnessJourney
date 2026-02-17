package hu.hm.fitjourneyapi.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.controller.social.MessageController;
import hu.hm.fitjourneyapi.dto.user.AuthRequest;
import hu.hm.fitjourneyapi.dto.user.AuthResponse;
import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.services.interfaces.security.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_ShouldReturnAuthResponse() throws Exception {
        UserCreateDTO createDTO = UserCreateDTO.builder().email("test@example.com").password("password").build();

        AuthResponse mockResponse = new AuthResponse(new UserDTO(), "mock-jwt-token");

        when(authService.register(any(UserCreateDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }

    @Test
    void login_ShouldReturnAuthResponse() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("test");
        authRequest.setPassword("password");
        AuthResponse mockResponse = new AuthResponse(new UserDTO(), "mock-jwt-token");

        when(authService.login(any(AuthRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }
}