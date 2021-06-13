package com.example.serialization;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(properties = "camunda.bpm.generate-unique-process-engine-name=true")
public class TestJacksonDataFormatConfigurator {
    @Autowired
    private RuntimeService runtimeService;

    private final Foo foo = new Foo("value", 1, null, List.of("one", "two"));

    private ProcessInstance startProcessWithVariable(String processKey) {
        ObjectValue fooVariable = Variables.objectValue(foo).create();
        VariableMap variables = Variables.createVariables().putValueTyped("foo", fooVariable);
        return runtimeService.startProcessInstanceByKey(processKey, variables);
    }

    @Test
    @Deployment(resources = "testSerialization.bpmn")
    public void testSerialization() {
        ProcessInstance processInstance = startProcessWithVariable("TestSerialization");
        Foo untypedVariable = (Foo) runtimeService.getVariable(processInstance.getId(), "foo");
        assertThat(untypedVariable).isEqualTo(foo);

        ObjectValue serializedVariable = runtimeService.getVariableTyped(processInstance.getId(), "foo");
        String expectedSerializedValue = "{\"text\":\"value\",\"number\":1,\"nullable\":null,\"items\":[\"one\",\"two\"]}";

        assertThat(serializedVariable.getValueSerialized()).isEqualTo(expectedSerializedValue);
        assertThat(serializedVariable.getValue()).isEqualTo(foo);
    }
}

