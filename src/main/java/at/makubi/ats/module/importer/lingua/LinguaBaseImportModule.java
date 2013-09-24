package at.makubi.ats.module.importer.lingua;

import at.makubi.ats.entities.Entry;
import at.makubi.ats.entities.Identifier;
import at.makubi.ats.entities.Language;
import at.makubi.ats.entities.Translation;
import at.makubi.ats.module.importer.ImportModule;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LinguaBaseImportModule implements ImportModule {

    private Language getLanguageForGerman(Iterable<Language> availableLanguages) {
        for(Language language : availableLanguages) {
            if("de".equals(language.getCode())) {
                return language;
            }
        }

        throw new RuntimeException("Unable to find Language for 'de'");
    }

    @Override
    public Iterable<Entry> getEntriesForImport(File file, Iterable<Language> availableLanguages) {
        final Language de = getLanguageForGerman(availableLanguages);

        final Collection<Entry> entries = new ArrayList<Entry>();

        try {
            final List<LinguaEntry> linguaEntries = Collections.unmodifiableList(getLinguaEntries(file));

            for(int i = 0; i < linguaEntries.size(); i++) {
                final LinguaEntry linguaEntry = linguaEntries.get(i);

                final Entry entry = new Entry();

                final Collection<Translation> translations = new ArrayList<Translation>();
                final Translation translation = new Translation();
                translation.setLanguage(de);
                translation.setText(linguaEntry.getText());
                translations.add(translation);

                final Identifier identifier = new Identifier();
                final long linguaNumber = linguaEntry.getIdentifier();
                final long linguaSubNumber = getSubNumber(linguaEntry, linguaEntries.subList(0, i));
                identifier.setText(linguaNumber + "." + (linguaSubNumber < 10 ? "0" + linguaSubNumber : linguaSubNumber));

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
        final List<LinguaEntry> linguaEntries = new ArrayList<LinguaEntry>();

        final Iterable<String> lines = Files.readLines(file, Charsets.ISO_8859_1);

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

    private int getSubNumber(LinguaEntry linguaEntry, Collection<LinguaEntry> yetAddedLinguaEntries) {
        final long entryNumber = linguaEntry.getIdentifier();

        int count = 1;

        for(LinguaEntry entry : yetAddedLinguaEntries) {
            if(entry.getIdentifier() == entryNumber) {
                count++;
            }
        }

        return count;
    }
}
