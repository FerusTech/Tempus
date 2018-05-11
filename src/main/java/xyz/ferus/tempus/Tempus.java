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
package xyz.ferus.tempus;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import xyz.ferus.tempus.command.Commands;
import xyz.ferus.tempus.service.ZonedService;
import xyz.ferus.tempus.service.impl.SimpleZonedService;

import javax.annotation.Nonnull;

@Plugin(
        id = PluginInfo.ID,
        name = PluginInfo.NAME,
        version = PluginInfo.VERSION,
        description = PluginInfo.DESCRIPTION,
        url = PluginInfo.URL,
        dependencies = {
                @Dependency(id = "spongeapi")
        },
        authors = {
                "Nicholas Badger (FerusGrim)"
        }
)
public class Tempus {

    public Tempus() {}

    @Listener
    public void onGamePreInitialization(@Nonnull final GamePreInitializationEvent event) {
        Sponge.getServiceManager().setProvider(this, ZonedService.class, new SimpleZonedService());
        Commands.register(this);
    }
}
