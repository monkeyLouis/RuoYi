package com.ruoyi.web.controller.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 表格相關
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/table")
public class DemoTableController extends BaseController
{
    private String prefix = "demo/table";

    private final static List<UserTableModel> users = new ArrayList<UserTableModel>();
    {
        users.add(new UserTableModel(1, "1000001", "測試1", "0", "15888888888", "ry@qq.com", 150.0, "0"));
        users.add(new UserTableModel(2, "1000002", "測試2", "1", "15666666666", "ry@qq.com", 180.0, "1"));
        users.add(new UserTableModel(3, "1000003", "測試3", "0", "15666666666", "ry@qq.com", 110.0, "1"));
        users.add(new UserTableModel(4, "1000004", "測試4", "1", "15666666666", "ry@qq.com", 220.0, "1"));
        users.add(new UserTableModel(5, "1000005", "測試5", "0", "15666666666", "ry@qq.com", 140.0, "1"));
        users.add(new UserTableModel(6, "1000006", "測試6", "1", "15666666666", "ry@qq.com", 330.0, "1"));
        users.add(new UserTableModel(7, "1000007", "測試7", "0", "15666666666", "ry@qq.com", 160.0, "1"));
        users.add(new UserTableModel(8, "1000008", "測試8", "1", "15666666666", "ry@qq.com", 170.0, "1"));
        users.add(new UserTableModel(9, "1000009", "測試9", "0", "15666666666", "ry@qq.com", 180.0, "1"));
        users.add(new UserTableModel(10, "1000010", "測試10", "0", "15666666666", "ry@qq.com", 210.0, "1"));
        users.add(new UserTableModel(11, "1000011", "測試11", "1", "15666666666", "ry@qq.com", 110.0, "1"));
        users.add(new UserTableModel(12, "1000012", "測試12", "0", "15666666666", "ry@qq.com", 120.0, "1"));
        users.add(new UserTableModel(13, "1000013", "測試13", "1", "15666666666", "ry@qq.com", 380.0, "1"));
        users.add(new UserTableModel(14, "1000014", "測試14", "0", "15666666666", "ry@qq.com", 280.0, "1"));
        users.add(new UserTableModel(15, "1000015", "測試15", "0", "15666666666", "ry@qq.com", 570.0, "1"));
        users.add(new UserTableModel(16, "1000016", "測試16", "1", "15666666666", "ry@qq.com", 260.0, "1"));
        users.add(new UserTableModel(17, "1000017", "測試17", "1", "15666666666", "ry@qq.com", 210.0, "1"));
        users.add(new UserTableModel(18, "1000018", "測試18", "1", "15666666666", "ry@qq.com", 340.0, "1"));
        users.add(new UserTableModel(19, "1000019", "測試19", "1", "15666666666", "ry@qq.com", 160.0, "1"));
        users.add(new UserTableModel(20, "1000020", "測試20", "1", "15666666666", "ry@qq.com", 220.0, "1"));
        users.add(new UserTableModel(21, "1000021", "測試21", "1", "15666666666", "ry@qq.com", 120.0, "1"));
        users.add(new UserTableModel(22, "1000022", "測試22", "1", "15666666666", "ry@qq.com", 130.0, "1"));
        users.add(new UserTableModel(23, "1000023", "測試23", "1", "15666666666", "ry@qq.com", 490.0, "1"));
        users.add(new UserTableModel(24, "1000024", "測試24", "1", "15666666666", "ry@qq.com", 570.0, "1"));
        users.add(new UserTableModel(25, "1000025", "測試25", "1", "15666666666", "ry@qq.com", 250.0, "1"));
        users.add(new UserTableModel(26, "1000026", "測試26", "1", "15666666666", "ry@qq.com", 250.0, "1"));
    }

