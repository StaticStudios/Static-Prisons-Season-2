package net.staticstudios.prisons.challenges;

import net.kyori.adventure.text.Component;
import net.staticstudios.prisons.data.PlayerData;
import net.staticstudios.prisons.utils.Prefix;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class ChallengeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PlayerData playerData = new PlayerData(player);

        boolean receivedChallenges = false;

        List<Challenge> playerChallenges = Challenge.getChallenges(player).stream().filter(c -> !c.shouldExpire()).toList();

        //See if the player should receive hourly challenges
        if (System.currentTimeMillis() - (ChallengeManager.FREE_HOURLY_CHALLENGES.every() * 60L * 60L + 5 * 60L) * 1000 > playerData.getLastGotFreeChallengeAt(ChallengeDuration.HOURLY)) {
            playerData.setLastGotFreeChallengeAt(ChallengeDuration.HOURLY, System.currentTimeMillis());
            int hourlyChallenges = 3;

            for (Challenge challenge : playerChallenges) {
                if (challenge.getDuration() == ChallengeDuration.HOURLY) {
                    hourlyChallenges--;
                }
            }

            for (int i = 0; i < hourlyChallenges; i++) {
                Challenge.createPlayerChallenge(player, Challenge.createRandomChallenge(ChallengeDuration.HOURLY));
            }
            if (hourlyChallenges > 0) {
                receivedChallenges = true;
            }
        }

        //See if the player should receive daily challenges
        if (System.currentTimeMillis() - (ChallengeManager.FREE_DAILY_CHALLENGES.every() * 60L * 60L + 5 * 60L) * 1000 > playerData.getLastGotFreeChallengeAt(ChallengeDuration.DAILY)) {
            playerData.setLastGotFreeChallengeAt(ChallengeDuration.DAILY, System.currentTimeMillis());
            int dailyChallenges = 5;

            for (Challenge challenge : playerChallenges) {
                if (challenge.getDuration() == ChallengeDuration.DAILY) {
                    dailyChallenges--;
                }
            }

            for (int i = 0; i < dailyChallenges; i++) {
                Challenge.createPlayerChallenge(player, Challenge.createRandomChallenge(ChallengeDuration.DAILY));
            }
            if (dailyChallenges > 0) {
                receivedChallenges = true;
            }
        }

        //See if the player should receive weekly challenges
        if (System.currentTimeMillis() - (ChallengeManager.FREE_WEEKLY_CHALLENGES.every() * 60L * 60L + 5 * 60L) * 1000 > playerData.getLastGotFreeChallengeAt(ChallengeDuration.WEEKLY)) {
            playerData.setLastGotFreeChallengeAt(ChallengeDuration.WEEKLY, System.currentTimeMillis());
            int weeklyChallenges = 3;

            for (Challenge challenge : playerChallenges) {
                if (challenge.getDuration() == ChallengeDuration.WEEKLY) {
                    weeklyChallenges--;
                }
            }

            for (int i = 0; i < weeklyChallenges; i++) {
                Challenge.createPlayerChallenge(player, Challenge.createRandomChallenge(ChallengeDuration.WEEKLY));
            }
            if (weeklyChallenges > 0) {
                receivedChallenges = true;
            }
        }

        if (receivedChallenges) {
            player.sendMessage(Prefix.CHALLENGES.append(Component.text("You've received new challenges!")));
        }
    }
}
