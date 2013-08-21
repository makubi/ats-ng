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
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Grid;
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
    private Translation currentTranslation;

    @Inject
    @Property
    private EntryService entryService;

    @Property
    private String searchBox;

    @Property
    @Persist
    private Iterable<Entry> foundEntries;

    Object onSuccessFromSearchForm() {
        foundEntries = entryService.getAllEntriesWithText(searchBox);

        return zone;
    }

}
