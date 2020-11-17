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

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public int getLevel(){
		return level;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFetter(int fetter){
		this.fetter = fetter;
	}

	public int getFetter(){
		return fetter;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setElement(String element){
		this.element = element;
	}

	public String getElement(){
		return element;
	}

	public void setRarity(int rarity){
		this.rarity = rarity;
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