    private final static List<AreaModel> areas = new ArrayList<AreaModel>();
    {
        areas.add(new AreaModel(1, 0, "廣東省", "440000", "GDS", "GuangDongSheng", 1));
        areas.add(new AreaModel(2, 0, "湖南省", "430000", "HNS", "HuNanSheng", 1));
        areas.add(new AreaModel(3, 0, "河南省", "410000", "HNS", "HeNanSheng", 0));
        areas.add(new AreaModel(4, 0, "湖北省", "420000", "HBS", "HuBeiSheng", 0));
        areas.add(new AreaModel(5, 0, "遼寧省", "210000", "LNS", "LiaoNingSheng", 0));
        areas.add(new AreaModel(6, 0, "山東省", "370000", "SDS", "ShanDongSheng", 0));
        areas.add(new AreaModel(7, 0, "陝西省", "610000", "SXS", "ShanXiSheng", 0));
        areas.add(new AreaModel(8, 0, "貴州省", "520000", "GZS", "GuiZhouSheng", 0));
        areas.add(new AreaModel(9,  0, "上海市", "310000", "SHS", "ShangHaiShi", 0));
        areas.add(new AreaModel(10, 0, "重慶市", "500000", "CQS", "ChongQingShi", 0));
        areas.add(new AreaModel(11, 0, "若依省", "666666", "YYS", "RuoYiSheng", 0));
        areas.add(new AreaModel(12, 0, "安徽省", "340000", "AHS", "AnHuiSheng", 0));
        areas.add(new AreaModel(13, 0, "福建省", "350000", "FJS", "FuJianSheng", 0));
        areas.add(new AreaModel(14, 0, "海南省", "460000", "HNS", "HaiNanSheng", 0));
        areas.add(new AreaModel(15, 0, "江蘇省", "320000", "JSS", "JiangSuSheng", 0));
        areas.add(new AreaModel(16, 0, "青海省", "630000", "QHS", "QingHaiSheng", 0));
        areas.add(new AreaModel(17, 0, "廣西壯族自治區", "450000", "GXZZZZQ", "GuangXiZhuangZuZiZhiQu", 0));
        areas.add(new AreaModel(18, 0, "寧夏回族自治區", "640000", "NXHZZZQ", "NingXiaHuiZuZiZhiQu", 0));
        areas.add(new AreaModel(19, 0, "內蒙古自治區", "150000", "NMGZZQ", "NeiMengGuZiZhiQu", 0));
        areas.add(new AreaModel(20, 0, "新疆維吾爾自治區", "650000", "XJWWEZZQ", "XinJiangWeiWuErZiZhiQu", 0));
        areas.add(new AreaModel(21, 0, "江西省", "360000", "JXS", "JiangXiSheng", 0));
        areas.add(new AreaModel(22, 0, "浙江省", "330000", "ZJS", "ZheJiangSheng", 0));
        areas.add(new AreaModel(23, 0, "河北省", "130000", "HBS", "HeBeiSheng", 0));
        areas.add(new AreaModel(24, 0, "天津市", "120000", "TJS", "TianJinShi", 0));
        areas.add(new AreaModel(25, 0, "山西省", "140000", "SXS", "ShanXiSheng", 0));
        areas.add(new AreaModel(26, 0, "台灣省", "710000", "TWS", "TaiWanSheng", 0));
        areas.add(new AreaModel(27, 0, "甘肅省", "620000", "GSS", "GanSuSheng", 0));
        areas.add(new AreaModel(28, 0, "四川省", "510000", "SCS", "SiChuanSheng", 0));
        areas.add(new AreaModel(29, 0, "雲南省", "530000", "YNS", "YunNanSheng", 0));
        areas.add(new AreaModel(30, 0, "北京市", "110000", "BJS", "BeiJingShi", 0));
        areas.add(new AreaModel(31, 0, "香港特別行政區", "810000", "XGTBXZQ", "XiangGangTeBieXingZhengQu", 0));
        areas.add(new AreaModel(32, 0, "澳門特別行政區", "820000", "AMTBXZQ", "AoMenTeBieXingZhengQu", 0));
        
        areas.add(new AreaModel(100, 1, "深圳市", "440300", "SZS", "ShenZhenShi", 1));
        areas.add(new AreaModel(101, 1, "廣州市", "440100", "GZS", "GuangZhouShi", 0));
        areas.add(new AreaModel(102, 1, "東莞市", "441900", "DGS", "DongGuanShi", 0));
        areas.add(new AreaModel(103, 2, "長沙市", "410005", "CSS", "ChangShaShi", 1));
        areas.add(new AreaModel(104, 2, "岳陽市", "414000", "YYS", "YueYangShi", 0));
        
        areas.add(new AreaModel(1000, 100, "龍崗區", "518172", "LGQ", "LongGangQu", 0));
        areas.add(new AreaModel(1001, 100, "南山區", "518051", "NSQ", "NanShanQu", 0));
        areas.add(new AreaModel(1002, 100, "寶安區", "518101", "BAQ", "BaoAnQu", 0));
        areas.add(new AreaModel(1003, 100, "福田區", "518081", "FTQ", "FuTianQu", 0));
        areas.add(new AreaModel(1004, 103, "天心區", "410004", "TXQ", "TianXinQu", 0));
        areas.add(new AreaModel(1005, 103, "開福區", "410008", "KFQ", "KaiFuQu", 0));
        areas.add(new AreaModel(1006, 103, "芙蓉區", "410011", "FRQ", "FuRongQu", 0));
        areas.add(new AreaModel(1007, 103, "雨花區", "410011", "YHQ", "YuHuaQu", 0));
    }

