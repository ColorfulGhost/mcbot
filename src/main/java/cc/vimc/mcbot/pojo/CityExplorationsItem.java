package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class CityExplorationsItem{

	@JSONField(name="exploration_percentage")
	private int explorationPercentage;

	@JSONField(name="level")
	private int level;

	@JSONField(name="icon")
	private String icon;

	@JSONField(name="name")
	private String name;

	@JSONField(name="id")
	private int id;

	public void setExplorationPercentage(int explorationPercentage){
		this.explorationPercentage = explorationPercentage;
	}

	public int getExplorationPercentage(){
		return explorationPercentage;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public int getLevel(){
		return level;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return icon;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"CityExplorationsItem{" + 
			"exploration_percentage = '" + explorationPercentage + '\'' + 
			",level = '" + level + '\'' + 
			",icon = '" + icon + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}