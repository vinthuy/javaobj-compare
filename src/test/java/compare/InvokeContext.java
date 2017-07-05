package compare;


import com.vinthuy.unitils.core.CompareConfig;
import com.vinthuy.unitils.core.CompareResult;
import com.vinthuy.unitils.core.ReflectComparator;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ruiyong.hry on 24/03/2017.
 */
public class InvokeContext {

    public Map<String, List<Invoke>> subInvokes = new HashMap<String, List<Invoke>>();


    public Map<String, List<Invoke>> getSubInvokes() {
        return subInvokes;
    }

    public void setSubInvokes(Map<String, List<Invoke>> subInvokes) {
        this.subInvokes = subInvokes;
    }

    public static class Invoke {
        public Object[] params;

        public Object[] getParams() {
            return params;
        }

        public void setParams(Object[] params) {
            this.params = params;
        }
    }

    public static class A {
        public A(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int id;
    }


    @Test
    public void testKeyDotsMap() throws IOException {


        InvokeContext lefInvokeContext = new InvokeContext();
        List<Invoke> leftInvoke1 = new ArrayList<Invoke>();
        Invoke lv1 = new Invoke();
        lv1.params = new Object[]{new A(110), new A(120), new A(130),};
        leftInvoke1.add(lv1);
        Invoke lv2 = new Invoke();
        lv2.params = new Object[]{new A(210), new A(220), new A(230),};
        leftInvoke1.add(lv2);

        //lefInvokeContext.subInvokes.put("com.tmall.ofp.dataservice.dao.FulfillOrderDao@saveFulfillOrder(FulfillOrderPO)1", leftInvoke1);

        InvokeContext rightInvokeContext = new InvokeContext();
        List<Invoke> rightInvoke1 = new ArrayList<Invoke>();
        Invoke rv1 = new Invoke();
        rv1.params = new Object[]{new A(210), new A(220), new A(230),};
        rightInvoke1.add(rv1);

        Invoke rv2 = new Invoke();
        rv2.params = new Object[]{new A(10), new A(20), new A(30)};
        rightInvoke1.add(rv2);

        //rightInvokeContext.subInvokes.put("com.tmall.ofp.dataservice.dao.FulfillOrderDao@saveFulfillOrderV2(FulfillOrderPO)", rightInvoke1);

        lefInvokeContext.subInvokes.put("com.tmall.ofp.dataservice.dao.FulfillOrderDao@saveFulfillOrder(FulfillOrderPO)", rightInvoke1);
        rightInvokeContext.subInvokes.put("com.tmall.ofp.dataservice.dao.FulfillOrderDao@saveFulfillOrder(FulfillOrderPO)", leftInvoke1);

        CompareConfig cc = new CompareConfig();
        cc.ignoreCollOrder();
        cc.setIgnorePaths(new ArrayList<String>());
//        cc.getIgnorePaths().add("subInvokes.com.tmall.ofp.dataservice.dao.FulfillGoodDao@saveFulfillGood(FulfillGoodPO).params.id");
        //cc.getIgnorePaths().add(".params.id");
        //cc.getIgnorePaths().add("subInvokes.com.tmall.ofp.dataservice.dao.FulfillOrderDao@saveFulfillOrder(FulfillOrderPO).params.id");
        //subInvokes.com.tmall.ofp.dataservice.dao.FulfillOrderDao@saveFulfillOrder(FulfillOrderPO).params.id
        //subInvokes.com.tmall.ofp.dataservice.dao.FulfillGoodDao@saveFulfillGood(FulfillGoodPO)[1,0].params[2,0].id
        cc.setIgnoredClassField(new ArrayList<String>());
        cc.getIgnoredClassField().add("compare.InvokeContext.subInvokes");
        ReflectComparator compare = new ReflectComparator(cc);

        CompareResult cr = compare.compare(lefInvokeContext, rightInvokeContext);
        System.out.println(cr.toString());
    }
}

