package hu.hm.fitjourneyapi.controller.social;

import hu.hm.fitjourneyapi.dto.social.comment.CommentCreateDTO;
import hu.hm.fitjourneyapi.dto.social.comment.CommentDTO;
import hu.hm.fitjourneyapi.services.interfaces.social.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments() {
        return ResponseEntity.ok(commentService.getComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(commentService.getCommentById(id));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(commentService.getCommentsByPostId(id));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUser(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(commentService.getCommentsByUserId(id));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostAndUser(@PathVariable UUID postId, @PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(commentService.getCommentsByPostIdAndUserId(postId, userId));
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDTO> postComment(@RequestBody CommentCreateDTO commentDTO, @PathVariable UUID postId, @PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(commentService.createComment(commentDTO, postId, userId));

        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable UUID id, String content) {
        try {
            return ResponseEntity.ok(commentService.updateComment(id, content));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable UUID id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}