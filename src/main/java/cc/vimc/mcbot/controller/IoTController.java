package cc.vimc.mcbot.controller;

import cc.vimc.mcbot.utils.BeanUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/IoT")
public class IoTController {

    @RequestMapping("/switch")
    public String door(String inTopic, String status) {
        try {
            BeanUtil.mqttGateway.sendToMqtt(status, inTopic);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "ok";
    }
}
