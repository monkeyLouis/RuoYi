package com.ruoyi.web.controller.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.CxSelect;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.json.JSONObject.JSONArray;
import com.ruoyi.common.utils.StringUtils;

/**
 * 表單相關
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/form")
public class DemoFormController
{
    private String prefix = "demo/form";

    private final static List<UserFormModel> users = new ArrayList<UserFormModel>();
    {
        users.add(new UserFormModel(1, "1000001", "測試1", "15888888888"));
        users.add(new UserFormModel(2, "1000002", "測試2", "15666666666"));
        users.add(new UserFormModel(3, "1000003", "測試3", "15666666666"));
        users.add(new UserFormModel(4, "1000004", "測試4", "15666666666"));
        users.add(new UserFormModel(5, "1000005", "測試5", "15666666666"));
    }

    /**
     * 按鈕頁
     */
    @GetMapping("/button")
    public String button()
    {
        return prefix + "/button";
    }

    /**
     * 下拉框
     */
    @GetMapping("/select")
    public String select()
    {
        return prefix + "/select";
    }

    /**
     * 時間軸
     */
    @GetMapping("/timeline")
    public String timeline()
    {
        return prefix + "/timeline";
    }

    /**
     * 進度條
     */
    @GetMapping("/progress_bars")
    public String progress_bars()
    {
        return prefix + "/progress_bars";
    }

    /**
     * 表單校驗
     */
    @GetMapping("/validate")
    public String validate()
    {
        return prefix + "/validate";
    }

    /**
     * 功能擴展（包含文件上傳）
     */
    @GetMapping("/jasny")
    public String jasny()
    {
        return prefix + "/jasny";
    }

    /**
     * 拖動排序
     */
    @GetMapping("/sortable")
    public String sortable()
    {
        return prefix + "/sortable";
    }

    /**
     * 單據列印
     */
    @GetMapping("/invoice")
    public String invoice()
    {
        return prefix + "/invoice";
    }

    /**
     * 標籤 & 提示
     */
    @GetMapping("/labels_tips")
    public String labels_tips()
    {
        return prefix + "/labels_tips";
    }

    /**
     * 選項卡 & 面板
     */
    @GetMapping("/tabs_panels")
    public String tabs_panels()
    {
        return prefix + "/tabs_panels";
    }

    /**
     * 柵格
     */
    @GetMapping("/grid")
    public String grid()
    {
        return prefix + "/grid";
    }

    /**
     * 表單向導
     */
    @GetMapping("/wizard")
    public String wizard()
    {
        return prefix + "/wizard";
    }

    /**
     * 文件上傳
     */
    @GetMapping("/upload")
    public String upload()
    {
        return prefix + "/upload";
    }

    /**
     * 日期和時間頁
     */
    @GetMapping("/datetime")
    public String datetime()
    {
        return prefix + "/datetime";
    }

    /**
     * 左右互選組件
     */
    @GetMapping("/duallistbox")
    public String duallistbox()
    {
        return prefix + "/duallistbox";
    }

    /**
     * 基本表單
     */
    @GetMapping("/basic")
    public String basic()
    {
        return prefix + "/basic";
    }

    /**
     * 卡片列表
     */
    @GetMapping("/cards")
    public String cards()
    {
        return prefix + "/cards";
    }

    /**
     * summernote 富文本編輯器
     */
    @GetMapping("/summernote")
    public String summernote()
    {
        return prefix + "/summernote";
    }

    /**
     * 搜索自動補全
     */
    @GetMapping("/autocomplete")
    public String autocomplete()
    {
        return prefix + "/autocomplete";
    }

    /**
     * 多級同步下拉
     */
    @GetMapping("/cxselect")
    public String cxselect(ModelMap mmap)
    {
        CxSelect cxSelectTB = new CxSelect();
        cxSelectTB.setN("淘寶");
        cxSelectTB.setV("taobao");
        CxSelect cxSelectTm = new CxSelect();
        cxSelectTm.setN("天貓");
        cxSelectTm.setV("tm");
        CxSelect cxSelectJhs = new CxSelect();
        cxSelectJhs.setN("聚划算");
        cxSelectJhs.setV("jhs");
        List<CxSelect> tmList = new ArrayList<CxSelect>();
        tmList.add(cxSelectTm);
        tmList.add(cxSelectJhs);
        cxSelectTB.setS(tmList);

        CxSelect cxSelectJD = new CxSelect();
        cxSelectJD.setN("京東");
        cxSelectJD.setV("jd");
        CxSelect cxSelectCs = new CxSelect();
        cxSelectCs.setN("京東超市");
        cxSelectCs.setV("jdcs");
        CxSelect cxSelectSx = new CxSelect();
        cxSelectSx.setN("京東生鮮");
        cxSelectSx.setV("jdsx");
        List<CxSelect> jdList = new ArrayList<CxSelect>();
        jdList.add(cxSelectCs);
        jdList.add(cxSelectSx);
        cxSelectJD.setS(jdList);

        List<CxSelect> cxList = new ArrayList<CxSelect>();
        cxList.add(cxSelectTB);
        cxList.add(cxSelectJD);

        mmap.put("data", JSON.toJSON(cxList));
        return prefix + "/cxselect";
    }

    /**
     * 局部刷新
     */
    @GetMapping("/localrefresh")
    public String localRefresh(ModelMap mmap)
    {
        JSONArray list = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("name", "這條任務數據是由ModelMap傳遞到頁面的，點擊添加按鈕後會將這條數據替換為新數據");
        item.put("type", "默認");
        item.put("date", "2020.06.10");
        list.add(item);
        mmap.put("tasks", list);
        mmap.put("min", 2);
        mmap.put("max", 10);
        return prefix + "/localrefresh";
    }

    /**
     * 局部刷新-添加任務
     * 
     * @param fragment 頁面中的模板名稱
     * @param taskName 任務名稱
     */
    @PostMapping("/localrefresh/task")
    public String localRefreshTask(String fragment, String taskName, ModelMap mmap)
    {
        JSONArray list = new JSONArray();
        JSONObject item = new JSONObject();
        item.put("name", StringUtils.defaultIfBlank(taskName, "透過電話銷售過程中了解各盛市的設備儀器使用、採購情況及相關重要追蹤人"));
        item.put("type", "新增");
        item.put("date", "2018.06.10");
        list.add(item);
        item = new JSONObject();
        item.put("name", "提高自己電話行銷技巧，靈活專業地與客戶進行電話交流");
        item.put("type", "新增");
        item.put("date", "2018.06.12");
        list.add(item);
        mmap.put("tasks", list);
        return prefix + "/localrefresh::" + fragment;
    }

    /**
     * 模擬數據
     */
    @GetMapping("/cityData")
    @ResponseBody
    public String cityData()
    {
        String data = "[{\"n\":\"湖南省\",\"s\":[{\"n\":\"長沙市\",\"s\":[{\"n\":\"芙蓉區\"},{\"n\":\"天心區\"},{\"n\":\"嶽麓區\"},{\"n\":\"開福區\"},{\"n\":\"雨花區\"},{\"n\":\"望城區\"},{\"n\":\"長沙縣\"},{\"n\":\"寧鄉縣\"},{\"n\":\"瀏陽市\"}]},{\"n\":\"株洲市\",\"s\":[{\"n\":\"荷塘區\"},{\"n\":\"蘆淞區\"},{\"n\":\"石峰區\"},{\"n\":\"天元區\"},{\"n\":\"株洲縣\"},{\"n\":\"攸縣\"},{\"n\":\"茶陵縣\"},{\"n\":\"炎陵縣\"},{\"n\":\"醴陵市\"}]},{\"n\":\"湘潭市\",\"s\":[{\"n\":\"雨湖區\"},{\"n\":\"岳塘區\"},{\"n\":\"湘潭縣\"},{\"n\":\"湘鄉市\"},{\"n\":\"韶山市\"}]},{\"n\":\"衡陽市\",\"s\":[{\"n\":\"珠暉區\"},{\"n\":\"雁峰區\"},{\"n\":\"石鼓區\"},{\"n\":\"蒸湘區\"},{\"n\":\"南嶽區\"},{\"n\":\"衡陽縣\"},{\"n\":\"衡南縣\"},{\"n\":\"衡山縣\"},{\"n\":\"衡東縣\"},{\"n\":\"祁東縣\"},{\"n\":\"耒陽市\"},{\"n\":\"常寧市\"}]},{\"n\":\"邵陽市\",\"s\":[{\"n\":\"雙清區\"},{\"n\":\"大祥區\"},{\"n\":\"北塔區\"},{\"n\":\"邵東縣\"},{\"n\":\"新邵縣\"},{\"n\":\"邵陽縣\"},{\"n\":\"隆回縣\"},{\"n\":\"洞口縣\"},{\"n\":\"綏寧縣\"},{\"n\":\"新寧縣\"},{\"n\":\"城步苗族自治縣\"},{\"n\":\"武岡市\"}]},{\"n\":\"岳陽市\",\"s\":[{\"n\":\"岳陽樓區\"},{\"n\":\"雲溪區\"},{\"n\":\"君山區\"},{\"n\":\"岳陽縣\"},{\"n\":\"華容縣\"},{\"n\":\"湘陰縣\"},{\"n\":\"平江縣\"},{\"n\":\"汨羅市\"},{\"n\":\"臨湘市\"}]},{\"n\":\"常德市\",\"s\":[{\"n\":\"武陵區\"},{\"n\":\"鼎城區\"},{\"n\":\"安鄉縣\"},{\"n\":\"漢壽縣\"},{\"n\":\"澧縣\"},{\"n\":\"臨澧縣\"},{\"n\":\"桃源縣\"},{\"n\":\"石門縣\"},{\"n\":\"津市市\"}]},{\"n\":\"張家界市\",\"s\":[{\"n\":\"永定區\"},{\"n\":\"武陵源區\"},{\"n\":\"慈利縣\"},{\"n\":\"桑植縣\"}]},{\"n\":\"益陽市\",\"s\":[{\"n\":\"資陽區\"},{\"n\":\"赫山區\"},{\"n\":\"南縣\"},{\"n\":\"桃江縣\"},{\"n\":\"安化縣\"},{\"n\":\"沅江市\"}]},{\"n\":\"郴州市\",\"s\":[{\"n\":\"北湖區\"},{\"n\":\"蘇仙區\"},{\"n\":\"桂陽縣\"},{\"n\":\"宜章縣\"},{\"n\":\"永興縣\"},{\"n\":\"嘉禾縣\"},{\"n\":\"臨武縣\"},{\"n\":\"汝城縣\"},{\"n\":\"桂東縣\"},{\"n\":\"安仁縣\"},{\"n\":\"資興市\"}]},{\"n\":\"永州市\",\"s\":[{\"n\":\"零陵區\"},{\"n\":\"冷水灘區\"},{\"n\":\"祁陽縣\"},{\"n\":\"東安縣\"},{\"n\":\"雙牌縣\"},{\"n\":\"道縣\"},{\"n\":\"江永縣\"},{\"n\":\"寧遠縣\"},{\"n\":\"藍山縣\"},{\"n\":\"新田縣\"},{\"n\":\"江華瑤族自治縣\"}]},{\"n\":\"懷化市\",\"s\":[{\"n\":\"鶴城區\"},{\"n\":\"中方縣\"},{\"n\":\"沅陵縣\"},{\"n\":\"辰谿縣\"},{\"n\":\"漵浦縣\"},{\"n\":\"會同縣\"},{\"n\":\"麻陽苗族自治縣\"},{\"n\":\"新晃侗族自治縣\"},{\"n\":\"芷江侗族自治縣\"},{\"n\":\"靖州苗族侗族自治縣\"},{\"n\":\"通道侗族自治縣\"},{\"n\":\"洪江市\"}]},{\"n\":\"婁底市\",\"s\":[{\"n\":\"婁星區\"},{\"n\":\"雙峰縣\"},{\"n\":\"新化縣\"},{\"n\":\"冷水江市\"},{\"n\":\"漣源市\"}]},{\"n\":\"湘西土家族苗族自治州\",\"s\":[{\"n\":\"吉首市\"},{\"n\":\"瀘溪縣\"},{\"n\":\"鳳凰縣\"},{\"n\":\"花垣縣\"},{\"n\":\"保靖縣\"},{\"n\":\"古丈縣\"},{\"n\":\"永順縣\"},{\"n\":\"龍山縣\"}]}]},{\"n\":\"廣東省\",\"s\":[{\"n\":\"廣州市\",\"s\":[{\"n\":\"荔灣區\"},{\"n\":\"越秀區\"},{\"n\":\"海珠區\"},{\"n\":\"天河區\"},{\"n\":\"白雲區\"},{\"n\":\"黃埔區\"},{\"n\":\"番禺區\"},{\"n\":\"花都區\"},{\"n\":\"南沙區\"},{\"n\":\"蘿崗區\"},{\"n\":\"增城市\"},{\"n\":\"從化市\"}]},{\"n\":\"韶關市\",\"s\":[{\"n\":\"武江區\"},{\"n\":\"湞江區\"},{\"n\":\"曲江區\"},{\"n\":\"始興縣\"},{\"n\":\"仁化縣\"},{\"n\":\"翁源縣\"},{\"n\":\"乳源瑤族自治縣\"},{\"n\":\"新豐縣\"},{\"n\":\"樂昌市\"},{\"n\":\"南雄市\"}]},{\"n\":\"深圳市\",\"s\":[{\"n\":\"羅湖區\"},{\"n\":\"福田區\"},{\"n\":\"南山區\"},{\"n\":\"寶安區\"},{\"n\":\"龍崗區\"},{\"n\":\"鹽田區\"}]},{\"n\":\"珠海市\",\"s\":[{\"n\":\"香洲區\"},{\"n\":\"斗門區\"},{\"n\":\"金灣區\"}]},{\"n\":\"汕頭市\",\"s\":[{\"n\":\"龍湖區\"},{\"n\":\"金平區\"},{\"n\":\"濠江區\"},{\"n\":\"潮陽區\"},{\"n\":\"潮南區\"},{\"n\":\"澄海區\"},{\"n\":\"南澳縣\"}]},{\"n\":\"佛山市\",\"s\":[{\"n\":\"禪城區\"},{\"n\":\"南海區\"},{\"n\":\"順德區\"},{\"n\":\"三水區\"},{\"n\":\"高明區\"}]},{\"n\":\"江門市\",\"s\":[{\"n\":\"蓬江區\"},{\"n\":\"江海區\"},{\"n\":\"新會區\"},{\"n\":\"台山市\"},{\"n\":\"開平市\"},{\"n\":\"鶴山市\"},{\"n\":\"恩平市\"}]},{\"n\":\"湛江市\",\"s\":[{\"n\":\"赤坎區\"},{\"n\":\"霞山區\"},{\"n\":\"坡頭區\"},{\"n\":\"麻章區\"},{\"n\":\"遂溪縣\"},{\"n\":\"徐聞縣\"},{\"n\":\"廉江市\"},{\"n\":\"雷州市\"},{\"n\":\"吳川市\"}]},{\"n\":\"茂名市\",\"s\":[{\"n\":\"茂南區\"},{\"n\":\"茂港區\"},{\"n\":\"電白縣\"},{\"n\":\"高州市\"},{\"n\":\"化州市\"},{\"n\":\"信宜市\"}]},{\"n\":\"肇慶市\",\"s\":[{\"n\":\"端州區\"},{\"n\":\"鼎湖區\"},{\"n\":\"廣寧縣\"},{\"n\":\"懷集縣\"},{\"n\":\"封開縣\"},{\"n\":\"德慶縣\"},{\"n\":\"高要市\"},{\"n\":\"四會市\"}]},{\"n\":\"惠州市\",\"s\":[{\"n\":\"惠城區\"},{\"n\":\"惠陽區\"},{\"n\":\"博羅縣\"},{\"n\":\"惠東縣\"},{\"n\":\"龍門縣\"}]},{\"n\":\"梅州市\",\"s\":[{\"n\":\"梅江區\"},{\"n\":\"梅縣\"},{\"n\":\"大埔縣\"},{\"n\":\"豐順縣\"},{\"n\":\"五華縣\"},{\"n\":\"平遠縣\"},{\"n\":\"蕉嶺縣\"},{\"n\":\"興寧市\"}]},{\"n\":\"汕尾市\",\"s\":[{\"n\":\"城區\"},{\"n\":\"海豐縣\"},{\"n\":\"陸河縣\"},{\"n\":\"陸豐市\"}]},{\"n\":\"河源市\",\"s\":[{\"n\":\"源城區\"},{\"n\":\"紫金縣\"},{\"n\":\"龍川縣\"},{\"n\":\"連平縣\"},{\"n\":\"和平縣\"},{\"n\":\"東源縣\"}]},{\"n\":\"陽江市\",\"s\":[{\"n\":\"江城區\"},{\"n\":\"陽西縣\"},{\"n\":\"陽東縣\"},{\"n\":\"陽春市\"}]},{\"n\":\"清遠市\",\"s\":[{\"n\":\"清城區\"},{\"n\":\"清新區\"},{\"n\":\"佛岡縣\"},{\"n\":\"陽山縣\"},{\"n\":\"連山壯族瑤族自治縣\"},{\"n\":\"連南瑤族自治縣\"},{\"n\":\"英德市\"},{\"n\":\"連州市\"}]},{\"n\":\"東莞市\"},{\"n\":\"中山市\"},{\"n\":\"潮州市\",\"s\":[{\"n\":\"湘橋區\"},{\"n\":\"潮安區\"},{\"n\":\"饒平縣\"}]},{\"n\":\"揭陽市\",\"s\":[{\"n\":\"榕城區\"},{\"n\":\"揭東區\"},{\"n\":\"揭西縣\"},{\"n\":\"惠來縣\"},{\"n\":\"普寧市\"}]},{\"n\":\"雲浮市\",\"s\":[{\"n\":\"雲城區\"},{\"n\":\"新興縣\"},{\"n\":\"鬱南縣\"},{\"n\":\"雲安縣\"},{\"n\":\"羅定市\"}]}]}]";
        return data;
    }

    /**
     * 獲取用戶數據
     */
    @GetMapping("/userModel")
    @ResponseBody
    public AjaxResult userModel()
    {
        AjaxResult ajax = new AjaxResult();

        ajax.put("code", 200);
        ajax.put("value", users);
        return ajax;
    }

    /**
     * 獲取數據集合
     */
    @GetMapping("/collection")
    @ResponseBody
    public AjaxResult collection()
    {
        String[] array = { "ruoyi 1", "ruoyi 2", "ruoyi 3", "ruoyi 4", "ruoyi 5" };
        AjaxResult ajax = new AjaxResult();
        ajax.put("value", array);
        return ajax;
    }
}

class UserFormModel
{
    /** 用戶ID */
    private int userId;

    /** 用戶編號 */
    private String userCode;

    /** 用戶姓名 */
    private String userName;

    /** 用戶手機 */
    private String userPhone;

    public UserFormModel()
    {

    }

    public UserFormModel(int userId, String userCode, String userName, String userPhone)
    {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUserCode()
    {
        return userCode;
    }

    public void setUserCode(String userCode)
    {
        this.userCode = userCode;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserPhone()
    {
        return userPhone;
    }

    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
    }

}
