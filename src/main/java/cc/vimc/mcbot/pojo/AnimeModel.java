package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class AnimeModel{

	@JSONField(name="RawDocsCount")
	private int rawDocsCount;

	@JSONField(name="CacheHit")
	private boolean cacheHit;

	@JSONField(name="docs")
	private List<DocsItem> docs;

	@JSONField(name="limit_ttl")
	private int limitTtl;

	@JSONField(name="RawDocsSearchTime")
	private long rawDocsSearchTime;

	@JSONField(name="quota")
	private int quota;

	@JSONField(name="limit")
	private int limit;

	@JSONField(name="ReRankSearchTime")
	private long reRankSearchTime;

	@JSONField(name="quota_ttl")
	private int quotaTtl;

	@JSONField(name="trial")
	private int trial;

	public void setRawDocsCount(int rawDocsCount){
		this.rawDocsCount = rawDocsCount;
	}

	public int getRawDocsCount(){
		return rawDocsCount;
	}

	public void setCacheHit(boolean cacheHit){
		this.cacheHit = cacheHit;
	}

	public boolean isCacheHit(){
		return cacheHit;
	}

	public void setDocs(List<DocsItem> docs){
		this.docs = docs;
	}

	public List<DocsItem> getDocs(){
		return docs;
	}

	public void setLimitTtl(int limitTtl){
		this.limitTtl = limitTtl;
	}

	public int getLimitTtl(){
		return limitTtl;
	}

	public void setRawDocsSearchTime(long rawDocsSearchTime){
		this.rawDocsSearchTime = rawDocsSearchTime;
	}

	public long getRawDocsSearchTime(){
		return rawDocsSearchTime;
	}

	public void setQuota(int quota){
		this.quota = quota;
	}

	public int getQuota(){
		return quota;
	}

	public void setLimit(int limit){
		this.limit = limit;
	}

	public int getLimit(){
		return limit;
	}

	public void setReRankSearchTime(long reRankSearchTime){
		this.reRankSearchTime = reRankSearchTime;
	}

	public long getReRankSearchTime(){
		return reRankSearchTime;
	}

	public void setQuotaTtl(int quotaTtl){
		this.quotaTtl = quotaTtl;
	}

	public int getQuotaTtl(){
		return quotaTtl;
	}

	public void setTrial(int trial){
		this.trial = trial;
	}

	public int getTrial(){
		return trial;
	}

	@Override
 	public String toString(){
		return
			"AnimeModel{" +
			"rawDocsCount = '" + rawDocsCount + '\'' +
			",cacheHit = '" + cacheHit + '\'' +
			",docs = '" + docs + '\'' +
			",limit_ttl = '" + limitTtl + '\'' +
			",rawDocsSearchTime = '" + rawDocsSearchTime + '\'' +
			",quota = '" + quota + '\'' +
			",limit = '" + limit + '\'' +
			",reRankSearchTime = '" + reRankSearchTime + '\'' +
			",quota_ttl = '" + quotaTtl + '\'' +
			",trial = '" + trial + '\'' +
			"}";
		}
}