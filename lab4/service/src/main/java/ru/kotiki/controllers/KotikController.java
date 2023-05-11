package ru.kotiki.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.UserDto;
import ru.kotiki.entities.Color;
import ru.kotiki.entities.Kotik;
import ru.kotiki.services.KotikService;
import ru.kotiki.services.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class KotikController {

    private final KotikService kotikService;
    protected final UserService userService;

    @Autowired
    public KotikController(KotikService kotikService, UserService userService) {
        this.kotikService = kotikService;
        this.userService = userService;
    }

    @GetMapping("/api/kotiki/{kotikId}")
    public ResponseEntity<KotikDto> getById(@PathVariable long kotikId) {
        KotikDto kotikDto = kotikService.findById(kotikId);
        if (kotikDto == null) {
            return ResponseEntity.notFound().build();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return ResponseEntity.ok(kotikDto);
        } else {
            UserDto user = userService.findByUsername(auth.getName());
            if (Objects.equals(user.getOwnerId(), kotikDto.getOwnerId())) {
                return ResponseEntity.ok(kotikDto);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/api/kotiki")
    public ResponseEntity<List<KotikDto>> getAll(@RequestParam(name = "color", required = false) Color color) {
        List<KotikDto> kotiki;
        if (color != null) {
            kotiki = kotikService.findKotikiByColor(color);
        } else {
            kotiki = kotikService.findAll();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return ResponseEntity.ok(kotiki);
        } else {
            UserDto user = userService.findByUsername(auth.getName());
            List<KotikDto> ownerKotiki = new ArrayList<>();
            for (KotikDto kotik : kotiki) {
                if (Objects.equals(user.getOwnerId(), kotik.getOwnerId())) {
                    ownerKotiki.add(kotik);
                }
            }
            return ResponseEntity.ok(ownerKotiki);
        }
    }

    @PostMapping("/api/kotiki")
    public ResponseEntity<KotikDto> postKotik(@RequestBody KotikDto kotikDto) throws URISyntaxException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            kotikService.save(kotikDto);
            return ResponseEntity.created(new URI("/api/kotiki/" + kotikDto.getId())).build();
        } else {
            UserDto user = userService.findByUsername(auth.getName());
            if (Objects.equals(user.getOwnerId(), kotikDto.getOwnerId())) {
                kotikService.save(kotikDto);
                return ResponseEntity.created(new URI("/api/kotiki/" + kotikDto.getId())).build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/api/kotiki/{kotikId}/friends")
    public ResponseEntity<List<KotikDto>> getFriendsListByKotikId(@PathVariable long kotikId) {
        List<KotikDto> friends = kotikService.findFriendsByKotikId(kotikId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return ResponseEntity.ok(friends);
        } else {
            UserDto user = userService.findByUsername(auth.getName());
            KotikDto kotik = kotikService.findById(kotikId);
            if (user.getOwnerId() == kotik.getOwnerId()) {
                return ResponseEntity.ok(friends);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping("/api/kotiki/{kotikId}/friends")
    public ResponseEntity<Void> postFriends(@PathVariable long kotikId, @RequestBody FriendRequest friendRequest) throws URISyntaxException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            kotikService.friend(kotikId, friendRequest.getFriendKotikId());
            return ResponseEntity.created(new URI("/api/kotiki/" + kotikId + "/friends/" + friendRequest.getFriendKotikId())).build();
        } else {
            UserDto user = userService.findByUsername(auth.getName());
            KotikDto kotik1 = kotikService.findById(kotikId);
            KotikDto kotik2 = kotikService.findById(friendRequest.getFriendKotikId());
            if (Objects.equals(user.getOwnerId(), kotik1.getOwnerId()) || Objects.equals(user.getOwnerId(), kotik2.getOwnerId())) {
                kotikService.friend(kotikId, friendRequest.getFriendKotikId());
                return ResponseEntity.created(new URI("/api/kotiki/" + kotikId + "/friends/" + friendRequest.getFriendKotikId())).build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/api/kotiki/{kotikId}/friends/{friendKotikId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable long kotikId, @PathVariable long friendKotikId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            kotikService.unfriend(kotikId, friendKotikId);
            return ResponseEntity.ok().build();
        } else {
            UserDto user = userService.findByUsername(auth.getName());
            KotikDto kotik1 = kotikService.findById(kotikId);
            KotikDto kotik2 = kotikService.findById(friendKotikId);
            if (Objects.equals(user.getOwnerId(), kotik1.getOwnerId()) || Objects.equals(user.getOwnerId(), kotik2.getOwnerId())) {
                kotikService.unfriend(kotikId, friendKotikId);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}