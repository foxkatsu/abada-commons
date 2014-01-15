#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package};

/*
 * #%L
 * Web Archetype Module for DWM
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

import com.katsu.springframework.web.servlet.menu.MenuEntry;
import java.util.Arrays;
import javax.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author katsu
 */
@Controller("${artifactId.replace('.','-')}Controller")
public class SampleController{

    @RequestMapping(value = "/${artifactId.replace('.','-')}/bam.htm")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    @MenuEntry(icon = "${artifactId.replace('.','-')}/image/monitoriza.png", menuGroup = "${artifactId.replace('.','-')}", order = 0, text = "${artifactId.replace('.','-')}", iconGroup = "",orderGroup = -1)
    public String getBam(Model model) {
        model.addAttribute("js", Arrays.asList("${artifactId.replace('.','/')}/web/js/sample.js"));
        return "dynamic/main";
    }
}
