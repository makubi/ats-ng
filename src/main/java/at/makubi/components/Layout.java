package at.makubi.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;

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

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "active"
                : null;
    }

    public String[] getPageNames()
    {
        return new String[]{"Index", "AddEntry", "Contact"};
    }
}
