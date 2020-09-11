package cc.vimc.mcbot.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class HitokotoDataModel {

	@JSONField(name="hitokoto")
	private String hitokoto;

	@JSONField(name="creator")
	private String creator;

	@JSONField(name="commit_from")
	private String commitFrom;

	@JSONField(name="length")
	private int length;

	@JSONField(name="created_at")
	private String createdAt;

	@JSONField(name="from")
	private String from;

	@JSONField(name="creator_uid")
	private int creatorUid;

	@JSONField(name="id")
	private int id;

	@JSONField(name="reviewer")
	private int reviewer;

	@JSONField(name="type")
	private String type;

	@JSONField(name="from_who")
	private String fromWho;

	@JSONField(name="uuid")
	private String uuid;
}