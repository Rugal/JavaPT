package ga.rugal.jpt.springmvc.multipart;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * http://stackoverflow.com/a/10041789/1242236
 *
 * @author Rugal Bernstein
 */
public class ExtendedMultipartResolver extends CommonsMultipartResolver
{

    private static final String MULTIPART = "multipart";

    private boolean isMultipartContent(HttpServletRequest request)
    {
        String httpMethod = request.getMethod().toLowerCase();
        // test for allowed methods here...
        String contentType = request.getContentType();
        return (contentType != null && contentType.toLowerCase().startsWith(MULTIPART));
    }

    @Override
    public boolean isMultipart(HttpServletRequest request)
    {
        return (request != null && isMultipartContent(request));
    }
}
