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

import xyz.ferus.tempus.service.EventInstant;

import javax.annotation.Nonnull;
import java.time.Instant;

public class SimpleEventInstant implements EventInstant {

    @Nonnull private final String id;
    @Nonnull private Instant instant;

    public SimpleEventInstant(@Nonnull final String id) {
        this(id, Instant.now());
    }

    public SimpleEventInstant(@Nonnull final String id, @Nonnull final Instant instant) {
        this.id = id;
        this.instant = instant;
    }

    @Nonnull
    @Override
    public String getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public Instant getInstant() {
        return this.instant;
    }

    @Nonnull
    @Override
    public Instant setInstant(@Nonnull final Instant instant) {
        final Instant old = this.instant;
        this.instant = instant;
        return old;
    }
}
