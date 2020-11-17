package cc.vimc.mcbot.utils;

import cn.hutool.core.util.ReUtil;

public class RegxUtils {

    private String regex;

    public static final String CQ_CODE_REGX = "\\[CQ\\:image,file=(.*),url=(.*)\\]";
    public static final String CQ_AT = "\\[CQ\\:at,qq=(.*)\\]";

    public RegxUtils(String regex) {
        this.regex = regex;
    }

    public String getCQCodeImageURL() {
        return ReUtil.get(CQ_CODE_REGX, regex, 2);
    }

    public String getCQCodeImageFileName() {
        return ReUtil.get(CQ_CODE_REGX, regex, 1);
    }

    public String getQQ() {
        return ReUtil.get(CQ_AT, regex, 1);
    }

}
