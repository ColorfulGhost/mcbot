package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class ProxyData{

	@JSONField(name="check_count")
	private int checkCount;

	@JSONField(name="proxy")
	private String proxy;

	@JSONField(name="last_time")
	private String lastTime;

	@JSONField(name="last_status")
	private int lastStatus;

	@JSONField(name="source")
	private String source;

	@JSONField(name="region")
	private String region;

	@JSONField(name="type")
	private String type;

	@JSONField(name="fail_count")
	private int failCount;

	public void setCheckCount(int checkCount){
		this.checkCount = checkCount;
	}

	public int getCheckCount(){
		return checkCount;
	}

	public void setProxy(String proxy){
		this.proxy = proxy;
	}

	public String getProxy(){
		return proxy;
	}

	public void setLastTime(String lastTime){
		this.lastTime = lastTime;
	}

	public String getLastTime(){
		return lastTime;
	}

	public void setLastStatus(int lastStatus){
		this.lastStatus = lastStatus;
	}

	public int getLastStatus(){
		return lastStatus;
	}

	public void setSource(String source){
		this.source = source;
	}

	public String getSource(){
		return source;
	}

	public void setRegion(String region){
		this.region = region;
	}

	public String getRegion(){
		return region;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setFailCount(int failCount){
		this.failCount = failCount;
	}

	public int getFailCount(){
		return failCount;
	}

	@Override
 	public String toString(){
		return 
			"ProxyData{" + 
			"check_count = '" + checkCount + '\'' + 
			",proxy = '" + proxy + '\'' + 
			",last_time = '" + lastTime + '\'' + 
			",last_status = '" + lastStatus + '\'' + 
			",source = '" + source + '\'' + 
			",region = '" + region + '\'' + 
			",type = '" + type + '\'' + 
			",fail_count = '" + failCount + '\'' + 
			"}";
		}
}