package xyz.ferus.tempus.command;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Identifiable;
import xyz.ferus.tempus.service.EventInstant;
import xyz.ferus.tempus.service.ZonedObject;
import xyz.ferus.tempus.service.ZonedService;
import xyz.ferus.tempus.service.impl.SimpleEventInstant;
import xyz.ferus.tempus.util.FormatUtil;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class EventCommand implements CommandExecutor {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull final CommandSource src, @Nonnull final CommandContext args) throws CommandException {
        final Optional<ZonedService> serviceCheck = Sponge.getServiceManager().provide(ZonedService.class);
        if (!serviceCheck.isPresent()) {
            throw new CommandException(Text.of(TextColors.RED, "There is no registered ZonedService!"));
        }
        final ZonedService service = serviceCheck.get();
        final ZoneId zoneId = service.getPlayerZone(src);
        final ZonedDateTime currentTime = ZonedDateTime.now(zoneId);

        final Optional<String> eventId = args.getOne("event_id");
        if (!eventId.isPresent()) {
            throw new CommandException(Text.of(TextColors.RED, "Failed to provide an Event ID."));
        }

        final ZonedDateTime providedTime;
        if (args.hasAny("year") || args.hasAny("month") || args.hasAny("day") || args.hasAny("hour") || args.hasAny("minute") || args.hasAny("second")) {
            final Optional<EventInstant> eventCheck = service.getEvent(eventId.get());
            if (eventCheck.isPresent()) {
                if (!src.hasPermission("tempus.event.edit")) {
                    throw new CommandException(Text.of(TextColors.RED, "You do not have permission to edit events!"));
                }

                providedTime = getProvidedTime(eventCheck.get().getInstant().atZone(zoneId),
                        args.getOne("year"),
                        args.getOne("month"),
                        args.getOne("day"),
                        args.getOne("hour"),
                        args.getOne("minute"),
                        args.getOne("second")
                );
            } else {
                if (!src.hasPermission("tempus.event.create")) {
                    throw new CommandException(Text.of(TextColors.RED, "You do not have permission to create events!"));
                }

                providedTime = getProvidedTime(currentTime,
                        args.getOne("year"),
                        args.getOne("month"),
                        args.getOne("day"),
                        args.getOne("hour"),
                        args.getOne("minute"),
                        args.getOne("second")
                );
            }
        } else {
            if (service.getEvent(eventId.get()).isPresent()) {
                if (!src.hasPermission("tempus.event.view")) {
                    throw new CommandException(Text.of(TextColors.RED, "You do not have permission to view events!"));
                }

                final EventInstant event = service.getEvent(eventId.get()).get();
                src.sendMessage(Text.of(TextColors.YELLOW, "Event ",
                        TextColors.GREEN, event.getId(),
                        TextColors.YELLOW, " is scheduled for: ",
                        TextColors.GREEN, FormatUtil.fullWithOrdinal(event.getInstant().atZone(zoneId))
                ));

                return CommandResult.success();
            } else if (!src.hasPermission("tempus.event.create")) {
                throw new CommandException(Text.of(TextColors.RED, "You do not have permission to create events!"));
            }

            providedTime = currentTime.plusDays(1); // If no arguments are given, assume one day in the future.
        }

        service.addEvent(new SimpleEventInstant(eventId.get(), providedTime.toInstant()));

        src.sendMessage(Text.of(TextColors.YELLOW, "Created event ",
                TextColors.GREEN, eventId.get(),
                TextColors.YELLOW, " scheduled for: ",
                TextColors.GREEN, FormatUtil.fullWithOrdinal(providedTime)
        ));

        return CommandResult.success();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private static ZonedDateTime getProvidedTime(@Nonnull final ZonedDateTime currentTime,
                                                 @Nonnull final Optional<Integer> oYear,
                                                 @Nonnull final Optional<Integer> oMonth,
                                                 @Nonnull final Optional<Integer> oDay,
                                                 @Nonnull final Optional<Integer> oHour,
                                                 @Nonnull final Optional<Integer> oMinute,
                                                 @Nonnull final Optional<Integer> oSecond) {
        return LocalDateTime.of(
                oYear.orElse(currentTime.getYear()),
                oMonth.orElse(currentTime.getMonthValue()),
                oDay.orElse(currentTime.getDayOfMonth()),
                oHour.orElse(currentTime.getHour()),
                oMinute.orElse(currentTime.getMinute()),
                oSecond.orElse(currentTime.getSecond())
        ).atZone(currentTime.getZone());
    }
}
