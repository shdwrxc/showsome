package org.xyc.showsome.pecan.drools;

import java.io.InputStreamReader;
import java.io.Reader;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.PackageBuilder;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;

/**
 * created by wks on date: 2017/1/20
 */
public class DroolTest {

    @Test
    public void execRule() throws Exception {
        // 读写DRL文件
        Reader source = new InputStreamReader(DroolTest.class.getResourceAsStream("/rules/test.drl"));

        //添加DRL源到PackageBuilder，可以添加多个
        PackageBuilder builder = new PackageBuilder();
        builder.addPackageFromDrl(source);

        //添加package到RuleBase（部署规则集）
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackages(builder.getPackages());

        WorkingMemory workingMemory = ruleBase.newStatefulSession();

        DroolSample sample = new DroolSample();
//        sample.setStr("hello");
        sample.setI(0);
        sample.addList("hi");

        workingMemory.insert(sample);
        workingMemory.fireAllRules();
    }

    @Test
    public void execRemoteRule() throws Exception {
        //远程加载规则包
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newUrlResource("http://localhost:8080/rules/test.drl"),
                ResourceType.DRL);
        //创建知识库
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        //将规则包加载到知识库中
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        //创建Fact对象
        DroolSample sample = new DroolSample();
        //        sample.setStr("hello");
        sample.setI(0);
        sample.addList("hi");

        //创建KnowledgeSession,将Fact对象插入到WorkingMemory中
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        ksession.insert(sample);
        ksession.fireAllRules();
        ksession.dispose();
    }
}