    private final static List<UserTableColumn> columns = new ArrayList<UserTableColumn>();
    {
        columns.add(new UserTableColumn("用戶ID", "userId"));
        columns.add(new UserTableColumn("用戶編號", "userCode"));
        columns.add(new UserTableColumn("用戶姓名", "userName"));
        columns.add(new UserTableColumn("用戶手機", "userPhone"));
        columns.add(new UserTableColumn("用戶信箱", "userEmail"));
        columns.add(new UserTableColumn("用戶狀態", "status"));
    }
    
    private final static List<DocumentModel> documents = new ArrayList<DocumentModel>();
    {
        documents.add(new DocumentModel(1, "247-XW·2024-D10-0001", "新聞熱線[2024]000001", "索尼射擊遊戲《Concord》停止運營，玩家將獲全額退款", "索尼宣布多人射擊遊戲《Concord》將於9月6日停止運營，玩家將獲得全額退款。遊戲總監Ryan Ellis在給玩家的信中表示，這款遊戲首次發布“並沒有像我們預期的那樣順利”。《Concord》的開發歷時8年，投資超過1.5億美元。遊戲在Steam平台的售價為40美元，採用買斷制銷售模式。據SteamDB統計，遊戲上市後的最高同時線上人數為697人。"));
        documents.add(new DocumentModel(2, "247-XW·2024-D30-0002", "新聞熱線[2024]000002", "網紅帳號被封，央媒：如此炫富毒瘤早就該拔了", "在社交平台上分享自己的生活日常，本來無可厚非。但無底線地展示物慾、宣揚拜金，取笑甚至嘲諷工薪者的煙火生活，就會遮蔽普通人的平凡質樸和堅韌奮鬥，在無形中消解芸芸眾生腳踏實地、自立自強的社會正氣。對這種助長金錢至上、刺激公眾焦慮，既汙染網路生態，又撕裂社會和諧的炫富“毒瘤”，必須堅決拔除之。在國家有關部門的部署下，近日，多個網路平台開展“不良價值導向內容專項治理”行動，對“奢靡浪費”“炫富拜金”等問題從嚴打擊，倡導理性、文明的消費觀和價值觀。"));
        documents.add(new DocumentModel(3, "CT01-XW·2024-Y-0003", "新聞熱線[2024]000003", "重慶一夫妻被騙至緬甸，家屬：兩人已被解救，預計很快能回國", "5月25日，重慶一對夫妻在前往泰國後失聯，疑被詐騙集團騙至緬甸的消息引發廣泛關注。警方已對此事立案調查，而這對夫妻的親屬則每天生活在焦急和不安之中。親屬：家都癱瘓了，事情一經曝光，迅速登上了熱搜，成為公眾熱議的話題。據了解，這對夫妻原計劃是去泰國談生意，但不幸的是，他們的泰國之行變成了一場噩夢。親屬李先生透露，4月14日，他們夫妻二人抵達泰國，不久後便疑似被人以10萬元的價格賣到緬甸，目前被困在緬甸妙瓦底的一個電信詐騙園區。"));
        documents.add(new DocumentModel(4, "CT01-XW·2024-Y-0004", "新聞熱線[2024]000004", "江濱社區聯合派出所、金霞消防站開展電動自行車安全隱患夜查活動", "近日，長沙市開福區江濱社區聯合派出所、金霞消防站深入居民社區、單位場所，以電動車自行車火災防範為重點，開展消防安全夜查行動。此次夜查緊緊圍繞老舊居民區、“三合一”場所、沿街門店、夜間經營使用場所等場所開展監督檢查，重點檢查電動自行車違規停放充電、堵塞疏散通道和安全出口，架空層違規作為電動自行車停放充電場所，電動自行車違規“進樓入戶”“飛線充電”，電動自行車擅自改裝等五大類問題。"));
        documents.add(new DocumentModel(5, "CT01-XW·2024-Y-0005", "新聞熱線[2024]000005", "《黑神話》讓海外玩家迷上“悟空”", "備受全球玩家矚目的首款國產3A遊戲《黑神話：悟空》日前正式發布。精美絕倫的東方美學世界、精彩紛呈的中國神話故事、酣暢淋漓的遊戲體驗，這款遊戲為全球玩家帶來一場視覺與文化的雙重盛宴。從“悟空”成功出海的背後，海外人士看到了中國遊戲產業的巨大進步，感受到了中國文化的多元精彩，並對下一個“悟空”的誕生及更多中國文化產品走向世界充滿期待。"));
        documents.add(new DocumentModel(6, "CT01-XW·2024-Y-0006", "新聞熱線[2024]000006", "市場狀況充滿挑戰！極星宣布裁員全球約15%的員工", "據路透社報導，極星週五以“充滿挑戰的市場狀況”為由，宣布計劃在全球範圍內裁減約450個職位。此次裁員之際，許多人都對電動汽車需求降溫表示擔憂，而且極星預計汽車業務最早將在2025年開始實現收支平衡。極星發言人表示：“作為該商業計劃的一部分，我們需要調整我們的業務和運營規模。” “這涉及減少外部支出，遺憾的是，還包括我們的員工數量。”該公司還表示，希望減少對富豪及其母公司吉利外部融資的依賴，最近還表示希望削減成本並提高電動汽車的利潤率。"));
        documents.add(new DocumentModel(7, "CT01-XW·2024-Y-0007", "新聞熱線[2024]000007", "浙江隱秘富豪涉百億非法集資案，部分資金流入新造車公司", "5月10日、11日，上海北廣投資管理有限公司（下稱“北廣投”）非法集資案在上海黃浦區人民法院一審公開開庭審理，北廣投實控人周敏、法定代表人朱江等30餘名中高階主管被控非法吸收公眾存款罪。根據財新報導，這一案件中，非法集資的資金有部分流入了兩家新能源車企——愛馳汽車、萬象汽車。同時，有多位投資人引述與經偵部門溝通時的說法稱，該案事發時未兌付金額有130餘億元，其中去往廣微控股45億元、萬象汽車63億元、愛馳汽車15億元。"));
        documents.add(new DocumentModel(8, "CT01-XW·2024-Y-0008", "新聞熱線[2024]000008", "特斯拉宣布Model Y升級：搭載HW4.0硬體，售價仍25.89萬元起", "2月1日，特斯拉官方宣布ModelY升級。外觀上，新增ModelY專屬色“快銀車漆”，並採用烈焰紅代替中國紅、星空灰代替冷光銀；性能上，ModelY全系配備全新一代自動輔助駕駛硬體(HW4.0)，通過搭載超遠距離雙目攝影機，ModelY的最遠探測距離達424公尺。由此，特斯拉全系車型均配備了自動輔助駕駛硬體HW4.0。在售價方面，特斯拉中國官網顯示，ModelY車型依然保持原價。ModelY後輪驅動版25.89萬元起、ModelY長續航版29.99萬元起、ModelY高性能版售價36.39萬元起。"));
        documents.add(new DocumentModel(9, "CT01-XW·2024-D10-0009", "新聞熱線[2024]000009", "華為手機歸來，誰最受傷？", "低迷週期下的智慧型手機市場在2023年下半年迎來了華為的回歸，這也給本就競爭激烈的市場環境帶來了更大變數。1月29日，有消息稱，華為已註冊“星耀手機”品牌商標，定位中端手機市場，但上述消息並未獲得華為方面確認。“目前星耀的相關資訊我們看到了，但是沒有獲得產品資訊以及啟動線下鋪貨的通知。對於和其他品牌的二選一問題，聽其他省份的經銷商說過，但目前（華為渠道）這邊也沒有更多動作。”一位廣東區域的華為核心經銷商對記者說。但華為手機的反撲已經開始。在多家調研機構公布的2023年四季度智慧型手機出貨數據中，華為手機的量正在明顯上升，當季增幅在35%到47%之間。不過，從全年數據來看，並未登上前五榜單。"));
        documents.add(new DocumentModel(10, "CT01-XW·2024-D10-0010", "新聞熱線[2024]000010", "瘋狂裁員的矽谷大廠：除了AI，其它都是將就", "放眼望去，近期科技企業財報形勢一片大好，裁員浪潮卻仍在不斷蔓延。國內職場動態看脈脈，那矽谷裁員情況就得看layoff.fyi了。數據顯示，2024年，103家科技企業進行了裁員，28963位員工失去了飯碗。其中，電子支付公司PayPal大筆一揮，裁掉2500人，微軟則在開年就裁掉1900人。回望2023年，Google、Meta、亞馬遜、微軟均為裁員重災區，裁員人數在一萬左右。具體而言，Google近日披露的財報指出，2023年Google解僱了12000多名員工，光是在遣散費和其他費用上就花費了21億美元。而且裁員費用還在不斷增加，2024年剛過去一個月，Google就已經花費了7億美元用來裁員。"));
        documents.add(new DocumentModel(11, "CT01-XW·2024-D30-0011", "新聞熱線[2024]000011", "國產手機品牌重新崛起背後：市場正在逐步恢復活力，競爭也越來越激烈", "2024年伊始，隨著全球經濟的逐漸復甦，手機消費市場也展現出勃勃生機。中國信通院最新數據顯示，2023年中國市場手機出貨量實現了6.5%的同比增長，其中5G手機增長勢頭更為強勁，占比高達82.8%。1月25日，國際數據公司（IDC）發布了最新手機季度跟蹤報告，揭示了中國智慧型手機市場在2023年第四季度的出貨量情況。報告顯示，該季度中國智慧型手機市場出貨量達到了約7363萬台，同比增長1.2%。這是在連續十個季度同比下降後，中國智慧型手機市場首次實現反彈。這一積極信號表明，市場正在逐步恢復活力，各大品牌之間的競爭也越來越激烈。"));
        documents.add(new DocumentModel(12, "CT01-XW·2024-D30-0012", "新聞熱線[2024]000012", "SpaceX將於1月31日向國際太空站發射天鵝號貨運飛船", "1月29日消息，美國太空探索技術公司SpaceX計劃於當地時間1月30日，利用“獵鷹9號”火箭從佛羅里達州甘迺迪太空中心發射諾斯羅普·格魯曼公司的“天鵝號”貨運飛船至國際太空站。此次任務是執行NG-20商業補給，將運送約8200多磅的物資、設備及科學實驗器材。"));
    }

