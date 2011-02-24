// $Id$
/*
 * WorldGuard
 * Copyright (C) 2010 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.sk89q.worldguard.bukkit.commands;

import com.sk89q.worldguard.bukkit.LoggerToChatHandler;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.commands.CommandHandler.CommandHandlingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Michael
 */
public class CommandReloadWG extends WgCommand {

    public boolean handle(CommandSender sender, String senderName, String command, String[] args, CommandHandler ch, WorldGuardPlugin wg) throws CommandHandlingException {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may use this command");
            return true;
        }

        Player player = (Player) sender;
        ch.checkPermission(player, "/reloadwg");
        ch.checkArgs(args, 0, 0);

        LoggerToChatHandler handler = new LoggerToChatHandler(player);
        handler.setLevel(Level.ALL);
        Logger minecraftLogger = Logger.getLogger("Minecraft");
        minecraftLogger.addHandler(handler);

        try {
            wg.loadConfiguration();
            wg.postReload();
            player.sendMessage("WorldGuard configuration reloaded.");
        } catch (Throwable t) {
            player.sendMessage("Error while reloading: "
                    + t.getMessage());
        } finally {
            minecraftLogger.removeHandler(handler);
        }

        return true;
    }
}