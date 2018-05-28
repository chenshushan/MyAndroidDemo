package com.example.chen.myapplication;

import android.util.SparseArray;
import com.example.chen.myapplication.app.bean.GoodsItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() throws Exception {
		String json = "{\"mGarbage\":false,\"mKeys\":[21512,21513,0,0,0,0,0,0,0,0,0,0,0],\"mSize\":2,\"mValues\":[{\"count\":1,\"id\":21512,\"img\":\"http://wm.gou00.cn/api.php?r\\u003d/image/get\\u0026appcode\\u003d9373224c982cc891befc4788cf3548b9\\u0026token\\u003d18d279d1faaac72a89fe06af13fa7ecb\\u0026width\\u003d265\\u0026height\\u003d160\\u0026view\\u003dimage\\u0026id\\u003d4529\",\"name\":\"排骨炖豆角美食\",\"price\":48.0,\"rating\":5,\"typeId\":2857,\"typeName\":\"特色炒菜类\"},{\"count\":1,\"id\":21513,\"img\":\"http://wm.gou00.cn/api.php?r\\u003d/image/get\\u0026appcode\\u003d9373224c982cc891befc4788cf3548b9\\u0026token\\u003d18d279d1faaac72a89fe06af13fa7ecb\\u0026width\\u003d265\\u0026height\\u003d160\\u0026view\\u003dimage\\u0026id\\u003d7250\",\"name\":\"牛腩炖萝卜美食\",\"price\":48.0,\"rating\":4,\"typeId\":2857,\"typeName\":\"特色炒菜类\"}]}";
		Gson gson = new Gson();
		Type type = new TypeToken<SparseArray>() {}.getType();
		SparseArray sparseArray = gson.fromJson(json, type);
		Map map = (Map) sparseArray.valueAt(0);
		GoodsItem goodsItem = new GoodsItem();
		BeanUtils.populate(goodsItem, map);
		System.out.println(goodsItem);

	}
}