    /**
     * 搜索相關
     */
    @GetMapping("/search")
    public String search()
    {
        return prefix + "/search";
    }

    /**
     * 數據匯總
     */
    @GetMapping("/footer")
    public String footer()
    {
        return prefix + "/footer";
    }

    /**
     * 組合表頭
     */
    @GetMapping("/groupHeader")
    public String groupHeader()
    {
        return prefix + "/groupHeader";
    }

    /**
     * 表格導出
     */
    @GetMapping("/export")
    public String export()
    {
        return prefix + "/export";
    }

    /**
     * 表格導出選擇列
     */
    @GetMapping("/exportSelected")
    public String exportSelected()
    {
        return prefix + "/exportSelected";
    }

    /**
     * 導出數據
     */
    @PostMapping("/exportData")
    @ResponseBody
    public AjaxResult exportSelected(UserTableModel userModel, String userIds)
    {
        List<UserTableModel> userList = new ArrayList<UserTableModel>(Arrays.asList(new UserTableModel[users.size()]));
        Collections.copy(userList, users);

        // 條件過濾
        if (StringUtils.isNotEmpty(userIds))
        {
            userList.clear();
            for (Long userId : Convert.toLongArray(userIds))
            {
                for (UserTableModel user : users)
                {
                    if (user.getUserId() == userId)
                    {
                        userList.add(user);
                    }
                }
            }
        }
        ExcelUtil<UserTableModel> util = new ExcelUtil<UserTableModel>(UserTableModel.class);
        return util.exportExcel(userList, "用戶數據");
    }

