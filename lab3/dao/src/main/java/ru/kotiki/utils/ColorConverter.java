package ru.kotiki.utils;

import ru.kotiki.entities.Color;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ColorConverter  implements AttributeConverter<Color, String> {

    public String convertToDatabaseColumn(Color value) {
        return (value == null) ? null : value.getColor();
    }

    public Color convertToEntityAttribute(String value) {
        return (value == null) ? null : Color.fromColor(value);
    }
}
