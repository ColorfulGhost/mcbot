package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class AvatarsItem{

	@JSONField(name="image")
	private String image;

	@JSONField(name="level")
	private int level;

	@JSONField(name="name")
	private String name;

	@JSONField(name="fetter")
	private int fetter;

	@JSONField(name="id")
	private int id;

	@JSONField(name="element")
	private String element;

	@JSONField(name="rarity")
	private int rarity;

	public String getImage(){
		return image;
	}

	public int getLevel(){
		return level;
	}

	public String getName(){
		return name;
	}

	public int getFetter(){
		return fetter;
	}

	public int getId(){
		return id;
	}

	public String getElement(){
		return element;
	}

	public int getRarity(){
		return rarity;
	}

	@Override
 	public String toString(){
		return 
			"AvatarsItem{" + 
			"image = '" + image + '\'' + 
			",level = '" + level + '\'' + 
			",name = '" + name + '\'' + 
			",fetter = '" + fetter + '\'' + 
			",id = '" + id + '\'' + 
			",element = '" + element + '\'' + 
			",rarity = '" + rarity + '\'' + 
			"}";
		}
}