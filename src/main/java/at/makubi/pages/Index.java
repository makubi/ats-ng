package at.makubi.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import at.makubi.entities.Entry;
import at.makubi.entities.Identifier;
import at.makubi.entities.Translation;
import at.makubi.services.EntryService;
import at.makubi.services.ZoneService;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Persist(PersistenceConstants.FLASH)
    @Property
    private int count;

    Object onActionFromAddRandomEntry() {
        Identifier identifier = new Identifier();
        identifier.setNumber(count++);
        identifier.setSubNumber(count);

        Collection<Translation> translations = new ArrayList<Translation>();

        Translation translation = new Translation();
        translation.setCountryCode("de");
        translation.setText("Hallo " + count);
        translations.add(translation);

        Entry entry = new Entry();
        entry.setIdentifier(identifier);
        entry.setTexts(translations);
        entry.setMetaInformation("meta inf");
        entry.setMaxLength(10);

        entryService.createEntry(entry);

        return zone;
    }

}
