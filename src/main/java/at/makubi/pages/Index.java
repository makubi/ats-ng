package at.makubi.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import at.makubi.entities.Zone;
import at.makubi.services.ZoneService;
import org.apache.tapestry5.annotations.*;
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
//    @InjectComponent
//    private Zone zone;
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
    private Zone currentZone;

    @Inject
    @Property
    private ZoneService zoneService;

    public Collection<String> getZones() {
        final Collection<String> zoneNames = new ArrayList<String>();

        for(Zone zone : zoneService.getZones()) {
            zoneNames.add(zone.getName());
        }

        return zoneNames;
    }
}
