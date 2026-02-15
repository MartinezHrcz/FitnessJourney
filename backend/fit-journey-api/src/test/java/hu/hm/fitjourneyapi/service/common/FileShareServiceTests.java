package hu.hm.fitjourneyapi.service.common;

import hu.hm.fitjourneyapi.services.implementation.common.FileShareServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileShareServiceTests {

    private FileShareServiceImpl fileShareService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileShareService = new FileShareServiceImpl(tempDir.toString());
    }

    @Test
    void store_ValidFile_ReturnsFilenameAndExists() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );

        String savedFilename = fileShareService.store(mockFile);

        assertNotNull(savedFilename);
        assertTrue(savedFilename.contains("test-image.jpg"));
        assertTrue(fileShareService.exists(savedFilename));
    }

    @Test
    void store_EmptyFile_ThrowsException() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", new byte[0]);

        assertThrows(RuntimeException.class, () -> fileShareService.store(emptyFile));
    }

    @Test
    void loadAsResource_Success() {
        MockMultipartFile mockFile = new MockMultipartFile("file", "load-me.txt", "text/plain", "hello".getBytes());
        String filename = fileShareService.store(mockFile);

        Resource resource = fileShareService.loadAsResource(filename);

        assertNotNull(resource);
        assertTrue(resource.exists());
        assertEquals(filename, resource.getFilename());
    }

    @Test
    void loadAsResource_FileNotFound_ThrowsException() {
        assertThrows(RuntimeException.class, () -> fileShareService.loadAsResource("non-existent.png"));
    }

    @Test
    void delete_FileDeleted_ReturnsFalseOnExists() {
        MockMultipartFile mockFile = new MockMultipartFile("file", "delete-me.txt", "text/plain", "data".getBytes());
        String filename = fileShareService.store(mockFile);
        assertTrue(fileShareService.exists(filename));

        fileShareService.delete(filename);

        assertFalse(fileShareService.exists(filename));
    }

    @Test
    void init_CreatesDirectoryIfMissing() {
        Path nestedPath = tempDir.resolve("new-folder");
        new FileShareServiceImpl(nestedPath.toString());

        assertTrue(nestedPath.toFile().exists());
        assertTrue(nestedPath.toFile().isDirectory());
    }
}