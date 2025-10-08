package com.zzyl.nursing.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import cn.hutool.core.util.IdcardUtil;
import com.alibaba.fastjson2.JSON;
import com.zzyl.common.ai.AIModelInvoker;
import com.zzyl.common.exception.base.BaseException;
import com.zzyl.common.utils.StringUtils;
import com.zzyl.nursing.dto.HealthAssessmentDto;
import com.zzyl.nursing.util.IDCardUtils;
import com.zzyl.nursing.vo.health.HealthReportVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.zzyl.nursing.mapper.HealthAssessmentMapper;
import com.zzyl.nursing.domain.HealthAssessment;
import com.zzyl.nursing.service.IHealthAssessmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;

/**
 健康评估Service业务层处理

 @author ruoyi
 @date 2024-10-10 */
@Service
public class HealthAssessmentServiceImpl extends ServiceImpl<HealthAssessmentMapper, HealthAssessment> implements IHealthAssessmentService {

    @Autowired
    private HealthAssessmentMapper healthAssessmentMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private AIModelInvoker aIModelInvoker;

    /**
     查询健康评估

     @param id 健康评估主键
     @return 健康评估
     */
    @Override
    public HealthAssessment selectHealthAssessmentById(Long id) {
        return getById(id);
    }

    /**
     查询健康评估列表

     @param healthAssessment 健康评估
     @return 健康评估
     */
    @Override
    public List<HealthAssessment> selectHealthAssessmentList(HealthAssessment healthAssessment) {
        return healthAssessmentMapper.selectHealthAssessmentList(healthAssessment);
    }

    /**
     新增健康评估

     @param healthAssessmentDto 健康评估
     @return 结果
     */
    @Override
    public Long insertHealthAssessment(HealthAssessmentDto healthAssessmentDto) {

        String prompt = getPrompt(healthAssessmentDto);

        //调用大模型接口
        String result = aIModelInvoker.qianfanInvoker(prompt);

        if (StringUtils.isEmpty(result)) {
            throw new BaseException("AI分析失败");
        }

        //将result 转化为java 对象
        // 将 JSONObject 转换为 HealthReportVo 对象
        HealthReportVo healthReportVo = JSON.parseObject(result, HealthReportVo.class);

        HealthAssessment healthAssessment = new HealthAssessment();
        BeanUtils.copyProperties(healthAssessmentDto, healthAssessment);

        healthAssessment.setBirthDate(IDCardUtils.getBirthDateByIdCard(healthAssessmentDto.getIdCard()));
        healthAssessment.setAge(IDCardUtils.getAgeByIdCard(healthAssessmentDto.getIdCard()));

        healthAssessment.setGender(IDCardUtils.getGenderFromIdCard(healthAssessmentDto.getIdCard()));

        healthAssessment.setHealthScore(String.valueOf(healthReportVo.getHealthAssessment().getHealthIndex()));
        healthAssessment.setRiskLevel(healthReportVo.getHealthAssessment().getRiskLevel());

        healthAssessment.setSuggestionForAdmission((healthReportVo.getHealthAssessment().getHealthIndex() > 60.0) ? 0 : 1);

        healthAssessment.setNursingLevelName(getNursingLevelName(healthReportVo.getHealthAssessment().getHealthIndex()));

        healthAssessment.setAdmissionStatus(1);

        healthAssessment.setTotalCheckDate(healthReportVo.getTotalCheckDate());

        healthAssessment.setAssessmentTime(LocalDateTime.now());

        healthAssessment.setReportSummary(healthReportVo.getSummarize());

        healthAssessment.setDiseaseRisk(JSON.toJSONString(healthReportVo.getRiskDistribution()));

        healthAssessment.setAbnormalAnalysis(JSON.toJSONString(healthReportVo.getAbnormalData()));

        healthAssessment.setSystemScore(JSON.toJSONString(healthReportVo.getSystemScore()));

        save(healthAssessment);

        return healthAssessment.getId();
    }
    private String getNursingLevelName(double healthScore) {
        // 处理边界
        if (healthScore < 0 || healthScore > 100) {
            throw new IllegalArgumentException("健康评分必须在0到100之间");
        }

        if(healthScore >= 60 && healthScore < 70) {
            return "三级护理等级";
        }else if (healthScore >= 70 && healthScore < 80) {
            return "二级护理等级";
        }else if (healthScore >= 80 && healthScore < 90) {
            return "一级护理等级";
        }else if (healthScore >= 90) {
            return "特级护理等级";
        }
        return "无";

    }

