package ru.kotiki.dto;

import org.springframework.stereotype.Component;
import ru.kotiki.entities.Kotik;
import ru.kotiki.entities.Owner;

@Component
public class Mapper {

    public KotikDto toKotikDto(Kotik kotik) {
        KotikDto kotikDto = new KotikDto(
                kotik.getName(),
                kotik.getDateOfBirth(),
                kotik.getBreed(),
                kotik.getColor(),
                kotik.getOwnerId()
        );
        kotikDto.setId(kotik.getId());
        return kotikDto;
    }

    public Kotik toKotik(KotikDto kotikDto) {
        Kotik kotik = new Kotik(
                kotikDto.getName(),
                kotikDto.getDateOfBirth(),
                kotikDto.getBreed(),
                kotikDto.getColor(),
                kotikDto.getOwnerId()
        );
        return kotik;
    }

    public OwnerDto toOwnerDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto(
                owner.getName(),
                owner.getDateOfBirth()
        );
        ownerDto.setId(owner.getId());
        return ownerDto;
    }

    public Owner toOwner(OwnerDto ownerDto) {
        Owner owner = new Owner(
                ownerDto.getName(),
                ownerDto.getDateOfBirth()
        );
        return owner;
    }
}
