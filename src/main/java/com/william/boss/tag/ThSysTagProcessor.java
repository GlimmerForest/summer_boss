package com.william.boss.tag;

import lombok.EqualsAndHashCode;
import org.springframework.util.Assert;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * 自定义标签
 * @author john
 */
@EqualsAndHashCode(callSuper = false)
public class ThSysTagProcessor extends AbstractAttributeTagProcessor {
    private static final String DEMO_TAG  = "demo";

    /**
     templateMode: 模板模式，这里使用HTML模板。
     dialectPrefix: 标签前缀。即xxx:text中的xxx。
     elementName：匹配标签元素名。举例来说如果是div，则我们的自定义标签只能用在div标签中。为null能够匹配所有的标签。
     prefixElementName: 标签名是否要求前缀。
     attributeName: 自定义标签属性名。这里为text。
     prefixAttributeName：属性名是否要求前缀，如果为true，Thymeleaf会要求使用text属性时必须加上前缀，即xxx:text。
     precedence：标签处理的优先级，此处使用和Thymeleaf标准方言相同的优先级。
     removeAttribute：标签处理后是否移除自定义属性。
     */
    public ThSysTagProcessor(String dialectPrefix) {
        super(TemplateMode.HTML,  dialectPrefix, null, false, DEMO_TAG, true, StandardDialect.PROCESSOR_PRECEDENCE,true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        final IEngineConfiguration configuration = context.getConfiguration();
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = parser.parseExpression(context, attributeValue);
        // 获取未处理的数据
        final String title = (String) expression.execute(context);

        Assert.isTrue(title != null, "数据格式不正确");
        String[] strArr = title.split(",");

        Assert.isTrue(strArr.length > 1, "数据格式不正确");
        String result = strArr[0] + "," + strArr[1] + "," + strArr[0] + "," + strArr[1];

        structureHandler.setBody(result, false);
    }
}