/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.Group;
import jpa.GroupId;
import jpa.User;

/**
 *
 * @author madLife
 */
@Stateless
public class GroupManager {
    
     @PersistenceContext private EntityManager em;
     
     public void addToGroup( User user, String gid ){
         if( user!=null )
         {
            GroupId groupId = new GroupId();
            groupId.setGroupid(gid);
            groupId.setUserid(user.getUserid());
            Group group = new Group();
            group.setId(groupId);
            em.persist(group);
         }       
     }
}
