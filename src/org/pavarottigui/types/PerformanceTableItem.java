/*
 * Copyright (C) 2020 Igor Nunes, Beatriz Costa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pavarottigui.types;

import javafx.beans.property.SimpleStringProperty;

import org.pavarotti.core.components.Performance;

/**
 *
 * @author Igor Nunes, Beatriz Costa
 */
public class PerformanceTableItem {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty price;
    private SimpleStringProperty days;
    private SimpleStringProperty opera;
    private SimpleStringProperty casting;
    private SimpleStringProperty singers;
    private SimpleStringProperty dancers;
    private SimpleStringProperty tickets;
    
    public PerformanceTableItem() { }
    
    public PerformanceTableItem(
            String id, String name, Double price, Integer days,
            Integer operadir, Integer castingdir,
            Integer singers, Integer dancers, Integer tickets
    ) {
        this.id       = new SimpleStringProperty(id);
        this.name     = new SimpleStringProperty(name);
        this.price    = new SimpleStringProperty(price.toString());
        this.days     = new SimpleStringProperty(days.toString());
        this.opera    = new SimpleStringProperty(operadir.toString());
        this.casting  = new SimpleStringProperty(castingdir.toString());
        this.singers  = new SimpleStringProperty(singers.toString());
        this.dancers  = new SimpleStringProperty(dancers.toString());
        this.tickets  = new SimpleStringProperty(tickets.toString());
    }
    
    public PerformanceTableItem(Performance perf) {
        this.id         = new SimpleStringProperty(perf.getID());
        this.name       = new SimpleStringProperty(perf.getName());
        this.price      = new SimpleStringProperty(perf.getBasePrice().toString());
        this.days       = new SimpleStringProperty(String.valueOf(perf.getHall().size()));
        this.opera      = new SimpleStringProperty(perf.getOperaDirector().toString());
        this.casting    = new SimpleStringProperty(perf.getCastingDirector().toString());
        this.singers    = new SimpleStringProperty(String.valueOf(perf.getSingers().size()));
        this.dancers    = new SimpleStringProperty(String.valueOf(perf.getDancers().size()));
        this.tickets    = new SimpleStringProperty(String.valueOf(perf.ticketsSold()));
    }
    
    public String getId() {
        return id.get();
    }
    
    public void setId(String id) {
        this.id.set(id);
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public String getPrice() {
        return price.get();
    }
    
    public void setPrice(String price) {
        this.price.set(price);
    }
    
    public String getDays() {
        return days.get();
    }
    
    public void setDays(String days) {
        this.days.set(days);
    }
    
    public String getOpera() {
        return opera.get();
    }
    
    public void setOpera(String operadir) {
        this.opera.set(operadir);
    }
    
    public String getCasting() {
        return casting.get();
    }
    
    public void setCasting(String castingdir) {
        this.casting.set(castingdir);
    }
    
    public String getSingers() {
        return singers.get();
    }
    
    public void setSingers(String singers) {
        this.singers.set(singers);
    }
    
    public String getDancers() {
        return dancers.get();
    }
    
    public void setDancers(String dancers) {
        this.dancers.set(dancers);
    }

    public String getTickets() {
        return tickets.get();
    }

    public void setTickets(String tickets) {
        this.tickets.set(tickets);
    }
    
    
}
