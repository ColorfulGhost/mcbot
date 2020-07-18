package cc.vimc.mcbot.pojo;

import lombok.Data;

@Data
public class BangumiList {
    private String titleCN;
    private String titleJP;
    private String titleEN;
    private String officalSite;
    private String weekDayJP;
    private String weekDayCN;
    private String timeJP;
    private String timeCN;
    private String[] onAirSite;
    private boolean newBgm;
    private Integer bgmId;
    private String showDate;
}
