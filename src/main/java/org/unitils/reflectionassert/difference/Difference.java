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
package org.unitils.reflectionassert.difference;

import com.google.common.base.Objects;

/**
 * A class for holding the difference between two objects.
 *
 * @author Tim Ducheyne
 * @author Filip Neven
 * @author huruiyong
 */
public class Difference {

    /* The left result value */
    private Object leftValue;

    /* The right result value */
    private Object rightValue;

    /* A message describing the difference */
    private String message;

    private Class fieldClass;


    /**
     * Creates a difference.
     *
     * @param message    a message describing the difference
     * @param leftValue  the left instance
     * @param rightValue the right instance
     */
    public Difference(String message, Object leftValue, Object rightValue) {
        this.message = message;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        fieldClass = Object.class;
    }


    /**
     * Gets the left value.
     *
     * @return the value
     */
    public Object getLeftValue() {
        return leftValue;
    }


    /**
     * Gets the right value.
     *
     * @return the value
     */
    public Object getRightValue() {
        return rightValue;
    }


    /**
     * Gets the message indicating the kind of difference.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }


    public Class getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(Class fieldClass) {
        this.fieldClass = fieldClass;
    }

    public Difference buildFieldClass(Class fieldClass) {
        setFieldClass(fieldClass);
        return this;
    }

    /**
     * Double dispatch method. Dispatches back to the given visitor.
     * <p/>
     * All subclasses should copy this method in their own class body.
     *
     * @param visitor  The visitor, not null
     * @param argument An optional argument for the visitor, null if not applicable
     * @return The result
     */
    public <T, A> T accept(DifferenceVisitor<T, A> visitor, A argument) {
        return visitor.visit(this, argument);
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("leftValue", leftValue)
                .add("rightValue", rightValue)
                .add("message", message)
                .add("fieldClass", fieldClass.getName())
                .toString();
    }
}