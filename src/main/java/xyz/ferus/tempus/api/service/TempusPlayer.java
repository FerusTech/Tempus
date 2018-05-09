package xyz.ferus.tempus.api.service;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.profile.GameProfile;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface TempusPlayer {

    @Nonnull
    UUID getUniqueId();

    @Nonnull
    default Optional<Player> getPlayer() {
        return Sponge.getServer().getPlayer(this.getUniqueId());
    }

    @Nonnull
    default GameProfile getProfile() throws ExecutionException, InterruptedException {
        return Sponge.getServer().getGameProfileManager().get(this.getUniqueId()).get();
    }

    @Nonnull
    ZoneId getZoneId();

    @Nonnull
    ZoneId setZoneId(@Nonnull final ZoneId zoneId);

    @Nonnull
    default ZoneId setZoneId(@Nonnull final String key) {
        return this.setZoneId(ZoneId.of(key));
    }

    @Nonnull
    default ZonedDateTime getTimeAt(@Nonnull final Instant instant) {
        return instant.atZone(this.getZoneId());
    }

    @Nonnull
    default ZonedDateTime getCurrentTime() {
        return this.getTimeAt(Instant.now());
    }

    @Nonnull
    default Instant getInstantAt(@Nonnull final ZonedDateTime time) {
        return time.toInstant();
    }

    @Nonnull
    default String formatTimeAt(@Nonnull final Instant instant, @Nonnull final DateTimeFormatter formatter) {
        return this.getTimeAt(instant).format(formatter);
    }

    @Nonnull
    default String formatCurrentTime(@Nonnull final DateTimeFormatter formatter) {
        return this.formatTimeAt(Instant.now(), formatter);
    }
}
