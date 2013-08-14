package at.makubi.module.importer.lingua;

import at.makubi.entities.Entry;
import at.makubi.entities.Identifier;
import at.makubi.entities.Translation;
import at.makubi.module.importer.ImportModule;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LinguaBaseImportModule implements ImportModule {

    @Override
    public Iterable<Entry> getEntriesForImport(File file) {
        Collection<Entry> entries = new ArrayList<Entry>();

        try {

            List<LinguaEntry> linguaEntries = getLinguaEntries(file);

            for(LinguaEntry linguaEntry : linguaEntries) {
                Entry entry = new Entry();

                Collection<Translation> translations = new ArrayList<Translation>();
                Translation translation = new Translation();
                translation.setCountryCode("de");
                translation.setText(linguaEntry.getText());
                translations.add(translation);

                Identifier identifier = new Identifier();
                identifier.setNumber(linguaEntry.getIdentifier());
                identifier.setSubNumber(getSubNumber(linguaEntry, linguaEntries));

                entry.setTexts(translations);
                entry.setMaxLength(linguaEntry.getMaxLength());
                entry.setIdentifier(identifier);

                entries.add(entry);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return entries;
    }

    private List<LinguaEntry> getLinguaEntries(File file) throws IOException {
        List<LinguaEntry> linguaEntries = new ArrayList<LinguaEntry>();

        List<String> lines = Files.readLines(file, Charsets.ISO_8859_1);

        for(String line : lines) {
            Pattern pattern = Pattern.compile("[\\t\\s]*([0-9]+),[\\t\\s]*([0-9]+),[\\t\\s]*\"(.*)\",");
            Matcher matcher = pattern.matcher(line);

            if(matcher.matches()) {
                LinguaEntry linguaEntry = new LinguaEntry(Long.parseLong(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3));

                linguaEntries.add(linguaEntry);
            }
        }

        return linguaEntries;
    }

    private int getSubNumber(LinguaEntry linguaEntry, Collection<LinguaEntry> linguaEntries) {
        String entryText = linguaEntry.getText();
        long entryNumber = linguaEntry.getIdentifier();
        int maxLength = linguaEntry.getMaxLength();

        int count = 0;

        for(LinguaEntry entry : linguaEntries) {
            if(entry.getIdentifier() == entryNumber) {
                count++;

                if(entry.getText().equals(entryText) && entry.getMaxLength() == maxLength) {
                    if(count == 0) {
                        throw new RuntimeException("These two texts match in number, maxLength, text: " + entryNumber + " / " + entry.getIdentifier());
                    }

                    return count;
                }
            }


        }

        throw new RuntimeException(linguaEntry.getText() + " not found");
    }
}
