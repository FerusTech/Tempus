/*
 * This file is part of Tempus, licensed under the MIT License (MIT).
 *
 * Copyright (c) FerusTech <https://github.com/FerusTech>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package xyz.ferus.tempus.service;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.util.Identifiable;

import javax.annotation.Nonnull;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

public interface ZonedService {

    @Nonnull
    Optional<ZonedPlayer> getPlayer(@Nonnull final UUID uuid);

    @Nonnull
    default ZoneId getPlayerZone(@Nonnull final UUID uuid) {
        return this.getPlayer(uuid)
                .map(ZonedObject::getZoneId)
                .orElse(ZoneId.systemDefault());
    }

    @Nonnull
    default ZoneId getPlayerZone(@Nonnull final User player) {
        return this.getPlayerZone(player.getUniqueId());
    }

    @Nonnull
    default ZoneId getPlayerZone(@Nonnull final CommandSource commandSource) {
        return commandSource instanceof Identifiable ?
                this.getPlayerZone(((Identifiable) commandSource).getUniqueId()) :
                ZoneId.systemDefault();
    }

    void addPlayer(@Nonnull final ZonedPlayer player);

    default void removePlayer(@Nonnull final ZonedPlayer player) {
        this.removePlayer(player.getUniqueId());
    }

    void removePlayer(@Nonnull final UUID uuid);

    @Nonnull
    Optional<EventInstant> getEvent(@Nonnull final String id);

    @Nonnull
    EventInstant getOrCreateEvent(@Nonnull final String id);

    void addEvent(@Nonnull final EventInstant event);

    void removeEvent(@Nonnull final String id);
}
