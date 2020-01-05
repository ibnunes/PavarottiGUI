/*
 * Copyright (C) 2020 Igor Nunes, Beatriz Costa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.strutil;

import java.util.ArrayList;

/**
 *
 * @author Igor Nunes, Beatriz Costa
 */
public class ArrayUtility {
    public static String convertToString(final ArrayList<String> LIST) {
        String result = "";
        for (String s : LIST)
            result += s + "\n";
        return result;
    }
}
