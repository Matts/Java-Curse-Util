package me.codingmatt.curse.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/*
 * (c) Matthew Smeets 2015, codingmatt.me
 * 
 *  This isn't the best code i've written, but it works. 
 *  There's an example of how to use this code in the main class.
 *  
 *  If you have updates to this code/cleaner versions, please send them via a pull request and i'll merge.
 * 
 */
public class CurseObject {
	private String title, category,release_type;
	private int favorites, likes;
	private String url, project_url;	
	private CurseDownload latestDownload;
	private ArrayList<MinecraftVersions> modsByVersion = new ArrayList<MinecraftVersions>();


	public CurseObject(String curseUrl){
		String content;
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new URL(curseUrl).openStream()));
			while ((content = br.readLine()) != null) {
				sb.append(content);
			}
			br.close();
			
			JsonElement jelement = new JsonParser().parse(sb.toString());
		    JsonObject  jobject = jelement.getAsJsonObject();
		    
		    setTitle(jobject.getAsJsonPrimitive("title").getAsString());
		    setCategory(jobject.getAsJsonPrimitive("category").getAsString());
		    setUrl(jobject.getAsJsonPrimitive("url").getAsString());
		    setFavorites(jobject.getAsJsonPrimitive("favorites").getAsInt());
		    setLikes(jobject.getAsJsonPrimitive("likes").getAsInt());
		    setRelease_type(jobject.getAsJsonPrimitive("release_type").getAsString());
		    
		    project_url=jobject.getAsJsonPrimitive("project_url").getAsString();
		    
		    setLatestDownload(new CurseDownload(jobject.getAsJsonObject("download").getAsJsonPrimitive("url").getAsString(),jobject.getAsJsonObject("download").getAsJsonPrimitive("name").getAsString(),jobject.getAsJsonObject("download").getAsJsonPrimitive("type").getAsString(),jobject.getAsJsonObject("download").getAsJsonPrimitive("version").getAsString(), jobject.getAsJsonObject("download").getAsJsonPrimitive("downloads").getAsInt(),jobject.getAsJsonObject("download").getAsJsonPrimitive("id").getAsLong()));
		    
		    JsonObject versionsObject = jobject.getAsJsonObject("versions");
		    
		    for(Map.Entry<String, JsonElement> entry : versionsObject.entrySet()){
		    	ArrayList<CurseDownload> downloads = new ArrayList<CurseDownload>();
		    	
		    	JsonArray array = entry.getValue().getAsJsonArray();
		    	for (int i = 0; i<array.size();i++){
		    		JsonObject downloadVersion = array.get(i).getAsJsonObject();
		    		downloads.add(new CurseDownload(downloadVersion.getAsJsonPrimitive("url").getAsString(),downloadVersion.getAsJsonPrimitive("name").getAsString(),downloadVersion.getAsJsonPrimitive("type").getAsString(),downloadVersion.getAsJsonPrimitive("version").getAsString(), downloadVersion.getAsJsonPrimitive("downloads").getAsInt(),downloadVersion.getAsJsonPrimitive("id").getAsLong()));
		    	}
		    	 
		    	modsByVersion.add(new MinecraftVersions(entry.getKey(), downloads));
		    }
		    
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public String getProjectUrl(){
		return project_url;
	}
	
	public CurseDownload getLatestDownload() {
		return latestDownload;
	}

	public void setLatestDownload(CurseDownload latestDownload) {
		this.latestDownload = latestDownload;
	}

	public class CurseDownload {
		private String url,name,type,minecraft_version;
		private int downloads;
		private long identifier;
		
		public CurseDownload(String url, String name, String type, String minecraft_version, int downloads, long identifier) {
			super();
			this.url = url;
			this.name = name;
			this.type = type;
			this.minecraft_version = minecraft_version;
			this.downloads = downloads;
			this.identifier=identifier;
		}

		public long getIdentifier() {
			return identifier;
		}

		public String getUrl() {
			return url;
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public String getMinecraft_version() {
			return minecraft_version;
		}

		public int getDownloads() {
			return downloads;
		}
	}
	
	public class MinecraftVersions {
		private String minecraftVersion;
		private ArrayList<CurseDownload> compatableDownloads;
		
		public MinecraftVersions(String minecraftVersion, ArrayList<CurseDownload> compatableDownloads) {
			super();
			this.minecraftVersion = minecraftVersion;
			this.compatableDownloads = compatableDownloads;
		}
		
		public MinecraftVersions(String minecraftVersion) {
			super();
			this.minecraftVersion = minecraftVersion;
		}

		public String getMinecraftVersion() {
			return minecraftVersion;
		}

		public void setMinecraftVersion(String minecraftVersion) {
			this.minecraftVersion = minecraftVersion;
		}

		public ArrayList<CurseDownload> getCompatableDownloads() {
			return compatableDownloads;
		}

		public void setCompatableDownloads(ArrayList<CurseDownload> compatableDownloads) {
			this.compatableDownloads = compatableDownloads;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRelease_type() {
		return release_type;
	}

	public void setRelease_type(String release_type) {
		this.release_type = release_type;
	}

	public int getFavorites() {
		return favorites;
	}

	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

	public ArrayList<MinecraftVersions> getModsByVersion() {
		return modsByVersion;
	}

	public void setModsByVersion(ArrayList<MinecraftVersions> modsByVersion) {
		this.modsByVersion = modsByVersion;
	}
		
}
