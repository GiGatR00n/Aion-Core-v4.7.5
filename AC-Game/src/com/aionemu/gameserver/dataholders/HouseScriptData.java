/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, ragarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.housing.LBox;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "lboxes")
public class HouseScriptData {

    private static final Logger log = LoggerFactory.getLogger(HouseScriptData.class);
    private static Marshaller marshaller;

    static {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        JAXBContext jc = null;

        try {
            schema = sf.newSchema(new File("./data/static_data/housing/scripts.xsd"));
            jc = JAXBContext.newInstance(HouseScriptData.class);
            marshaller = jc.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-16");
        } catch (Exception e) {
            log.error("Could not instantiate HouseScriptData : \n" + e);
        }
    }

    @XmlElement(name = "lbox", required = true)
    protected List<LBox> scriptData;
    @XmlTransient
    private final Map<Integer, LBox> defaultTemplates = new HashMap<Integer, LBox>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (LBox template : scriptData) {
            defaultTemplates.put(template.getId(), template);
        }
        scriptData.clear();
        scriptData = null;
    }

    public static class XmlFormatter {

        private static final Logger log = LoggerFactory.getLogger(XmlFormatter.class);
        private static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        private static DocumentBuilder db;

        static {
            try {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                log.error("Could not instantiate XmlFormatter : \n" + e);
            }
        }

        public static String format(String unformattedXml) {
            try {
                final Document document = parseXmlFile(unformattedXml);

                OutputFormat format = new OutputFormat(document);
                format.setIndenting(true);
                format.setIndent(2);
                format.setEncoding("UTF-16");
                Writer out = new StringWriter();
                XMLSerializer serializer = new XMLSerializer(out, format);
                serializer.serialize(document);
                return out.toString();
            } catch (IOException e) {
            }
            return null;
        }

        private static Document parseXmlFile(String in) {
            try {
                InputSource is = new InputSource(new StringReader(in));
                return db.parse(is);
            } catch (SAXException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String createScript(int scriptId, int position, int iconId) {
        LBox template = defaultTemplates.get(scriptId);
        LBox result = (LBox) template.clone();
        result.setId(position);
        result.setIcon(iconId);

        HouseScriptData fragment = new HouseScriptData();
        fragment.scriptData = new ArrayList<LBox>();
        fragment.scriptData.add(result);

        Writer writer = new StringWriter();
        try {
            marshaller.marshal(fragment, writer);
        } catch (JAXBException e) {
        }
        return XmlFormatter.format(writer.toString());
    }

    public int size() {
        return defaultTemplates.size();
    }
}
