/*
 * Copyright 2008,  Unitils.org
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
package org.unitils.reflectionassert.comparator.impl;

import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.difference.Difference;

/**
 * Comparator for objects. This will compare all corresponding field values.
 *
 * @author Tim Ducheyne
 * @author Filip Neven
 */
public class ObjectComparator extends ExcludeFieldComparator {

//    @Override
//    public Difference compare(Object left, Object right, boolean onlyFirstDifference, ReflectionComparator reflectionComparator) {
//        setCompareConfig(reflectionComparator.getCompareConfig(), false);
//        return super.compare(left, right, onlyFirstDifference, reflectionComparator);
//    }

}
