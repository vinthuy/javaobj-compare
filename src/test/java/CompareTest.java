import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.vinthuy.unitils.core.CompareConfig;
import com.vinthuy.unitils.core.CompareResult;
import com.vinthuy.unitils.core.ReflectComparator;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.ReflectionComparatorFactory;
import org.unitils.reflectionassert.comparator.Comparator;

import java.io.IOException;
import java.util.*;

public class CompareTest {

    static ReflectionComparator comparator = ReflectionComparatorFactory.createRefectionComparator(new ArrayList<Comparator>());

    public class User {
        private int id;
        private String name;
        private Ad ad;

        private Map<String, MapTest> maptest;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public User buildAd(String a, String b) {
            this.ad = new Ad(a, b);
            return this;
        }

        public Ad getAd() {
            return ad;
        }

        public void setAd(Ad ad) {
            this.ad = ad;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("id", id)
                    .add("name", name)
                    .add("ad", ad)
                    .toString();
        }

        public Map<String, MapTest> getMaptest() {
            return maptest;
        }

        public void setMaptest(Map<String, MapTest> maptest) {
            this.maptest = maptest;
        }
    }

    public class MapTest {
        private String map1;
        private String map2;

        public String getMap1() {
            return map1;
        }

        public void setMap1(String map1) {
            this.map1 = map1;
        }

        public MapTest(String map1, String map2) {
            this.map1 = map1;
            this.map2 = map2;
        }
    }

    public class Ad {
        private String a;
        private String b;

        public Ad(String a, String b) {
            this.a = a;
            this.b = b;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("a", a)
                    .add("b", b)
                    .toString();
        }
    }


    @Test
    public void testCompare() throws IOException {
        List<User> workingObject = Arrays.asList(new User(1, "1").buildAd("a", "b"), new User(2, "2").buildAd("a", "d"), new User(3, "3").buildAd("d", "e"));
        List<User> baseObject = Arrays.asList(new User(1, "2").buildAd("a", "b"), new User(3, "3").buildAd("d", "e"), new User(2, "4").buildAd("a", "e"));
        CompareConfig cc = new CompareConfig();
        //cc.setClassExcludeFields()
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(workingObject, baseObject);
        System.out.println(cr.toString());
    }


    @Test
    public void testCompareClass() throws IOException {
        User userA = new User(1, "1").buildAd("a1", "b1");
        User userB = new User(1, "1").buildAd("a1", "b2");
        CompareConfig cc = new CompareConfig();
        List<String> ignorePath = new ArrayList<String>();
        ignorePath.add("ad.b");
        cc.setIgnorePaths(ignorePath);
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(userA, userB);
        System.out.println(cr.toString());
    }


    @Test
    public void testCompareMapClass() throws IOException {
        User userA = new User(1, "1").buildAd("a1", "b1");
        Map<String, MapTest> userAMap = new HashMap<String, MapTest>();
        userAMap.put("userMapa", new MapTest("map1a", "map2a"));
        userAMap.put("userMapb", new MapTest("map1b", "map2b"));
        userAMap.put("userMapc", new MapTest("map1c", "map2c"));
        userA.setMaptest(userAMap);

        User userB = new User(1, "1").buildAd("a1", "b2");
        Map<String, MapTest> userBMap = new HashMap<String, MapTest>();
        userBMap.put("userMapa", new MapTest("Bmap1a", "map2a"));
        userBMap.put("userMapb", new MapTest("Bmap1b", "map2b"));
        userBMap.put("userMapc", new MapTest("Bmap1c", "map2c"));
        userB.setMaptest(userBMap);


        CompareConfig cc = new CompareConfig();
        List<String> ignorePath = new ArrayList<String>();
        ignorePath.add("ad.a");
        ignorePath.add("maptest.userMapa.map1");
        cc.setIgnorePaths(ignorePath);
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(userA, userB);
        System.out.println(cr.toString());
    }


    @Test
    public void testCompareCollect() throws IOException {
        List<User> workingObject = Arrays.asList(new User(1, "1").buildAd("a", "b"), new User(2, "2").buildAd("a", "d"), new User(3, "3").buildAd("d", "e"));
        List<User> baseObject = Arrays.asList(new User(1, "2").buildAd("a", "b"), new User(3, "3").buildAd("d", "e"), new User(2, "4").buildAd("a", "e"));
        CompareConfig cc = new CompareConfig();
//        List<String> ignorePath = new ArrayList<String>();
//        ignorePath.add("ad.a");
//        ignorePath.add("ad.b");
//        ignorePath.add("id");
//        ignorePath.add("name");
//        cc.setIgnorePaths(ignorePath);
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(workingObject, baseObject);
        System.out.println(cr.toString());
    }


