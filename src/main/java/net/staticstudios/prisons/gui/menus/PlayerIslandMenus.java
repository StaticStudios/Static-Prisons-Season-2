package net.staticstudios.prisons.gui.menus;

public class PlayerIslandMenus {
    /*
    //Main island GUI
    public static void main() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                menuItems = new ArrayList<>();
                if (!playerData.getIfPlayerHasIsland()) {
                    String partOfAnIsland;
                    if (playerData.getIfPlayerHasIsland()) {
                        partOfAnIsland = ChatColor.RED + "You currently have an active cell.";
                    } else {
                        partOfAnIsland = ChatColor.AQUA + "You currently are not part of an cell.";
                    }
                    String command = ChatColor.AQUA + "/island invite " + player.getName();
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.GRASS_BLOCK, ChatColor.GREEN + "Create a new cell", ChatColor.GRAY + "This will create a new Skyblock", ChatColor.GRAY + "cell. You can only be a part of", ChatColor.GRAY + "one cell at a time.", "", partOfAnIsland));
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                    menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.COBBLESTONE, ChatColor.GREEN + "Join an cell", ChatColor.GRAY + "You will need to be invited", ChatColor.GRAY + "to an cell in order to join.", ChatColor.GRAY + "Ask the owner of the cell", ChatColor.GRAY + "to run the following command: ", command));
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                    menuItems.add(GUI.createLightGrayPlaceholderItem());
                    guiTitle = ChatColor.translateAlternateColorCodes('&', "&aCells ");
                } else {
                    menuItems.add(GUI.createMenuItem(identifier, Material.COMPASS, ChatColor.AQUA + "Go To Cell", ChatColor.GRAY + "-Warp to your cell"));
                    menuItems.add(GUI.createMenuItem(identifier, Material.BOOK, ChatColor.GREEN + "Cell Info", ChatColor.GRAY + "-View information about your cell"));
                    menuItems.add(GUI.createMenuItem(identifier, Material.PLAYER_HEAD, ChatColor.BLUE + "Cell Members", ChatColor.GRAY + "-View your cell members", ChatColor.GRAY + "-Promote/demote your cell members", ChatColor.GRAY + "-Kick/ban your cell members"));
                    menuItems.add(GUI.createMenuItem(identifier, Material.REDSTONE_TORCH, ChatColor.DARK_GRAY + "Cell Settings", ChatColor.GRAY + "-Allow/disallow visitors", ChatColor.GRAY + "-Allow/disallow invites", ChatColor.GRAY + "-Change cell member warp", ChatColor.GRAY + "-Change cell visitor warp"));
                    menuItems.add(GUI.createMenuItem(identifier, Material.RED_TERRACOTTA, ChatColor.RED + "Delete Your Cell", ChatColor.GRAY + "-Delete this cell", ChatColor.RED + "-THIS CANNOT BE UN-DONE!"));
                    guiTitle = ChatColor.translateAlternateColorCodes('&', "&aYour Cell");
                }
            }
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getIfPlayerHasIsland()) {
                    SkyBlockIsland island = playerData.getPlayerIsland();
                    island.teleportPlayerToMemberWarp(player);
                }
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getIfPlayerHasIsland()) {
                    SkyBlockIsland island = playerData.getPlayerIsland();
                    StringBuilder message = new StringBuilder(ChatColor.AQUA + island.getIslandName() + "'s Info:" + "\n");
                    message.append(ChatColor.GRAY).append("------------").append("\n");
                    message.append(ChatColor.LIGHT_PURPLE).append("Members (").append(island.getIslandPlayerUUIDS().size()).append("): ");
                    ServerData serverData = new ServerData();
                    for (int i = 0; i < island.getIslandPlayerUUIDS().size(); i++) {
                        String memberUUID = island.getIslandPlayerUUIDS().get(i);
                        String name;
                        if (Bukkit.getPlayer(UUID.fromString(memberUUID)) == null) {
                            name = ChatColor.GRAY + serverData.getPlayerNameFromUUID(memberUUID);
                        } else name = ChatColor.GREEN + serverData.getPlayerNameFromUUID(memberUUID);
                        message.append(name);
                        if (i + 1 != island.getIslandPlayerUUIDS().size()) {
                            message.append(", ");
                        }
                    }
                    message.append("\n");
                    message.append(ChatColor.LIGHT_PURPLE).append("Level: ").append(ChatColor.AQUA).append("null").append("\n");
                    message.append(ChatColor.LIGHT_PURPLE).append("Value: ").append(ChatColor.AQUA).append("null").append("\n");
                    message.append(ChatColor.LIGHT_PURPLE).append("Bank: ").append(ChatColor.AQUA).append("null").append("\n");
                    message.append(ChatColor.GRAY).append("------------").append("\n");
                    message.append(ChatColor.DARK_GRAY).append(ChatColor.ITALIC).append("ID: ").append(new SkyBlockIsland(playerData.getPlayerIslandUUID()).getUUID());
                    player.sendMessage(message.toString());
                    player.closeInventory();
                }
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getIfPlayerHasIsland()) {
                    GUI.getGUIPage("islandMembers").open(player);
                }
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getIfPlayerHasIsland()) {
                    SkyBlockIsland.createNewIsland(player);
                } else {
                    GUI.getGUIPage("islandSettings").open(player);
                }
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getIfPlayerHasIsland()) {
                    SkyBlockIsland island = playerData.getPlayerIsland();
                    if (!island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                        player.sendMessage(ChatColor.RED + "Only the cell owner can do this!");
                        return;
                    }
                    GUI.getGUIPage("islandDelete").open(player);
                }
            }
            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getIfPlayerHasIsland()) {
                    GUI.getGUIPage("islandInvites").open(player);
                }
            }
        };
        guiPage.identifier = "islandsMain";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&aCells");
        guiPage.onCloseGoToMenu = "main";
        guiPage.register();
    }
    //View and edit island members
    public static void members() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                menuItems = new ArrayList<>();
                for (String uuid : island.getIslandPlayerUUIDS()) {
                    String prefix;
                    String[] lore;
                    if (uuid.equals(player.getUniqueId().toString())) {
                        lore = new String[]{ChatColor.GRAY + "You cannot manage yourself."};
                    } else if (!island.getIslandOwnerUUID().equals(player.getUniqueId().toString()) && !island.getIslandManagerUUID().equals(player.getUniqueId().toString()) || island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                        lore = new String[]{ChatColor.GRAY + "Click to manage " + new ServerData().getPlayerNameFromUUID(uuid)};
                    } else {
                        lore = new String[]{ChatColor.GRAY + "You cannot manage this player."};
                    }
                    if (island.getIslandOwnerUUID().equals(uuid)) {
                        prefix = ChatColor.translateAlternateColorCodes('&', "&8[&cOwner&8] &b");
                    } else if (island.getIslandManagerUUID().contains(uuid)) {
                        prefix = ChatColor.translateAlternateColorCodes('&', "&8[&9Manager&8] &b");
                    } else if (island.getIslandAdminUUIDS().contains(uuid)) {
                        prefix = ChatColor.translateAlternateColorCodes('&', "&8[&dAdmin&8] &b");
                    } else {
                        prefix = ChatColor.translateAlternateColorCodes('&', "&8[&aMember&8] &b");
                    }
                    ItemStack item = GUI.createMenuItemOfPlayerSkull(identifier, Bukkit.getOfflinePlayer(UUID.fromString(uuid)), prefix + new ServerData().getPlayerNameFromUUID(uuid), lore);
                    ItemMeta meta = item.getItemMeta();
                    meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, uuid);
                    item.setItemMeta(meta);
                    menuItems.add(item);
                }
            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (!island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                    if (player.getUniqueId().toString().equals(uuid)) {
                        player.sendMessage(ChatColor.RED + "You cannot edit yourself silly!");
                        return;
                    }
                    if (!island.getIslandPlayerUUIDS().contains(uuid)) {
                        player.sendMessage(ChatColor.RED + "This user is no longer a part of your cell!");
                        return;
                    }
                    if (!island.getIslandPlayerUUIDS().contains(player.getUniqueId().toString())) {
                        player.sendMessage(ChatColor.RED + "You are no longer a part of this cell!");
                        return;
                    }

                    //Permission checks to make sure players are not editing users with a higher rank then them
                    if (island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                        if (island.getIslandOwnerUUID().equals(uuid)) {
                            player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                            return;
                        }
                    }
                    if (island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                        if (!island.getIslandMemberUUIDS().contains(uuid)) {
                            player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                            return;
                        }
                    }


                    GUI.getGUIPage("islandMembersEdit").args = uuid;
                    GUI.getGUIPage("islandMembersEdit").open(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                }
            }
        };
        guiPage.identifier = "islandMembers";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&aYour Cell Members");
        guiPage.onCloseGoToMenu = "islandsMain";
        guiPage.register();
    }

    //Edit island members
    public static void membersEdit() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String uuid = args;
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                String prefix;
                if (island.getIslandOwnerUUID().equals(uuid)) {
                    prefix = ChatColor.translateAlternateColorCodes('&', "&8[&cOwner&8] &b");
                } else if (island.getIslandManagerUUID().contains(uuid)) {
                    prefix = ChatColor.translateAlternateColorCodes('&', "&8[&9Manager&8] &b");
                } else if (island.getIslandAdminUUIDS().contains(uuid)) {
                    prefix = ChatColor.translateAlternateColorCodes('&', "&8[&dAdmin&8] &b");
                } else {
                    prefix = ChatColor.translateAlternateColorCodes('&', "&8[&aMember&8] &b");
                }
                menuItems = new ArrayList<>();
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.REDSTONE_BLOCK, ChatColor.DARK_RED + "Ban " + new ServerData().getPlayerNameFromUUID(uuid), ChatColor.GRAY + "This user will be kicked from your cell", ChatColor.GRAY + "and will not be able to join back!", "", ChatColor.DARK_GRAY + "Manager+ can do this"));
                menuItems.add(GUI.createMenuItem(identifier, Material.RED_STAINED_GLASS, ChatColor.RED + "Kick " + new ServerData().getPlayerNameFromUUID(uuid), ChatColor.GRAY + "This user will be kicked from your cell", ChatColor.GRAY + "and will need an invite to join back!", "", ChatColor.DARK_GRAY + "Admin+ can do this"));
                menuItems.add(GUI.createMenuItem(identifier, Material.COAL, ChatColor.GREEN + "Make " + new ServerData().getPlayerNameFromUUID(uuid) + " a member", ChatColor.GRAY + "This user will get the cell rank: Member.", ChatColor.GRAY + "This is the lowest cell rank.", "", ChatColor.DARK_GRAY + "Admin+ can do this"));
                menuItems.add(GUI.createMenuItemOfPlayerSkull(identifier, Bukkit.getOfflinePlayer(UUID.fromString(uuid)), prefix + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid), ChatColor.GRAY + "Currently modifying " + new ServerData().getPlayerNameFromUUID(uuid) + "'s cell rank"));
                menuItems.add(GUI.createMenuItem(identifier, Material.IRON_INGOT, ChatColor.LIGHT_PURPLE + "Make " + new ServerData().getPlayerNameFromUUID(uuid) + " an admin", ChatColor.GRAY + "This user will get the cell rank: Admin.", ChatColor.GRAY + "This is the second-lowest cell rank.", "", ChatColor.DARK_GRAY + "Manager+ can do this"));
                menuItems.add(GUI.createMenuItem(identifier, Material.DIAMOND, ChatColor.BLUE + "Make " + new ServerData().getPlayerNameFromUUID(uuid) + " the cell manager", ChatColor.GRAY + "This user will get the cell rank: Manager.", ChatColor.GRAY + "This is the second-highest cell rank.", "", ChatColor.DARK_GRAY + "Only the owner can do this."));
                menuItems.add(GUI.createEnchantedMenuItem(identifier, Material.EMERALD, ChatColor.RED + "Make " + new ServerData().getPlayerNameFromUUID(uuid) + " the cell owner", ChatColor.GRAY + "This user will get the cell rank: Owner.", ChatColor.GRAY + "This is the highest cell rank.", "", ChatColor.DARK_GRAY + "Only the owner can do this."));
                for (int i = 0; i < menuItems.size(); i++) {
                    ItemStack item = menuItems.get(i);
                    ItemMeta meta = item.getItemMeta();
                    meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, uuid);
                    item.setItemMeta(meta);
                    menuItems.set(i, item);
                }
            }
            @Override
            public void item1Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                //Verifies that the user that is being modified is still a member of the island and that the user modifying them is still a member of the island
                if (!island.getIslandPlayerUUIDS().contains(uuid)) {
                    player.sendMessage(ChatColor.RED + "This user is no longer a part of your cell!");
                    return;
                }
                if (!island.getIslandPlayerUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "You are no longer a part of this cell!");
                    return;
                }
                //Permission checks to make sure players are not editing users with a higher rank then them
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                    if (island.getIslandOwnerUUID().equals(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                    if (!island.getIslandMemberUUIDS().contains(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString()) || island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                    island.playerBanned(player, UUID.fromString(uuid));
                } else {
                    player.sendMessage(ChatColor.RED + "You are not a high enough rank to do this!");
                }
                GUI.getGUIPage("islandMembers").open(player);
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                //Verifies that the user that is being modified is still a member of the island and that the user modifying them is still a member of the island
                if (!island.getIslandPlayerUUIDS().contains(uuid)) {
                    player.sendMessage(ChatColor.RED + "This user is no longer a part of your cell!");
                    return;
                }
                if (!island.getIslandPlayerUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "You are no longer a part of this cell!");
                    return;
                }
                //Permission checks to make sure players are not editing users with a higher rank then them
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                    if (island.getIslandOwnerUUID().equals(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                    if (!island.getIslandMemberUUIDS().contains(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (!island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    island.playerKicked(player, UUID.fromString(uuid));
                } else {
                    player.sendMessage(ChatColor.RED + "You are not a high enough rank to do this!");
                }
                GUI.getGUIPage("islandMembers").open(player);
            }
            @Override
            public void item3Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                //Verifies that the user that is being modified is still a member of the island and that the user modifying them is still a member of the island
                if (!island.getIslandPlayerUUIDS().contains(uuid)) {
                    player.sendMessage(ChatColor.RED + "This user is no longer a part of your cell!");
                    return;
                }
                if (!island.getIslandPlayerUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "You are no longer a part of this cell!");
                    return;
                }
                //Permission checks to make sure players are not editing users with a higher rank then them
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                    if (island.getIslandOwnerUUID().equals(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                    if (!island.getIslandMemberUUIDS().contains(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (!island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    if (island.getIslandManagerUUID().equals(uuid)) {
                        island.setIslandManagerUUID("");
                        player.sendMessage(ChatColor.GREEN + "You have just changed " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.GREEN + " cell role. " + ChatColor.DARK_GRAY + "Manager -> Member");
                        island.addIslandMemberUUID(uuid);
                    } else if (island.getIslandAdminUUIDS().contains(uuid)) {
                        island.removeIslandAdminUUID(uuid);
                        player.sendMessage(ChatColor.GREEN + "You have just changed " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.GREEN + " cell role. " + ChatColor.DARK_GRAY + "Admin -> Member");
                        island.addIslandMemberUUID(uuid);
                    } else {
                        player.sendMessage(ChatColor.GREEN + "You have just changed " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.GREEN + " cell role. " + ChatColor.DARK_GRAY + "Member -> Member");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not a high enough rank to do this!");
                }
                open(player);
            }

            @Override
            public void item5Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                //Verifies that the user that is being modified is still a member of the island and that the user modifying them is still a member of the island
                if (!island.getIslandPlayerUUIDS().contains(uuid)) {
                    player.sendMessage(ChatColor.RED + "This user is no longer a part of your cell!");
                    return;
                }
                if (!island.getIslandPlayerUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "You are no longer a part of this cell!");
                    return;
                }
                //Permission checks to make sure players are not editing users with a higher rank then them
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                    if (island.getIslandOwnerUUID().equals(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                    if (!island.getIslandMemberUUIDS().contains(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString()) || island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                    if (island.getIslandManagerUUID().equals(uuid)) {
                        island.setIslandManagerUUID("");
                        player.sendMessage(ChatColor.GREEN + "You have just changed " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.GREEN + " cell role. " + ChatColor.DARK_GRAY + "Manager -> Admin");
                        island.addIslandAdminUUID(uuid);
                    } else if (island.getIslandAdminUUIDS().contains(uuid)) {
                        player.sendMessage(ChatColor.GREEN + "You have just changed " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.GREEN + " cell role. " + ChatColor.DARK_GRAY + "Admin -> Admin");
                    } else {
                        island.removeIslandMemberUUID(uuid);
                        player.sendMessage(ChatColor.GREEN + "You have just changed " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.GREEN + " cell role. " + ChatColor.DARK_GRAY + "Member -> Admin");
                        island.addIslandAdminUUID(uuid);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not a high enough rank to do this!");
                }
                open(player);
            }

            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                //Verifies that the user that is being modified is still a member of the island and that the user modifying them is still a member of the island
                if (!island.getIslandPlayerUUIDS().contains(uuid)) {
                    player.sendMessage(ChatColor.RED + "This user is no longer a part of your cell!");
                    return;
                }
                if (!island.getIslandPlayerUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "You are no longer a part of this cell!");
                    return;
                }
                //Permission checks to make sure players are not editing users with a higher rank then them
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                    if (island.getIslandOwnerUUID().equals(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                    if (!island.getIslandMemberUUIDS().contains(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                    if (!island.getIslandManagerUUID().equals("")) {
                        player.sendMessage(ChatColor.RED + "There can only be one cell manager at a time, please change " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.RED + " cell role before doing this!");
                        return;
                    }
                    if (island.getIslandManagerUUID().equals(uuid)) {
                        player.sendMessage(ChatColor.GREEN + "You have just changed " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.GREEN + " cell role. " + ChatColor.DARK_GRAY + "Manager -> Manager");
                    } else if (island.getIslandAdminUUIDS().contains(uuid)) {
                        island.removeIslandAdminUUID(uuid);
                        player.sendMessage(ChatColor.GREEN + "You have just changed " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.GREEN + " cell role. " + ChatColor.DARK_GRAY + "Admin -> Manager");
                        island.setIslandManagerUUID(uuid);
                    } else {
                        island.removeIslandMemberUUID(uuid);
                        player.sendMessage(ChatColor.GREEN + "You have just changed " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + "'s" + ChatColor.GREEN + " cell role. " + ChatColor.DARK_GRAY + "Member -> Manager");
                        island.setIslandManagerUUID(uuid);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You are not a high enough rank to do this!");
                }
                open(player);
            }

            @Override
            public void item7Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                //Verifies that the user that is being modified is still a member of the island and that the user modifying them is still a member of the island
                if (!island.getIslandPlayerUUIDS().contains(uuid)) {
                    player.sendMessage(ChatColor.RED + "This user is no longer a part of your cell!");
                    return;
                }
                if (!island.getIslandPlayerUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "You are no longer a part of this cell!");
                    return;
                }
                //Permission checks to make sure players are not editing users with a higher rank then them
                if (island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                    if (island.getIslandOwnerUUID().equals(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandAdminUUIDS().contains(player.getUniqueId().toString())) {
                    if (!island.getIslandMemberUUIDS().contains(uuid)) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to edit this user!");
                        return;
                    }
                }
                if (island.getIslandOwnerUUID().equals(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "This action cannot be undone, please confirm that this is what you would like to do!");
                    GUI.getGUIPage("islandTransferOwnership").args = uuid;
                    GUI.getGUIPage("islandTransferOwnership").open(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are not a high enough rank to do this!");
                    open(player);
                }
            }
        };
        guiPage.identifier = "islandMembersEdit";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&aManage Member");
        guiPage.onCloseGoToMenu = "islandMembers";
        guiPage.register();
    }

    //Transfer ownership of an island
    public static void transferOwnership() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                String newOwnerUUID = args;
                menuItems = new ArrayList<>();
                ItemStack item = GUI.createMenuItemOfPlayerSkull(identifier, Bukkit.getOfflinePlayer(UUID.fromString(newOwnerUUID)), ChatColor.DARK_RED + "Transfer Cell Ownership To " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(newOwnerUUID), ChatColor.RED + "This action cannot be un-done!", ChatColor.RED + "You will get the island rank: Member!");
                ItemMeta meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, newOwnerUUID);
                item.setItemMeta(meta);
                menuItems.add(item);
            }
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                String uuid = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (island.getIslandPlayerUUIDS().contains(uuid)) {
                    if (island.getIslandManagerUUID().equals(uuid)) {
                        island.setIslandManagerUUID("");
                    } else if (island.getIslandAdminUUIDS().contains(uuid)) {
                        island.removeIslandAdminUUID(uuid);
                    } else if (island.getIslandMemberUUIDS().contains(uuid)) {
                        island.removeIslandMemberUUID(uuid);
                    }
                    island.setIslandOwnerUUID(uuid);
                    island.addIslandMemberUUID(player.getUniqueId().toString());
                    for (String _uuid : island.getIslandPlayerUUIDS()) {
                        if (Bukkit.getPlayer(UUID.fromString(_uuid)) != null) Bukkit.getPlayer(UUID.fromString(_uuid)).sendMessage(ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(uuid) + ChatColor.GREEN + " has been made owner of your cell by: " + ChatColor.LIGHT_PURPLE + new ServerData().getPlayerNameFromUUID(player.getUniqueId().toString()));
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "This user is no longer a part of your cell!");
                }
                GUI.getGUIPage("islandsMain").open(player);
            }
        };
        guiPage.identifier = "islandTransferOwnership";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&cTransfer Cell Ownership");
        guiPage.onCloseGoToMenu = "islandMembers";
        guiPage.register();
    }




    //View island invites
    public static void invites() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                menuItems = new ArrayList<>();
                SkyblockIslandInviteManager.updateExpiredIslandInvites(player.getUniqueId().toString());
                IslandInvites invites = SkyblockIslandInviteManager.getIslandInvites(player.getUniqueId().toString());
                for (String key : invites.invites.keySet()) {
                    IslandInvite invite = invites.invites.get(key);
                    ItemStack item = GUI.createMenuItemOfPlayerSkull(identifier, Bukkit.getOfflinePlayer(UUID.fromString(invite.inviterUUID)), ChatColor.LIGHT_PURPLE + new ServerData().getIslandNameFromUUID(key), ChatColor.GRAY + "" + ChatColor.ITALIC + "Invited By: " + ChatColor.AQUA + new ServerData().getPlayerNameFromUUID(invite.inviterUUID));
                    ItemMeta meta = item.getItemMeta();
                    meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING, key);
                    item.setItemMeta(meta);
                    menuItems.add(item);
                }
            }
            @Override
            public void itemClicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (playerData.getIfPlayerHasIsland()) {
                    player.sendMessage(ChatColor.RED + "You are already part of an cell!");
                    return;
                }
                String islandID = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "uuid"), PersistentDataType.STRING);
                SkyblockIslandInviteManager.updateExpiredIslandInvites(player.getUniqueId().toString());
                IslandInvites invites = SkyblockIslandInviteManager.getIslandInvites(player.getUniqueId().toString());
                if (!invites.invites.containsKey(islandID)) {
                    player.sendMessage(ChatColor.RED + "This invite has expired!");
                    open(player);
                    return;
                }
                if (new SkyBlockIsland(islandID).getBannedPlayerUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "You are currently banned from this cell, ask the cell manager or the cell owner to unban you. They can do so by running the following command: " + ChatColor.GRAY + "\"/cell unban " + player.getName() + "\"");
                    return;
                }
                if (!new SkyBlockIsland(islandID).getAllowInvites()) {
                    player.sendMessage(ChatColor.RED + "This cell is currently not accepting invites!");
                    return;
                }
                if (new SkyBlockIsland(islandID).getIslandPlayerUUIDS().size() < SkyBlockIsland.MAX_PLAYERS_PER_ISLAND) {
                    new SkyBlockIsland(islandID).playerJoined(player);
                } else {
                    player.sendMessage(ChatColor.RED + "This island is currently full!");
                }
            }
        };
        guiPage.identifier = "islandInvites";
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&aYour Cell Invites");
        guiPage.onCloseGoToMenu = "islandsMain";
        guiPage.register();
    }
    //Delete island menu
    public static void delete() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                GUI.getGUIPage("islandDeleteConfirm").open(player);
            }
        };
        guiPage.identifier = "islandDelete";
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.BARRIER, ChatColor.RED + "Delete your cell", ChatColor.DARK_RED + "THIS ACTION CANNOT BE UN-DONE!"));
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&4Delete Your Cell");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Delete island confirm menu
    public static void deleteConfirm() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void item8Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                SkyBlockIsland island = new PlayerData(player).getPlayerIsland();
                island.delete();
                player.closeInventory();
                player.sendMessage(ChatColor.RED + "You have successfully deleted your cell!");
            }
        };
        guiPage.identifier = "islandDeleteConfirm";
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createLightGrayPlaceholderItem());
        guiPage.menuItems.add(GUI.createMenuItem(guiPage.identifier, Material.BARRIER, ChatColor.RED + "Confirm the deletion your cell", ChatColor.DARK_RED + "THIS ACTION CANNOT BE UN-DONE!"));
        guiPage.guiTitle = ChatColor.translateAlternateColorCodes('&', "&4Confirm The Deletion Of Your Cell");
        guiPage.onCloseGoToMenu = null;
        guiPage.register();
    }
    //Island settings menu
    public static void settings() {
        GUIPage guiPage = new GUIPage() {
            @Override
            public void onOpen(Player player) {
                PlayerData playerData = new PlayerData(player);
                SkyBlockIsland island = playerData.getPlayerIsland();
                menuItems = new ArrayList<>();
                if (island.getAllowVisitors()) {
                    menuItems.add(GUI.createMenuItem(identifier, Material.FIRE_CHARGE, ChatColor.AQUA + "Allow visitors to warp to your cell", ChatColor.GREEN + "This is set to true", "", ChatColor.GRAY + "Click to change"));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.FIRE_CHARGE, ChatColor.AQUA + "Allow visitors to warp to your cell", ChatColor.RED + "This is set to false", "", ChatColor.GRAY + "Click to change"));
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                if (island.getAllowInvites()) {
                    menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.AQUA + "Allow players to be invited to your cell", ChatColor.GREEN + "This is set to true", "", ChatColor.GRAY + "Click to change"));
                } else menuItems.add(GUI.createMenuItem(identifier, Material.PAPER, ChatColor.AQUA + "Allow players to be invited to your cell", ChatColor.RED + "This is set to false", "", ChatColor.GRAY + "Click to change"));
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.COMPASS, ChatColor.AQUA + "Change member warp", ChatColor.GRAY + "This is where cell members will", ChatColor.GRAY + "go to when typing \"/cell go\"", "",
                        ChatColor.AQUA + "Current warp location: X=" + new DecimalFormat("0.##").format(island.getIslandMemberWarpX()) + ", y=" + new DecimalFormat("0.##").format(island.getIslandMemberWarpY()) + ", z=" + new DecimalFormat("0.##").format(island.getIslandMemberWarpZ()),
                        ChatColor.GRAY + "Click to change to your current location"));
                menuItems.add(GUI.createLightGrayPlaceholderItem());
                menuItems.add(GUI.createMenuItem(identifier, Material.CLOCK, ChatColor.AQUA + "Change visitor warp", ChatColor.GRAY + "This is where visitors will warp to", "",
                        ChatColor.AQUA + "Current warp location: X=" + new DecimalFormat("0.##").format(island.getIslandVisitorWarpX()) + ", y=" + new DecimalFormat("0.##").format(island.getIslandVisitorWarpY()) + ", z=" + new DecimalFormat("0.##").format(island.getIslandVisitorWarpZ()),
                        ChatColor.GRAY + "Click to change to your current location"));
            }
            @Override
            public void item0Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getIfPlayerHasIsland()) return;
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (!island.getIslandOwnerUUID().equals(player.getUniqueId().toString()) && !island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "Only the cell owner or manager can do this!");
                    return;
                }
                island.setAllowVisitors(!island.getAllowVisitors());
                open(player);
            }
            @Override
            public void item2Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getIfPlayerHasIsland()) return;
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (!island.getIslandOwnerUUID().equals(player.getUniqueId().toString()) && !island.getIslandManagerUUID().equals(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "Only the cell owner or manager can do this!");
                    return;
                }
                island.setAllowInvites(!island.getAllowInvites());
                open(player);
            }
            @Override
            public void item4Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getIfPlayerHasIsland()) return;
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "Only cell admins+ can do this!");
                    return;
                }
                if (IslandManager.playersInIslands.containsKey(player.getUniqueId())) {
                    if (!IslandManager.playersInIslands.get(player.getUniqueId()).equals(UUID.fromString(island.getUUID()))) {
                        player.sendMessage(ChatColor.RED + "You must be at your cell to run this command!");
                        return;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You must be at your cell to run this command!");
                    return;
                }
                island.setIslandMemberWarpX(((double) Math.round(player.getLocation().getX() * 10)) / 10);
                island.setIslandMemberWarpY(((double) Math.round(player.getLocation().getY() * 10)) / 10);
                island.setIslandMemberWarpZ(((double) Math.round(player.getLocation().getZ() * 10)) / 10);
                open(player);
            }
            @Override
            public void item6Clicked(InventoryClickEvent e) {
                Player player = (Player) e.getWhoClicked();
                PlayerData playerData = new PlayerData(player);
                if (!playerData.getIfPlayerHasIsland()) return;
                SkyBlockIsland island = playerData.getPlayerIsland();
                if (island.getIslandMemberUUIDS().contains(player.getUniqueId().toString())) {
                    player.sendMessage(ChatColor.RED + "Only cell admins+ can do this!");
                    return;
                }
                if (IslandManager.playersInIslands.containsKey(player.getUniqueId())) {
                    if (!IslandManager.playersInIslands.get(player.getUniqueId()).equals(UUID.fromString(island.getUUID()))) {
                        player.sendMessage(ChatColor.RED + "You must be at your cell to run this command!");
                        return;
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You must be at your cell to run this command!");
                    return;
                }
                island.setIslandVisitorWarpX(((double) Math.round(player.getLocation().getX() * 10)) / 10);
                island.setIslandVisitorWarpY(((double) Math.round(player.getLocation().getY() * 10)) / 10);
                island.setIslandVisitorWarpZ(((double) Math.round(player.getLocation().getZ() * 10)) / 10);
                open(player);
            }
        };
        guiPage.guiTitle = ChatColor.GREEN + "Your Cell Settings";
        guiPage.identifier = "islandSettings";
        guiPage.onCloseGoToMenu = "islandsMain";
        guiPage.register();
    }

     */
}
