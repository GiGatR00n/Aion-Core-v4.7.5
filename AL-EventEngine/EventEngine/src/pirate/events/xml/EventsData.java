/*
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aion-Lightning.
 * If not, see <http://www.gnu.org/licenses/>.
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
 *
 */
package pirate.events.xml;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.LoggerFactory;
import pirate.events.enums.EventType;

/**
 *
 * @author flashman
 */
@XmlRootElement(name = "events")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventsData {

    @XmlElement(name = "event")
    private List<EventTemplate> events;
    private Map<EventType, EventTemplate> eventsByType;

    void afterUnmarshal(Unmarshaller u, Object parent) {
        eventsByType = new EnumMap<EventType, EventTemplate>(EventType.class);
        for (EventTemplate et : events) {
            if (this.eventsByType.containsKey(et.getEventType())) {
                LoggerFactory.getLogger(EventsData.class).info("[afterUnmarshal] Events map contains type: {}", et.getEventType().name());
                continue;
            }
            this.eventsByType.put(et.getEventType(), et);
        }
    }

    public int size() {
        return this.eventsByType.size();
    }

    public EventTemplate getEventTemplate(EventType type) {
        return this.eventsByType.get(type);
    }

    /**
     * List Eventov after marshaling.
     *
     * @return
     */
    public Collection<EventTemplate> getMappedEvents() {
        return this.eventsByType.values();
    }

    /**
     * List with opening event to be marshaled.
     *
     * @return
     */
    public List<EventTemplate> getNotMappedEvents() {
        return events;
    }

    public void setEvents(List<EventTemplate> events) {
        this.events = events;
        this.afterUnmarshal(null, null);
    }
}
