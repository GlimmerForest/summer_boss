package com.william.boss.tag;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author john
 */
@Component
public class ThSysDialect extends AbstractProcessorDialect {
    /**
     * 定义方言名称
     */
    private static final String DIALECT_NAME = "Sys Dialect";

    public ThSysDialect() {
        //设置自定义方言与"方言处理器"优先级相同
        super(DIALECT_NAME, "self", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<>();
        processors.add(new ThSysTagProcessor(dialectPrefix));
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }
}
