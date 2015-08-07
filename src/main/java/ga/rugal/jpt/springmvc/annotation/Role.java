package ga.rugal.jpt.springmvc.annotation;

import ga.rugal.jpt.core.entity.Admin;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Rugal Bernstein
 */
@Target(
    {
        ElementType.TYPE,
        ElementType.METHOD
    })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Role
{

    Admin.Level[] value();
}
