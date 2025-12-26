package hu.hm.fitjourneyapi.controller.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendCreateDTO;
import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.services.interfaces.social.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService FriendService;

    public FriendController(FriendService FriendService) {
        this.FriendService = FriendService;
    }

    @GetMapping
    public ResponseEntity<List<FriendDTO>> getFriends() {
        return ResponseEntity.ok(FriendService.getFriends());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FriendDTO> getFriend(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(FriendService.getFriendById(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<FriendDTO>> getFriendsByUserId(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(FriendService.getFriendsByUserId(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FriendDTO> createFriend(@RequestBody FriendCreateDTO friendDTO) {
        try{
            return ResponseEntity.ok(FriendService.createFriend(friendDTO));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFriend(@PathVariable UUID id) {
        try {
            FriendService.deleteFriend(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
