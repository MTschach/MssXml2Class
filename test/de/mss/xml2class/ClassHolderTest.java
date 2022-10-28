package de.mss.xml2class;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClassHolderTest {

   private ClassHolder classUnderTest;


   private ConstantHolder getConstantHolder(int i) {
      final ConstantHolder c = new ConstantHolder();
      c.setName("constant" + i);
      c.setType("String");
      c.setValue("constant" + i);
      return c;
   }


   private VariableHolder getVariableHolder(int i) {
      final VariableHolder c = new VariableHolder();
      c.setName("variable" + i);
      c.setType("String");
      c.setValue("variable" + i);
      return c;
   }


   @BeforeEach
   public void setUp() throws Exception {
      this.classUnderTest = new ClassHolder();
      this.classUnderTest.setComment("a comment");
      this.classUnderTest.setName("TestClass");
      this.classUnderTest.setPackageName("de.mss.xml2class.test");
      this.classUnderTest.setVersion("123");
   }


   @Test
   public void testExtendedClass() {
      this.classUnderTest.setExtendsFrom("MyBaseClass");
      this.classUnderTest.setImplementsFrom("MyInterfaceClass");
      this.classUnderTest.setVersion(null);
      assertEquals(" extends MyBaseClass", this.classUnderTest.getExtendsFrom());
      assertEquals(" implements MyInterfaceClass", this.classUnderTest.getImplementsFrom());
      assertEquals("", this.classUnderTest.getVersion());
   }


   @Test
   public void testMembers() {
      this.classUnderTest.setConstants(null);
      this.classUnderTest.setVariables(null);
      this.classUnderTest.setSpecialMethods(null);

      this.classUnderTest.addConstant(getConstantHolder(1));
      this.classUnderTest.addConstant(null);
      this.classUnderTest.addConstant(getConstantHolder(2));

      this.classUnderTest.addVariable(getVariableHolder(1));
      this.classUnderTest.addVariable(null);
      this.classUnderTest.addVariable(getVariableHolder(2));

      assertEquals(Integer.valueOf(2), Integer.valueOf(this.classUnderTest.getConstants().size()));
      assertEquals(Integer.valueOf(2), Integer.valueOf(this.classUnderTest.getVariables().size()));
   }


   @Test
   public void testOk() {
      assertEquals("a comment", this.classUnderTest.getComment());
      assertEquals("TestClass", this.classUnderTest.getName());
      assertEquals("de.mss.xml2class.test", this.classUnderTest.getPackageName());
      assertEquals("", this.classUnderTest.getExtendsFrom());
      assertEquals("", this.classUnderTest.getImplementsFrom());
      assertEquals("private static final long serialVersionUID = 123l;", this.classUnderTest.getVersion());
      assertNotNull(this.classUnderTest.getConstants());
      assertNotNull(this.classUnderTest.getVariables());
      assertNotNull(this.classUnderTest.getSpecialMethods());
      assertFalse(this.classUnderTest.hasRequiredFields());
      assertFalse(this.classUnderTest.hasBaseClass());
      assertFalse(this.classUnderTest.hasInterface());
   }
}
