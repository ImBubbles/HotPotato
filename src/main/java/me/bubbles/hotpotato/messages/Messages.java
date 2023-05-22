package me.bubbles.hotpotato.messages;

import org.bukkit.configuration.file.FileConfiguration;

public class Messages {
    private static FileConfiguration config;

    public Messages(FileConfiguration configuration) {
        config=configuration;
    }

    public enum Message {
        NO_PERMS(config.getString("NO_PERMS")),
        PREFIX(config.getString("PREFIX")),
        PRIMARY(config.getString("COLOR_PRIMARY")),
        SECONDARY(config.getString("COLOR_SECONDARY"));

        private String str;
        Message(String str) {
            this.str=str;
        }
        public String getStr() {
            return str;
        }
    }

}
