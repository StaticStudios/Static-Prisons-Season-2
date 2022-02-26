package me.staticstudios.prisons.discord;

import me.staticstudios.prisons.Main;
import me.staticstudios.prisons.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Date;

public class DiscordBot {
    public static JDA jda;
    public static void initialize() {
        try {
            login();
        } catch (LoginException | NullPointerException e) {
            Bukkit.getLogger().warning("Could not log the discord bot in! Trying again in 5 seconds.");
            Bukkit.getScheduler().runTaskLater(Main.getMain(), DiscordBot::initialize, 100);
        }
        LoadDiscordCommands.initialize();
    }
    private static void login() throws LoginException {
        jda = JDABuilder.createDefault(Utils.getFileContents("./data/discord/bot_token.dont_open")).build();
        jda.addEventListener(new DiscordListener());
    }
    public static void updatePlayersOnline() {
        jda.getPresence().setActivity(Activity.watching(" " + Bukkit.getOnlinePlayers().size() + " people on Static Prisons"));
    }
    public static void sendMessage(TextChannel channel, String message) {
        channel.sendMessage(message).queue();
    }
    public static void sendMessage(TextChannel channel, EmbedBuilder message) {
        channel.sendMessageEmbeds(message.build()).queue();
    }
    public static void logNormalChatMessageToChannelArchive(String msg, String sender) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Chat Message Log");
        embed.addField("Message Contents: ", msg, true);
        embed.setColor(Color.GREEN);
        embed.setFooter("Message sent by: " + sender);
        embed.setTimestamp(new Date().toInstant());
        sendMessage(jda.getTextChannelById("923297700739416114"), embed);
    }
    public static void logNormalBroadcastMessageToChannelArchive(String msg) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Server Broadcast Log");
        embed.addField("Message Contents: ", msg, true);
        embed.setColor(15736779);
        embed.setTimestamp(new Date().toInstant());
        sendMessage(jda.getTextChannelById("923297700739416114"), embed);
    }
    public static void logCommandMessageToChannelArchive(String msg, String sender) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Command Log");
        embed.addField("Message Contents: ", msg, true);
        embed.setColor(Color.RED);
        embed.setFooter("Command sent by: " + sender);
        embed.setTimestamp(new Date().toInstant());
        sendMessage(jda.getTextChannelById("923297700739416114"), embed);
    }
    public static void logMessageToChannelArchive(String title, String description, String sentBy, Color color) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(title);
        embed.addField("Description", description, true);
        embed.setColor(color);
        embed.setFooter("Command sent by: " + sentBy);
        embed.setTimestamp(new Date().toInstant());
        sendMessage(jda.getTextChannelById("923297700739416114"), embed);
    }
}
