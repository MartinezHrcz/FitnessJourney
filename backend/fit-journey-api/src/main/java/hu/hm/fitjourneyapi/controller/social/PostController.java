package hu.hm.fitjourneyapi.controller.social;

import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.services.interfaces.social.PostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAll(Authentication authentication) {
        return ResponseEntity.ok(postService.getPosts(UUID.fromString(authentication.getName())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getById(@PathVariable UUID id, Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        try {
          return ResponseEntity.ok(postService.getPostById(id, currentUserId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostDTO>> getByUserId(Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        try {
          return ResponseEntity.ok(postService.getPostsByUserId(currentUserId));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestBody PostCreateDTO createDTO, Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        try {
            return ResponseEntity.ok(postService.createPost(createDTO, currentUserId));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> createWithImage(
            PostCreateDTO createDTO,
            @RequestPart(value = "image", required = false) MultipartFile image,
            Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        try {
            return ResponseEntity.ok(postService.createPostWithImage(createDTO, image, currentUserId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable UUID id, @RequestBody PostUpdateDTO updateDTO, Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        try{
            return ResponseEntity.ok(postService.updatePost(id, updateDTO,currentUserId));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        try{
            postService.deletePostById(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
