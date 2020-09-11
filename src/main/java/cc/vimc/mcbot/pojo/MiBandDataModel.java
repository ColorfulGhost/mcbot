package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class MiBandDataModel {

	@JSONField(name="calories")
	private int calories;

	@JSONField(name="steps")
	private int steps;

	@JSONField(name="fat_burned")
	private int fatBurned;

	@JSONField(name="meters")
	private int meters;

	public void setCalories(int calories){
		this.calories = calories;
	}

	public int getCalories(){
		return calories;
	}

	public void setSteps(int steps){
		this.steps = steps;
	}

	public int getSteps(){
		return steps;
	}

	public void setFatBurned(int fatBurned){
		this.fatBurned = fatBurned;
	}

	public int getFatBurned(){
		return fatBurned;
	}

	public void setMeters(int meters){
		this.meters = meters;
	}

	public int getMeters(){
		return meters;
	}
}