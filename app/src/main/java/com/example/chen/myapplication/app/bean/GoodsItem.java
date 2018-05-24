package com.example.chen.myapplication.app.bean;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoodsItem {

    public int id; // 菜品主键
    public int typeId; // 种类id
    public int rating; // 评分
    public String name; // 菜名
    public String typeName; // 种类名
    public double price; // 单价
    public int count; // 数量
    public String img; // 图片

    private static List<GoodsItem> goodsList;
    private static List<GoodsItem> typeList;



    private static String mIndex;



    public GoodsItem(){}

    public GoodsItem(int id, double price, String name, int typeId, String typeName, String img) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
        this.img = img;
        rating = new Random().nextInt(5) + 1;
    }


    private static String[] typeRandom = new String[]{"", "类", "系"};
    private static String[] foodRandom = new String[]{"", "菜品", "美食"};
    private static Random random = new Random();


    public static void initData(String index) {

        goodsList = new ArrayList<>();
        typeList = new ArrayList<>();

        String json = "{\"id\":\"424\",\"is_countdown\":\"0\",\"category\":\"0\",\"city\":\"0\",\"city_place_region\":\"0\",\"city_place_street\":\"0\",\"name\":\"馨雅轩美食\",\"flag\":\"\",\"price\":0,\"nowprice\":0,\"intro\":\"\",\"content\":\"\",\"cue\":\"\",\"theysay\":\"\",\"wesay\":\"\",\"begintime\":\"1485100800\",\"overtime\":\"1642867200\",\"time_remain\":149501288,\"perioddate\":\"0\",\"addtime\":\"1485110385\",\"successnum\":\"0\",\"maxnum\":\"0\",\"oncemax\":\"0\",\"oncemin\":\"1\",\"multibuy\":\"false\",\"allinone\":\"false\",\"type\":\"ticket\",\"weight\":\"0\",\"discount\":0,\"sells_count\":0,\"succ_buyers\":0,\"succ_remain\":0,\"surplus\":9999,\"sellerid\":\"439\",\"sellername\":\"馨雅轩美食\",\"sellerphone\":\"13480907999\",\"selleraddress\":\"三亚外贸路23号\",\"sellermap\":[\"109.501675\",\"18.267902\",\"16\"],\"status\":\"2\",\"longitude\":\"109.501675000000\",\"latitude\":\"18.267902000000\",\"favorite\":false,\"entity\":[{\"id\":\"2857\",\"product_id\":\"424\",\"name\":\"特色炒菜\",\"required\":\"\",\"attrs\":[{\"id\":\"21510\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"香辣鱿鱼须\",\"price_moves\":\"38.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19830\"},{\"id\":\"21511\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"酸辣鸡杂\",\"price_moves\":\"28.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19831\"},{\"id\":\"21512\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"排骨炖豆角\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19832\"},{\"id\":\"21513\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"牛腩炖萝卜\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19833\"},{\"id\":\"21514\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"牛肉炖西红柿\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19834\"},{\"id\":\"21515\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"香干回锅肉\",\"price_moves\":\"26.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19836\"},{\"id\":\"21516\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"小鸡炖蘑菇粉条\",\"price_moves\":\"38.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19837\"},{\"id\":\"21517\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"红烧肉炖粉条\",\"price_moves\":\"38.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19838\"},{\"id\":\"21518\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"宫保鸡丁\",\"price_moves\":\"26.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19839\"},{\"id\":\"21519\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"红烧牛腩\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19840\"},{\"id\":\"21520\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"小炒牛肉\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19841\"},{\"id\":\"21521\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"香菇肉片\",\"price_moves\":\"28.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19842\"},{\"id\":\"21522\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"糖醋排骨\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19843\"},{\"id\":\"21523\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"葱爆羊肉\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19844\"},{\"id\":\"21524\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"辣子鸡\",\"price_moves\":\"38.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19845\"},{\"id\":\"21525\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"菠萝排骨\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19846\"},{\"id\":\"21526\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"红烧鸡块\",\"price_moves\":\"38.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19847\"},{\"id\":\"21527\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"干炸里脊\",\"price_moves\":\"36.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19848\"},{\"id\":\"21528\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"地三鲜\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19849\"},{\"id\":\"21529\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"红烧排骨\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19850\"},{\"id\":\"21530\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"孜然羊肉\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19851\"},{\"id\":\"21531\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"木须肉\",\"price_moves\":\"20.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19852\"},{\"id\":\"21532\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"东北小炒肉\",\"price_moves\":\"28.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19853\"},{\"id\":\"21533\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"溜肉片\",\"price_moves\":\"28.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19854\"},{\"id\":\"21534\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"红烧鸡翅\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19855\"},{\"id\":\"21535\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"可乐鸡翅\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19856\"},{\"id\":\"21536\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"香辣肉丝\",\"price_moves\":\"26.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19857\"},{\"id\":\"21537\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"糖醋里脊\",\"price_moves\":\"36.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19858\"},{\"id\":\"21538\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"爆炒鸡胗\",\"price_moves\":\"33.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19859\"},{\"id\":\"21539\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"鱼香肉丝\",\"price_moves\":\"26.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19860\"},{\"id\":\"21540\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"干煸豆角\",\"price_moves\":\"20.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19861\"},{\"id\":\"21541\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"麻婆豆腐\",\"price_moves\":\"25.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19862\"},{\"id\":\"21542\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"木耳炒肉\",\"price_moves\":\"26.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19863\"},{\"id\":\"21543\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"溜猪肚片\",\"price_moves\":\"38.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19864\"},{\"id\":\"21544\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"溜三样\",\"price_moves\":\"28.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19865\"},{\"id\":\"21545\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"熘肝尖\",\"price_moves\":\"20.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19866\"},{\"id\":\"21546\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"炒肥瘦\",\"price_moves\":\"28.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19867\"},{\"id\":\"21547\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"尖椒护心肉\",\"price_moves\":\"36.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19868\"},{\"id\":\"21548\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"肉段烧茄子\",\"price_moves\":\"28.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19869\"},{\"id\":\"21549\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"排骨烧刀鱼\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19870\"},{\"id\":\"21550\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"肥肠土豆片\",\"price_moves\":\"26.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19871\"},{\"id\":\"21551\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"回锅肉\",\"price_moves\":\"26.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19872\"},{\"id\":\"21552\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"红烧肉炖豆腐\",\"price_moves\":\"38.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19873\"},{\"id\":\"21553\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"溜肥肠\",\"price_moves\":\"36.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19874\"},{\"id\":\"21554\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"干煸牛肉\",\"price_moves\":\"48.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19875\"},{\"id\":\"21555\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"芹菜尖椒肉\",\"price_moves\":\"22.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19876\"},{\"id\":\"21556\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"孜然肉片\",\"price_moves\":\"30.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19877\"},{\"id\":\"21557\",\"binding\":\"true\",\"cat_id\":\"2857\",\"name\":\"溜肉段\",\"price_moves\":\"30.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19878\"}]},{\"id\":\"2856\",\"product_id\":\"424\",\"name\":\"打包盒必点\",\"required\":\"\",\"attrs\":[{\"id\":\"21508\",\"binding\":\"true\",\"cat_id\":\"2856\",\"name\":\"打包盒必点\",\"price_moves\":\"1.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19879\"},{\"id\":\"21509\",\"binding\":\"true\",\"cat_id\":\"2856\",\"name\":\"买几样菜拍几个\",\"price_moves\":\"0.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19880\"}]},{\"id\":\"2858\",\"product_id\":\"424\",\"name\":\"特色小炒系\",\"required\":\"\",\"attrs\":[{\"id\":\"21558\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"酱炒鸡蛋\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19882\"},{\"id\":\"21559\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"干炸鲜菇\",\"price_moves\":\"20.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19883\"},{\"id\":\"21560\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"蒜蓉菠菜\",\"price_moves\":\"20.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19884\"},{\"id\":\"21561\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"蒜蓉西兰花\",\"price_moves\":\"22.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19885\"},{\"id\":\"21562\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"鱼香茄子\",\"price_moves\":\"20.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19886\"},{\"id\":\"21563\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"醋溜白菜\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19887\"},{\"id\":\"21564\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"香辣土豆片\",\"price_moves\":\"20.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19888\"},{\"id\":\"21565\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"肉末炒粉\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19889\"},{\"id\":\"21566\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"酸辣土豆丝\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19890\"},{\"id\":\"21567\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"肉末扒茄子\",\"price_moves\":\"22.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19891\"},{\"id\":\"21568\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"尖椒干豆腐\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19892\"},{\"id\":\"21569\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"韭菜炒鸡蛋\",\"price_moves\":\"22.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19893\"},{\"id\":\"21570\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"油焖尖椒\",\"price_moves\":\"24.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19894\"},{\"id\":\"21571\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"烧茄子\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19895\"},{\"id\":\"21572\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"苦瓜煎蛋\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19896\"},{\"id\":\"21573\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"油焖菠菜\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19897\"},{\"id\":\"21574\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"麻辣豆腐\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19898\"},{\"id\":\"21575\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"空心菜\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19899\"},{\"id\":\"21576\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"干豆腐炒韭菜\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19900\"},{\"id\":\"21577\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"香菇油菜\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19901\"},{\"id\":\"21578\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"手撕包菜\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19902\"},{\"id\":\"21579\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"芹菜炒粉\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19903\"},{\"id\":\"21580\",\"binding\":\"true\",\"cat_id\":\"2858\",\"name\":\"荠菜粉\",\"price_moves\":\"18.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19904\"}]},{\"id\":\"2859\",\"product_id\":\"424\",\"name\":\"米饭\",\"required\":\"true\",\"attrs\":[{\"id\":\"21581\",\"binding\":\"true\",\"cat_id\":\"2859\",\"name\":\"米饭\",\"price_moves\":\"2.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19881\"}]},{\"id\":\"2860\",\"product_id\":\"424\",\"name\":\"酒水饮料\",\"required\":\"true\",\"attrs\":[{\"id\":\"21582\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"青岛啤酒瓶装\",\"price_moves\":\"8.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19905\"},{\"id\":\"21583\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"青岛啤酒罐装\",\"price_moves\":\"8.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19908\"},{\"id\":\"21584\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"康师傅矿泉水\",\"price_moves\":\"2.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19910\"},{\"id\":\"21585\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"美年达青苹果\",\"price_moves\":\"4.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19911\"},{\"id\":\"21586\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"可口可乐\",\"price_moves\":\"4.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19914\"},{\"id\":\"21587\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"酸梅汤\",\"price_moves\":\"4.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19916\"},{\"id\":\"21588\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"美年达橙味\",\"price_moves\":\"4.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19918\"},{\"id\":\"21589\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"冰糖雪梨\",\"price_moves\":\"4.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19920\"},{\"id\":\"21590\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"维他奶\",\"price_moves\":\"5.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19922\"},{\"id\":\"21591\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"怡泉柠檬味\",\"price_moves\":\"5.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19923\"},{\"id\":\"21592\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"椰树\",\"price_moves\":\"5.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19926\"},{\"id\":\"21593\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"百事可乐\",\"price_moves\":\"4.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19927\"},{\"id\":\"21594\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"雪碧\",\"price_moves\":\"4.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19929\"},{\"id\":\"21595\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"加多宝\",\"price_moves\":\"4.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19931\"},{\"id\":\"21596\",\"binding\":\"true\",\"cat_id\":\"2860\",\"name\":\"王老吉\",\"price_moves\":\"4.00\",\"fund\":\"0.00\",\"num\":\"10000\",\"img\":\"19933\"}]}],\"postcomment\":\"0\",\"display\":\"0\",\"paymentlist\":false,\"cuts\":[{\"flag\":\"newbie_cut\",\"cut\":10,\"enable\":true,\"desc\":\"新用户首单立减10元\"}],\"limit_level\":\"0\",\"rebate_money\":\"0.000\",\"yungou\":\"0\",\"sub_sellercount\":\"0\",\"needknow\":\"\",\"tags\":[],\"is_day_time\":1,\"hash\":{\"md5\":\"65301c4e40af8ed688587331147ebb23\"}}";
        Gson gson = new Gson();
        ShopcartBean.DataBean dataBean = gson.fromJson(json, ShopcartBean.DataBean.class);
        if (dataBean != null) {
            List<ShopcartBean.DataBean.EntityBean> entityList = dataBean.getEntity();
            GoodsItem item = null;
            for (int i = 0; i < entityList.size(); i++) {
                ShopcartBean.DataBean.EntityBean typeEntity = entityList.get(i); // 某个分类和具体菜品
                List<ShopcartBean.DataBean.EntityBean.AttrsBean> attrsList = typeEntity.getAttrs();
                for (int j = 0; j < attrsList.size(); j++) {
                    ShopcartBean.DataBean.EntityBean.AttrsBean food = attrsList.get(j); // 菜品
                    int nextInt = random.nextInt(3);
                    String foodName = food.getName() + foodRandom[nextInt];
                    nextInt = random.nextInt(3);
                    String name = typeEntity.getName() + typeRandom[nextInt];
                    String s = Shop.shopImg.get(random.nextInt(49)); // 图片路径

                    item = new GoodsItem(food.getId(), food.getPrice_moves(), foodName, typeEntity.getId(), name, s);

                    goodsList.add(item);
                }
                typeList.add(item);
            }
        }
    }



    public static List<GoodsItem> getGoodsList(String index) throws InterruptedException {
        if (goodsList == null) {
            initData(index);
        }
        Thread.sleep(300);
        return goodsList;
    }
    public static List<GoodsItem> getTypeList(String index) throws InterruptedException {

        if (typeList == null) {
            initData(index);
        }
        Thread.sleep(600);
        return typeList;
    }




}