    /**
     * 翻頁記住選擇
     */
    @GetMapping("/remember")
    public String remember()
    {
        return prefix + "/remember";
    }

    /**
     * 表格保存狀態
     */
    @GetMapping("/cookie")
    public String cookie()
    {
        return prefix + "/cookie";
    }

    /**
     * 跳轉至指定頁
     */
    @GetMapping("/pageGo")
    public String pageGo()
    {
        return prefix + "/pageGo";
    }

    /**
     * 自訂查詢參數
     */
    @GetMapping("/params")
    public String params()
    {
        return prefix + "/params";
    }

    /**
     * 多表格
     */
    @GetMapping("/multi")
    public String multi()
    {
        return prefix + "/multi";
    }

    /**
     * 點擊按鈕載入表格
     */
    @GetMapping("/button")
    public String button()
    {
        return prefix + "/button";
    }

    /**
     * 直接載入表格數據
     */
    @GetMapping("/data")
    public String data(ModelMap mmap)
    {
        mmap.put("users", users);
        return prefix + "/data";
    }

    /**
     * 表格凍結列
     */
    @GetMapping("/fixedColumns")
    public String fixedColumns()
    {
        return prefix + "/fixedColumns";
    }

    /**
     * 自訂觸發事件
     */
    @GetMapping("/event")
    public String event()
    {
        return prefix + "/event";
    }

