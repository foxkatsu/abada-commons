/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.springframework.web.servlet.menu;

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

import com.abada.json.serializer.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.annotation.security.RolesAllowed;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Menu Entry Service. Search for {@link MenuEntry} and return
 *
 * @author katsu
 */
public class MenuServiceImpl extends ApplicationObjectSupport implements BeanPostProcessor, MenuService {

    private static final Log logger = LogFactory.getLog(MenuServiceImpl.class);
    private static final Pattern SEPARATOR = Pattern.compile("/", Pattern.LITERAL);
    /**
     * Search classes from that package name Must be not null or empty string
     */
    private String parentPackage;
    private List<MenuEntryBean> menus;
    private ResourceBundleMessageSource messageSource;

    protected ResourceBundleMessageSource getMessageSource() {
        if (messageSource == null) {
            try {
                messageSource = this.getApplicationContext().getBean(ResourceBundleMessageSource.class);
            } catch (NoSuchBeanDefinitionException e1) {
                logger.error(e1);
                messageSource = null;
            }
        }
        return messageSource;
    }

    public String getParentPackage() {
        return parentPackage;
    }

    public void setParentPackage(String parentPackage) {
        this.parentPackage = parentPackage;
    }

    protected List<MenuEntryBean> getMenus() {
        if (menus == null) {
            menus = new ArrayList<MenuEntryBean>();
        }
        return menus;
    }

    public void setMenus(List<MenuEntryBean> menus) {
        this.menus = menus;
    }

    private void createMenuEntry(Method m) {
        //Group
        MenuEntry me = m.getAnnotation(MenuEntry.class);
        String[] groups = SEPARATOR.split(me.menuGroup());
        MenuEntryBean group = this.getCreateGroup(groups, 0, this.getMenus(), me);
        //Entry
        RequestMapping rm = m.getAnnotation(RequestMapping.class);
        RequestMapping rc = m.getClass().getAnnotation(RequestMapping.class);
        MenuEntryBean aux = new MenuEntryBean();
        aux.setIcon(me.icon());
        //FIXME Add ContextPath                
        aux.setUrl((rc!=null&&rc.value().length>0?rc.value()[0]:"/")+(rm.value().length>0?(rm.value()[0].startsWith("/")?rm.value()[0].substring(1):rm.value()[0]):""));
        aux.setOrder(me.order());
        aux.setText(me.text());
        aux.setDevices(Arrays.asList(me.devices()));
        RolesAllowed sec = m.getAnnotation(RolesAllowed.class);
        if (sec != null) {
            aux.setRoles(Arrays.asList(sec.value()));
        }
        group.addChild(aux);
    }

    private synchronized void lookForMenuEntries(Object bean) {
        try {
            if (bean.getClass().getName().startsWith(this.getParentPackage())) {
                List<Method> listMethod = ReflectionUtils.getMethodsWithAnnotation(bean, MenuEntry.class);
                if (listMethod != null && !listMethod.isEmpty()) {
                    for (Method m : listMethod) {
                        createMenuEntry(m);
                    }
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e);
            }
        }

//        try {
//            if (bean.getClass().getName().startsWith(this.getParentPackage())) {
//                List<Method> listMethod = ReflectionUtils.getMethodsWithAnnotation(bean, MenuEntry.class);
//                if (listMethod != null && !listMethod.isEmpty()) {
//                    //Groups
//                    for (Method m : listMethod) {
//                        MenuEntry me = m.getAnnotation(MenuEntry.class);
//                        MenuEntryBean aux = new MenuEntryBean();
//                        aux.setText(this.getText(getMessageSource(), me.menuGroup()));
//                        if (me.orderGroup() >= 0) {
//                            aux.setOrder(me.orderGroup());
//                        }
//                        if (!this.getMenus().contains(aux)) {
//                            this.getMenus().add(aux);
//                        }
//                    }
//                    //MenuEntry
//                    for (Method m : listMethod) {
//                        MenuEntry me = m.getAnnotation(MenuEntry.class);
//                        MenuEntryBean group = this.getGroup(getMessageSource(), me.menuGroup());
//                        if (group != null) {
//                            RequestMapping rm = m.getAnnotation(RequestMapping.class);
//                            MenuEntryBean aux = new MenuEntryBean();
//                            aux.setIcon(me.icon());
//                            //FIXME Add ContextPath
//                            aux.setUrl(rm.value()[0]);
//                            aux.setOrder(me.order());
//                            aux.setText(me.text());
//                            RolesAllowed sec = m.getAnnotation(RolesAllowed.class);
//                            if (sec != null) {
//                                aux.setRoles(Arrays.asList(sec.value()));
//                            }
//                            group.addChild(aux);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e);
//        }
    }

