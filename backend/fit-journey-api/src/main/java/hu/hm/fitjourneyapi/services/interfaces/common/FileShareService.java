package hu.hm.fitjourneyapi.services.interfaces.common;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileShareService {

    String store(MultipartFile file);

    Resource loadAsResource(String filename);

    void delete(String filename);

    boolean exists(String filename);
}