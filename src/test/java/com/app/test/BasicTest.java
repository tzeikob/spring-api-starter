package com.app.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextConfig.class, loader = AnnotationConfigContextLoader.class)
public class BasicTest {

    @Autowired
    private String text;

    @Test
    public void assertText() {
        assertEquals("Hello World", text);
    }
}
