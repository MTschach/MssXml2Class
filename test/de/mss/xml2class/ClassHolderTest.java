package de.mss.xml2class;

import org.junit.Test;

import junit.framework.TestCase;

public class ClassHolderTest extends TestCase {

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


   @Override
   public void setUp() throws Exception {
      super.setUp();

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
      assertEquals("Extends From", " extends MyBaseClass", this.classUnderTest.getExtendsFrom());
      assertEquals("Implements", " implements MyInterfaceClass", this.classUnderTest.getImplementsFrom());
      assertEquals("Version", "", this.classUnderTest.getVersion());
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
      assertEquals("comment", "a comment", this.classUnderTest.getComment());
      assertEquals("ClassName", "TestClass", this.classUnderTest.getName());
      assertEquals("package", "de.mss.xml2class.test", this.classUnderTest.getPackageName());
      assertEquals("Extends From", "", this.classUnderTest.getExtendsFrom());
      assertEquals("Implements", "", this.classUnderTest.getImplementsFrom());
      assertEquals("Version", "private static final long serialVersionUID = 123l;", this.classUnderTest.getVersion());
      assertNotNull("constants", this.classUnderTest.getConstants());
      assertNotNull("variables", this.classUnderTest.getVariables());
      assertNotNull("specialMethods", this.classUnderTest.getSpecialMethods());
      assertFalse("hasRequiredField", this.classUnderTest.hasRequiredFields());
      assertFalse("hasBaseClass", this.classUnderTest.hasBaseClass());
      assertFalse("hasInterface", this.classUnderTest.hasInterface());
      assertEquals("HashCode", 1993134103, this.classUnderTest.hashCode());
   }
}
