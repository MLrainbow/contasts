package com.example.easycontacts.data;

public class LetterItem implements ListItem {
    private String letter;

    public LetterItem(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }
}
