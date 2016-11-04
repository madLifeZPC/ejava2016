/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author madLife
 */
@Entity
@Table(name="groups")
public class Group {
    
    @EmbeddedId 
    private GroupId id;

    public GroupId getId() {
        return id;
    }

    public void setId(GroupId id) {
        this.id = id;
    }
}
