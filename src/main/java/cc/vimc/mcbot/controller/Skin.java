package cc.vimc.mcbot.controller;

import cc.vimc.mcbot.mapper.CoolQUserMapper;
import cc.vimc.mcbot.pojo.CoolQUser;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;


@RestController
@RequestMapping("/skin")
public class Skin {
    @Autowired
    CoolQUserMapper coolQUserMapper;

    @GetMapping(value = "/getSkin/{player}.png", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    byte[] getPlayerSkin(@PathVariable String player) {
        CoolQUser coolQUser = coolQUserMapper.selectUserExist(player);
        if (coolQUser ==null) {
            //史蒂夫皮肤
            return IOUtils.decodeBase64("iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAIb0lEQVR4Ae1aa4hVVRRe5z7n4Ty0Ya6m5YijKQpOpBhm/krJgsDeRWAgQdSfCKIfUlJQKPYrRIMI7J/RjwoL1EBEyD+NpGm+a3yRzeA47xnvu/Xtc9eZfc6ce++5c8+M98psuGe/1lp7f2utvfa+Zx/DoMJp+UMNWVAkkkmKhMOKGGWk5N0gvby2XZXzPb44fLrYEPlYp6U95GUUAK6PRhVpMpOxWMI1aVUeibtjrI8q3Vn0lVjwpAABP8aKCAWDrjj2Hjtla39+2VJavLDW1laJlUCxSVnuzpbP5KyPpQClyJJwkzF3bp1bc8W1FVWADjIQCFAqnaYw5/dL8oQE6x7AkWpzgfB+UYCnGADwAJ7gHHEgPmpQc5Od9fMXVtPN3hQteMBs7xs2d4pKV5QdRZ7ZBtnlAVxStC5L+m6AaA/A9bxRVAtwwWIsm89oOGX4aRgGBfhXGw1R0vR4ClCaUrzzhYwAxdMpSqXShFgQDgUpyzzB3K4Q5s1hLJ5iOVluz7Icc4hAwNw18p0jrvePuO+hMsMpzm0egIkDEIAEGEFNJER3E+YMUtkM1ddEqClisEqC1Dscp3SWl0YINCkaSzEvKyadSk2YcrFzxASGaWwIhYIhSiTY3AETANwBiognsO2xJVkRu9/dStFwhGprGmlsZJDNHqC+//6lnT8copGxBCXZRUJBg8CrlIAtM21QJBJUHuHlHDGNmG1DBVLs1nDpKCtCTZ7rAALrRyJh2rH1NcokDertG6ZbPb3UdauH6xnqHxqkt59ap2hAC54080IGZJlbZkodoTEiYkYp5wjwTEcyt0EjzRPkybIlk+zKbz65it7buJbq2YJ1fOB5dvuXdODYYqpJJmhhrJX2/RyjbXsPUmPLg4oGtOABr5LBsohlIlX6OcIKgpRld2UrRSIRenV1G81paKbB5F1qqW2g2NwFFIo208GjRxWoF59eTzevX6Z/unupJhqmxnAN3RnqpwOdV3k5JZT1RQESBN3OEfCK8zeG7mkQNFY+3JjFMggHQrRm6SJ64pE2SsdH6HZ/v9rS5sdaKMLW/P54J21e8ahSwK/nTtMz6ztocCRJw8OjNHtWmFqamykYraffLl6l3y91KY9CfMGuoJ8j0gxazhGVoIDQ62uWKlAAANDZdFxtdaFIlLJGktd8t7V2D50+ScGwuXEMj6Upzksiy/YDbZgPSmnmfXzRfFrXPo8yvBtAHhLOBj+duazKeOAcsWlZm1Lc9hudVvu9KPCub08vbe5EPLPSn11vWGUULl644GSx9Wc7O7MfbN9ma5PK7s++IdqzR6quubF/f2H5Bw5kqb3d5L1yhd75aifdGhiheU31Kv/xj8sF+Z2D2s4Bzs7J1vO9H5isPBufgEejXrYRea94+jPkXdw0U7IH6NaHF5SapsQDpvRNEIP2w/KiKKNj2ftqzY/Gu3nPj1HrnMekT+U9d06S9KFB7x8eu0knnhsdp8fkNmwgjoj0yS9f06JYI3V188mR045n3zLprl0zc3kuXEhUp708yb16o3jcpDh/XijNXOhBBxpnf0eHjd7YsqVgTFBLAAAnk2bVLjDZupkfP6zJpiYFCIBt4GfPJsIPSeiRAzzAyM+kGH/qtGgVGSiP5pQvNGgrMVlLANafVMLgehILcpuyOv5G6xYuQK+LUQqBhWM8L50HbTIGlOH0KJuQ4pXyg+DQ0PgoWAKSZJKo62WdXi/39JicAChlkaXnYnW09fXpPZMqWx5QiBveIXEA696WGhqI3IC0tpruKmAkB7NOjzpAuyVW3N6rx62egXNnaHFmBVGX1UR/X/iLmuoiZsPVi9SSuTXeyaVXbLWJFVcPAEgdaMEYoYOBMvSkA0NsQNJppDwwYPbpT51Xb+fy7cERqwXgB0ZzLy2sVu8FVw+wgpsmR2LEhL4GNoeuBPDA+kgAJsDNlvGngEcLaEAL0LJcUGc5AGdZeJxblXRF6F3S3tJY/FygFCDgdCGeywhSkvSytDlzNxoB7aR11EUR+YBBWS3Mk6/fIU5VXT3ASViSgsT6TiFuIHVl6Hx6OSenkCeIYpxDeqmHmmInFd1At/0A5GQWuiUr7QHriNI5UwsYPdjBtQFcd22nYJ0ewKWOMvMVAgdL3zb/cCqpQosloPq0WOEcVuqWB+QDGD97Vmhd847vhniHGOJTZJ3aKdrmmX+vTWK8FRpVAfXEx+PtSw+bWyd4WufMsgLuiQ9zgZK51n16iVS8WeI6rAqEACmeIbk7df5W110gP3nhnnxLBUAACAkKQ9Jp0a9odpnH5E3fzjLBK0rzoVtXmiXYoS790uc1n/A+wCvj/ULnqwdUo1JmFFCNVvNzzjMe4Kc2q1HWjAdUo9X8nPOMB/ipzWqUNeMB1Wg1P+c84wF+arMaZc14QDVazc85W2+E/BJa9vcFjvv/Lbs+su7+cfu779ipgnd9peKovCWg3/nr5VKReaSvPAXo12sMQr78kNwjLs9klacAfeoOZehdfpXLfie4qsTvC+pqY7YXnkc2mi9LLUDLl4+/SsdFqPP2t8T7f0tunsK0eYDcL6pX3c7J6Pf7+u2vfAsg/fo1uVPGJOvTogD9FXjReQpouVDRGeTyRW8rszwtCihpjvqdv+4NJQnxTuzLOUC+HfAyrH7tDvqpvv8vNiffPEDWOAZ0fl9QbBLod15t6bc+XvgnS+OLB2CN6wpwDXS5GRbqA4lcbLoBgpJw/S3KKeUa3E0e2nxRQD7henu+QOi0vPAIOLc7P+kT2nJy35ZAPoBeJ+cGVHihpKlKZXuAXKtP9vuCpuHcB045hIWsCyXJEpG8XMWUrQCZgCjC+QFFse8LwC/LQF/jhRQhY/qR+7YEypmM7v4S4CBPynp/OeO48f4PWNpWH2yDfKgAAAAASUVORK5CYII=");
        }

        //数据库存在读取缓存正版皮肤
        if (!StringUtils.isEmpty(coolQUser.getSkin())) {
            return IOUtils.decodeBase64(coolQUser.getSkin());
        }

        String fileName = player + ".png";
        String uuidJSON = HttpUtil.get("https://api.mojang.com/users/profiles/minecraft/"+player);
        //获取UUID
        JSONObject nameAndUUID = JSONObject.parseObject(uuidJSON);
        String onlineUUID = nameAndUUID.getString("id");
        File skinFile = new File(fileName);
        //获取皮肤
        HttpUtil.downloadFile("https://crafatar.com/skins/" + onlineUUID, skinFile);
        String base64Skin = Base64.encode(skinFile);
        //缓存皮肤数据
        coolQUserMapper.updateSkin(base64Skin,player);
        FileReader fileReader = new FileReader(skinFile);

        return IoUtil.readBytes(fileReader.getInputStream());

    }

}
