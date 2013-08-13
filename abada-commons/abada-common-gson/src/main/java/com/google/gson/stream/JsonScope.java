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
 * Lexical scoping elements within a JSON reader or writer.
 *
 * @author Jesse Wilson
 * @since 1.6
 */
enum JsonScope {

    /**
     * An array with no elements requires no separators or newlines before
     * it is closed.
     */
    EMPTY_ARRAY,

    /**
     * A array with at least one value requires a comma and newline before
     * the next element.
     */
    NONEMPTY_ARRAY,

    /**
     * An object with no name/value pairs requires no separators or newlines
     * before it is closed.
     */
    EMPTY_OBJECT,

    /**
     * An object whose most recent element is a key. The next element must
     * be a value.
     */
    DANGLING_NAME,

    /**
     * An object with at least one name/value pair requires a comma and
     * newline before the next element.
     */
    NONEMPTY_OBJECT,

    /**
     * No object or array has been started.
     */
    EMPTY_DOCUMENT,

    /**
     * A document with at an array or object.
     */
    NONEMPTY_DOCUMENT,

    /**
     * A document that's been closed and cannot be accessed.
     */
    CLOSED,
}
