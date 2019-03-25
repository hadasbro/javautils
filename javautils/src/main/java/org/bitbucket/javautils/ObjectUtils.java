package org.bitbucket.javautils;
import de.sandkastenliga.tools.projector.core.Projector;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "WeakerAccess"})
final public class ObjectUtils {

    private static Projector projector;

    static {
        projector = new Projector();
    }

    /**
     * convertPojoToDto
     *
     * POJO collection -> DTO collection
     * via Projector
     * @see "https://github.com/glaures/modelprojector"
     *
     * @param targetClass -
     * @param post -
     * @param <T> -
     * @param <S> -
     * @return <T extends DtoObject, S extends EntityTag> List<T>
     */
    public static <T extends DtoObject, S extends EntityTag> List<T> convertPojoToDto(Class<T> targetClass, Collection<S> post) {

        return post
                .stream()
                .map(p -> ObjectUtils.convertPojoToDto(targetClass, p))
                .collect(Collectors.toList());

    }

    /**
     * convertPojoToDto
     *
     * POJO -> DTO
     * @see "https://github.com/glaures/modelprojector"
     *
     * @param targetClass -
     * @param post -
     * @param <T> -
     * @param <S> -
     * @return  <T extends DtoObject, S extends EntityTag> T
     */
    public static <T extends DtoObject, S extends EntityTag> T convertPojoToDto(Class<T> targetClass, S post) {
        return projector.project(post, targetClass);
    }

    public static <T, S> T convert(Class<T> targetClass, S post) {
        return projector.project(post, targetClass);
    }

}
