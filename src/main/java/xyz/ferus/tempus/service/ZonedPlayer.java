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

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.profile.GameProfile;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface ZonedPlayer extends ZonedObject {

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
