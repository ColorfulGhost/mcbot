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

	public int getExplorationPercentage(){
		return explorationPercentage;
	}

	public int getLevel(){
		return level;
	}

	public String getIcon(){
		return icon;
	}

	public String getName(){
		return name;
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