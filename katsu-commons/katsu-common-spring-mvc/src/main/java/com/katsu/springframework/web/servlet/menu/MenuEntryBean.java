/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.katsu.springframework.web.servlet.menu;

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

import com.katsu.ofuscation.annotation.NoOfuscation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 *
 * @author katsu
 */
@NoOfuscation
public class MenuEntryBean {
    private String text;
    private String icon;
    private String url;
    private List<Device> devices;
    private List<String> roles;
    private int order;
    private List<MenuEntryBean> childs;        

    public MenuEntryBean(){        
    }
    
    public MenuEntryBean(MenuEntryBean meb,ResourceBundleMessageSource resource,Locale locale){
        this(resource.getMessage(meb.getText(), null,meb.getText(), locale), meb.getIcon(),meb.getUrl(), meb.getRoles(), meb.getOrder(),meb.getDevices());
    }
    
    public MenuEntryBean(String text, String icon, String url, List<String> roles, int order,List<Device> devices) {
        this.text = text;
        this.icon = icon;
        this.url = url;
        this.roles = roles;
        this.order = order;
        this.childs = new ArrayList<MenuEntryBean>();
        this.devices=devices;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<MenuEntryBean> getChilds() {
        return childs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setChilds(List<MenuEntryBean> childs) {
        this.childs = childs;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addChild(MenuEntryBean me){
        if (childs==null)
            childs=new ArrayList<MenuEntryBean>();
        childs.add(me);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuEntryBean other = (MenuEntryBean) obj;
        if ((this.text == null) ? (other.text != null) : !this.text.equals(other.text)) {
            return false;
        }
        if ((this.icon == null) ? (other.icon != null) : !this.icon.equals(other.icon)) {
            return false;
        }
        if (this.childs != other.childs && (this.childs == null || !this.childs.equals(other.childs))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public boolean constainsRoles(String... roles){
        if (this.getRoles()==null) return true;
        if (roles!=null){
            for (String role:roles){
                if (this.getRoles().contains(role))
                    return true;
            }
        }
        return false;
    }
    
    public boolean constainsDevice(Device... devices){
        if (this.getDevices()==null) return true;
        if (devices!=null){
            for (Device device:devices){
                if (this.getDevices().contains(device))
                    return true;
            }
        }
        return false;
    }
    
}
