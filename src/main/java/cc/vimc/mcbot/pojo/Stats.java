package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class Stats{

	@JSONField(name="domain_number")
	private int domainNumber;

	@JSONField(name="exquisite_chest_number")
	private int exquisiteChestNumber;

	@JSONField(name="geoculus_number")
	private int geoculusNumber;

	@JSONField(name="spiral_abyss")
	private String spiralAbyss;

	@JSONField(name="luxurious_chest_number")
	private int luxuriousChestNumber;

	@JSONField(name="anemoculus_number")
	private int anemoculusNumber;

	@JSONField(name="precious_chest_number")
	private int preciousChestNumber;

	@JSONField(name="way_point_number")
	private int wayPointNumber;

	@JSONField(name="win_rate")
	private int winRate;

	@JSONField(name="avatar_number")
	private int avatarNumber;

	@JSONField(name="common_chest_number")
	private int commonChestNumber;

	@JSONField(name="active_day_number")
	private int activeDayNumber;

	@JSONField(name="achievement_number")
	private int achievementNumber;

	public int getDomainNumber(){
		return domainNumber;
	}

	public int getExquisiteChestNumber(){
		return exquisiteChestNumber;
	}

	public int getGeoculusNumber(){
		return geoculusNumber;
	}

	public String getSpiralAbyss(){
		return spiralAbyss;
	}

	public int getLuxuriousChestNumber(){
		return luxuriousChestNumber;
	}

	public int getAnemoculusNumber(){
		return anemoculusNumber;
	}

	public int getPreciousChestNumber(){
		return preciousChestNumber;
	}

	public int getWayPointNumber(){
		return wayPointNumber;
	}

	public int getWinRate(){
		return winRate;
	}

	public int getAvatarNumber(){
		return avatarNumber;
	}

	public int getCommonChestNumber(){
		return commonChestNumber;
	}

	public int getActiveDayNumber(){
		return activeDayNumber;
	}

	public int getAchievementNumber(){
		return achievementNumber;
	}

	@Override
 	public String toString(){
		return 
			"Stats{" + 
			"domain_number = '" + domainNumber + '\'' + 
			",exquisite_chest_number = '" + exquisiteChestNumber + '\'' + 
			",geoculus_number = '" + geoculusNumber + '\'' + 
			",spiral_abyss = '" + spiralAbyss + '\'' + 
			",luxurious_chest_number = '" + luxuriousChestNumber + '\'' + 
			",anemoculus_number = '" + anemoculusNumber + '\'' + 
			",precious_chest_number = '" + preciousChestNumber + '\'' + 
			",way_point_number = '" + wayPointNumber + '\'' + 
			",win_rate = '" + winRate + '\'' + 
			",avatar_number = '" + avatarNumber + '\'' + 
			",common_chest_number = '" + commonChestNumber + '\'' + 
			",active_day_number = '" + activeDayNumber + '\'' + 
			",achievement_number = '" + achievementNumber + '\'' + 
			"}";
		}
}