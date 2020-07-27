package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class DocsItem{

	@JSONField(name="title_chinese")
	private String titleChinese;

	@JSONField(name="title_native")
	private String titleNative;

	@JSONField(name="synonyms")
	private List<String> synonyms;

	@JSONField(name="title_romaji")
	private String titleRomaji;

	@JSONField(name="episode")
	private int episode;

	@JSONField(name="mal_id")
	private int malId;

	@JSONField(name="title")
	private String title;

	@JSONField(name="anilist_id")
	private int anilistId;

	@JSONField(name="is_adult")
	private boolean isAdult;

	@JSONField(name="tokenthumb")
	private String tokenthumb;

	@JSONField(name="synonyms_chinese")
	private List<Object> synonymsChinese;

	@JSONField(name="at")
	private double at;

	@JSONField(name="filename")
	private String filename;

	@JSONField(name="similarity")
	private double similarity;

	@JSONField(name="season")
	private String season;

	@JSONField(name="title_english")
	private String titleEnglish;

	@JSONField(name="from")
	private double from;

	@JSONField(name="to")
	private double to;

	@JSONField(name="anime")
	private String anime;

	public void setTitleChinese(String titleChinese){
		this.titleChinese = titleChinese;
	}

	public String getTitleChinese(){
		return titleChinese;
	}

	public void setTitleNative(String titleNative){
		this.titleNative = titleNative;
	}

	public String getTitleNative(){
		return titleNative;
	}

	public void setSynonyms(List<String> synonyms){
		this.synonyms = synonyms;
	}

	public List<String> getSynonyms(){
		return synonyms;
	}

	public void setTitleRomaji(String titleRomaji){
		this.titleRomaji = titleRomaji;
	}

	public String getTitleRomaji(){
		return titleRomaji;
	}

	public void setEpisode(int episode){
		this.episode = episode;
	}

	public int getEpisode(){
		return episode;
	}

	public void setMalId(int malId){
		this.malId = malId;
	}

	public int getMalId(){
		return malId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setAnilistId(int anilistId){
		this.anilistId = anilistId;
	}

	public int getAnilistId(){
		return anilistId;
	}

	public void setIsAdult(boolean isAdult){
		this.isAdult = isAdult;
	}

	public boolean isIsAdult(){
		return isAdult;
	}

	public void setTokenthumb(String tokenthumb){
		this.tokenthumb = tokenthumb;
	}

	public String getTokenthumb(){
		return tokenthumb;
	}

	public void setSynonymsChinese(List<Object> synonymsChinese){
		this.synonymsChinese = synonymsChinese;
	}

	public List<Object> getSynonymsChinese(){
		return synonymsChinese;
	}

	public void setAt(double at){
		this.at = at;
	}

	public double getAt(){
		return at;
	}

	public void setFilename(String filename){
		this.filename = filename;
	}

	public String getFilename(){
		return filename;
	}

	public void setSimilarity(double similarity){
		this.similarity = similarity;
	}

	public double getSimilarity(){
		return similarity;
	}

	public void setSeason(String season){
		this.season = season;
	}

	public String getSeason(){
		return season;
	}

	public void setTitleEnglish(String titleEnglish){
		this.titleEnglish = titleEnglish;
	}

	public String getTitleEnglish(){
		return titleEnglish;
	}

	public void setFrom(double from){
		this.from = from;
	}

	public double getFrom(){
		return from;
	}

	public void setTo(double to){
		this.to = to;
	}

	public double getTo(){
		return to;
	}

	public void setAnime(String anime){
		this.anime = anime;
	}

	public String getAnime(){
		return anime;
	}

	@Override
 	public String toString(){
		return 
			"DocsItem{" + 
			"title_chinese = '" + titleChinese + '\'' + 
			",title_native = '" + titleNative + '\'' + 
			",synonyms = '" + synonyms + '\'' + 
			",title_romaji = '" + titleRomaji + '\'' + 
			",episode = '" + episode + '\'' + 
			",mal_id = '" + malId + '\'' + 
			",title = '" + title + '\'' + 
			",anilist_id = '" + anilistId + '\'' + 
			",is_adult = '" + isAdult + '\'' + 
			",tokenthumb = '" + tokenthumb + '\'' + 
			",synonyms_chinese = '" + synonymsChinese + '\'' + 
			",at = '" + at + '\'' + 
			",filename = '" + filename + '\'' + 
			",similarity = '" + similarity + '\'' + 
			",season = '" + season + '\'' + 
			",title_english = '" + titleEnglish + '\'' + 
			",from = '" + from + '\'' + 
			",to = '" + to + '\'' + 
			",anime = '" + anime + '\'' + 
			"}";
		}
}