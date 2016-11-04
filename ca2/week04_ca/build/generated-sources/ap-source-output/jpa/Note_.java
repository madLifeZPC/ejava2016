package jpa;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-04T15:30:53")
@StaticMetamodel(Note.class)
public class Note_ { 

    public static volatile SingularAttribute<Note, Timestamp> date;
    public static volatile SingularAttribute<Note, Integer> noteid;
    public static volatile SingularAttribute<Note, String> title;
    public static volatile SingularAttribute<Note, String> category;
    public static volatile SingularAttribute<Note, User> user;
    public static volatile SingularAttribute<Note, String> content;

}