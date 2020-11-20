package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class ListItem{

	@JSONField(name="is_chosen")
	private boolean isChosen;

	@JSONField(name="game_uid")
	private String gameUid;

	@JSONField(name="is_official")
	private boolean isOfficial;

	@JSONField(name="level")
	private int level;

	@JSONField(name="game_biz")
	private String gameBiz;

	@JSONField(name="nickname")
	private String nickname;

	@JSONField(name="region_name")
	private String regionName;

	@JSONField(name="region")
	private String region;

	public void setIsChosen(boolean isChosen){
		this.isChosen = isChosen;
	}

	public boolean isIsChosen(){
		return isChosen;
	}

	public void setGameUid(String gameUid){
		this.gameUid = gameUid;
	}

	public String getGameUid(){
		return gameUid;
	}

	public void setIsOfficial(boolean isOfficial){
		this.isOfficial = isOfficial;
	}

	public boolean isIsOfficial(){
		return isOfficial;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public int getLevel(){
		return level;
	}

	public void setGameBiz(String gameBiz){
		this.gameBiz = gameBiz;
	}

	public String getGameBiz(){
		return gameBiz;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return nickname;
	}

	public void setRegionName(String regionName){
		this.regionName = regionName;
	}

	public String getRegionName(){
		return regionName;
	}

	public void setRegion(String region){
		this.region = region;
	}

	public String getRegion(){
		return region;
	}

	@Override
 	public String toString(){
		return 
			"ListItem{" + 
			"is_chosen = '" + isChosen + '\'' + 
			",game_uid = '" + gameUid + '\'' + 
			",is_official = '" + isOfficial + '\'' + 
			",level = '" + level + '\'' + 
			",game_biz = '" + gameBiz + '\'' + 
			",nickname = '" + nickname + '\'' + 
			",region_name = '" + regionName + '\'' + 
			",region = '" + region + '\'' + 
			"}";
		}
}