/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.katsu.extjs;

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
import java.util.List;

/**
 * Usado para representar la estructura que espera Extjs en los TreePanel
 * @author katsu
 */
@NoOfuscation
public class TreeResponse {
    /**
     *
     */
    private Integer id;
    /**
     * Texto a mostrar
     */
    private String text;
    /**
     * Si es una hoja o no
     */
    private boolean leaf;
    /**
     * Hijos si que tiene si no es una hoja
     */
    private List<TreeResponse> children;

    public List<TreeResponse> getChildren() {
        return children;
    }

    public void setChildren(List<TreeResponse> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
