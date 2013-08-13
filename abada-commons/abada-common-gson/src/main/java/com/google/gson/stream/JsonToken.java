/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gson.stream;

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

/**
 * A structure, name or value type in a JSON-encoded string.
 *
 * @author Jesse Wilson
 * @since 1.6
 */
public enum JsonToken {

  /**
   * The opening of a JSON array. Written using {@link JsonWriter#beginObject}
   * and read using {@link JsonReader#beginObject}.
   */
  BEGIN_ARRAY,

  /**
   * The closing of a JSON array. Written using {@link JsonWriter#endArray}
   * and read using {@link JsonReader#endArray}.
   */
  END_ARRAY,

  /**
   * The opening of a JSON object. Written using {@link JsonWriter#beginObject}
   * and read using {@link JsonReader#beginObject}.
   */
  BEGIN_OBJECT,

  /**
   * The closing of a JSON object. Written using {@link JsonWriter#endObject}
   * and read using {@link JsonReader#endObject}.
   */
  END_OBJECT,

  /**
   * A JSON property name. Within objects, tokens alternate between names and
   * their values. Written using {@link JsonWriter#name} and read using {@link
   * JsonReader#nextName}
   */
  NAME,

  /**
   * A JSON string.
   */
  STRING,

  /**
   * A JSON number represented in this API by a Java {@code double}, {@code
   * long}, or {@code int}.
   */
  NUMBER,

  /**
   * A JSON {@code true} or {@code false}.
   */
  BOOLEAN,

  /**
   * A JSON {@code null}.
   */
  NULL,

  /**
   * The end of the JSON stream. This sentinel value is returned by {@link
   * JsonReader#peek()} to signal that the JSON-encoded value has no more
   * tokens.
   */
  END_DOCUMENT
}
