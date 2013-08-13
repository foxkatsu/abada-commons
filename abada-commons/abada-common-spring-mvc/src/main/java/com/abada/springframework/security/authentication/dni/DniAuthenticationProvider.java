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

import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 *
 * @author katsu
 */
public class DniAuthenticationProvider implements AuthenticationProvider{
    private static final Log log = LogFactory.getLog(DniAuthenticationProvider.class);
    /**
     * 
     */
    private DniAuthenticationDao dniAuthenticationDao;

    public DniAuthenticationDao getDniAuthenticationDao() {
        return dniAuthenticationDao;
    }

    public void setDniAuthenticationDao(DniAuthenticationDao dniAuthenticationDao) {
        this.dniAuthenticationDao = dniAuthenticationDao;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AbstractAuthenticationToken auth=(AbstractAuthenticationToken)authentication;
        UserDetails result=dniAuthenticationDao.getUserByDNI(auth.getName());
        if (result!=null){
            return createSuccessAuthentication(auth,result, result.getAuthorities());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
         return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
    
    private PreAuthenticatedAuthenticationToken createSuccessAuthentication(AbstractAuthenticationToken auth,UserDetails upat, Collection<? extends GrantedAuthority> roles) {
        PreAuthenticatedAuthenticationToken result = new PreAuthenticatedAuthenticationToken(upat, upat.getAuthorities(), roles);
        result.setDetails(auth.getDetails());
        return result;
    }
}
