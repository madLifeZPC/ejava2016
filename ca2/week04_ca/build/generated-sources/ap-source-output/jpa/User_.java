package jpa;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.Note;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-04T15:30:53")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> password;
    public static volatile CollectionAttribute<User, Note> notes;
    public static volatile SingularAttribute<User, String> userid;

}