    /**
     * 表格細節視圖
     */
    @GetMapping("/detail")
    public String detail()
    {
        return prefix + "/detail";
    }

    /**
     * 表格父子視圖
     */
    @GetMapping("/child")
    public String child()
    {
        return prefix + "/child";
    }

    /**
     * 表格圖片預覽
     */
    @GetMapping("/image")
    public String image()
    {
        return prefix + "/image";
    }

    /**
     * 動態增刪改查
     */
    @GetMapping("/curd")
    public String curd()
    {
        return prefix + "/curd";
    }

    /**
     * 表格行拖拽操作
     */
    @GetMapping("/reorderRows")
    public String reorderRows()
    {
        return prefix + "/reorderRows";
    }

    /**
     * 表格列拖拽操作
     */
    @GetMapping("/reorderColumns")
    public String reorderColumns()
    {
        return prefix + "/reorderColumns";
    }

    /**
     * 表格列寬拖動
     */
    @GetMapping("/resizable")
    public String resizable()
    {
        return prefix + "/resizable";
    }

    /**
     * 表格行內編輯操作
     */
    @GetMapping("/editable")
    public String editable()
    {
        return prefix + "/editable";
    }

    /**
     * 主子表提交
     */
    @GetMapping("/subdata")
    public String subdata()
    {
        return prefix + "/subdata";
    }

    /**
     * 表格自動刷新
     */
    @GetMapping("/refresh")
    public String refresh()
    {
        return prefix + "/refresh";
    }

    /**
     * 表格列印配置
     */
    @GetMapping("/print")
    public String print()
    {
        return prefix + "/print";
    }

    /**
     * 表格標題格式化
     */
    @GetMapping("/headerStyle")
    public String headerStyle()
    {
        return prefix + "/headerStyle";
    }

    /**
     * 表格動態列
     */
    @GetMapping("/dynamicColumns")
    public String dynamicColumns()
    {
        return prefix + "/dynamicColumns";
    }

    /**
     * 表格虛擬滾動
     */
    @GetMapping("/virtualScroll")
    public String virtualScroll()
    {
        return prefix + "/virtualScroll";
    }

    /**
     * 自訂視圖分頁
     */
    @GetMapping("/customView")
    public String customView()
    {
        return prefix + "/customView";
    }

    /**
     * 全文索引
     */
    @GetMapping("/textSearch")
    public String textSearch()
    {
        return prefix + "/textSearch";
    }

    /**
     * 非同步載入表格樹
     */
    @GetMapping("/asynTree")
    public String asynTree()
    {
        return prefix + "/asynTree";
    }

    /**
     * 表格其他操作
     */
    @GetMapping("/other")
    public String other()
    {
        return prefix + "/other";
    }

    /**
     * 動態獲取列
     */
    @PostMapping("/ajaxColumns")
    @ResponseBody
    public AjaxResult ajaxColumns(UserTableColumn userColumn)
    {
        List<UserTableColumn> columnList = new ArrayList<UserTableColumn>(Arrays.asList(new UserTableColumn[columns.size()]));
        Collections.copy(columnList, columns);
        if (userColumn != null && "userBalance".equals(userColumn.getField()))
        {
            columnList.add(new UserTableColumn("用戶餘額", "userBalance"));
        }
        return AjaxResult.success(columnList);
    }

