/*
 * Copyright (c) 2018 Fondesa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fondesa.kpermissions.extension

/**
 * Creates a [String] using this array's elements.
 * The elements will be appended to each other separated by the [separator].
 *
 * @param separator character used to separate the elements of the array.
 * By default the [separator] is a comma.
 * @return result [String] with the elements of the array or an empty [String]
 * if the array was empty.
 */
fun Array<out String>.flatString(separator: Char = ','): String {
    val sb = StringBuilder()
    forEachIndexed { index, s ->
        sb.append(s)
        if (index != lastIndex) {
            // Don't add the separator after the last element.
            sb.append(separator)
        }
    }
    return sb.toString()
}