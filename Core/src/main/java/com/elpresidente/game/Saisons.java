package com.elpresidente.game;

public enum Saisons {
    PRINTEMPS("Printemps"),
    ETE("été"),
    AUTOMNE("Automne"),
    HIVER("Hiver") ;

    private final String text;

    /**
     * @param text
     */
    Saisons(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

}
