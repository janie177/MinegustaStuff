package com.minegusta.minegustastuff.races;

import com.google.common.collect.Lists;
import com.minegusta.minegustastuff.data.ConfigFile;

import java.util.List;

public class Races {
    public static String getDefaultRace() {
        if (ConfigFile.getDefaultConfig().isSet("default_race")) {
            return ConfigFile.getDefaultConfig().getString("default_race");
        }
        return "human";
    }

    public enum RacesList {
        human("Human", ConfigFile.getDefaultConfig().getStringList("human")),
        enderborn("Enderborn", ConfigFile.getDefaultConfig().getStringList("enderborn")),
        dwarf("Dwarf", ConfigFile.getDefaultConfig().getStringList("dwarf")),
        elf("Elf", ConfigFile.getDefaultConfig().getStringList("elf"));

        private String name;
        private List<String> info;

        private RacesList(String name, List<String> info) {
            this.name = name;
            this.info = info;
        }

        public static String getRace(String raceName) {
            return RacesList.valueOf(raceName).name;
        }

        public static List<String> getRaceInfo(String raceName) {
            return RacesList.valueOf(raceName).info;
        }

        public static List<String> getRaces() {
            List<String> raceList = Lists.newArrayList();
            for (RacesList value : RacesList.values()) {
                raceList.add(value.name);
            }
            return raceList;

        }

    }

}
