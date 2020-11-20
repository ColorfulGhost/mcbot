package cc.vimc.mcbot.pojo;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class UserGameRoles {

	@JSONField(name="list")
	private List<ListItem> list;

	public void setList(List<ListItem> list){
		this.list = list;
	}

	public List<ListItem> getList(){
		return list;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"list = '" + list + '\'' + 
			"}";
		}
}