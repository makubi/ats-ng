package at.makubi.ats.module.importer.lingua;

public class LinguaEntry {

    private final long identifier;
    private final int maxLength;
    private final String text;

    public LinguaEntry(long identifier, int maxLength, String text) {
        this.identifier = identifier;
        this.maxLength = maxLength;
        this.text = text;
    }

    public long getIdentifier() {
        return identifier;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getText() {
        return text;
    }
}
