package xyz.ferus.tempus.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FormatUtil {

    private static final Map<String, DateTimeFormatter> FORMATS = Maps.newHashMap();

    private static final Map<Integer, DateTimeFormatter> ORDINAL_NOT_TH;
    private static final DateTimeFormatter ORDINAL_TH;
    static {
        ORDINAL_TH = DateTimeFormatter.ofPattern("EE, MMMM dd'th' h:mma");

        final DateTimeFormatter st = DateTimeFormatter.ofPattern("EE, MMMM dd'st' uuuu 'at' h:mma");
        final DateTimeFormatter nd = DateTimeFormatter.ofPattern("EE, MMMM dd'nd' uuuu 'at' h:mma");
        final DateTimeFormatter rd = DateTimeFormatter.ofPattern("EE, MMMM dd'rd' uuuu 'at' h:mma");

        FORMATS.put("EE, MMMM dd'th' h:mma", ORDINAL_TH);
        FORMATS.put("EE, MMMM dd'st' uuuu 'at' h:mma", st);
        FORMATS.put("EE, MMMM dd'nd' uuuu 'at' h:mma", nd);
        FORMATS.put("EE, MMMM dd'rd' uuuu 'at' h:mma", rd);

        ORDINAL_NOT_TH = ImmutableMap.<Integer, DateTimeFormatter>builder()
                .put(1, st).put(21, st).put(31, st)
                .put(2, nd).put(22, nd)
                .put(3, rd).put(23, rd)
                .build();
    }

    public static String format(@Nonnull final ZonedDateTime time, final String format) {
        final DateTimeFormatter computed = FORMATS.computeIfAbsent(format, DateTimeFormatter::ofPattern);
        return time.format(computed);
    }

    public static String fullWithOrdinal(@Nonnull final ZonedDateTime time) {
        return ORDINAL_NOT_TH.getOrDefault(time.getDayOfMonth(), ORDINAL_TH).format(time);
    }
}
