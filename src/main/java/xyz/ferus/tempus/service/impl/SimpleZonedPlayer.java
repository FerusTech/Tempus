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

import xyz.ferus.tempus.service.ZonedPlayer;

import javax.annotation.Nonnull;
import java.time.ZoneId;
import java.util.UUID;

public class SimpleZonedPlayer implements ZonedPlayer {

    @Nonnull private final UUID uniqueId;
    @Nonnull private ZoneId zoneId;

    public SimpleZonedPlayer(@Nonnull final UUID uniqueId) {
        this(uniqueId, ZoneId.systemDefault());
    }

    public SimpleZonedPlayer(@Nonnull final UUID uniqueId, @Nonnull final ZoneId zoneId) {
        this.uniqueId = uniqueId;
        this.zoneId = zoneId;
    }

    @Nonnull
    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    @Nonnull
    @Override
    public ZoneId getZoneId() {
        return this.zoneId;
    }

    @Nonnull
    @Override
    public ZoneId setZoneId(@Nonnull ZoneId zoneId) {
        final ZoneId old = this.zoneId;
        this.zoneId = zoneId;
        return old;
    }
}
