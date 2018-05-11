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
package xyz.ferus.tempus.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import xyz.ferus.tempus.service.EventInstant;
import xyz.ferus.tempus.service.ZonedPlayer;
import xyz.ferus.tempus.service.ZonedService;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class SimpleZonedService implements ZonedService {

    @Nonnull private final Map<UUID, ZonedPlayer> players;
    @Nonnull private final Set<EventInstant> events;

    public SimpleZonedService() {
        this.players = Maps.newHashMap();
        this.events = Sets.newHashSet();
    }

    @Nonnull
    @Override
    public Optional<ZonedPlayer> getPlayer(@Nonnull final UUID uuid) {
        return Optional.ofNullable(this.players.get(uuid));
    }

    @Override
    public void addPlayer(@Nonnull final ZonedPlayer player) {
        this.players.put(player.getUniqueId(), player);
    }

    @Override
    public void removePlayer(@Nonnull final UUID uuid) {
        this.players.remove(uuid);
    }

    @Nonnull
    @Override
    public Optional<EventInstant> getEvent(@Nonnull final String id) {
        return this.events.stream().filter(event -> event.getId().equalsIgnoreCase(id)).findFirst();
    }

    @Nonnull
    @Override
    public EventInstant getOrCreateEvent(@Nonnull final String id) {
        return new SimpleEventInstant(id);
    }

    @Override
    public void addEvent(@Nonnull final EventInstant event) {
        Preconditions.checkArgument(!this.getEvent(event.getId()).isPresent(),
                "Event with ID " + event.getId() + " has already been registered.");
        this.events.add(event);
    }

    @Override
    public void removeEvent(@Nonnull final String id) {
        this.events.removeIf(event -> event.getId().equalsIgnoreCase(id));
    }
}