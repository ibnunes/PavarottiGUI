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

import org.pavarotti.core.intf.Person;
import org.pavarotti.core.intf.StaffMember;

/**
 *
 * @author Igor Nunes, Beatriz Costa
 */
public class StaffTableItem {
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty position;
    private SimpleStringProperty admission;
    private SimpleStringProperty birthday;
    private SimpleStringProperty gender;
    
    public StaffTableItem() { }
    
    public <T extends Person & StaffMember> StaffTableItem(T t) {
        this.id        = new SimpleStringProperty(String.valueOf(t.getID()));
        this.name      = new SimpleStringProperty(t.getName());
        this.position  = new SimpleStringProperty(t.getPosition());
        this.admission = new SimpleStringProperty(t.getAdmission().toString());
        this.birthday  = new SimpleStringProperty(t.getBirthday().toString());
        this.gender    = new SimpleStringProperty(t.getGender().toString());
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

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public String getAdmission() {
        return admission.get();
    }

    public void setAdmission(String admission) {
        this.admission.set(admission);
    }

    public String getBirthday() {
        return birthday.get();
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }
}
