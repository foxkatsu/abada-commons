/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.springframework.security.authentication;

/*
 * #%L
 * Katsu Commons
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

import com.katsu.json.Json;
import com.katsu.json.JsonFactory;
import com.katsu.json.JsonType;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.codec.Base64;

/**
 *
 * @author katsu
 */
public class HtmlAuthenticationProvider implements AuthenticationProvider {

    private static final Log logger = LogFactory.getLog(HtmlAuthenticationProvider.class);
    /**
     * Url that return a list of user's roles.
     */
    private URL url;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
    private Json json;

    public HtmlAuthenticationProvider() {
        json=JsonFactory.getInstance().getInstance(JsonType.DEFAULT);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AbstractAuthenticationToken upat = (AbstractAuthenticationToken) authentication;
        if (!upat.isAuthenticated()) {
            try {
                Collection<? extends GrantedAuthority> roles = doLogin(upat);
                if (roles != null) {
                    return createSuccessAuthentication(upat, roles);
                }
            } catch (Exception e) {
                throw new AuthenticationException("Can't authenticate", e) {
                };
            } finally {
            }
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> doLogin(AbstractAuthenticationToken upat) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        try {
//            httpclient.getCredentialsProvider().setCredentials(
//                    new AuthScope(AuthScope.ANY),
//                    new UsernamePasswordCredentials(upat.getPrincipal().toString(), upat.getCredentials().toString()));
            httpget = new HttpGet(url.toURI().toASCIIString());
            httpget.setHeader("Authorization", getAuthString(upat.getPrincipal().toString(),upat.getCredentials().toString()));
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
    /*
     * With httpclient 4.1
     * private Collection<Role> doLogin(UsernamePasswordAuthenticationToken upat) throws Exception {        
     DefaultHttpClient httpclient = new DefaultHttpClient();
     HttpEntity entity = null;
     try {
     httpclient.getCredentialsProvider().setCredentials(
     new AuthScope(AuthScope.ANY),
     new UsernamePasswordCredentials(upat.getPrincipal().toString(), upat.getCredentials().toString()));
     HttpGet httpget = new HttpGet(url.toURI());
    
     System.out.println("executing request" + httpget.getRequestLine());
     HttpResponse response = httpclient.execute(httpget);
     entity = (HttpEntity) response.getEntity();
     if (response.getStatusLine().getStatusCode() == 200) {
     Gson gson = new Gson();
     Role[] roles = gson.fromJson(new InputStreamReader(entity.getContent()), Role[].class);
     return Arrays.asList(roles);
     } else {
     throw new Exception(response.getStatusLine().getStatusCode() + " Http code response.");
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
     }*/

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
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

    private Authentication createSuccessAuthentication(AbstractAuthenticationToken upat, Collection<? extends GrantedAuthority> roles) {
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(upat.getPrincipal(), upat.getCredentials(), roles);
        result.setDetails(upat.getDetails());
        return result;
    }

    private String getAuthString(String username, String password) {
        StringBuilder sb=new StringBuilder("Basic ");
        StringBuilder sb2=new StringBuilder(username);
        sb2.append(':');
        sb2.append(password);
        sb.append(new String(Base64.encode(sb2.toString().getBytes())));
        return sb.toString();
    }
}