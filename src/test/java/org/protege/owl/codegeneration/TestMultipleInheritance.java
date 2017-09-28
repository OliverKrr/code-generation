package org.protege.owl.codegeneration;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

public class TestMultipleInheritance {
    /*
     * Actually the fact that it compiles is really a sufficient test in this
     * case...
     */

    @Test
    public void testMultipleInheritance() throws NoSuchMethodException, SecurityException {
        Class<?> c = org.protege.owl.codegeneration.inferred.generate04.impl.DefaultC.class;
        assertNotNull(c.getMethod("getP"));
        assertNotNull(c.getMethod("getQ"));
    }

}
