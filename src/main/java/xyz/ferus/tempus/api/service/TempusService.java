package xyz.ferus.tempus.api.service;

import javax.annotation.Nonnull;
import java.time.ZoneId;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public interface TempusService {

    @Nonnull
    StoragePreference getStoragePreference();

    @Nonnull
    TempusPlayer get(@Nonnull final UUID uuid);

    @Nonnull
    Collection<TempusPlayer> getAll();

    @Nonnull
    default Collection<TempusPlayer> getAllBy(@Nonnull final ZoneId zoneId) {
        return this.getAll().stream()
                .filter(player -> player.getZoneId().equals(zoneId))
                .collect(Collectors.toSet());
    }
}
