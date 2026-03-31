package hu.hm.fitjourneyapi.controller.common;

import hu.hm.fitjourneyapi.services.interfaces.common.FileShareService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileShareService fileShareService;

    public FileController(FileShareService fileShareService) {
        this.fileShareService = fileShareService;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        if (filename == null || filename.isBlank() || !fileShareService.exists(filename)) {
            return ResponseEntity.noContent().build();
        }

        Resource file;
        try {
            file = fileShareService.loadAsResource(filename);
        } catch (RuntimeException ex) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(file);
    }
}