    private String getPrompt(HealthAssessmentDto healthAssessmentDto) {
        // 从redis中读取信息
        String healthReport = (String) redisTemplate.opsForHash().get("healthReport", healthAssessmentDto.getIdCard());

        String prompt = "请以一个专业医生的视角来分析这份体检报告，报告中包含了一些异常数据，我需要您对这些数据进行解读，并给出相应的健康建议。\n" +
                "体检内容如下：\n" +
                healthReport + "\n" +
                "\n" +
                "要求：\n" +
                "1. 提取体检报告中的“总检日期”；\n" +
                "2. 通过临床医学、疾病风险评估模型和数据智能分析，给该用户的风险等级和健康指数给出结果。风险等级分为：健康、提示、风险、危险、严重危险。健康指数范围为0至100分；\n" +
                "3. 根据用户身体各项指标数据，详细说明该用户各项风险等级的占比是多少，最多保留两位小数。结论格式：该用户健康占比20.00%，提示占比20.00%，风险占比20%，危险占比20%，严重危险占比20%；\n" +
                "4. 对于体检报告有异常数据，请列出（异常数据的结论、体检项目名称、检查结果、参考值、单位、异常解读、建议）这8字段。解读异常数据，解决这些数据可能代表的健康问题或风险。分析可能的原因，包括但不限于生活习惯、饮食习惯、遗传因素等。基于这些异常数据和可能的原因，请给出具体的健康建议，包括饮食调整、运动建议、生活方式改变以及是否需要进一步检查或治疗等。\n" +
                "结论格式：异常数据的结论：肥胖，体检项目名称：体重指数BMI，检查结果：29.2，参考值>24，单位：-。异常解读：体重超标包括超重与肥胖。体重指数（BMI）=体重（kg）/身⾼（m）的平⽅，BMI≥24为超重，BMI≥28为肥胖；男性腰围≥90cm和⼥性腰围≥85cm为腹型肥胖。体重超标是⼀种由多因素（如遗传、进⻝油脂较多、运动少、疾病等）引起的慢性代谢性疾病，尤其是肥胖，已经被世界卫⽣组织列为导致疾病负担的⼗⼤危险因素之⼀。AI建议：采取综合措施预防和控制体重，积极改变⽣活⽅式，宜低脂、低糖、⾼纤维素膳⻝，多⻝果蔬及菌藻类⻝物，增加有氧运动。若有相关疾病（如⾎脂异常、⾼⾎压、糖尿病等）应积极治疗。\n" +
                "5. 根据这个体检报告的内容，分别是给人体的8大系统打分，每项满分为100分，8大系统分别为：呼吸系统、消化系统、内分泌系统、免疫系统、循环系统、泌尿系统、运动系统、感官系统\n" +
                "6. 给体检报告做一个总结，总结格式：体检报告中尿蛋⽩、癌胚抗原、⾎沉、空腹⾎糖、总胆固醇、⽢油三酯、低密度脂蛋⽩胆固醇、⾎清载脂蛋⽩B、动脉硬化指数、⽩细胞、平均红细胞体积、平均⾎红蛋⽩共12项指标提示异常，尿液常规共1项指标处于临界值，⾎脂、⾎液常规、尿液常规、糖类抗原、⾎清酶类等共43项指标提示正常，综合这些临床指标和数据分析：肾脏、肝胆、⼼脑⾎管存在隐患，其中⼼脑⾎管有“⾼危”⻛险；肾脏部位有“中危”⻛险；肝胆部位有“低危”⻛险。\n" +
                "\n" +
                "输出要求：\n" +
                "最后，将以上结果输出为JSON格式，不要包含其他的文字说明，所有的返回结果都是json，详细格式如下：\n" +
                "\n" +
                "{\n" +
                "  \"totalCheckDate\": \"YYYY-MM-DD\",\n" +
                "  \"healthAssessment\": {\n" +
                "    \"riskLevel\": \"healthy/caution/risk/danger/severeDanger\",\n" +
                "    \"healthIndex\": XX.XX\n" +
                "  },\n" +
                "  \"riskDistribution\": {\n" +
                "    \"healthy\": XX.XX,\n" +
                "    \"caution\": XX.XX,\n" +
                "    \"risk\": XX.XX,\n" +
                "    \"danger\": XX.XX,\n" +
                "    \"severeDanger\": XX.XX\n" +
                "  },\n" +
                "  \"abnormalData\": [\n" +
                "    {\n" +
                "      \"conclusion\": \"异常数据的结论\",\n" +
                "      \"examinationItem\": \"体检项目名称\",\n" +
                "      \"result\": \"检查结果\",\n" +
                "      \"referenceValue\": \"参考值\",\n" +
                "      \"unit\": \"单位\",\n" +
                "      \"interpret\":\"对于异常的结论进一步详细的说明\",\n" +
                "      \"advice\":\"针对于这一项的异常，给出一些健康的建议\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"systemScore\": {\n" +
                "    \"breathingSystem\": XX,\n" +
                "    \"digestiveSystem\": XX,\n" +
                "    \"endocrineSystem\": XX,\n" +
                "    \"immuneSystem\": XX,\n" +
                "    \"circulatorySystem\": XX,\n" +
                "    \"urinarySystem\": XX,\n" +
                "    \"motionSystem\": XX,\n" +
                "    \"senseSystem\": XX\n" +
                "  },\n" +
                "  \"summarize\": \"体检报告的总结\"\n" +
                "}";
        return prompt;
    }

    /**
     修改健康评估

     @param healthAssessment 健康评估
     @return 结果
     */
    @Override
    public int updateHealthAssessment(HealthAssessment healthAssessment) {
        return updateById(healthAssessment) ? 1 : 0;
    }

    /**
     批量删除健康评估

     @param ids 需要删除的健康评估主键
     @return 结果
     */
    @Override
    public int deleteHealthAssessmentByIds(Long[] ids) {
        return removeByIds(Arrays.asList(ids)) ? 1 : 0;
    }

    /**
     删除健康评估信息

     @param id 健康评估主键
     @return 结果
     */
    @Override
    public int deleteHealthAssessmentById(Long id) {
        return removeById(id) ? 1 : 0;
    }
}
