package com.vinthuy.unitils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.ReflectionComparatorFactory;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.comparator.impl.ExcludeFieldComparator;


public class CampareTest {

    static ReflectionComparator comparator = null;
//	ReflectionComparator comparator = ReflectionComparatorFactory
//			.createRefectionComparator(new ReflectionComparatorMode[0]);

    static {
        List<Comparator> x = Lists.newArrayList();
        ExcludeFieldComparator spec = new ExcludeFieldComparator();
        x.add(spec);
        spec.setFilterClassFields(Sets.newHashSet(new String[] { "com.vinthuy.ReflectCompareTest.A.id" }));
        comparator = ReflectionComparatorFactory.createRefectionComparator(x,
                new ReflectionComparatorMode[] {});

    }

	@Test
	public void test_list() {
		A l = new A();
		l.setList(new ArrayList<String>());
		l.getList().add("a");
		l.getList().add("b");
		A r = new A();
		r.setList(new LinkedList<String>());
		r.getList().add("a");
		r.getList().add("b");
		assertTrue(comparator.getDifference(l, r) == null);
	}

	@Test
	public void test_empty_list() {
		A l = new A();
		l.setSet(new TreeSet<String>());
		A r = new A();
		assertTrue(comparator.getDifference(l, r) == null);
	}

	@Test
	public void test_empty_set() {
		A l = new A();
		l.setList(new ArrayList<String>());
		A r = new A();
		assertTrue(comparator.getDifference(l, r) == null);
	}

	@Test
	public void test_arr() {
		B l = new B();
		B r = new B();
		l.setArr(new String[0]);
		assertTrue(comparator.getDifference(l, r) == null);
	}

    @Test
    public void test_empty_error(){
        A a1 = new A();
        A a2 = new A();
        a1.setList(Lists.newArrayList(new String[]{"A"}));
        assertTrue(comparator.getDifference(a1,a2)!=null);
        assertTrue(comparator.getDifference(a2,a1)!=null);
    }


    @Test
    public void test_empty_set_error(){
        A a1 = new A();
        A a2 = new A();
        a2.setSet(Sets.newHashSet(new String[]{}));
        assertTrue(comparator.getDifference(a1, a2) == null);
        assertTrue(comparator.getDifference(a2,a1)==null);
    }
    @Test
    public void test_empty_arr_error(){
        A a1 = new A();
        A a2 = new A();
        a1.setArr(new String[]{});
        assertTrue(comparator.getDifference(a2,a1)==null);
        assertTrue(comparator.getDifference(a1, a2) == null);
        A a3 = new A();
        a3.setArr(new String[]{"x"});
        assertTrue(comparator.getDifference(a3,a1)!=null);
        assertTrue(comparator.getDifference(a1, a3) != null);
    }

	public static class A {
		private List<String> list = null;
		private Set<String> set = null;

        public String[] getArr() {
            return arr;
        }

        public void setArr(String[] arr) {
            this.arr = arr;
        }

        private String[] arr=null;



		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}

		public Set<String> getSet() {
			return set;
		}

		public void setSet(Set<String> set) {
			this.set = set;
		}
	}

	public static class B {
		private String[] arr;

		public String[] getArr() {
			return arr;
		}

		public void setArr(String[] arr) {
			this.arr = arr;
		}
	}
}