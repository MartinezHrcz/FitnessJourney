package hu.hm.fitjourneyapi.controller.common;

import hu.hm.fitjourneyapi.services.interfaces.common.FileShareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileShareService fileShareService;

    @Test
    @WithMockUser
    void getFile_MissingFile_ReturnsNoContent() throws Exception {
        when(fileShareService.exists(eq("missing.png"))).thenReturn(false);

        mockMvc.perform(get("/api/files/missing.png"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void getFile_ExistingFile_ReturnsOk() throws Exception {
        Resource resource = new ByteArrayResource(new byte[]{1, 2, 3});

        when(fileShareService.exists(eq("img.png"))).thenReturn(true);
        when(fileShareService.loadAsResource(eq("img.png"))).thenReturn(resource);

        mockMvc.perform(get("/api/files/img.png"))
                .andExpect(status().isOk());
    }
}
