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

import com.abada.json.Json;
import com.abada.json.JsonFactory;
import com.abada.json.JsonType;
import com.abada.springframework.security.authentication.Role;
import com.abada.springframework.security.authentication.Role1;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


public class HttpDniAuthenticationDao implements DniAuthenticationDao {

    private static final Log logger= LogFactory.getLog(HttpDniAuthenticationDao.class);
    
    private URL url;
    private String username;
    private String password;
    private Json json;        

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HttpDniAuthenticationDao() {
        json=JsonFactory.getInstance().getInstance(JsonType.DEFAULT);
    }
    
    @Override
    public UserDetails getUserByDNI(String dni) {
        try{
            Collection<? extends GrantedAuthority> roles=doLogin(url, username, password, dni);
            UserDetails result=createUserDetails(dni,roles);
            return result;            
        }catch (Exception e){
            logger.error("Error",e);
        }
        return null;
    }
    
    private Collection<? extends GrantedAuthority> doLogin(URL url, String username,String password,String dni) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        try {
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(AuthScope.ANY),
                    new UsernamePasswordCredentials(username, password));
            URIBuilder urib=new URIBuilder(url.toURI().toASCIIString());
            urib.addParameter("dni", dni);
            httpget = new HttpGet(urib.build());
            httpResponse = httpclient.execute(httpget);
            entity = httpResponse.getEntity();

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                logger.trace(httpResponse.getStatusLine().toString());
                String body = new java.util.Scanner(new InputStreamReader(entity.getContent())).useDelimiter("\\A").next();
                List<? extends GrantedAuthority> roles = json.deserialize(body, new TypeToken<List<Role>>() {
                }.getType());
                if (roles != null && roles.size() > 0 && roles.get(0).getAuthority() == null) {
                    roles = json.deserialize(body, new TypeToken<List<Role1>>() {
                    }.getType());
                }
                return roles;
            } else {
                throw new Exception(httpResponse.getStatusLine().getStatusCode() + " Http code response.");
            }
        } catch (Exception e) {
            if (entity != null) {
                logger.error(log(entity.getContent()), e);
            } else {
                logger.error(e);
            }
            throw e;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
    
    private String log(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[1024];
        int read;
        while ((read = is.read(b)) != -1) {
            sb.append(new String(b, 0, read));
        }
        return sb.toString();
    }        

    private UserDetails createUserDetails(String dni, Collection<? extends GrantedAuthority> roles) {
        UserDetails result=new User(dni, "", roles);
        return result;
    }
}
