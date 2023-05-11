package ru.kotiki.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.OwnerDto;
import ru.kotiki.entities.Owner;
import ru.kotiki.services.OwnerService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/api/owners/{ownerId}")
    public ResponseEntity<OwnerDto> getById(@PathVariable long ownerId) {
        try {
            OwnerDto ownerDto = ownerService.findById(ownerId);
            return ResponseEntity.ok(ownerDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/owners/all")
    public ResponseEntity<List<OwnerDto>> getAll() {
        List<OwnerDto> owners = ownerService.findAll();
        return ResponseEntity.ok(owners);
    }

    @PostMapping("/api/owners")
    public ResponseEntity<OwnerDto> postOwner(@RequestBody OwnerDto ownerDto) throws URISyntaxException {
        OwnerDto owner = ownerService.save(ownerDto);
        return ResponseEntity.created(new URI("/api/owners/" + owner.getId())).build();
    }

    @DeleteMapping("/api/owners/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable long ownerId) {
        ownerService.delete(ownerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/owners/{ownerId}/kotiki")
    public ResponseEntity<List<KotikDto>> getKotiki(@PathVariable long ownerId) {
        List<KotikDto> kotiki = ownerService.findKotikiByOwnerId(ownerId);
        return ResponseEntity.ok(kotiki);
    }
}