    @Test
    public void testCompareCollectAndMap() throws IOException {
        User user1 = new User(1, "1").buildAd("a", "b");
        Map<String, MapTest> userAMap1 = new HashMap<String, MapTest>();
        userAMap1.put("userMapa", new MapTest("map1a", "map2a"));
        userAMap1.put("userMapb", new MapTest("map1b", "map2b"));
        userAMap1.put("userMapc", new MapTest("map1c", "map2c"));
        user1.setMaptest(userAMap1);
        User user2 = new User(2, "2").buildAd("a", "d");
        Map<String, MapTest> userAMap2 = new HashMap<String, MapTest>();
        userAMap2.put("userMapa", new MapTest("map1a", "map2a"));
        userAMap2.put("userMapb", new MapTest("map1b", "map2b"));
        userAMap2.put("userMapc", new MapTest("map1c", "map2c"));
        user2.setMaptest(userAMap2);

        User user3 = new User(3, "3").buildAd("d", "e");
        Map<String, MapTest> userAMap3 = new HashMap<String, MapTest>();
        userAMap3.put("userMapa", new MapTest("map1a", "map2a"));
        userAMap3.put("userMapb", new MapTest("map1b", "map2b"));
        userAMap3.put("userMapc", new MapTest("map1c", "map2c"));
        user3.setMaptest(userAMap3);


        User user11 = new User(1, "2").buildAd("a", "b");
        Map<String, MapTest> userAMap11 = new HashMap<String, MapTest>();
        userAMap11.put("userMapa", new MapTest("map11a", "map21a"));
        userAMap11.put("userMapb", new MapTest("map11b", "map21b"));
        userAMap11.put("userMapc", new MapTest("map11c", "map21c"));
        user11.setMaptest(userAMap11);

        User user12 = new User(3, "3").buildAd("d", "e");
        Map<String, MapTest> userAMap12 = new HashMap<String, MapTest>();
        userAMap12.put("userMapa", new MapTest("map12a", "map22a"));
        userAMap12.put("userMapb", new MapTest("map12b", "map22b"));
        userAMap12.put("userMapc", new MapTest("map12c", "map22c"));
        user12.setMaptest(userAMap11);
        User user13 = new User(2, "4").buildAd("a", "e");
        Map<String, MapTest> userAMap13 = new HashMap<String, MapTest>();
        userAMap13.put("userMapa", new MapTest("map13a", "map23a"));
        userAMap13.put("userMapb", new MapTest("map13b", "map23b"));
        userAMap13.put("userMapc", new MapTest("map13c", "map23c"));
        user13.setMaptest(userAMap13);

        List<User> workingObject = Arrays.asList(user1, user2, user3);
        List<User> baseObject = Arrays.asList(user11, user12, user13);
        CompareConfig cc = new CompareConfig();
        List<String> ignorePath = new ArrayList<String>();
        ignorePath.add("ad.a");
        ignorePath.add("ad.b");
        ignorePath.add("id");
        ignorePath.add("name");
        ignorePath.add("maptest.userMapc");
        ignorePath.add("maptest.userMapb");
        ignorePath.add("maptest.userMapa.map1");
        cc.setIgnorePaths(ignorePath);
        cc.ignoreCollOrder();
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(workingObject, baseObject);
        System.out.println(cr.toString());
    }


