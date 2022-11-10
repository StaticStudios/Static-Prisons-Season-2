package net.staticstudios.prisons.challenges.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.staticstudios.gui.GUIButton;
import net.staticstudios.gui.GUIPlaceholders;
import net.staticstudios.gui.StaticGUI;
import net.staticstudios.gui.builder.ButtonBuilder;
import net.staticstudios.gui.builder.GUIBuilder;
import net.staticstudios.prisons.challenges.Challenge;
import net.staticstudios.prisons.challenges.ChallengeDuration;
import net.staticstudios.prisons.challenges.ChallengeManager;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.gui.MainMenus;
import net.staticstudios.prisons.utils.ComponentUtil;
import net.staticstudios.prisons.utils.Prefix;
import net.staticstudios.prisons.utils.PrisonUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChallengeMenus {

    static final GUIButton BUY_HOURLY_CHALLENGE = ButtonBuilder.builder()
            .name(Component.empty().append(Component.text("Empty Challenge Slot").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)))
            .icon(Material.BARRIER)
            .lore(List.of(
                    Component.empty().append(Component.text("Get a new random challenge")),
                    Component.empty().append(Component.text("to complete within an hour!")),
                    Component.empty(),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)).append(Component.text("Costs: ").color(ComponentUtil.RED)).append(Component.text(PrisonUtils.addCommasToNumber(ChallengeManager.HOURLY_CHALLENGE_COST) + " Tokens").color(ComponentUtil.WHITE)),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)).append(Component.text("Duration: ").color(ComponentUtil.RED)).append(Component.text("1 Hour").color(ComponentUtil.WHITE)),
                    Component.empty(),
                    Component.empty().append(Component.text("Left-click to purchase").color(ComponentUtil.YELLOW))
            ))
            .onLeftClick(plr -> {
                PlayerData playerData = new PlayerData(plr);
                if (playerData.getTokens() < ChallengeManager.HOURLY_CHALLENGE_COST) {
                    plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You do not have enough tokens to purchase this challenge!").color(ComponentUtil.RED)));
                    return;
                }
                playerData.removeTokens(ChallengeManager.HOURLY_CHALLENGE_COST);

                Challenge challenge = Challenge.createRandomChallenge(ChallengeDuration.HOURLY);
                Challenge.createPlayerChallenge(plr, challenge);
                plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You have a new HOURLY challenge!")));
                hourlyChallenges(plr, true);
            })
            .build();
    static final GUIButton BUY_DAILY_CHALLENGE = ButtonBuilder.builder()
            .name(Component.empty().append(Component.text("Empty Challenge Slot").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)))
            .icon(Material.BARRIER)
            .lore(List.of(
                    Component.empty().append(Component.text("Get a new random challenge")),
                    Component.empty().append(Component.text("to complete within a day!")),
                    Component.empty(),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)).append(Component.text("Costs: ").color(ComponentUtil.RED)).append(Component.text(PrisonUtils.addCommasToNumber(ChallengeManager.DAILY_CHALLENGE_COST) + " Tokens").color(ComponentUtil.WHITE)),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)).append(Component.text("Duration: ").color(ComponentUtil.RED)).append(Component.text("1 Day").color(ComponentUtil.WHITE)),
                    Component.empty(),
                    Component.empty().append(Component.text("Left-click to purchase").color(ComponentUtil.YELLOW))
            ))
            .onLeftClick(plr -> {
                PlayerData playerData = new PlayerData(plr);
                if (playerData.getTokens() < ChallengeManager.DAILY_CHALLENGE_COST) {
                    plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You do not have enough tokens to purchase this challenge!").color(ComponentUtil.RED)));
                    return;
                }
                playerData.removeTokens(ChallengeManager.DAILY_CHALLENGE_COST);

                Challenge challenge = Challenge.createRandomChallenge(ChallengeDuration.DAILY);
                Challenge.createPlayerChallenge(plr, challenge);
                plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You have a new DAILY challenge!")));
                dailyChallenges(plr, true);
            })
            .build();
    static final GUIButton BUY_WEEKLY_CHALLENGE = ButtonBuilder.builder()
            .name(Component.empty().append(Component.text("Empty Challenge Slot").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)))
            .icon(Material.BARRIER)
            .lore(List.of(
                    Component.empty().append(Component.text("Get a new random challenge")),
                    Component.empty().append(Component.text("to complete within a week!")),
                    Component.empty(),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)).append(Component.text("Costs: ").color(ComponentUtil.RED)).append(Component.text(PrisonUtils.addCommasToNumber(ChallengeManager.WEEKLY_CHALLENGE_COST) + " Tokens").color(ComponentUtil.WHITE)),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.RED).decorate(TextDecoration.BOLD)).append(Component.text("Duration: ").color(ComponentUtil.RED)).append(Component.text("1 Week").color(ComponentUtil.WHITE)),
                    Component.empty(),
                    Component.empty().append(Component.text("Left-click to purchase").color(ComponentUtil.YELLOW))
            ))
            .onLeftClick(plr -> {
                PlayerData playerData = new PlayerData(plr);
                if (playerData.getTokens() < ChallengeManager.WEEKLY_CHALLENGE_COST) {
                    plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You do not have enough tokens to purchase this challenge!").color(ComponentUtil.RED)));
                    return;
                }
                playerData.removeTokens(ChallengeManager.WEEKLY_CHALLENGE_COST);

                Challenge challenge = Challenge.createRandomChallenge(ChallengeDuration.WEEKLY);
                Challenge.createPlayerChallenge(plr, challenge);
                plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You have a new WEEKLY challenge!")));
                weeklyChallenges(plr, true);
            })
            .build();

    public static void open(Player player, boolean fromCommand) {
        StaticGUI gui = GUIBuilder.builder()
                .title("Challenges")
                .size(27)
                .fillWith(GUIPlaceholders.GRAY)
                .onClose((plr, g) -> {
                    if (!fromCommand) MainMenus.open(plr);
                })
                .build();

        gui.setButton(11, ButtonBuilder.builder()
                .icon(Material.GOLDEN_CARROT)
                .name(Component.text("Hourly Challenges").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD))
                .lore(List.of(
                        Component.empty(),
                        Component.empty().append(Component.text("View your current hourly")),
                        Component.empty().append(Component.text("challenges or get new ones!"))
                ))
                .onClick((e, g) -> hourlyChallenges((Player) e.getWhoClicked(), fromCommand))
                .build());

        gui.setButton(13, ButtonBuilder.builder()
                .icon(Material.CLOCK)
                .name(Component.text("Daily Challenges").color(ComponentUtil.RED).decorate(TextDecoration.BOLD))
                .lore(List.of(
                        Component.empty(),
                        Component.empty().append(Component.text("View your current daily")),
                        Component.empty().append(Component.text("challenges or get new ones!"))
                ))
                .onClick((e, g) -> dailyChallenges((Player) e.getWhoClicked(), fromCommand))
                .build());

        gui.setButton(15, ButtonBuilder.builder()
                .icon(Material.CREEPER_BANNER_PATTERN)
                .name(Component.text("Weekly Challenges").color(ComponentUtil.BLUE).decorate(TextDecoration.BOLD))
                .lore(List.of(
                        Component.empty(),
                        Component.empty().append(Component.text("View your current weekly")),
                        Component.empty().append(Component.text("challenges or get new ones!"))
                ))
                .onClick((e, g) -> weeklyChallenges((Player) e.getWhoClicked(), fromCommand))
                .build());

        gui.open(player);
    }

    public static void hourlyChallenges(Player player, boolean fromCommand) {
        Challenge.expirePlayerChallenges(player);
        List<Challenge> playerChallenges = Challenge.getChallenges(player).stream()
                .filter(challenge -> challenge.getDuration().equals(ChallengeDuration.HOURLY))
                .toList();
        StaticGUI gui = GUIBuilder.builder()
                .title("Hourly Challenges")
                .size(9)
                .fillWith(GUIPlaceholders.GRAY)
                .onClose((plr, g) -> open(plr, fromCommand))
                .build();
        for (int i = 0; i < 3; i++) {
            if (i >= playerChallenges.size()) {
                gui.setButton(i + 3, BUY_HOURLY_CHALLENGE);
                continue;
            }
            Challenge challenge = playerChallenges.get(i);
            List<Component> lore = new ArrayList<>(challenge.getChallengeType().DESCRIPTION);
            lore.addAll(List.of(
                    Component.empty(),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text("Tier: ").color(ComponentUtil.YELLOW)).append(Component.text(challenge.getTier()).color(ComponentUtil.WHITE)),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text("Progress: ").color(ComponentUtil.YELLOW)).append(Component.text(PrisonUtils.addCommasToNumber(challenge.getProgress()) + "/" + PrisonUtils.addCommasToNumber(challenge.getGoal())).color(ComponentUtil.WHITE)),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text("Expires In: ").color(ComponentUtil.YELLOW)).append(Component.text(PrisonUtils.formatTime(challenge.getDuration().durationInMillis() - (System.currentTimeMillis() - challenge.getStartTime()))).color(ComponentUtil.WHITE)),
                    Component.empty(),
                    Component.empty().append(Component.text("Right-click to end this challenge (costs " + PrisonUtils.addCommasToNumber(ChallengeManager.HOURLY_CHALLENGE_COST / 3) + " Tokens)").color(ComponentUtil.YELLOW))
            ));

            gui.setButton(i + 3, ButtonBuilder.builder()
                    .name(challenge.getChallengeType().DISPLAY_NAME)
                    .lore(lore)
                    .icon(challenge.getChallengeType().ICON)
                    .onRightClick(plr -> {
                        if (challenge.shouldExpire()) {
                            hourlyChallenges(plr, fromCommand);
                            return;
                        }
                        PlayerData playerData = new PlayerData(plr);
                        if (playerData.getTokens() < ChallengeManager.HOURLY_CHALLENGE_COST / 3) {
                            plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You do not have enough tokens to end this challenge!").color(ComponentUtil.RED)));
                            return;
                        }
                        playerData.removeTokens(ChallengeManager.HOURLY_CHALLENGE_COST / 3);
                        Challenge.removePlayerChallenge(plr, challenge);
                        plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You ended your challenge!").color(ComponentUtil.GREEN)));
                        hourlyChallenges(plr, fromCommand);
                    })
                    .build());
        }
        gui.open(player);
    }

    public static void dailyChallenges(Player player, boolean fromCommand) {
        Challenge.expirePlayerChallenges(player);
        List<Challenge> playerChallenges = Challenge.getChallenges(player).stream()
                .filter(challenge -> challenge.getDuration().equals(ChallengeDuration.DAILY))
                .toList();
        StaticGUI gui = GUIBuilder.builder()
                .title("Daily Challenges")
                .size(9)
                .fillWith(GUIPlaceholders.GRAY)
                .onClose((plr, g) -> open(plr, fromCommand))
                .build();
        for (int i = 0; i < 5; i++) {
            if (i >= playerChallenges.size()) {
                gui.setButton(i + 2, BUY_DAILY_CHALLENGE);
                continue;
            }
            Challenge challenge = playerChallenges.get(i);
            List<Component> lore = new ArrayList<>(challenge.getChallengeType().DESCRIPTION);
            lore.addAll(List.of(
                    Component.empty(),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text("Tier: ").color(ComponentUtil.YELLOW)).append(Component.text(challenge.getTier()).color(ComponentUtil.WHITE)),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text("Progress: ").color(ComponentUtil.YELLOW)).append(Component.text(PrisonUtils.addCommasToNumber(challenge.getProgress()) + "/" + PrisonUtils.addCommasToNumber(challenge.getGoal())).color(ComponentUtil.WHITE)),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text("Expires In: ").color(ComponentUtil.YELLOW)).append(Component.text(PrisonUtils.formatTime(challenge.getDuration().durationInMillis() - (System.currentTimeMillis() - challenge.getStartTime()))).color(ComponentUtil.WHITE)),
                    Component.empty(),
                    Component.empty().append(Component.text("Right-click to end this challenge (costs " + PrisonUtils.addCommasToNumber(ChallengeManager.DAILY_CHALLENGE_COST / 3) + " Tokens)").color(ComponentUtil.YELLOW))
            ));

            gui.setButton(i + 2, ButtonBuilder.builder()
                    .name(challenge.getChallengeType().DISPLAY_NAME)
                    .lore(lore)
                    .icon(challenge.getChallengeType().ICON)
                    .onRightClick(plr -> {
                        if (challenge.shouldExpire()) {
                            dailyChallenges(plr, fromCommand);
                            return;
                        }
                        PlayerData playerData = new PlayerData(plr);
                        if (playerData.getTokens() < ChallengeManager.DAILY_CHALLENGE_COST / 3) {
                            plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You do not have enough tokens to end this challenge!").color(ComponentUtil.RED)));
                            return;
                        }
                        playerData.removeTokens(ChallengeManager.DAILY_CHALLENGE_COST / 3);
                        Challenge.removePlayerChallenge(plr, challenge);
                        plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You ended your challenge!").color(ComponentUtil.GREEN)));
                        dailyChallenges(plr, fromCommand);
                    })
                    .build());
        }
        gui.open(player);
    }

    public static void weeklyChallenges(Player player, boolean fromCommand) {
        Challenge.expirePlayerChallenges(player);
        List<Challenge> playerChallenges = Challenge.getChallenges(player).stream()
                .filter(challenge -> challenge.getDuration().equals(ChallengeDuration.WEEKLY))
                .toList();
        StaticGUI gui = GUIBuilder.builder()
                .title("Weekly Challenges")
                .size(9)
                .fillWith(GUIPlaceholders.GRAY)
                .onClose((plr, g) -> open(plr, fromCommand))
                .build();
        for (int i = 0; i < 3; i++) {
            if (i >= playerChallenges.size()) {
                gui.setButton(i + 3, BUY_WEEKLY_CHALLENGE);
                continue;
            }
            Challenge challenge = playerChallenges.get(i);
            List<Component> lore = new ArrayList<>(challenge.getChallengeType().DESCRIPTION);
            lore.addAll(List.of(
                    Component.empty(),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text("Tier: ").color(ComponentUtil.YELLOW)).append(Component.text(challenge.getTier()).color(ComponentUtil.WHITE)),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text("Progress: ").color(ComponentUtil.YELLOW)).append(Component.text(PrisonUtils.addCommasToNumber(challenge.getProgress()) + "/" + PrisonUtils.addCommasToNumber(challenge.getGoal())).color(ComponentUtil.WHITE)),
                    Component.empty().append(Component.text("| ").color(ComponentUtil.YELLOW).decorate(TextDecoration.BOLD)).append(Component.text("Expires In: ").color(ComponentUtil.YELLOW)).append(Component.text(PrisonUtils.formatTime(challenge.getDuration().durationInMillis() - (System.currentTimeMillis() - challenge.getStartTime()))).color(ComponentUtil.WHITE)),
                    Component.empty(),
                    Component.empty().append(Component.text("Right-click to end this challenge (costs " + PrisonUtils.addCommasToNumber(ChallengeManager.WEEKLY_CHALLENGE_COST / 3) + " Tokens)").color(ComponentUtil.YELLOW))
            ));

            gui.setButton(i + 3, ButtonBuilder.builder()
                    .name(challenge.getChallengeType().DISPLAY_NAME)
                    .lore(lore)
                    .icon(challenge.getChallengeType().ICON)
                    .onRightClick(plr -> {
                        if (challenge.shouldExpire()) {
                            weeklyChallenges(plr, fromCommand);
                            return;
                        }
                        PlayerData playerData = new PlayerData(plr);
                        if (playerData.getTokens() < ChallengeManager.WEEKLY_CHALLENGE_COST / 3) {
                            plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You do not have enough tokens to end this challenge!").color(ComponentUtil.RED)));
                            return;
                        }
                        playerData.removeTokens(ChallengeManager.WEEKLY_CHALLENGE_COST / 3);
                        Challenge.removePlayerChallenge(plr, challenge);
                        plr.sendMessage(Prefix.CHALLENGES.append(Component.text("You ended your challenge!").color(ComponentUtil.GREEN)));
                        weeklyChallenges(plr, fromCommand);
                    })
                    .build());
        }
        gui.open(player);
    }
}