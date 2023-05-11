package ru.kotiki.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.OwnerDto;
import ru.kotiki.entities.Color;
import ru.kotiki.services.KotikService;
import ru.kotiki.utils.ColorConverter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class KotikController {

    private final KotikService kotikService;

    @Autowired
    public KotikController(KotikService kotikService) {
        this.kotikService = kotikService;
    }

    @GetMapping("/api/kotiki/{kotikId}")
    public ResponseEntity<KotikDto> getById(@PathVariable long kotikId) {
        try {
            KotikDto kotikDto = kotikService.findById(kotikId);
            return ResponseEntity.ok(kotikDto);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/kotiki/all")
    public ResponseEntity<List<KotikDto>> getAll(@RequestParam(name = "color", required = false) String color) {
        List<KotikDto> kotiki;
        if (color != null) {
            try {
                ColorConverter converter = new ColorConverter();
                Color c = converter.convertToEntityAttribute(color);
                kotiki = kotikService.findByColor(c);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            kotiki = kotikService.findAll();
        }
        return ResponseEntity.ok(kotiki);
    }

    @PostMapping("/api/kotiki")
    public ResponseEntity<KotikDto> postKotik(@RequestBody KotikDto kotikDto) throws URISyntaxException {
        KotikDto kotik = kotikService.save(kotikDto);
        return ResponseEntity.created(new URI("/api/kotiki/" + kotik.getId())).build();
    }

    @DeleteMapping("/api/kotiki/{kotikId}")
    public ResponseEntity<Void> deleteKotik(@PathVariable long kotikId) {
        kotikService.delete(kotikId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/kotiki/{kotikId}/owner")
    public ResponseEntity<OwnerDto> findOwner(@PathVariable long kotikId) {
        OwnerDto owner = kotikService.findOwner(kotikId);
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/api/kotiki/{kotikId}/friends")
    public ResponseEntity<List<KotikDto>> getFriendsByKotikId(@PathVariable long kotikId) {
        List<KotikDto> friends = kotikService.findFriends(kotikId);
        return ResponseEntity.ok(friends);
    }

    @PostMapping("/api/kotiki/{kotikId}/friends")
    public ResponseEntity<Void> postFriends(@PathVariable long kotikId, @RequestBody FriendRequest friendRequest) throws URISyntaxException {
        kotikService.friend(kotikId, friendRequest.getKotikId());
        return ResponseEntity.created(new URI("/api/kotiki/" + kotikId + "/friends/" + friendRequest.getKotikId())).build();
    }

    @DeleteMapping("/api/kotiki/{kotik1Id}/friends/{kotik2Id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable long kotik1Id, @PathVariable long kotik2Id) {
        kotikService.unfriend(kotik1Id, kotik2Id);
        return ResponseEntity.ok().build();
    }
}
