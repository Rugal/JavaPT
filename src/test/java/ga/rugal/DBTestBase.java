package ga.rugal;

import ga.JUnitSpringTestBase;
import org.junit.Before;
import org.junit.Ignore;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author rugal
 */
@ContextConfiguration(classes = config.ApplicationContext.class)
@Ignore
public abstract class DBTestBase extends JUnitSpringTestBase
{

    @Before
    public void setup()
    {

    }
}