    @Test
    public void testCompareVal() throws IOException {
        User user1 = new User(1, "1").buildAd("a", "b");
        Map<String, MapTest> userAMap1 = new HashMap<String, MapTest>();
        userAMap1.put("userMapa", new MapTest("map1a", "map2a"));
        userAMap1.put("userMapb", new MapTest("map1b", "map2b"));
        userAMap1.put("userMapc", new MapTest("map1c", "map2c"));
        user1.setMaptest(userAMap1);
        User user2 = new User(2, "2").buildAd("a", "d");
        Map<String, MapTest> userAMap2 = new HashMap<String, MapTest>();
        userAMap2.put("userMapa", new MapTest("map1a", "map2a"));
        userAMap2.put("userMapb", new MapTest("map1b", "map2b"));
        userAMap2.put("userMapc", new MapTest("map1c", "map2c"));
        user2.setMaptest(userAMap2);

        User user3 = new User(3, "3").buildAd("d", "e");
        Map<String, MapTest> userAMap3 = new HashMap<String, MapTest>();
        userAMap3.put("userMapa", new MapTest("map1a", "map2a"));
        userAMap3.put("userMapb", new MapTest("map1b", "map2b"));
        userAMap3.put("userMapc", new MapTest("map1c", "map2c"));
        user3.setMaptest(userAMap3);


        User user11 = new User(1, "2").buildAd("a", "b");
        Map<String, MapTest> userAMap11 = new HashMap<String, MapTest>();
        userAMap11.put("userMapa", new MapTest("map11a", "map21a"));
        userAMap11.put("userMapb", new MapTest("map11b", "map21b"));
        userAMap11.put("userMapc", new MapTest("map11c", "map21c"));
        user11.setMaptest(userAMap11);

        User user12 = new User(3, "3").buildAd("d", "e");
        Map<String, MapTest> userAMap12 = new HashMap<String, MapTest>();
        userAMap12.put("userMapa", new MapTest("map12a", "map22a"));
        userAMap12.put("userMapb", new MapTest("map12b", "map22b"));
        userAMap12.put("userMapc", new MapTest("map12c", "map22c"));
        user12.setMaptest(userAMap11);
        User user13 = new User(2, "4").buildAd("a", "e");
        Map<String, MapTest> userAMap13 = new HashMap<String, MapTest>();
        userAMap13.put("userMapa", new MapTest("map13a", "map23a"));
        userAMap13.put("userMapb", new MapTest("map13b", "map23b"));
        userAMap13.put("userMapc", new MapTest("map13c", "map23c"));
        user13.setMaptest(userAMap13);

        List<User> workingObject = Arrays.asList(user1, user2, user3);
        List<User> baseObject = Arrays.asList(user11, user12, user13);
        CompareConfig cc = new CompareConfig();
        List<String> ignorePath = new ArrayList<String>();
        ignorePath.add("ad.a");
        ignorePath.add("ad.b");
        ignorePath.add("id");
        ignorePath.add("name");
        ignorePath.add("maptest.userMapc");
        ignorePath.add("maptest.userMapb");
        ignorePath.add("maptest.userMapa.map2");
        cc.setIgnorePaths(ignorePath);
        cc.ignoreCollOrder();
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(workingObject, baseObject);
        System.out.println(cr.toString());
    }

    public class ClassB {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    @Test
    public void testJson() {
        User user = new User(1, "1").buildAd("a", "b");
        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void testValueTransform() {
        ClassB classB = new ClassB();
        classB.setName("a");
        String atest = "{\"ad\":{\"a\":\"a\",\"b\":\"b\"},\"id\":1,\"name\":\"1\"}";
        classB.setValue(atest);

        ClassB classB1 = new ClassB();
        classB1.setName("a");
        String atest1 = "{\"ad\":{\"a\":\"a1\",\"b\":\"b1\"},\"id\":1,\"name\":\"1\"}";
        classB1.setValue(atest1);
        CompareConfig cc = new CompareConfig();
        List<String> spList = new ArrayList<String>();
        spList.add("value");
        cc.setSpecifyPathJsonString(spList);

        List<String> ignorePath = new ArrayList<String>();
        ignorePath.add("value.ad.a");
        cc.setIgnorePaths(ignorePath);
//        cc.setBreakFirstCompareFail(false);

        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(classB, classB1);
        System.out.println(cr.toString());
    }


    @Test
    public void testValueMapTransform() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "a");
        String atest = "{\"ad\":{\"a\":\"a\",\"b\":\"b\"},\"id\":1,\"name\":\"1\"}";
        map.put("value", atest);

        Map<String, String> map1 = new HashMap<String, String>();

        map1.put("name", "b");
        String atest1 = "{\"ad\":{\"a\":\"a1\",\"b\":\"b1\"},\"id\":1,\"name\":\"1\"}";
        map1.put("value", atest1);
        CompareConfig cc = new CompareConfig();
        List<String> spList = new ArrayList<String>();
        spList.add("value");
        cc.setSpecifyPathJsonString(spList);

        List<String> ignorePath = new ArrayList<String>();
        ignorePath.add("name");
        ignorePath.add("value.ad.a");
        cc.setIgnorePaths(ignorePath);
//        cc.setBreakFirstCompareFail(false);

        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(map, map1);
        System.out.println(cr.toString());
    }


    @Test
    public void testCompareCollectOrder() throws IOException {

        User user1 = new User(1, "1").buildAd("a", "b");
        User user2 = new User(2, "2").buildAd("a", "d");
        User user3 = new User(3, "3").buildAd("d", "e");


        List<User> workingObject = Arrays.asList(user1, user2, user3);
        List<User> baseObject = Arrays.asList(user3, user1, user2);
        CompareConfig cc = new CompareConfig();
//        List<String> ignorePath = new ArrayList<String>();
//        ignorePath.add("ad.a");
//        ignorePath.add("ad.b");
//        ignorePath.add("id");
//        ignorePath.add("name");
//        cc.setIgnorePaths(ignorePath);
        cc.ignoreCollOrder();
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(workingObject, baseObject);
        System.out.println(cr.toString());
    }


