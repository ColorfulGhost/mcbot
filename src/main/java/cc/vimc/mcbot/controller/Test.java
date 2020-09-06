package cc.vimc.mcbot.controller;


import cc.vimc.mcbot.pojo.BangumiList;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@RestController("/test")
public class Test {
    public static final byte[] h = new byte[]{
            65, 80, 77, 80, 89, 68, 90, 70, 89, 68,
            90, 70, 65, 80, 77, 80, 65, 80, 77, 80,
            89, 68, 90, 70};


    @RequestMapping("/2")
    public void test2(){
//        String trainDataFile = "resources/tessdata";
        String fileName = "C:/Users/a8156/Desktop/QQ图片20200731193854.jpg";
//
//        Tesseract tesseract = new Tesseract();
//        tesseract.setDatapath("D:/Code/Java/mcbot/src/main/resources/tessdata");
//        tesseract.setLanguage("chi_sim");
//        try {
//            String s = tesseract.doOCR(new File(fileName));
//            log.error(s);
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }

        HashMap<String, Object> args = new HashMap<>();
        args.put("image", FileUtil.file(fileName));

        HttpUtil.post("http://192.168.1.220:88/doOCR",args);
    }


    @RequestMapping("/1")
    public void test() {


        String bangumiLayerYearJSON = HttpUtil.get("https://bgmlist.com/tempapi/archive.json");
        Map data = JSON.parseObject(bangumiLayerYearJSON).getJSONObject("data").toJavaObject(Map.class);
        Collection yearURLCollect = data.values();
        Map<String, String> lastYear = (Map<String, String>) yearURLCollect.toArray()[yearURLCollect.size() - 1];
        Collection<String> quarterCollect = lastYear.values();
        Map<String, String> lastQuarter = (Map<String, String>) quarterCollect.toArray()[quarterCollect.size() - 1];
        String lastQuarterBangumiURL = lastQuarter.get("path");

        String lastQuarterBangumi = HttpUtil.get(lastQuarterBangumiURL);

        Collection<Object> bgList = JSON.parseObject(lastQuarterBangumi).values();
        List<BangumiList> bangumiList = Convert.convert(new TypeReference<List<BangumiList>>() {
        }, bgList);

        Map<String, List<BangumiList>> tmpBGMList = new HashMap<>();

        for (BangumiList bgm : bangumiList) {
            if (bgm.isNewBgm()) {
                tmpBGMList.computeIfAbsent(bgm.getWeekDayCN(), k -> new ArrayList<>()).add(bgm);
            }
        }
        Date date = new Date();

        SimpleDateFormat hHmm = new SimpleDateFormat("HHmm");
        String hhmmString = hHmm.format(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);


        StringBuilder result = new StringBuilder();

        List<BangumiList> todayBGM = tmpBGMList.get(String.valueOf(dayOfWeek - 1));
        for (BangumiList bgm : todayBGM) {
            String timeCN = bgm.getTimeCN();
            if (!StringUtils.isEmpty(timeCN) && timeCN.equals(hhmmString)) {
                result.append("《").append(bgm.getTimeCN()).append("》").append("更新了！\n").append("放送地址：\n");
                for (String url : bgm.getOnAirSite()) {
                    result.append(url + "\n");
                }
                break;
            }
        }
        if (StringUtils.isEmpty(result.toString())) {
            return;
        }


//        byte[] arrayOfByte3 = a(work_key);
//        /str3 = new String(decryptBody(a.a(str6.getBytes()), arrayOfByte3), "UTF-8");
    }


    public static String workKey(String paramString) {
//        paramString = h.a((Context)App.g()).c(paramString);
//        if (TextUtils.isEmpty(paramString))
//            return "";
        byte[] arrayOfByte = decryptBody(paramString.getBytes(), b(h));
        if (arrayOfByte != null)
            paramString = new String(arrayOfByte);
        return paramString;
    }


    public static byte[] b(byte[] paramArrayOfbyte) {
        byte[] arrayOfByte = paramArrayOfbyte;
        if (paramArrayOfbyte.length != 24) {
            arrayOfByte = new byte[24];
            System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, 0, paramArrayOfbyte.length);
            if (paramArrayOfbyte.length == 16) {
                System.arraycopy(paramArrayOfbyte, 0, arrayOfByte, paramArrayOfbyte.length, 8);
            } else {
//                System.arraycopy(a.h, 0, arrayOfByte, paramArrayOfbyte.length, arrayOfByte.length - paramArrayOfbyte.length);
            }
        }
        return arrayOfByte;
    }

    public static byte[] decryptBody(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
        try {
//            Key key = e(paramArrayOfbyte2);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(paramArrayOfbyte1);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new byte[0];
        }
    }

    public static byte[] a(String paramString) {
        if (paramString != null) {
            byte[] arrayOfByte1 = paramString.getBytes();
            byte[] arrayOfByte2 = new byte[arrayOfByte1.length / 2];
            for (int i = 0; i < arrayOfByte1.length / 2; i++) {
                int j = i * 2;
                arrayOfByte2[i] = (byte) (a(arrayOfByte1[j]) << 4 & 0xF0);
                arrayOfByte2[i] = (byte) (arrayOfByte2[i] + a(arrayOfByte1[j + 1]));
            }
            return arrayOfByte2;
        }
        return null;
    }

    private static int a(byte paramByte) {
        if (paramByte >= 48 && paramByte < 58)
            return paramByte - 48;
        byte b1 = 65;
        if (paramByte < 65 || paramByte >= 71) {
            b1 = 97;
            if (paramByte < 97 || paramByte >= 103)
                return 0;
        }
        return paramByte - b1 + 10;
    }
}
