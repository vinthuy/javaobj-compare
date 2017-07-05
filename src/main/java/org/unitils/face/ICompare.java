package org.unitils.face;


import org.unitils.reflectionassert.difference.Difference;

public interface ICompare {
	Difference getDifference(Object expected, Object actual);
}