    /**
     * 查詢數據
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserTableModel userModel)
    {
        TableDataInfo rspData = new TableDataInfo();
        List<UserTableModel> userList = new ArrayList<UserTableModel>(Arrays.asList(new UserTableModel[users.size()]));
        Collections.copy(userList, users);
        // 查詢條件過濾
        if (StringUtils.isNotEmpty(userModel.getUserName()))
        {
            userList.clear();
            for (UserTableModel user : users)
            {
                if (user.getUserName().equals(userModel.getUserName()))
                {
                    userList.add(user);
                }
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (null == pageDomain.getPageNum() || null == pageDomain.getPageSize())
        {
            rspData.setRows(userList);
            rspData.setTotal(userList.size());
            return rspData;
        }
        Integer pageNum = (pageDomain.getPageNum() - 1) * 10;
        Integer pageSize = pageDomain.getPageNum() * 10;
        if (pageSize > userList.size())
        {
            pageSize = userList.size();
        }
        rspData.setRows(userList.subList(pageNum, pageSize));
        rspData.setTotal(userList.size());
        return rspData;
    }

    /**
     * 查詢全文索引數據
     */
    @PostMapping("/text/list")
    @ResponseBody
    public TableDataInfo textList(BaseEntity baseEntity)
    {
        TableDataInfo rspData = new TableDataInfo();
        List<DocumentModel> documentList = new ArrayList<DocumentModel>(Arrays.asList(new DocumentModel[documents.size()]));
        Collections.copy(documentList, documents);
        // 查詢條件過濾
        if (StringUtils.isNotEmpty(baseEntity.getSearchValue()))
        {
            documentList.clear();
            for (DocumentModel document : documents)
            {
                boolean indexFlag = false;
                if (document.getTitle().contains(baseEntity.getSearchValue()))
                {
                    indexFlag = true;
                    document.setTitle(document.getTitle().replace(baseEntity.getSearchValue(), "<font color=\"red\">" + baseEntity.getSearchValue() + "</font>"));
                }
                if (document.getContent().contains(baseEntity.getSearchValue()))
                {
                    indexFlag = true;
                    document.setContent(document.getContent().replace(baseEntity.getSearchValue(), "<font color=\"red\">" + baseEntity.getSearchValue() + "</font>"));
                }
                if (indexFlag)
                {
                    documentList.add(document);
                }
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (null == pageDomain.getPageNum() || null == pageDomain.getPageSize())
        {
            rspData.setRows(documentList);
            rspData.setTotal(documentList.size());
            return rspData;
        }
        Integer pageNum = (pageDomain.getPageNum() - 1) * 10;
        Integer pageSize = pageDomain.getPageNum() * 10;
        if (pageSize > documentList.size())
        {
            pageSize = documentList.size();
        }
        rspData.setRows(documentList.subList(pageNum, pageSize));
        rspData.setTotal(documentList.size());
        return rspData;
    }

    /**
     * 查詢樹表數據
     */
    @PostMapping("/tree/list")
    @ResponseBody
    public TableDataInfo treeList(AreaModel areaModel)
    {
        TableDataInfo rspData = new TableDataInfo();
        List<AreaModel> areaList = new ArrayList<AreaModel>(Arrays.asList(new AreaModel[areas.size()]));
        // 默認查詢條件 parentId 0
        Collections.copy(areaList, areas);
        areaList.clear();
        if (StringUtils.isNotEmpty(areaModel.getAreaName()))
        {
            for (AreaModel area : areas)
            {
                if (area.getParentId() == 0 && area.getAreaName().equals(areaModel.getAreaName()))
                {
                    areaList.add(area);
                }
            }
        }
        else
        {
            for (AreaModel area : areas)
            {
                if (area.getParentId() == 0)
                {
                    areaList.add(area);
                }
            }
        }
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = (pageDomain.getPageNum() - 1) * pageDomain.getPageSize();
        Integer pageSize = pageDomain.getPageNum() * pageDomain.getPageSize();
        if (pageSize > areaList.size())
        {
            pageSize = areaList.size();
        }
        rspData.setRows(areaList.subList(pageNum, pageSize));
        rspData.setTotal(areaList.size());
        return rspData;
    }

    /**
     * 查詢樹表子節點數據
     */
    @PostMapping("/tree/listChild")
    @ResponseBody
    public List<AreaModel> listChild(AreaModel areaModel)
    {
        List<AreaModel> areaList = new ArrayList<AreaModel>(Arrays.asList(new AreaModel[areas.size()]));
        // 查詢條件 parentId
        Collections.copy(areaList, areas);
        areaList.clear();
        if (StringUtils.isNotEmpty(areaModel.getAreaName()))
        {
            for (AreaModel area : areas)
            {
                if (area.getParentId().intValue() == areaModel.getParentId().intValue() && area.getAreaName().equals(areaModel.getAreaName()))
                {
                    areaList.add(area);
                }
            }
        }
        else
        {
            for (AreaModel area : areas)
            {
                if (area.getParentId().intValue() == areaModel.getParentId().intValue())
                {
                    areaList.add(area);
                }
            }
        }
        return areaList;
    }
}

class UserTableColumn
{
    /** 表頭 */
    private String title;
    /** 欄位 */
    private String field;

    public UserTableColumn()
    {

    }

    public UserTableColumn(String title, String field)
    {
        this.title = title;
        this.field = field;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }
}

class UserTableModel
{
    /** 用戶ID */
    private int userId;

    /** 用戶編號 */
    @Excel(name = "用戶編號", cellType = ColumnType.NUMERIC)
    private String userCode;

    /** 用戶姓名 */
    @Excel(name = "用戶姓名")
    private String userName;

    /** 用戶性別 */
    private String userSex;

    /** 用戶手機 */
    @Excel(name = "用戶手機")
    private String userPhone;

    /** 用戶信箱 */
    @Excel(name = "用戶信箱")
    private String userEmail;

    /** 用戶餘額 */
    @Excel(name = "用戶餘額", cellType = ColumnType.NUMERIC)
    private double userBalance;

    /** 用戶狀態（0正常 1停用） */
    private String status;

    /** 創建時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public UserTableModel()
    {

    }

    public UserTableModel(int userId, String userCode, String userName, String userSex, String userPhone,
            String userEmail, double userBalance, String status)
    {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.userSex = userSex;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userBalance = userBalance;
        this.status = status;
        this.createTime = DateUtils.getNowDate();
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

    public String getUserSex()
    {
        return userSex;
    }

    public void setUserSex(String userSex)
    {
        this.userSex = userSex;
    }

    public String getUserPhone()
    {
        return userPhone;
    }

    public void setUserPhone(String userPhone)
    {
        this.userPhone = userPhone;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public double getUserBalance()
    {
        return userBalance;
    }

    public void setUserBalance(double userBalance)
    {
        this.userBalance = userBalance;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
}
class AreaModel
{
    /** 編號 */
    private Long id;

    /** 父編號 */
    private Long parentId;

    /** 區域名稱 */
    private String areaName;

    /** 區域代碼 */
    private String areaCode;

    /** 名稱首字母 */
    private String simplePy;

    /** 名稱全拼 */
    private String pinYin;

    /** 是否有子節點（0無 1有） */
    private Integer isTreeLeaf = 1;

    public AreaModel()
    {

    }

    public AreaModel(int id, int parentId, String areaName, String areaCode, String simplePy, String pinYin, Integer isTreeLeaf)
    {
        this.id = Long.valueOf(id);
        this.parentId = Long.valueOf(parentId);
        this.areaName = areaName;
        this.areaCode = areaCode;
        this.simplePy = simplePy;
        this.pinYin = pinYin;
        this.isTreeLeaf = isTreeLeaf;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAreaName()
    {
        return areaName;
    }

    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public String getAreaCode()
    {
        return areaCode;
    }

    public void setAreaCode(String areaCode)
    {
        this.areaCode = areaCode;
    }

    public String getSimplePy()
    {
        return simplePy;
    }

    public void setSimplePy(String simplePy)
    {
        this.simplePy = simplePy;
    }

    public String getPinYin()
    {
        return pinYin;
    }

    public void setPinYin(String pinYin)
    {
        this.pinYin = pinYin;
    }

    public Integer getIsTreeLeaf()
    {
        return isTreeLeaf;
    }

    public void setIsTreeLeaf(Integer isTreeLeaf)
    {
        this.isTreeLeaf = isTreeLeaf;
    }
}

class DocumentModel
{
    /** 編號 */
    private int tableId;

    /** 檔號 */
    private String archiveNo;

    /** 文件編號 */
    private String docNo;

    /** 標題 */
    private String title;

    /** 內容 */
    private String content;

    public DocumentModel()
    {

    }

    public DocumentModel(int tableId, String archiveNo, String docNo, String title, String content)
    {
        this.tableId = tableId;
        this.archiveNo = archiveNo;
        this.docNo = docNo;
        this.title = title;
        this.content = content;
    }

    public int getTableId()
    {
        return tableId;
    }

    public String getArchiveNo()
    {
        return archiveNo;
    }

    public String getDocNo()
    {
        return docNo;
    }

    public String getTitle()
    {
        return title;
    }

    public String getContent()
    {
        return content;
    }

    public void setTableId(int tableId)
    {
        this.tableId = tableId;
    }

    public void setArchiveNo(String archiveNo)
    {
        this.archiveNo = archiveNo;
    }

    public void setDocNo(String docNo)
    {
        this.docNo = docNo;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
