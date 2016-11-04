/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.sql.Timestamp;
import java.util.Collection;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author madLife
 */
@Entity
@Table(name="notes")
public class Note {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="noteid")
    private Integer noteid;
    
    @Column(name="title")
    private String title;
    
    @Column(name="category")
    private String category;
    
    @Column(name="content")
    private String content;
    
    @Column(name="note_date")
    private Timestamp date;
    
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid" )
    private User user;

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public JsonObject toJsonObject(){
        return Json.createObjectBuilder()
                    .add("title", title)
                    .add("category", category)
                    .add("date", date.toString())
                    .add("content", content)
                    .add("user", user.getUserid())
                    .build();
    }
    
}
