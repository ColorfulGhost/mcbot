package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class YuanShenUserInfo{

	@JSONField(name="role")
	private Object role;

	@JSONField(name="stats")
	private Stats stats;

	@JSONField(name="city_explorations")
	private List<CityExplorationsItem> cityExplorations;

	@JSONField(name="avatars")
	private List<AvatarsItem> avatars;

	public void setRole(Object role){
		this.role = role;
	}

	public Object getRole(){
		return role;
	}

	public void setStats(Stats stats){
		this.stats = stats;
	}

	public Stats getStats(){
		return stats;
	}

	public void setCityExplorations(List<CityExplorationsItem> cityExplorations){
		this.cityExplorations = cityExplorations;
	}

	public List<CityExplorationsItem> getCityExplorations(){
		return cityExplorations;
	}

	public void setAvatars(List<AvatarsItem> avatars){
		this.avatars = avatars;
	}

	public List<AvatarsItem> getAvatars(){
		return avatars;
	}

	@Override
 	public String toString(){
		return 
			"YuanShenUserInfo{" + 
			"role = '" + role + '\'' + 
			",stats = '" + stats + '\'' + 
			",city_explorations = '" + cityExplorations + '\'' + 
			",avatars = '" + avatars + '\'' + 
			"}";
		}
}