package com.sample.consumingrest.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class APIController {

    @PostMapping(value="/pushEvents", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getPushEvents() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://soap-adobe-example.us-e2.cloudhub.io/NmsRtEvent/rtEventMethodsSoap";

        // create headers
        HttpHeaders headers = new HttpHeaders();
// set `content-type` header
        headers.setContentType(MediaType.APPLICATION_XML);

        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:nms:rtEvent\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <urn:PushEvents>\n" +
                "         <urn:sessiontoken>mc/vN1B0rVrMXc8</urn:sessiontoken>\n" +
                "         <urn:domEventCollection>\n" +
                "         <Events>\n" +
                "           <rtEvent type=\"bcpTeste\" email=\"bsaul@adobe.com\">\n" +
                "                           <ctx>\n" +
                "                           <website name=\"Adobe\"/>\n" +
                "                           <customer number=\"1234\"/>\n" +
                "                           <customer name=\"Benjamin2\"/>\n" +
                "                           <customer lastName=\"Saul\"/>\n" +
                "                           <customer email=\"bsaul@adobe.com\"/>\n" +
                "             \n" +
                "                        </ctx>\n" +
                "        </rtEvent>\n" +
                "\n" +
                "        <rtEvent type=\"bcpTeste\" email=\"bsaul@adobe.com\">\n" +
                "                        <ctx>\n" +
                "            \t\t\t\t<website name=\"Adobe\"/>\n" +
                "                           <customer number=\"1234\"/>\n" +
                "                           <customer name=\"Benjamin2\"/>\n" +
                "                           <customer lastName=\"Saul\"/>\n" +
                "                           <customer email=\"bsaul@adobe.com\"/>\n" +
                "                                                                          \n" +
                "                                                           </ctx>\n" +
                "                                           </rtEvent>\n" +
                "                           </Events>\n" +
                "         </urn:domEventCollection>\n" +
                "      </urn:PushEvents>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>\n" +
                " \n";

        ResponseEntity<String> respuesta = restTemplate.postForEntity(url,request,String.class);
        return "Exito";
    }
    @GetMapping(value="/getIds")
    public List<String> getIdsEvents(){
        List<String> lstStrings = new ArrayList<String>();

        String xml = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soap:Body>\n" +
                "      <ns0:PushEventsResponse xmlns:ns0=\"urn:nms:rtEvent\">\n" +
                "         <ns0:pdomIds>\n" +
                "            <ns0:Events>\n" +
                "               <ns0:rtEvent created=\"2020-03-18 22:52:38.814Z\" createdBy-id=\"1940\" email=\"bsaulcorreo1@adobe.com\" emailFormat=\"0\" folder-id=\"1936\" id=\"72057594037952937\" lastModified=\"2020-03-18 22:52:38.813Z\" scheduled=\"2020-03-18 22:52:38.814Z\" status=\"0\" type=\"bcpTeste\" wishedChannel=\"0\"/>\n" +
                "               <ns0:rtEvent created=\"2020-03-19 22:52:38.814Z\" createdBy-id=\"1941\" email=\"bsaulcorreo2@adobe.com\" emailFormat=\"0\" folder-id=\"1936\" id=\"6467586769997655\" lastModified=\"2020-03-18 22:52:38.813Z\" scheduled=\"2020-03-18 22:52:38.814Z\" status=\"0\" type=\"bcpTeste\" wishedChannel=\"0\"/>\n" +
                "            </ns0:Events>\n" +
                "         </ns0:pdomIds>\n" +
                "      </ns0:PushEventsResponse>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";

        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            NodeList list = doc.getElementsByTagName("ns0:rtEvent");
            for(int i = 0; i<list.getLength(); i++){
                Node nodo = list.item(i);
                String id = nodo.getAttributes().getNamedItem("id").getNodeValue();
                lstStrings.add(id);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return lstStrings;
    }
}
