/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.katsu.eclipselink;

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

import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.sessions.JNDIConnector;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author katsu
 */
public class JNDIEclipseLinkSessionCustomizer implements SessionCustomizer {

    private static final Log log = LogFactory.getLog(JNDIEclipseLinkSessionCustomizer.class);

    /**
     * Get a dataSource connection and set it on the session with lookupType=STRING_LOOKUP
     */
    @Override
    public void customize(Session session) throws Exception {
        JNDIConnector connector = null;
        Context context = null;
        try {
            context = new InitialContext();
            if (null == context) {
                throw new Exception("JNDIEclipseLinkSessionCustomizer: Context is null");
            }
            connector = (JNDIConnector) session.getLogin().getConnector(); // possible CCE
            // Change from Composite to String_Lookup
            connector.setLookupType(JNDIConnector.STRING_LOOKUP);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
