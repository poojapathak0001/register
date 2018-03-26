package org.register.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	static List <User> users= new ArrayList<User>();
    static int userCount=3;
    static {
        users.add(new User(100, "AB", "ab@gmail.com", "abc"));        
        users.add(new User(101, "XY", "xy@gmail.com", "xyz"));
        users.add(new User(102, "PQ", "pq@gmail.com", "pqr"));
    }
    
    
    public List<User> getAll(){
        return users;
    }
    
    public User save(User user) {
        if(user.getUserId()==null) {
            user.setUserId(++userCount);
        }
        users.add(user);
        return user; 
    }
    public User findOne(int id) {
        
        for(User user:users) {
            if(user.getUserId()==id) {
                return user;
            }
            
        }
        return null;
    }
    
    public List<User> findAll() {
       
        return users;
    }

    public User deleteById(int id) {
        Iterator<User> itr=users.iterator();
        while(itr.hasNext()) {
            User user=itr.next();
            if(user.getUserId()==id) {
                users.remove(user);
                return user;
            }
            
        }
        return null;
    }
}
