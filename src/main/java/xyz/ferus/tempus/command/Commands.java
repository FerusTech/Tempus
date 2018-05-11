package xyz.ferus.tempus.command;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import xyz.ferus.tempus.Tempus;

import javax.annotation.Nonnull;

public class Commands {

    public static void register(@Nonnull final Tempus owner) {
        Sponge.getCommandManager().register(owner, CommandSpec.builder()
                .executor(new EventCommand())
                .permission("tempus.event")
                .description(Text.of("Creates and schedules an event."))
                .arguments(GenericArguments.flags()
                        .valueFlag(GenericArguments.integer(Text.of("year")), "-year")
                        .valueFlag(GenericArguments.integer(Text.of("month")), "-month")
                        .valueFlag(GenericArguments.integer(Text.of("day")), "-day")
                        .valueFlag(GenericArguments.integer(Text.of("hour")), "-hour")
                        .valueFlag(GenericArguments.integer(Text.of("minute")), "-minute")
                        .valueFlag(GenericArguments.integer(Text.of("second")), "-second")

                        .buildWith(GenericArguments.string(Text.of("event_id"))))
                .build(), "event");

        Sponge.getCommandManager().register(owner, CommandSpec.builder()
                .executor(new TimeZoneCommand())
                .permission("tempus.timezone")
                .description(Text.of("Allows you to set (or view) your timezone."))
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("timezone"))))
                .build(), "timezone");
    }
}