    @Test
    public void testCompareStringBuilder() throws IOException {

        List<StringBuilder> stringBuilders = new ArrayList<StringBuilder>();
//        stringBuilders.add(new StringBuilder("1"));
        stringBuilders.add(new StringBuilder("1"));
        List<StringBuilder> stringBuilders1 = new ArrayList<StringBuilder>();
//        stringBuilders1.add(new StringBuilder("1"));
        stringBuilders1.add(new StringBuilder("1"));
        CompareConfig cc = new CompareConfig();
        cc.ignoreCollOrder();

        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(stringBuilders, stringBuilders1);
        System.out.println(cr.toString());
    }

    @Test
    public void testMap() throws IOException {
        Map<Integer, String> leftMap = new HashMap<Integer, String>();
        Map<Integer, String> rightMap = new HashMap<Integer, String>();

        rightMap.put(456, "hello");
        CompareConfig cc = new CompareConfig();
        cc.ignoreCollOrder();

        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(leftMap, rightMap);
        System.out.println(cr.toString());
    }

    @Test
    public void testKeyDotsMap() throws IOException {
        Map<String, List<TestA>> leftMap = new HashMap<String, List<TestA>>();
        Map<String, List<TestA>> rightMap = new HashMap<String, List<TestA>>();
        List<TestA> leftTestA = new ArrayList<TestA>();
        leftTestA.add(new TestA().setName("hu"));

        List<TestA> rightTestA = new ArrayList<TestA>();
        rightTestA.add(new TestA().setName("hu1"));
        rightMap.put("a.b.c", rightTestA);
        leftMap.put("a.b.c", leftTestA);

        CompareConfig cc = new CompareConfig();
        cc.ignoreCollOrder();
        cc.setIgnorePaths(new ArrayList<String>());
        cc.getIgnorePaths().add("a.b.c.name");
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(leftMap, rightMap);
        System.out.println(cr.toString());
    }


    @Test
    public void testKVMap() throws IOException {
        TestA ltestA = new TestA();
        ltestA.setName("type=1&subtype=2&miid=6583315435419666983&buyerid=700233817&shopid=87654321&id=535014916019&pid=mm_11166935_0_0&trackIdType=1&unionid=666666&clickid=136980052_8_85121665&clicktime=1492486326&loc=0&mode=&from=B&pri=5&extra=f%3DB%26cr%3D1%26p%3Dmm_11166935_0_0%26u%3D666666%26v%3D1.01%26ics%3D1%26ic%3D1%26cid%3D136980052_8_85121665%26t%3D1%26tt%3D1%26pt%3D1%26at%3D0%26ut%3D2%26wt%3D9%26af%3D1&performtime=1492486326300")
        ;
        TestA ltestB = new TestA();
        ltestB.setName("type=1&subtype=2&miid=6583315435419666983&buyerid=700233817&shopid=87654321&id=535014916019&pid=mm_11166935_0_0&trackIdType=1&unionid=666666&clickid=136980052_8_85121665&clicktime=1492486326&loc=0&mode=&from=B&pri=5&extra=f%3DB%26cr%3D1%26p%3Dmm_11166935_0_0%26u%3D666666%26v%3D1.01%26ics%3D1%26ic%3D1%26cid%3D136980052_8_85121665%26t%3D1%26tt%3D1%26pt%3D1%26at%3D0%26ut%3D2%26wt%3D9%26af%3D1&performtime=1492486326361");
        CompareConfig cc = new CompareConfig();
        cc.ignoreCollOrder();
        cc.setIgnorePaths(new ArrayList<String>());
//        cc.getIgnorePaths().add("name.performtime");
        cc.setSpecifyPathKvAttribute(new ArrayList<String>());
        cc.getSpecifyPathKvAttribute().add("name@=&");
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(ltestA, ltestB);
        System.out.println(cr.toString());
    }


    @Test
    public void testExfiPath() throws IOException {
        TestA ltestA = new TestA();
        ltestA.setName("123");
        TestA ltestB = new TestA();
        ltestB.setName("345");
        CompareConfig cc = new CompareConfig();
        cc.ignoreCollOrder();
        cc.setIgnoredClassField(new ArrayList<String>());
        cc.getIgnoredClassField().add("CompareTest.TestA.name");
        cc.setIgnorePaths(new ArrayList<String>());
        cc.getIgnorePaths().add("name");
        ReflectComparator compare = new ReflectComparator(cc);
        CompareResult cr = compare.compare(ltestA, ltestB);
        System.out.println(cr.toString());
    }



    public static class TestA {
        private String name;

        public String getName() {
            return name;
        }

        public TestA setName(String name) {
            this.name = name;
            return this;
        }
    }


}
