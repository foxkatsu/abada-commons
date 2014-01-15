/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gson;

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

import java.lang.reflect.Type;

/**
 * Context for serialization that is passed to a custom serializer during invocation of its
 * {@link JsonSerializer#serialize(Object, Type, JsonSerializationContext)} method.
 *
 * @author Inderjeet Singh
 * @author Joel Leitch
 */
public interface JsonSerializationContext {

  /**
   * Invokes default serialization on the specified object.
   *
   * @param src the object that needs to be serialized.
   * @return a tree of {@link JsonElement}s corresponding to the serialized form of {@code src}.
   */
  public JsonElement serialize(Object src);

  /**
   * Invokes default serialization on the specified object passing the specific type information.
   * It should never be invoked on the element received as a parameter of the
   * {@link JsonSerializer#serialize(Object, Type, JsonSerializationContext)} method. Doing
   * so will result in an infinite loop since Gson will in-turn call the custom serializer again.
   *
   * @param src the object that needs to be serialized.
   * @param typeOfSrc the actual genericized type of src object.
   * @return a tree of {@link JsonElement}s corresponding to the serialized form of {@code src}.
   */
  public JsonElement serialize(Object src, Type typeOfSrc);
}
