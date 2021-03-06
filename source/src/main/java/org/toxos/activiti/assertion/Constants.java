/*******************************************************************************
 * Copyright 2014 Tiese Barrell
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.toxos.activiti.assertion;

import java.util.Locale;

/**
 * Constants for process assertions.
 * 
 * @author Tiese Barrell
 * 
 */
public final class Constants {

    /**
     * The default {@link Locale} used for logging messages.
     */
    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    /**
     * The name for the bundle containing log messages.
     */
    static final String LOG_MESSAGES_BUNDLE_NAME = "messages.LogMessages";

    private Constants() {
        super();
    }
}
