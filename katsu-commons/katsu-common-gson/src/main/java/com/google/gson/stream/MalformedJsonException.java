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

import java.io.IOException;

/**
 * Thrown when a reader encounters malformed JSON. Some syntax errors can be
 * ignored by calling {@link JsonReader#setLenient(boolean)}.
 */
public final class MalformedJsonException extends IOException {
  private static final long serialVersionUID = 1L;

  public MalformedJsonException(String msg) {
    super(msg);
  }

  public MalformedJsonException(String msg, Throwable throwable) {
    super(msg);
    // Using initCause() instead of calling super() because Java 1.5 didn't retrofit IOException
    // with a constructor with Throwable. This was done in Java 1.6
    initCause(throwable);
  }

  public MalformedJsonException(Throwable throwable) {
    // Using initCause() instead of calling super() because Java 1.5 didn't retrofit IOException
    // with a constructor with Throwable. This was done in Java 1.6
    initCause(throwable);
  }
}