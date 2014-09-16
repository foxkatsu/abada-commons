#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.web.url;

/*
 * #%L
 * Web Archetype
 * %%
 * Copyright (C) 2013 Katsu
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import org.springframework.context.MessageSource;

/**
 *
 * @author katsu
 */
public class URLFactory {
    private MessageSource messageSource;
    
    private URL serverUrl;
    private String serverUrlRole;

    public synchronized String getServerUrlRole() {
        if (serverUrlRole==null)
            serverUrlRole=messageSource.getMessage("urlServerRoles", null, Locale.getDefault());
        return serverUrlRole;
    }

    public void setServerUrlRole(String serverUrlRole) {
        this.serverUrlRole = serverUrlRole;
    }

    public synchronized  URL getServerUrl() throws MalformedURLException {      
        if (serverUrl==null){
            serverUrl=new URL(messageSource.getMessage("urlServer", null, Locale.getDefault()));
        }
        return serverUrl;
    }

    public void seterverUrl(URL serverUrl) {
        this.serverUrl = serverUrl;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    
}
