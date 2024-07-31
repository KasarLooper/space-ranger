package com.kasarlooper.spaceranger;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import java.util.Scanner;

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
		config.setTitle("Space Ranger");
		MyGdxGame game = new MyGdxGame();

		Thread gameThread = new Thread(() -> new Lwjgl3Application(game, config));
		gameThread.start();

		try (Scanner scanner = new Scanner(System.in)) {
			label:
			while (true) {
				String command = scanner.nextLine().trim();
				switch (command) {
					case "win":
						if (!game.win()) System.err.println("Not playing a level");
						break;
					case "lose":
						if (!game.lose()) System.err.println("Not playing a level");
						break;
					case "exit":
						break label;
					default:
						System.err.println("Unknown command");
						break;
				}
			}
		}
	}
}