    private MenuEntryBean getCreateGroup( String[] text, int i, List<MenuEntryBean> menus, MenuEntry me) {
        MenuEntryBean group = getCreateGroup(text[i], menus);
        if (group != null) {
            if (i + 1 >= text.length) {
                return group;
            } else {
                return getCreateGroup( text, i + 1, group.getChilds(), me);
            }
        } else {
            return createGroups(text, i, menus, me);
        }
    }

    private MenuEntryBean getCreateGroup(String text, List<MenuEntryBean> menus) {
        for (MenuEntryBean meb : menus) {
            if (meb.getText().equals(text)) {
                return meb;
            }
        }
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        lookForMenuEntries(bean);
        return bean;
    }

    @Override
    public List<MenuEntryBean> getMenus(String contextPath, Locale locale, String... roles) {
        return getMenus(contextPath, Device.DESKTOP,locale, roles);
    }

    @Override
    public List<MenuEntryBean> getMenus(String contextPath, Device device, Locale locale, String... roles) {
        List<MenuEntryBean> result = new ArrayList<MenuEntryBean>();
        MenuEntryBean aux;
        for (MenuEntryBean menu : this.getMenus()) {
            aux = this.getMenus(contextPath, menu, device,locale, roles);
            if (aux != null) {
                result.add(aux);
            }
        }
        return result;
    }

    private MenuEntryBean getMenus(String contextPath, MenuEntryBean menu, Device device,Locale locale, String... roles) {
        MenuEntryBean result = new MenuEntryBean(menu,getMessageSource(),locale);
        if (menu.getChilds() == null || menu.getChilds().isEmpty()) {
            if (result.constainsRoles(roles) && result.constainsDevice(device)) {
                result.setIcon(this.getURL(contextPath, result.getIcon()));
                result.setUrl(this.getURL(contextPath, result.getUrl()));
                return result;
            }
        } else {
            MenuEntryBean aux;
            for (MenuEntryBean c : menu.getChilds()) {
                aux = getMenus(contextPath, c, device,locale, roles);
                if (aux != null) {
                    result.getChilds().add(aux);
                }
            }
            if (result.getChilds() != null && !result.getChilds().isEmpty()) {
                return result;
            }
        }
        return null;
    }

    private String getURL(String contextPath, String url) {
        if (url != null && !url.isEmpty()) {
            String result = contextPath;
            if (url.startsWith("/")) {
                result += url;
            } else {
                result += "/" + url;
            }
            return result;
        }
        return "";
    }

    private MenuEntryBean createGroups(String[] text, int i, List<MenuEntryBean> menus, MenuEntry me) {
        MenuEntryBean result = null;
        for (int i2 = i; i2 < text.length; i2++) {
            MenuEntryBean aux = new MenuEntryBean();
            aux.setText(text[i2]);
            if (i2 == 0) {
                aux.setOrder(me.orderGroup());
            }
            if (result != null) {
                result.addChild(aux);
            } else {
                menus.add(aux);
            }
            result = aux;
        }
        return result;
    }
}
