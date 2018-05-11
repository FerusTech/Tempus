package xyz.ferus.tempus.command;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import xyz.ferus.tempus.service.ZonedPlayer;
import xyz.ferus.tempus.service.ZonedService;
import xyz.ferus.tempus.service.impl.SimpleZonedPlayer;
import xyz.ferus.tempus.util.FormatUtil;

import javax.annotation.Nonnull;
import java.time.ZoneId;
import java.util.Optional;

public class TimeZoneCommand implements CommandExecutor {

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull final CommandSource src, @Nonnull final CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            throw new CommandException(Text.of(TextColors.RED, "Only a player can set their timezone."));
        }
        final Player player = (Player) src;

        final Optional<ZonedService> serviceCheck = Sponge.getServiceManager().provide(ZonedService.class);
        if (!serviceCheck.isPresent()) {
            throw new CommandException(Text.of(TextColors.RED, "There is no registered ZonedService!"));
        }
        final ZonedService service = serviceCheck.get();

        final Optional<String> timezoneArg = args.getOne("timezone");
        if (!timezoneArg.isPresent()) {
            final Optional<ZonedPlayer> zonedCheck = service.getPlayer(player.getUniqueId());
            if (zonedCheck.isPresent()) {
                player.sendMessage(Text.of(TextColors.YELLOW, "Your timezone is configured for: ",
                        TextColors.GREEN, zonedCheck.get().getZoneId().getId()));
            } else {
                player.sendMessage(Text.of(TextColors.YELLOW, "Your timezone is defaulting to system time: ",
                        TextColors.GREEN, ZoneId.systemDefault().getId()));
            }
        } else {
            try {
                final ZoneId zoneId = ZoneId.of(timezoneArg.get());
                final ZonedPlayer zonedPlayer = new SimpleZonedPlayer(player.getUniqueId(), zoneId);
                service.addPlayer(zonedPlayer);

                player.sendMessage(Text.of(TextColors.YELLOW, "You've set your timezone to ",
                        TextColors.GREEN, zoneId.getId(),
                        TextColors.YELLOW, ": ",
                        TextColors.GREEN, FormatUtil.fullWithOrdinal(zonedPlayer.getCurrentTime())));
            } catch (final Exception ignored) {
                throw new CommandException(Text.of(TextColors.RED, "You gave an invalid (or non-existent) timezone format!"));
            }
        }

        return CommandResult.success();
    }
}
