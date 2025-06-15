package model;

import java.io.*;
import java.util.*;

public class Ranking {
    private Map<String, Integer> playerWins = new HashMap<>();
    private static final String RANKING_FILE = "src/resources/ranking.txt";

    public Ranking() {
        loadRanking();
    }

    public void addWin(String playerName) {
        playerWins.put(playerName, playerWins.getOrDefault(playerName, 0) + 1);
        saveRanking();
    }

    public List<String> getRanking() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(playerWins.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());

        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.add(entry.getKey() + ": " + entry.getValue() + " vitórias");
        }
        return result;
    }

    private void loadRanking() {
        File file = new File(RANKING_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    playerWins.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRanking() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RANKING_FILE))) {
            for (Map.Entry<String, Integer> entry : playerWins.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
