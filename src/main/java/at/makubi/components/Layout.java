package at.makubi.components;

import at.makubi.Task;
import at.makubi.services.TaskService;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.corelib.mixins.ZoneRefresh;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;

import java.util.Collection;

/**
 * Layout component for pages of application ats.
 */
@Import(stylesheet = {"context:layout/bootstrap/css/bootstrap.css","context:layout/bootstrap/css/bootstrap-responsive.css"})
public class Layout
{
    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Inject
    private ComponentResources resources;

    @Property
    private Task currentTask;

    @Inject
    private TaskService taskService;

    @InjectComponent
    private Zone taskZone;

    public Collection<Task> getTasks() {
        return taskService.getTasks();
    }

    Object onRefreshTaskZone() {
        return taskZone.getBody();
    }

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "active"
                : null;
    }

    public String[] getPageNames()
    {
        return new String[]{"Index", "AddEntry", "Language", "Import", "Export"};
    }
}
