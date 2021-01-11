package eu.brickpics.staffsys.util;

import eu.brickpics.staffsys.punishment.PunishmentType;
import me.schlaubi.kaesk.api.ArgumentDeserializer;
import me.schlaubi.kaesk.api.converters.Converters;
import org.jetbrains.annotations.NotNull;

public class ConverterUtil {

    @NotNull
    public static ArgumentDeserializer<PunishmentType> punishmentTypeDeserializer() {
        return Converters.newEnumDeserializer(PunishmentType[]::new);
    }
}
