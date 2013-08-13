/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.springframework.security.web.authentication.preauth.x509;

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

import java.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.web.authentication.preauth.x509.SubjectDnX509PrincipalExtractor;

/**
 *
 * @author katsu
 */
public class X509AuthenticationFilter extends org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter{
    public static final String USER_CERTIFICATE_COOKIE = "userCertificateCookie";
    
    private String filterSubject;

    public String getFilterSubject() {
        return filterSubject;
    }

    public void setFilterSubject(String filterSubject) {
        this.filterSubject = filterSubject;
        SubjectDnX509PrincipalExtractor principalExtractor=new SubjectDnX509PrincipalExtractor();
        principalExtractor.setSubjectDnRegex(filterSubject);
        this.setPrincipalExtractor(principalExtractor);
    }
    
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        Object result=super.getPreAuthenticatedCredentials(request); //To change body of generated methods, choose Tools | Templates.
        if (result!=null){
            //Save User Certificate in 
            saveCertificateInSession(request);
        }        
        return result;
    }

    private void saveCertificateInSession(HttpServletRequest request) {
        HttpSession session=request.getSession();
        X509Certificate cert=extractClientCertificate(request);
        if (cert!=null){
            session.setAttribute(USER_CERTIFICATE_COOKIE, cert);
        }
    }
    
    private X509Certificate extractClientCertificate(HttpServletRequest request) {
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");

        if (certs != null && certs.length > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("X.509 client authentication certificate:" + certs[0]);
            }

            return certs[0];
        }

        if (logger.isDebugEnabled()) {
            logger.debug("No client certificate found in request.");
        }

        return null;
    }
}
