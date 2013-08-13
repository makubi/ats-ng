package at.makubi.pages;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import at.makubi.entities.Entry;
import at.makubi.entities.Identifier;
import at.makubi.entities.Translation;
import at.makubi.services.EntryService;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.*;

/**
 * Start page of application ats.
 */
public class Index
{
//    @Property
//    @Inject
//    @Symbol(SymbolConstants.TAPESTRY_VERSION)
//    private String tapestryVersion;
//
    @InjectComponent
    private Zone zone;
//
//    @Persist
//    @Property
//    private int clickCount;
//
//    @Inject
//    private AlertManager alertManager;
//
//    public String getCurrentTime()
//    {
//        return "A great day to learn Tapestry";
//    }
//
//    void onActionFromIncrement()
//    {
//        alertManager.info("Increment clicked");
//
//        clickCount++;
//    }
//
//    Object onActionFromIncrementAjax()
//    {
//        clickCount++;
//
//        alertManager.info("Increment (via Ajax) clicked");
//
//        return zone;
//    }

    @Property
    private Entry currentEntry;

    @Property
    private Translation currentText;

    @Inject
    @Property
    private EntryService entryService;

    @Property
    private int number;
    @Property
    private int subNumber;

    @Property
    private String language;
    @Property
    private String text;

    @Property
    private String metaInfo;
    @Property
    private int maxLength;

    Object onActionFromAddRandomEntry() {
        final int randomNumber = new Random().nextInt();

        final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        final StringBuilder sb = new StringBuilder();
        final Random random = new Random();

        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        final String randomString = sb.toString();

        Identifier identifier = new Identifier();
        identifier.setNumber(randomNumber);
        identifier.setSubNumber(randomNumber);

        Collection<Translation> translations = new ArrayList<Translation>();

        Translation translation = new Translation();
        translation.setCountryCode(randomString);
        translation.setText(randomString);
        translations.add(translation);

        Entry entry = new Entry();
        entry.setIdentifier(identifier);
        entry.setTexts(translations);
        entry.setMetaInformation(randomString);
        entry.setMaxLength(randomNumber);

        entryService.createEntry(entry);

        return zone;
    }

    Object onSuccessFromEntryForm() {
        Identifier identifier = new Identifier();
        identifier.setNumber(number);
        identifier.setSubNumber(subNumber);

        Collection<Translation> translations = new ArrayList<Translation>();

        Translation translation = new Translation();
        translation.setCountryCode(language);
        translation.setText(text);
        translations.add(translation);

        Entry entry = new Entry();
        entry.setIdentifier(identifier);
        entry.setTexts(translations);
        entry.setMetaInformation(metaInfo);
        entry.setMaxLength(maxLength);

        entryService.createEntry(entry);

        return zone;
    }

}
