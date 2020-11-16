package cc.vimc.mcbot.pojo;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class YuanShenUserInfo{

	@JSONField(name="role")
	private Object role;

	@JSONField(name="stats")
	private Stats stats;

	@JSONField(name="city_explorations")
	private List<CityExplorationsItem> cityExplorations;

	@JSONField(name="avatars")
	private List<AvatarsItem> avatars;

	public Object getRole(){
		return role;
	}

	public Stats getStats(){
		return stats;
	}

	public List<CityExplorationsItem> getCityExplorations(){
		return cityExplorations;
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