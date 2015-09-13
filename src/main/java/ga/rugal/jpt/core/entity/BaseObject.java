package ga.rugal.jpt.core.entity;

import com.google.gson.Gson;

/**
 *
 * @author Rugal Bernstein
 * @param <T>
 */
public abstract class BaseObject<T>
{

    private static final Gson GSON = new Gson();

    public T backToObject(Object data)
    {
        String json = GSON.toJson(data);
        return GSON.fromJson(json, this.getRealClass());
    }

    protected abstract Class<T> getRealClass();

}
