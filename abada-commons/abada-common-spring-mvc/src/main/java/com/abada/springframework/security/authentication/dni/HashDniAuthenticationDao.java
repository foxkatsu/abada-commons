/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.springframework.security.authentication.dni;

/*
 * #%L
 * Abada Commons
 * %%
 * Copyright (C) 2013 Abada Servicios Desarrollo (investigacion@abadasoft.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.abada.springframework.security.authentication.dni.DniAuthenticationDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


public class HashDniAuthenticationDao implements DniAuthenticationDao {
    private HashMap<String,List<String>> users;

    public HashMap<String, List<String>> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, List<String>> users) {
        this.users = users;
    }
    
    @Override
    public UserDetails getUserByDNI(String dni) {        
        if (dni!=null && users!=null){
            if (users.containsKey(dni)){
                Collection<GrantedAuthority> roles=new ArrayList<GrantedAuthority>();
                for (String r:users.get(dni)){
                    roles.add(new SimpleGrantedAuthority(r));
                }                                    
                return new User(dni, "", roles);
            }
        }
        return null;
    }    
}
