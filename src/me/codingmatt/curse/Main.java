package me.codingmatt.curse;

import me.codingmatt.curse.util.CurseObject;

public class Main {

	public static void main(String[] args) {
		CurseObject object = new CurseObject("http://widget.mcf.li/mc-mods/minecraft/233806-matts-mod-tray.json");
		System.out.println(object.getTitle());
		System.out.println("Latest Download: " + object.getLatestDownload().getName());
		System.out.println(object.getLatestDownload().getName() + " has had " +object.getLatestDownload().getDownloads() + " downloads" );

	}

}
