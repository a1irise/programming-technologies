package ru.kotiki.entities;

public enum Color {

    BLACK("black"),
    GREY("grey"),
    CREAM("cream"),
    WHITE("white"),
    GINGER("ginger"),
    BROWN("brown");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public static Color fromColor(String color) {
         if (color.equalsIgnoreCase("black")) {
             return BLACK;
         } else if (color.equalsIgnoreCase("grey")) {
             return GREY;
         } else if (color.equalsIgnoreCase("cream")) {
             return CREAM;
         } else if (color.equalsIgnoreCase("white")) {
             return WHITE;
         } else if (color.equalsIgnoreCase("ginger")) {
             return GINGER;
         } else if (color.equalsIgnoreCase("brown")) {
             return BROWN;
         }
         throw new UnsupportedOperationException("Error: Color " + color + " is not supported.");
    }
}
