package com.example.controller;


import com.example.service.CarToCustomerService;
import com.example.service.CustomerService;
import com.example.utils.RoutePlanning;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.awt.print.Book;
import java.util.List;

/**
 * @author: pwz
 * @create: 2022/9/26 11:24
 * @Description:
 * @FileName: AjaxController
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController {

    @Resource
    CustomerService customerService;

    @ResponseBody
    @PostMapping("/generate")
    public List<List<Double>> generatePath(String time, String model, String startStation,
                                           String consignee, String objective) {
        List<List<Double>> shortestPaths = RoutePlanning.selectStrategyByObjective(
                model, startStation, consignee, objective, 2, 2,
                Integer.parseInt(time) * 60, -1);
        if (shortestPaths == null) {
            return null;
        }
        List<Double> desLocation = customerService.getLocationByName(consignee);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(shortestPaths);
        jsonArray.add(desLocation);
        return jsonArray;
    }

    @ResponseBody
    @PostMapping("/getRoute")
    public List<List<Double>> getRoute(String model, String startStation
            , int uavType, int ugvType, String consignee, String objective, String time, String weight) {
        int weigh = (int) (Float.parseFloat(weight) * 1000);
        List<List<Double>> shortestPaths = RoutePlanning.selectStrategyByObjective(
                model, startStation, consignee, objective, uavType, ugvType, Integer.parseInt(time) * 60, weigh);
        List<Double> desLocation = customerService.getLocationByName(consignee);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(shortestPaths);
        jsonArray.add(desLocation);
        return jsonArray;
    }

    @ResponseBody
    @GetMapping("/workflow")
    public String getWorkflow() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" id=\"sid-38422fae-e03e-43a3-bef4-bd33b32041b2\" targetNamespace=\"http://bpmn.io/bpmn\" exporter=\"bpmn-js (https://demo.bpmn.io)\" exporterVersion=\"10.2.0\">\n" +
                "  <process id=\"Process_1\" isExecutable=\"false\">\n" +
                "    <startEvent id=\"StartEvent_1y45yut\" name=\"hunger noticed\">\n" +
                "      <outgoing>SequenceFlow_0h21x7r</outgoing>\n" +
                "    </startEvent>\n" +
                "    <task id=\"Task_1hcentk\" name=\"choose recipe\">\n" +
                "      <incoming>SequenceFlow_0h21x7r</incoming>\n" +
                "      <outgoing>SequenceFlow_0wnb4ke</outgoing>\n" +
                "    </task>\n" +
                "    <sequenceFlow id=\"SequenceFlow_0h21x7r\" sourceRef=\"StartEvent_1y45yut\" targetRef=\"Task_1hcentk\" />\n" +
                "    <exclusiveGateway id=\"ExclusiveGateway_15hu1pt\" name=\"desired dish?\">\n" +
                "      <incoming>SequenceFlow_0wnb4ke</incoming>\n" +
                "    </exclusiveGateway>\n" +
                "    <sequenceFlow id=\"SequenceFlow_0wnb4ke\" sourceRef=\"Task_1hcentk\" targetRef=\"ExclusiveGateway_15hu1pt\" />\n" +
                "  </process>\n" +
                "  <bpmndi:BPMNDiagram id=\"BpmnDiagram_1\">\n" +
                "    <bpmndi:BPMNPlane id=\"BpmnPlane_1\" bpmnElement=\"Process_1\">\n" +
                "      <bpmndi:BPMNShape id=\"Task_1hcentk_di\" bpmnElement=\"Task_1hcentk\">\n" +
                "        <omgdc:Bounds x=\"250\" y=\"90\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"ExclusiveGateway_15hu1pt_di\" bpmnElement=\"ExclusiveGateway_15hu1pt\" isMarkerVisible=\"true\">\n" +
                "        <omgdc:Bounds x=\"505\" y=\"105\" width=\"50\" height=\"50\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"497\" y=\"81\" width=\"66\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"StartEvent_1y45yut_di\" bpmnElement=\"StartEvent_1y45yut\">\n" +
                "        <omgdc:Bounds x=\"132\" y=\"112\" width=\"36\" height=\"36\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"114\" y=\"155\" width=\"73\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNEdge id=\"SequenceFlow_0h21x7r_di\" bpmnElement=\"SequenceFlow_0h21x7r\">\n" +
                "        <omgdi:waypoint x=\"168\" y=\"130\" />\n" +
                "        <omgdi:waypoint x=\"250\" y=\"130\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"SequenceFlow_0wnb4ke_di\" bpmnElement=\"SequenceFlow_0wnb4ke\">\n" +
                "        <omgdi:waypoint x=\"350\" y=\"130\" />\n" +
                "        <omgdi:waypoint x=\"505\" y=\"130\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "    </bpmndi:BPMNPlane>\n" +
                "  </bpmndi:BPMNDiagram>\n" +
                "</definitions>\n";
    }

}