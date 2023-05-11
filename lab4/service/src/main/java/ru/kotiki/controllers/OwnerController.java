package ru.kotiki.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kotiki.dto.KotikDto;
import ru.kotiki.dto.OwnerDto;
import ru.kotiki.services.OwnerService;

import java.net.URI;
import java.util.List;

@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/owners/{ownerId}")
    public ResponseEntity<OwnerDto> getById(@PathVariable long ownerId) {
        OwnerDto ownerDto = ownerService.findById(ownerId);
        if (ownerDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownerDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/owners/{ownerId}/kotiki")
    public ResponseEntity<List<KotikDto>> getOwnerKotiki(@PathVariable long ownerId) {
        List<KotikDto> kotiki = ownerService.findKotikiListByOwnerId(ownerId);
        return ResponseEntity.ok(kotiki);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/owners")
    public ResponseEntity<OwnerDto> postOwner(@RequestBody OwnerDto ownerDto) throws Exception {
        ownerService.save(ownerDto);
        return ResponseEntity.created(new URI("/api/owners/" + ownerDto.getId())).build();
    }
}
